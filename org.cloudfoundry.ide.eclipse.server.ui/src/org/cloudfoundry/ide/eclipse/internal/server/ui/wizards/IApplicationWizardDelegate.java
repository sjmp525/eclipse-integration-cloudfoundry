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

import java.util.List;

import org.cloudfoundry.ide.eclipse.internal.server.core.CloudFoundryServer;
import org.cloudfoundry.ide.eclipse.internal.server.core.application.IApplicationDelegate;
import org.cloudfoundry.ide.eclipse.internal.server.core.client.CloudFoundryApplicationModule;
import org.eclipse.jface.wizard.IWizardPage;

/**
 * Delegate that provides Application deployment wizard pages through the
 * extension point:
 * 
 * org.cloudfoundry.ide.eclipse.server.ui.applicationWizard
 * 
 * <p/>
 * The wizard delegate may correspond got an IApplicationDelegate, with the
 * difference that the wizard delegate provides UI when deploying the
 * application via the application deployment wizard. On the other hand, the
 * IApplicationDelegate is a core level delegate that contains API necessary to
 * push the application to a CF server. The need for two separate delegates is
 * due to the core and UI components of the plugin being separate.
 * 
 * 
 * @see IApplicationDelegate
 * 
 */
public interface IApplicationWizardDelegate {

	/**
	 * Provide a list of pages for the application deployment wizard that would
	 * replace the list of default pages. The default list of pages are not yet
	 * set in the wizard when this method is called by the framework. The
	 * returned list of pages will be set in the wizard via the wizard's
	 * addPages(...) API. Consequently, any pages that are returned by this
	 * method can be assumed to have a reference to the wizard via the
	 * getWizard(..) API in the IWizardPage when the page controls are created
	 * by the wizard.
	 * <p/>
	 * The descriptor contains values that need to be set in order for an
	 * application to be pushed to a Cloud Foundry server. Only one instance of
	 * the descriptor exists per wizard session, and it is shared amongst all
	 * the wizard pages. Values should be set directly in this descriptor in
	 * order for the wizard to be completed.
	 * 
	 * 
	 * @param descriptor shared descriptor that contains information necessary
	 * to push an application. Only one instance of the descriptor exists per
	 * wizard session, and it is shared amongst all the wizard pages
	 * @param cloudServer the Cloud Foundry server instance where the
	 * application will be pushed to.
	 * @param applicationModule Module representing the application that will be
	 * pushed to the Cloud Foundry server
	 * @return List of pages that should be set, which replace the default
	 * pages. Return null or empty list if the default wizard pages should be
	 * used.
	 */
	public List<IWizardPage> getWizardPages(ApplicationWizardDescriptor descriptor, CloudFoundryServer cloudServer,
			CloudFoundryApplicationModule applicationModule);

	/**
	 * This is called by the wizard first, before requesting the wizard pages.
	 * This allows implementations to access the server, shared application
	 * descriptor, and app module prior to creating the wizard pages.
	 * @param applicationDescriptor
	 * @param cloudServer
	 * @param module
	 */
	public void initialiseWizardDescriptor(ApplicationWizardDescriptor applicationDescriptor,
			CloudFoundryServer cloudServer, CloudFoundryApplicationModule module);

}
