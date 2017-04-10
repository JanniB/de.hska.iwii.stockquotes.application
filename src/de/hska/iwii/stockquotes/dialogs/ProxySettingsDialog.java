package de.hska.iwii.stockquotes.dialogs;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import de.hska.iwii.stockquotes.model.ProxyData;

public class ProxySettingsDialog extends TitleAreaDialog {

	private ProxyData _proxyData;
	private Label _proxyNameLabel;
	private Text _proxyNameText;
	private Label _proxyPortLabel;
	private Text _proxyPortText;
	private Label _userNameLabel;
	private Text _userNameText;
	private Label _passwortLabel;
	private Text _passwortText;
	private Button _proxyEnablement;
	private DataBindingContext _context;
	private Control[] _controls;
	
	public ProxySettingsDialog(Shell shell, ProxyData proxyData) {
		super(shell);
		_proxyData = proxyData;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		setTitleImage(getShell().getDisplay().getSystemImage(SWT.ICON_WORKING));
		setMessage(Messages.ProxySettingsDialog_0, IMessageProvider.INFORMATION);
		setTitle(Messages.ProxySettingsDialog_1);
		getShell().setText(Messages.ProxySettingsDialog_2);
		return contents;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite parentContents = (Composite) super.createDialogArea(parent);

		Composite contents = new Composite(parentContents, SWT.NONE);
		contents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		GridLayout layout = new GridLayout(2, false);
		contents.setLayout(layout);

		Label label = new Label(contents, SWT.BEGINNING);
		label.setText(Messages.ProxySettingsDialog_3);
		label.setLayoutData(new GridData(SWT.DEFAULT, SWT.CENTER, false, false));

		_proxyEnablement = new Button(contents, SWT.CHECK);
		_proxyEnablement.setText(Messages.ProxySettingsDialog_4);
		//_proxyActive.setSelection(true);
		_proxyEnablement.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		_proxyEnablement.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setControls();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// not necessary
			}
		});

		_proxyNameLabel = new Label(contents, SWT.BEGINNING);
		_proxyNameLabel.setText(Messages.ProxySettingsDialog_5);
		_proxyNameLabel.setLayoutData(new GridData(SWT.DEFAULT, SWT.CENTER, false, false));

		_proxyNameText = new Text(contents, SWT.BORDER);
		_proxyNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		_proxyPortLabel = new Label(contents, SWT.BEGINNING);
		_proxyPortLabel.setText(Messages.ProxySettingsDialog_6);
		_proxyPortLabel.setLayoutData(new GridData(SWT.DEFAULT, SWT.CENTER, false, false));

		_proxyPortText = new Text(contents, SWT.BORDER);
		_proxyPortText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		_userNameLabel = new Label(contents, SWT.BEGINNING);
		_userNameLabel.setText(Messages.ProxySettingsDialog_7);
		_userNameLabel.setLayoutData(new GridData(SWT.DEFAULT, SWT.CENTER, false, false));

		_userNameText = new Text(contents, SWT.BORDER);
		_userNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		_passwortLabel = new Label(contents, SWT.BEGINNING);
		_passwortLabel.setText(Messages.ProxySettingsDialog_8);
		_passwortLabel.setLayoutData(new GridData(SWT.DEFAULT, SWT.CENTER, false, false));

		_passwortText = new Text(contents, SWT.BORDER | SWT.PASSWORD);
		_passwortText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		_controls = new Control[] { _proxyNameLabel, _proxyNameText, _proxyPortLabel,
				_proxyPortText, _userNameLabel, _userNameText, _passwortLabel, _passwortText };
		
		contents.pack();

		createBindings();
		setControls();
		
		return contents;
	}

	private void createBindings() {
		_context = new DataBindingContext();
		
		IObservableValue stringWidgetObservable = WidgetProperties.text(SWT.Modify).observe(_proxyNameText);
		IObservableValue stringPropertyObservable = BeanProperties
				.value(ProxyData.class, ProxyData.PROPERTY_INET_ADRESS).observe(_proxyData);
		_context.bindValue(stringWidgetObservable, stringPropertyObservable);

		stringWidgetObservable = WidgetProperties.text(SWT.Modify).observe(_proxyPortText);
		stringPropertyObservable = BeanProperties.value(ProxyData.class, ProxyData.PROPERTY_PORT).observe(_proxyData);
		_context.bindValue(stringWidgetObservable, stringPropertyObservable);

		stringWidgetObservable = WidgetProperties.text(SWT.Modify).observe(_userNameText);
		stringPropertyObservable = BeanProperties.value(ProxyData.class, ProxyData.PROPERTY_USERNAME)
				.observe(_proxyData);
		_context.bindValue(stringWidgetObservable, stringPropertyObservable);

		stringWidgetObservable = WidgetProperties.text(SWT.Modify).observe(_passwortText);
		stringPropertyObservable = BeanProperties.value(ProxyData.class, ProxyData.PROPERTY_PASSWORD)
				.observe(_proxyData);
		_context.bindValue(stringWidgetObservable, stringPropertyObservable);
		

		IObservableValue booleanWidgetObservable = WidgetProperties.selection().observe(_proxyEnablement);
		stringPropertyObservable = BeanProperties.value(ProxyData.class, ProxyData.PROPERTY_IS_ACTIVE).observe(_proxyData);
		_context.bindValue(booleanWidgetObservable, stringPropertyObservable);
	}
	
	private void setControls() {
		for(Control control : _controls) {
			control.setEnabled(_proxyEnablement.getSelection());
		}
	}

	@Override
	protected void buttonPressed(int buttonId) {
		close();
	}
}
