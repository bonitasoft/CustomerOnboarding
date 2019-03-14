package com.bonitasoft.connector.sign;

import org.bonitasoft.engine.connector.AbstractConnector;
import org.bonitasoft.engine.connector.ConnectorValidationException;

public abstract class AbstractCoSignSendImpl extends AbstractConnector {

	protected final static String AUTHUSERNAME_INPUT_PARAMETER = "authUserName";
	protected final static String AUTHUSERPASS_INPUT_PARAMETER = "authUserPass";
	protected final static String AUTHINTEGRATORKEY_INPUT_PARAMETER = "authIntegratorKey";
	protected final static String FILE_INPUT_PARAMETER = "file";
	protected final static String FILETARGETNAME_INPUT_PARAMETER = "fileTargetName";
	protected final static String EMAILSUBJET_INPUT_PARAMETER = "emailSubjet";
	protected final static String EMAILCONTENT_INPUT_PARAMETER = "emailContent";
	protected final static String DESTMAIL_INPUT_PARAMETER = "destMail";
	protected final static String DESTNAME_INPUT_PARAMETER = "destName";
	protected final static String DESTACCOUNTID_INPUT_PARAMETER = "destAccountId";
	protected final static String PATH_INPUT_PARAMETER = "path";
	protected final String ENVELOPEID_OUTPUT_PARAMETER = "envelopeId";

	protected final java.lang.String getAuthUserName() {
		return (java.lang.String) getInputParameter(AUTHUSERNAME_INPUT_PARAMETER);
	}

	protected final java.lang.String getAuthUserPass() {
		return (java.lang.String) getInputParameter(AUTHUSERPASS_INPUT_PARAMETER);
	}

	protected final java.lang.String getAuthIntegratorKey() {
		return (java.lang.String) getInputParameter(AUTHINTEGRATORKEY_INPUT_PARAMETER);
	}

	protected final java.lang.String getFile() {
		return (java.lang.String) getInputParameter(FILE_INPUT_PARAMETER);
	}

	protected final java.lang.String getFileTargetName() {
		return (java.lang.String) getInputParameter(FILETARGETNAME_INPUT_PARAMETER);
	}

	protected final java.lang.String getEmailSubjet() {
		return (java.lang.String) getInputParameter(EMAILSUBJET_INPUT_PARAMETER);
	}

	protected final java.lang.String getEmailContent() {
		return (java.lang.String) getInputParameter(EMAILCONTENT_INPUT_PARAMETER);
	}

	protected final java.lang.String getDestMail() {
		return (java.lang.String) getInputParameter(DESTMAIL_INPUT_PARAMETER);
	}

	protected final java.lang.String getDestName() {
		return (java.lang.String) getInputParameter(DESTNAME_INPUT_PARAMETER);
	}

	protected final java.lang.String getDestAccountId() {
		return (java.lang.String) getInputParameter(DESTACCOUNTID_INPUT_PARAMETER);
	}

	protected final java.lang.String getPath() {
		return (java.lang.String) getInputParameter(PATH_INPUT_PARAMETER);
	}

	protected final void setEnvelopeId(java.lang.String envelopeId) {
		setOutputParameter(ENVELOPEID_OUTPUT_PARAMETER, envelopeId);
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
			getAuthUserPass();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"authUserPass type is invalid");
		}
		try {
			getAuthIntegratorKey();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"authIntegratorKey type is invalid");
		}
		try {
			getFile();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("file type is invalid");
		}
		try {
			getFileTargetName();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"fileTargetName type is invalid");
		}
		try {
			getEmailSubjet();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"emailSubjet type is invalid");
		}
		try {
			getEmailContent();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"emailContent type is invalid");
		}
		try {
			getDestMail();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("destMail type is invalid");
		}
		try {
			getDestName();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("destName type is invalid");
		}
		try {
			getDestAccountId();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"destAccountId type is invalid");
		}
		try {
			getPath();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("path type is invalid");
		}

	}

}
