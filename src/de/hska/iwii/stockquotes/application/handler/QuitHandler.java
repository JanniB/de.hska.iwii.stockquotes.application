package de.hska.iwii.stockquotes.application.handler;

import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;


public class QuitHandler {
	@Execute
	public void execute(IWorkbench workbench, IEclipseContext context,
			@Named(IServiceConstants.ACTIVE_SHELL) Shell shell){
		if (MessageDialog.openConfirm(shell, "StockQuotes Application", //$NON-NLS-1$
				"Anwendung beenden?")) { //$NON-NLS-1$
			workbench.close();
		}
	}
}
