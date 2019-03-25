/**
 * 
 */
package com.bonitasoft.connector.ml;

import org.bonitasoft.engine.connector.AbstractConnector;
import org.bonitasoft.engine.connector.ConnectorValidationException;

/**
 * This abstract class is generated and should not be modified.
 */
public abstract class AbstractGoogleMLPredictImpl extends AbstractConnector {

	protected final static String PROJECTID_INPUT_PARAMETER = "projectId";
	protected final static String MODELID_INPUT_PARAMETER = "modelId";
	protected final static String VERSIONID_INPUT_PARAMETER = "versionId";
	protected final static String JSONPAYLOAD_INPUT_PARAMETER = "jsonPayload";
	protected final String PREDICTION_OUTPUT_PARAMETER = "prediction";

	protected final java.lang.String getProjectId() {
		return (java.lang.String) getInputParameter(PROJECTID_INPUT_PARAMETER);
	}

	protected final java.lang.String getModelId() {
		return (java.lang.String) getInputParameter(MODELID_INPUT_PARAMETER);
	}

	protected final java.lang.String getVersionId() {
		return (java.lang.String) getInputParameter(VERSIONID_INPUT_PARAMETER);
	}

	protected final java.lang.String getJsonPayload() {
		return (java.lang.String) getInputParameter(JSONPAYLOAD_INPUT_PARAMETER);
	}

	protected final void setPrediction(java.lang.String prediction) {
		setOutputParameter(PREDICTION_OUTPUT_PARAMETER, prediction);
	}

	@Override
	public void validateInputParameters() throws ConnectorValidationException {
		try {
			getProjectId();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("projectId type is invalid");
		}
		try {
			getModelId();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("modelId type is invalid");
		}
		try {
			getVersionId();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("versionId type is invalid");
		}
		try {
			getJsonPayload();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("jsonPayload type is invalid");
		}

	}

}
