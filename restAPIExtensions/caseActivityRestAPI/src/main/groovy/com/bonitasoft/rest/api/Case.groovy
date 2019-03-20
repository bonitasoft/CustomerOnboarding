package com.bonitasoft.rest.api

import java.io.Serializable

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import com.bonitasoft.engine.api.ProcessAPI
import com.bonitasoft.engine.bpm.process.impl.ProcessInstanceSearchDescriptor
import com.bonitasoft.web.extension.rest.RestAPIContext
import com.bonitasoft.web.extension.rest.RestApiController
import groovy.json.JsonBuilder
import org.bonitasoft.engine.search.SearchOptionsBuilder
import org.bonitasoft.web.extension.rest.RestApiResponse
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Case implements RestApiController, CaseActivityHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(Case.class)

    @Override
    RestApiResponse doHandle(HttpServletRequest request, RestApiResponseBuilder responseBuilder, RestAPIContext context) {
        def contextPath = request.contextPath
        def processAPI = context.apiClient.getProcessAPI()
		def procId = request.getParameter "procId"
		if (!procId) {
			return buildResponse(responseBuilder, HttpServletResponse.SC_BAD_REQUEST,"""{"error" : "the parameter procId is missing"}""")
		}
        def searchOptions = new SearchOptionsBuilder(0, 9999).filter(ProcessInstanceSearchDescriptor.PROCESS_DEFINITION_ID, Long.getLong(procId)).done()
        def result = processAPI.searchProcessInstances(searchOptions).getResult()
                .collect {
            [id: it.id, customer: it.stringIndex1, state: asLabel(it.state.toUpperCase(), "info"), viewAction: viewActionLink(it.id, processAPI, contextPath)]
        }

        processAPI.searchArchivedProcessInstances(searchOptions).getResult()
                .collect {
            result << [id: it.sourceObjectId, customer: it.getStringIndexValue(1), state: asLabel(it.state.toUpperCase(), "default"), viewAction: viewActionLink(it.sourceObjectId, processAPI, contextPath)]
        }
		
        return responseBuilder.with {
            withResponseStatus(HttpServletResponse.SC_OK)
            withResponse(new JsonBuilder(result).toString())
            build()
        }
    }

    def asLabel(state, style) {
        """<span class="label label-$style">$state</span>"""
    }
    

    def String viewActionLink(long caseId, ProcessAPI processAPI, contextPath) {
        def openTasks = searchOpenedTasks(caseId, processAPI).result
                .findAll { canExecute(getState(it, processAPI)) }
        if (openTasks.size() > 0) {
            return """<a class="btn btn-primary btn-sm" href="${
                contextPath
            }/apps/cases/case?id=$caseId" target="_target">Open <span class="badge"> $openTasks.size</span></a>"""
        } else {
            return ""
        }
    }
	
	/**
	 * Build an HTTP response.
	 *
	 * @param  responseBuilder the Rest API response builder
	 * @param  httpStatus the status of the response
	 * @param  body the response body
	 * @return a RestAPIResponse
	 */
	RestApiResponse buildResponse(RestApiResponseBuilder responseBuilder, int httpStatus, Serializable body) {
		return responseBuilder.with {
			withResponseStatus(httpStatus)
			withResponse(body)
			build()
		}
	}

}
