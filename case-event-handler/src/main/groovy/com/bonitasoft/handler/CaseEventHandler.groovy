package com.bonitasoft.handler;

import org.bonitasoft.engine.api.ProcessAPI
import org.bonitasoft.engine.api.impl.ProcessAPIImpl
import org.bonitasoft.engine.bpm.data.DataDefinition
import org.bonitasoft.engine.bpm.flownode.ActivityDefinitionNotFoundException
import org.bonitasoft.engine.bpm.flownode.ActivityInstanceSearchDescriptor
import org.bonitasoft.engine.bpm.flownode.ActivityStates
import org.bonitasoft.engine.bpm.flownode.HumanTaskInstance
import org.bonitasoft.engine.core.process.instance.model.SUserTaskInstance
import org.bonitasoft.engine.events.model.SEvent
import org.bonitasoft.engine.events.model.SHandler
import org.bonitasoft.engine.events.model.SHandlerExecutionException
import org.bonitasoft.engine.events.model.SUpdateEvent
import org.bonitasoft.engine.expression.ExpressionType
import org.bonitasoft.engine.search.SearchOptionsBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class CaseEventHandler implements SHandler<SEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaseEventHandler.class)
	private static final String STATE_UPDATED = "ACTIVITYINSTANCE_STATE_UPDATED"
	private static final String ACTIVITY_CONTAINER = "Dymanic Activity Container"
	private static final String CREATE_ACTIVITY = "Create Activity"
	private String prefix
	private List<String> ignoredActivities = []

	public CaseEventHandler(String prefix, List<String> ignoredActivities){
		this.prefix = prefix
		this.ignoredActivities = ignoredActivities
	}

	@Override
	public void execute(SEvent event) throws SHandlerExecutionException {
		def SUserTaskInstance taskInstance =  event.getObject()
		def processInstanceId = taskInstance.parentProcessInstanceId
		ProcessAPI processAPI = new ProcessAPIImpl()
		def builder = new SearchOptionsBuilder(0, Integer.MAX_VALUE)
				.filter(ActivityInstanceSearchDescriptor.PROCESS_INSTANCE_ID, processInstanceId)
				.and()
				.filter(ActivityInstanceSearchDescriptor.STATE_NAME, ActivityStates.READY_STATE)
		ignoredActivities.each{ builder.and().differentFrom(ActivityInstanceSearchDescriptor.NAME, it)}
		def caseActivities = processAPI.searchHumanTaskInstances(builder.done()).getResult()
		def pDefId = processAPI.getProcessDefinitionIdFromProcessInstanceId(processInstanceId)
		caseActivities.each{ HumanTaskInstance task ->
			try {
			processAPI.getActivityDataDefinitions(task.processDefinitionId, task.name, 0, Integer.MAX_VALUE)
					.each{ DataDefinition dataDef ->
						if( dataDef.isTransientData()
						&& dataDef.name.startsWith(prefix)
						&& dataDef.defaultValueExpression != null
						&& dataDef.defaultValueExpression.expressionType != ExpressionType.TYPE_CONSTANT.toString()
						&& dataDef.defaultValueExpression.content != null
						&& !dataDef.defaultValueExpression.content.isEmpty()) {
							def dataInstance = processAPI.getActivityTransientDataInstance(dataDef.name, task.id)
							def deps = [:]
							def expressions = [:]
							expressions.put(dataDef.defaultValueExpression, deps)
							def result = processAPI.evaluateExpressionsOnActivityInstance(task.id, expressions)
							def newValue = result.values()[0]
							processAPI.updateActivityTransientDataInstance(dataDef.name,task.id, newValue)
							LOGGER.debug("$dataDef.name value of $task.id has been updated with: $newValue")
						}
					}
			}catch(ActivityDefinitionNotFoundException e) {
				//ignore
			}
		}
	}

	@Override
	public boolean isInterested(SEvent event) {
		return event.getType() == STATE_UPDATED  &&
				event.asType(SUpdateEvent) &&
				event.getObject() instanceof SUserTaskInstance &&
				((SUserTaskInstance)event.getObject()).stateName == ActivityStates.COMPLETED_STATE
	}


	@Override
	public String getIdentifier() {
		return UUID.randomUUID().toString();
	}
}
