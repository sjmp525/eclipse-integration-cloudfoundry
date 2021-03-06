/*******************************************************************************
 * Copyright (c) 2013 GoPivotal, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     GoPivotal, Inc. - initial API and implementation
 *******************************************************************************/
package org.cloudfoundry.ide.eclipse.internal.server.ui.wizards;

import org.cloudfoundry.ide.eclipse.internal.server.core.ApplicationAction;
import org.cloudfoundry.ide.eclipse.internal.server.core.CloudApplicationUrlLookup;
import org.cloudfoundry.ide.eclipse.internal.server.core.CloudFoundryServer;
import org.cloudfoundry.ide.eclipse.internal.server.core.CloudUtil;
import org.cloudfoundry.ide.eclipse.internal.server.core.application.IApplicationDelegate;
import org.cloudfoundry.ide.eclipse.internal.server.core.client.ApplicationDeploymentInfo;
import org.cloudfoundry.ide.eclipse.internal.server.core.client.CloudFoundryApplicationModule;
import org.cloudfoundry.ide.eclipse.internal.server.core.client.CloudFoundryServerBehaviour;

/**
 * 
 * This contains a reference to the core-level application delegate, which
 * contains API to push an application to a CF server. Since a wizard delegate
 * does NOT require a map to a core level application delegate, the link between
 * the two is not pushed up to the parent.
 */
public abstract class ApplicationWizardDelegate implements IApplicationWizardDelegate {

	private CloudApplicationUrlLookup urlLookup;

	private IApplicationDelegate appDelegate;

	public void setApplicationDelegate(IApplicationDelegate appDelegate) {
		this.appDelegate = appDelegate;
	}

	/**
	 * Corresponding core level application delegate that contains API for
	 * pushing an app to a CF server. This may be null, as a wizard delegate may
	 * not be mapped to an app delegate (in the event it uses a default app
	 * delegate from the CF Application framework) .
	 * @return Corresponding Application delegate, if it exists, or null.
	 */
	public IApplicationDelegate getApplicationDelegate() {
		return appDelegate;
	}

	/*
	 * FIXNS: Note that a very similar logic is also present in corresponding
	 * core level IApplicationDelegate implementation for Java web. Use one
	 * descriptor for both the wizard and the core, instead of two separate
	 * ones, to avoid duplication.
	 */
	public boolean isValid(ApplicationWizardDescriptor applicationDescriptor) {

		if (applicationDescriptor == null) {
			return false;
		}

		ApplicationDeploymentInfo deploymentInfo = applicationDescriptor.getDeploymentInfo();

		return deploymentInfo != null && deploymentInfo.getDeploymentName() != null && deploymentInfo.getMemory() > 0;

	}

	public void initialiseWizardDescriptor(ApplicationWizardDescriptor applicationDescriptor,
			CloudFoundryServer cloudServer, CloudFoundryApplicationModule module) {

		urlLookup = CloudApplicationUrlLookup.getCurrentLookup(cloudServer);

		String deploymentName = module != null ? module.getDeployedApplicationName() : null;

		ApplicationDeploymentInfo deploymentInfo = new ApplicationDeploymentInfo(deploymentName);

		applicationDescriptor.setDeploymentInfo(deploymentInfo);

		deploymentInfo.setMemory(CloudUtil.DEFAULT_MEMORY);

		applicationDescriptor.setStartDeploymentMode(ApplicationAction.START);

	}

	/**
	 * @see CloudFoundryServerBehaviour#getApplicationUrlLookup()
	 */
	public CloudApplicationUrlLookup getApplicationUrlLookup() {
		return urlLookup;
	}

}
