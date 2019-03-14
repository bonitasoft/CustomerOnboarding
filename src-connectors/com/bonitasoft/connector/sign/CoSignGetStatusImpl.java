/**
 * 
 */
package com.bonitasoft.connector.sign;

import java.util.logging.Logger;

import org.bonitasoft.engine.connector.ConnectorException;

import com.bonitasoft.libSign.LibSign;

/**
 *The connector execution will follow the steps
 * 1 - setInputParameters() --> the connector receives input parameters values
 * 2 - validateInputParameters() --> the connector can validate input parameters values
 * 3 - connect() --> the connector can establish a connection to a remote server (if necessary)
 * 4 - executeBusinessLogic() --> execute the connector
 * 5 - getOutputParameters() --> output are retrieved from connector
 * 6 - disconnect() --> the connector can close connection to remote server (if any)
 */
public class CoSignGetStatusImpl extends AbstractCoSignGetStatusImpl {
	
	String headerLog = "[Log : "+this.getClass().getName()+"]";

	//Init logger
	Logger logger = Logger.getLogger("com.bonitasoft.connector.sign");

	@Override
	protected void executeBusinessLogic() throws ConnectorException{
		try{
			//Get access to the connector input parameters
			//getAuthUserName();
			//getAuthPassword();
			//getAuthIntegratorKey();
			//getEnvelopeId();
			
			logger.info(headerLog + "Execute method executeBusinessLogic.");
			
			logger.info(headerLog + "Execute method executeBusinessLogic.");
			
			
			logger.info(headerLog + "------------------------ Calling libSign for openSession ------------------");
		
			LibSign.openSession(getAuthUserName(),getAuthPassword(),getAuthIntegratorKey(), getPath()); 
			
			logger.info(headerLog + "------------------------ Getting status... ------------------");
			
			String status = LibSign.getEnvelopeStatus(getEnvelopeId());
			setStatus(status);
			
			logger.info(headerLog + "------------------------ Status : "+ status +" ------------------");
		
			//WARNING : Set the output of the connector execution. If outputs are not set, connector fails
			//setStatus(status);
		} catch (Exception e) {
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
