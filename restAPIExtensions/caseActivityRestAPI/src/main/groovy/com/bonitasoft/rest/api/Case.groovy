package com.bonitasoft.rest.api;

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.bonitasoft.engine.search.SearchOptionsBuilder
import org.bonitasoft.web.extension.rest.RestApiResponse
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.bonitasoft.engine.api.ProcessAPI
import com.bonitasoft.web.extension.rest.RestAPIContext
import com.bonitasoft.web.extension.rest.RestApiController

import groovy.json.JsonBuilder

class Case implements RestApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(Case.class)
	private static final String ACTIVITY_CONTAINER = "Dymanic Activity Container"

	@Override
	RestApiResponse doHandle(HttpServletRequest request, RestApiResponseBuilder responseBuilder, RestAPIContext context) {
		def processAPI = context.apiClient.getProcessAPI()
		def result = processAPI.searchProcessInstances(new SearchOptionsBuilder(0, 9999).with {
			done()
		}).getResult()
		.collect{
			[id:it.id,name:processName(it.processDefinitionId,processAPI),state:it.state,viewAction:viewActionLink(it.id)]
		}
	
		processAPI.searchArchivedProcessInstances(new SearchOptionsBuilder(0, 9999).with {
			done()
		}).getResult()
		.collect{
			result << [id:it.sourceObjectId,name:processName(it.processDefinitionId,processAPI),state:it.state,viewAction:viewActionLink(it.sourceObjectId)]
		}
		
		return responseBuilder.with {
			withResponseStatus(HttpServletResponse.SC_OK)
			withResponse(new JsonBuilder(result).toString())
			build()
		}
	}
	
	def String processName(long processDefId,ProcessAPI processAPI) {
		def definition = processAPI.getProcessDefinition(processDefId)
		return "$definition.name ($definition.version)"
	}

	def String viewActionLink(long caseId) {
		return """<a href="http://localhost:8080/bonita/apps/cases/case?id=$caseId" target="_target"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></a>"""
	}
}
