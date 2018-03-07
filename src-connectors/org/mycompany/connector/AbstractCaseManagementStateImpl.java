package org.mycompany.connector;

import org.bonitasoft.engine.connector.AbstractConnector;
import org.bonitasoft.engine.connector.ConnectorValidationException;

public abstract class AbstractCaseManagementStateImpl extends AbstractConnector {

	protected final String STATE_OUTPUT_PARAMETER = "state";

	protected final void setState(java.lang.String state) {
		setOutputParameter(STATE_OUTPUT_PARAMETER, state);
	}

	@Override
	public void validateInputParameters() throws ConnectorValidationException {

	}

}
