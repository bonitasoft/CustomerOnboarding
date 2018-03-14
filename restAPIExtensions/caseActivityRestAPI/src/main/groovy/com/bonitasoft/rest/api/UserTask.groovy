package com.bonitasoft.rest.api;

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

import java.util.stream.Collectors

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.apache.http.HttpHeaders
import org.bonitasoft.engine.bpm.flownode.ActivityInstanceSearchDescriptor
import org.bonitasoft.engine.bpm.flownode.FlowNodeType
import org.bonitasoft.engine.search.SearchOptionsBuilder
import org.bonitasoft.web.extension.ResourceProvider
import org.bonitasoft.web.extension.rest.RestApiResponse
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.bonitasoft.engine.bpm.flownode.ManualTaskCreator
import com.bonitasoft.web.extension.rest.RestAPIContext
import com.bonitasoft.web.extension.rest.RestApiController

class UserTask implements RestApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserTask.class)
	
	@Override
	RestApiResponse doHandle(HttpServletRequest request, RestApiResponseBuilder responseBuilder, RestAPIContext context) {
		def jsonBody = new JsonSlurper().parse(request.getReader())
		def processAPI = context.apiClient.getProcessAPI()
		if(!jsonBody.taskId) {
			return responseBuilder.with {
				withResponseStatus(HttpServletResponse.SC_BAD_REQUEST)
				withResponse("No taskId in payload")
				build()
			}
		}
		def taskId = jsonBody.taskId.toLong()
		processAPI.assignUserTask(taskId, context.apiSession.userId)
		processAPI.executeUserTask(taskId, jsonBody)
		processAPI.addProcessComment(jsonBody.processInstanceId.toLong(), jsonBody.content)
		return responseBuilder.with {
			withResponseStatus(HttpServletResponse.SC_CREATED)
			build()
		}
	}
	
}
