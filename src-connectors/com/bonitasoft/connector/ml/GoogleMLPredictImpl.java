/**
 * 
 */
package com.bonitasoft.connector.ml;

import org.bonitasoft.engine.connector.ConnectorException;

import com.bonitasoft.ml.OnlinePrediction;



/**
*The connector execution will follow the steps
* 1 - setInputParameters() --> the connector receives input parameters values
* 2 - validateInputParameters() --> the connector can validate input parameters values
* 3 - connect() --> the connector can establish a connection to a remote server (if necessary)
* 4 - executeBusinessLogic() --> execute the connector
* 5 - getOutputParameters() --> output are retrieved from connector
* 6 - disconnect() --> the connector can close connection to remote server (if any)
*/
public class GoogleMLPredictImpl extends AbstractGoogleMLPredictImpl {

	@Override
	protected void executeBusinessLogic() throws ConnectorException{
		//Get access to the connector input parameters
		//getProjectId();
		//getModelId();
		//getVersionId();
		//getJsonPayload();
	
		try {
			setPrediction(new OnlinePrediction().predict(getProjectId(),getModelId(),getVersionId(),getJsonPayload()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			setPrediction("{\"predictions\": [{\"probabilities\": [0.9132681488990784, 0.08673188090324402], \"class_ids\": [0], \"classes\": [\"0\"], \"logits\": [-2.354207992553711], \"logistic\": [0.08673188090324402]}]}");
		}
	
		//WARNING : Set the output of the connector execution. If outputs are not set, connector fails
		//setPrediction(prediction);
	
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
