package com.bonitasoft.connector.sign;

import org.bonitasoft.engine.connector.AbstractConnector;
import org.bonitasoft.engine.connector.ConnectorValidationException;

public abstract class AbstractCoSignGetStatusImpl extends AbstractConnector {

	protected final static String AUTHUSERNAME_INPUT_PARAMETER = "authUserName";
	protected final static String AUTHPASSWORD_INPUT_PARAMETER = "authPassword";
	protected final static String AUTHINTEGRATORKEY_INPUT_PARAMETER = "authIntegratorKey";
	protected final static String ENVELOPEID_INPUT_PARAMETER = "envelopeId";
	protected final static String PATH_INPUT_PARAMETER = "path";
	protected final String STATUS_OUTPUT_PARAMETER = "status";

	protected final java.lang.String getAuthUserName() {
		return (java.lang.String) getInputParameter(AUTHUSERNAME_INPUT_PARAMETER);
	}

	protected final java.lang.String getAuthPassword() {
		return (java.lang.String) getInputParameter(AUTHPASSWORD_INPUT_PARAMETER);
	}

	protected final java.lang.String getAuthIntegratorKey() {
		return (java.lang.String) getInputParameter(AUTHINTEGRATORKEY_INPUT_PARAMETER);
	}

	protected final java.lang.String getEnvelopeId() {
		return (java.lang.String) getInputParameter(ENVELOPEID_INPUT_PARAMETER);
	}

	protected final java.lang.String getPath() {
		return (java.lang.String) getInputParameter(PATH_INPUT_PARAMETER);
	}

	protected final void setStatus(java.lang.String status) {
		setOutputParameter(STATUS_OUTPUT_PARAMETER, status);
	}

	@Override
	public void validateInputParameters() throws ConnectorValidationException {
		try {
			getAuthUserName();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"authUserName type is invalid");
		}
		try {
			getAuthPassword();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"authPassword type is invalid");
		}
		try {
			getAuthIntegratorKey();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"authIntegratorKey type is invalid");
		}
		try {
			getEnvelopeId();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("envelopeId type is invalid");
		}
		try {
			getPath();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("path type is invalid");
		}

	}

}
