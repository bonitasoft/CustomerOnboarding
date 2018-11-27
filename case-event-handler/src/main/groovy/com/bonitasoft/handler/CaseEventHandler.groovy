package com.bonitasoft.handler;

import java.io.Serializable

import org.bonitasoft.engine.api.ProcessAPI
import org.bonitasoft.engine.api.impl.APIUtils
import org.bonitasoft.engine.api.impl.ProcessAPIImpl
import org.bonitasoft.engine.bpm.category.Category
import org.bonitasoft.engine.bpm.category.CategoryCriterion
import org.bonitasoft.engine.bpm.connector.ConnectorDefinition
import org.bonitasoft.engine.bpm.data.DataDefinition
import org.bonitasoft.engine.bpm.data.DataInstance
import org.bonitasoft.engine.bpm.data.DataNotFoundException
import org.bonitasoft.engine.bpm.data.impl.ShortTextDataInstanceImpl
import org.bonitasoft.engine.bpm.flownode.ActivityDefinitionNotFoundException
import org.bonitasoft.engine.bpm.flownode.ActivityInstanceSearchDescriptor
import org.bonitasoft.engine.bpm.flownode.ActivityStates
import org.bonitasoft.engine.bpm.flownode.HumanTaskInstance
import org.bonitasoft.engine.bpm.flownode.UserTaskInstance
import org.bonitasoft.engine.bpm.process.DesignProcessDefinition
import org.bonitasoft.engine.bpm.process.ProcessDefinition
import org.bonitasoft.engine.core.process.instance.api.states.FlowNodeState
import org.bonitasoft.engine.core.process.instance.model.SUserTaskInstance
import org.bonitasoft.engine.data.instance.api.DataInstanceContainer
import org.bonitasoft.engine.data.instance.exception.SDataInstanceException
import org.bonitasoft.engine.data.instance.model.SDataInstance
import org.bonitasoft.engine.events.model.SEvent
import org.bonitasoft.engine.events.model.SHandler
import org.bonitasoft.engine.events.model.SHandlerExecutionException
import org.bonitasoft.engine.events.model.SUpdateEvent
import org.bonitasoft.engine.expression.ExpressionConstants
import org.bonitasoft.engine.expression.ExpressionType
import org.bonitasoft.engine.expression.model.SExpression
import org.bonitasoft.engine.log.technical.TechnicalLogSeverity
import org.bonitasoft.engine.log.technical.TechnicalLoggerService
import org.bonitasoft.engine.persistence.OrderByType
import org.bonitasoft.engine.recorder.model.EntityUpdateDescriptor
import org.bonitasoft.engine.search.SearchOptionsBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javassist.bytecode.stackmap.BasicBlock.Catch


class CaseEventHandler implements SHandler<SEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaseEventHandler.class)
	private static final String STATE_UPDATED = "ACTIVITYINSTANCE_STATE_UPDATED"
	private static final String ACTIVITY_CONTAINER = "Dymanic Activity Container"
	private static final String CREATE_ACTIVITY = "Create Activity"

	public CaseEventHandler(){
	}

	@Override
	public void execute(SEvent event) throws SHandlerExecutionException {
		def SUserTaskInstance taskInstance =  event.getObject()
		def processInstanceId = taskInstance.parentProcessInstanceId
		ProcessAPI processAPI = new ProcessAPIImpl()
		def caseActivities = processAPI.searchHumanTaskInstances(new SearchOptionsBuilder(0, Integer.MAX_VALUE).with {
			filter(ActivityInstanceSearchDescriptor.PROCESS_INSTANCE_ID, processInstanceId)
			and()
			differentFrom(ActivityInstanceSearchDescriptor.NAME, ACTIVITY_CONTAINER)
			and()
			differentFrom(ActivityInstanceSearchDescriptor.NAME, CREATE_ACTIVITY)
			and()
			filter(ActivityInstanceSearchDescriptor.STATE_NAME, ActivityStates.READY_STATE)
			done()
		}).getResult()
		def pDefId = processAPI.getProcessDefinitionIdFromProcessInstanceId(processInstanceId)
		def DesignProcessDefinition design = processAPI.getDesignProcessDefinition(pDefId)
		def dataToUpdate = []
		caseActivities.each{ HumanTaskInstance task ->
			def variables = [:]
			try {
				processAPI.getActivityDataDefinitions(taskInstance.processDefinitionId, task.name, 0, Integer.MAX_VALUE)
						.collect{ DataDefinition dataDef ->
							if(dataDef.defaultValueExpression != null
							&& dataDef.defaultValueExpression.expressionType != ExpressionType.TYPE_CONSTANT.toString()
							&& dataDef.defaultValueExpression.content != null
							&& !dataDef.defaultValueExpression.content.isEmpty()
							&& !dataDef.defaultValueExpression.content.equals("[]")) {
								def newValue = refreshDataDefaultValue(task.id, dataDef, processAPI)
								variables.put(dataDef.name, newValue)
								println "$dataDef.name value of $task.id has been updated to $newValue"
							}
						}
				processAPI.updateActivityInstanceVariables(task.id, variables)
			}catch(ActivityDefinitionNotFoundException e) {
				println "Cannot retrieve data for task $task.name"
			}
		}
	}

	def refreshDataDefaultValue(Long taskId,DataDefinition dataDef, ProcessAPI processAPI) {
		def dataInstance = processAPI.getActivityDataInstance(dataDef.name, taskId)
		def deps = [:]
		def expressions = [:]
		expressions.put(dataDef.defaultValueExpression, deps)
		def result = processAPI.evaluateExpressionsOnActivityInstance(taskId, expressions)
		return result.values()[0]
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
