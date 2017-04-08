package de.hska.iwii.stockquotes.application.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Shell;

import de.hska.iwii.stockquotes.dialogs.ProxySettingsDialog;
import de.hska.iwii.stockquotes.model.ProxyData;

public class ProxyHandler {
	ProxyData _proxyData;
	
	ProxyHandler() {
		_proxyData = new ProxyData();
	}

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		ProxySettingsDialog dialog = new ProxySettingsDialog(shell, _proxyData);
		dialog.open();
	}
}
