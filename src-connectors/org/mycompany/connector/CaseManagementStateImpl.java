/**
 * 
 */
package org.mycompany.connector;

import org.bonitasoft.engine.connector.ConnectorException;

/**
*The connector execution will follow the steps
* 1 - setInputParameters() --> the connector receives input parameters values
* 2 - validateInputParameters() --> the connector can validate input parameters values
* 3 - connect() --> the connector can establish a connection to a remote server (if necessary)
* 4 - executeBusinessLogic() --> execute the connector
* 5 - getOutputParameters() --> output are retrieved from connector
* 6 - disconnect() --> the connector can close connection to remote server (if any)
*/
public class CaseManagementStateImpl extends AbstractCaseManagementStateImpl {

	@Override
	protected void executeBusinessLogic() throws ConnectorException{
		//TODO execute your business logic here 
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		setState("N/A");
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
