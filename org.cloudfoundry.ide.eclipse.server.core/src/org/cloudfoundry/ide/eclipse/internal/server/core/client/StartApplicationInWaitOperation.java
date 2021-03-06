/*******************************************************************************
 * Copyright (c) 2012, 2013 GoPivotal, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     GoPivotal, Inc. - initial API and implementation
 *******************************************************************************/
package org.cloudfoundry.ide.eclipse.internal.server.core.client;

import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.cloudfoundry.client.lib.domain.CloudApplication.AppState;
import org.cloudfoundry.ide.eclipse.internal.server.core.CloudFoundryServer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.server.core.IModule;

public class StartApplicationInWaitOperation extends AbstractWaitForStateOperation {

	public StartApplicationInWaitOperation(CloudFoundryServer cloudServer, String operationLabel) {
		super(cloudServer, operationLabel);
	}

	public StartApplicationInWaitOperation(CloudFoundryServer cloudServer) {
		this(cloudServer, null);
	}

	@Override
	protected void doOperation(CloudFoundryServerBehaviour behaviour, IModule module, IProgressMonitor progress)
			throws CoreException {
		behaviour.startModule(new IModule[] { module }, progress);
	}

	@Override
	protected boolean isInState(AppState state) {
		return state.equals(CloudApplication.AppState.STARTED);
	}

}
