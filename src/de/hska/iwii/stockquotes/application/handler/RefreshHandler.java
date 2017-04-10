package de.hska.iwii.stockquotes.application.handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.swt.widgets.Shell;

import de.hska.iwii.stockquotes.application.parts.ViewPart;

public class RefreshHandler {
	@Inject private ViewPart view;

	@Execute
	public void execute(IWorkbench workbench, Shell shell){
		view.refreshContent();
	}
}
