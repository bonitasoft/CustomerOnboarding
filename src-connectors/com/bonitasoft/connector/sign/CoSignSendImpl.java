 /**
 * 
 */
package com.bonitasoft.connector.sign;

import java.util.logging.Logger;

import org.bonitasoft.engine.connector.ConnectorException;

import com.bonitasoft.libSign.LibSign;
import com.docusign.esign.model.EnvelopeSummary;

import org.bonitasoft.engine.bpm.document.Document;
import org.bonitasoft.engine.bpm.document.DocumentNotFoundException;
import org.bonitasoft.engine.api.ProcessAPI;

/**
 *The connector execution will follow the steps
 * 1 - setInputParameters() --> the connector receives input parameters values
 * 2 - validateInputParameters() --> the connector can validate input parameters values
 * 3 - connect() --> the connector can establish a connection to a remote server (if necessary)
 * 4 - executeBusinessLogic() --> execute the connector
 * 5 - getOutputParameters() --> output are retrieved from connector
 * 6 - disconnect() --> the connector can close connection to remote server (if any)
 */
public class CoSignSendImpl extends AbstractCoSignSendImpl {
	
	String headerLog = "[Log : "+this.getClass().getName()+"]";

	//Init logger
	Logger logger = Logger.getLogger("com.bonitasoft.connector.sign");

	@Override
	protected void executeBusinessLogic() throws ConnectorException{
		try{
			//Get access to the connector input parameters
			//getAuthUserName();
			//getAuthUserPass();
			//getAuthIntegratorKey();
			//getFile();
			//getFileTargetName();
			//getEmailSubjet();
			//getEmailContent();
			//getDestMail();
			//getDestName();
			//getDestAccountId();
			
			logger.info(headerLog + "Execute method executeBusinessLogic.");
			//Init processAPI
			ProcessAPI myProcessAPI = apiAccessor.getProcessAPI();
			
			String myDocument = getFile();
			Document doc = myProcessAPI.getLastDocument(getExecutionContext().getProcessInstanceId(), myDocument);
			byte[] docContent = myProcessAPI.getDocumentContent(doc.getContentStorageId());
		
			
			
			logger.info(headerLog + "------------------------ Calling libSign for openSession ------------------");
			LibSign.openSession(getAuthUserName(),getAuthUserPass(),getAuthIntegratorKey(), getPath()); 
			logger.info(headerLog + "------------------------ Calling libSign for EnvelopeSummary ------------------");
			EnvelopeSummary myEnvelopeSummary = LibSign.sendEnv(docContent, getFileTargetName(), getEmailSubjet(), getEmailContent(), getDestMail(), getDestName());
			
			
			setEnvelopeId(myEnvelopeSummary.getEnvelopeId());
			logger.info(headerLog + "------------------------ Finished, going to getStatus------------------");	
			
			
			//WARNING : Set the output of the connector execution. If outputs are not set, connector fails
			//setEnvelopeId(envelopeId);
		} catch (DocumentNotFoundException e) {
			logger.info(headerLog + "Error DocumentNotFoundException : " + e.getMessage());
		}
		
	 }

	@Override
	public void connect() throws ConnectorException{
		//[Optional] Open a connection to remote server
	
	}

	@Override
	public void disconnect() throws ConnectorException{
		//[Optional] Close connection to remote server
	
	}

}
