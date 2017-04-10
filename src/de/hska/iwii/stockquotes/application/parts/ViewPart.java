package de.hska.iwii.stockquotes.application.parts;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import de.hska.iwii.stockquotes.model.ProxyData;
import de.hska.iwii.stockquotes.net.IStockQuotes;
import de.hska.iwii.stockquotes.net.simulation.StockQuotesSimulation;
import de.hska.iwii.stockquotes.net.yahoo.StockQuotesYahoo;
import de.hska.iwii.stockquotes.provider.CompanyNameLabelProvider;
import de.hska.iwii.stockquotes.provider.CompanyShortNameLabelProvider;
import de.hska.iwii.stockquotes.provider.CurrentPriceLabelProvider;
import de.hska.iwii.stockquotes.provider.DateLabelProvider;
import de.hska.iwii.stockquotes.provider.DayHighPriceLabelProvider;
import de.hska.iwii.stockquotes.provider.DayLowPriceLabelProvider;
import de.hska.iwii.stockquotes.provider.TableDataContentProvider;
import de.hska.iwii.stockquotes.thread.FetchJob;

@Creatable
@Singleton
public class ViewPart {
	private Job _job;
	private final static int TIMER_PERIOD = 10000;
	private Timer _timer;
	private TableViewer _viewer;
	private Combo _index;
	private Text _filter;
	private Combo _source;
	private IStockQuotes _currentSource;
	@Inject
	private StockQuotesYahoo _yahooSource;
	@Inject
	private StockQuotesSimulation _simulationSource;
	@Inject
	private ProxyData _proxyData;

	@Focus
	public void createContents(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		Composite dataBar = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(3, true);
		dataBar.setLayout(gridLayout);
		dataBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Label label1 = new Label(dataBar, SWT.NONE);
		label1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		label1.setText(Messages.ViewPart_0);
		Label label2 = new Label(dataBar, SWT.NONE);
		label2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		label2.setText(Messages.ViewPart_1);
		Label label3 = new Label(dataBar, SWT.NONE);
		label3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		label3.setText(Messages.ViewPart_2);
		_index = new Combo(dataBar, SWT.BORDER);
		for (String str : IStockQuotes.ALL_STOCK_INDEXES) {
			_index.add(str);
		}
		_index.select(0);
		_index.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				refreshContent();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Not necessary
			}
		});

		_index.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		_filter = new Text(dataBar, SWT.BORDER);
		_filter.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		_filter.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (_filter.getText().isEmpty()) {
					System.out.println("Filter deactivated."); //$NON-NLS-1$
				} else {
					System.out.println("Filtering..."); //$NON-NLS-1$
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// Not necessary
			}

		});
		_filter.setText("optional"); //$NON-NLS-1$
		_filter.setEnabled(false);

		_source = new Combo(dataBar, SWT.BORDER);
		_source.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		_source.add("Simulation"); //$NON-NLS-1$
		_source.add("Yahoo-Server"); //$NON-NLS-1$
		_source.select(0);
		_source.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				refreshContent();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Not necessary
			}
		});

		Composite tableComp = new Composite(parent, SWT.NONE);
		tableComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		TableColumnLayout tcl = new TableColumnLayout();
		tableComp.setLayout(tcl);
		_viewer = new TableViewer(tableComp, SWT.FULL_SELECTION);
		_viewer.setContentProvider(new TableDataContentProvider());

		TableViewerColumn tableColumn1 = new TableViewerColumn(_viewer, SWT.LEFT);
		tableColumn1.getColumn().setText(Messages.ViewPart_8);
		tableColumn1.setLabelProvider(new CompanyShortNameLabelProvider());
		tcl.setColumnData(tableColumn1.getColumn(), new ColumnWeightData(10, true));
		TableViewerColumn tableColumn2 = new TableViewerColumn(_viewer, SWT.LEFT);
		tableColumn2.getColumn().setText(Messages.ViewPart_9);
		tableColumn2.setLabelProvider(new CompanyNameLabelProvider());
		tcl.setColumnData(tableColumn2.getColumn(), new ColumnWeightData(30, true));
		TableViewerColumn tableColumn3 = new TableViewerColumn(_viewer, SWT.LEFT);
		tableColumn3.getColumn().setText(Messages.ViewPart_10);
		tableColumn3.setLabelProvider(new CurrentPriceLabelProvider());
		tcl.setColumnData(tableColumn3.getColumn(), new ColumnWeightData(15, true));
		TableViewerColumn tableColumn4 = new TableViewerColumn(_viewer, SWT.LEFT);
		tableColumn4.getColumn().setText(Messages.ViewPart_11);
		tableColumn4.setLabelProvider(new DayHighPriceLabelProvider());
		tcl.setColumnData(tableColumn4.getColumn(), new ColumnWeightData(15, true));
		TableViewerColumn tableColumn5 = new TableViewerColumn(_viewer, SWT.LEFT);
		tableColumn5.getColumn().setText(Messages.ViewPart_12);
		tableColumn5.setLabelProvider(new DayLowPriceLabelProvider());
		tcl.setColumnData(tableColumn5.getColumn(), new ColumnWeightData(15, true));
		TableViewerColumn tableColumn6 = new TableViewerColumn(_viewer, SWT.LEFT);
		tableColumn6.getColumn().setText(Messages.ViewPart_13);
		tableColumn6.setLabelProvider(new DateLabelProvider());
		tcl.setColumnData(tableColumn6.getColumn(), new ColumnWeightData(15, true));

		Table table = _viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		_timer = new Timer();
		_timer.schedule(new TimerTask() {

			@Override
			public void run() {
				_viewer.getTable().getDisplay().asyncExec(new Runnable() {
					public void run() {
						refreshContent();
					}
				});
			}
		}, 0, TIMER_PERIOD);
	}

	public void refreshContent() {
		if (_job == null || _job.getState() == Job.NONE) {
			IStockQuotes quotes = getCurrentSource();
			String stockIndex = _index.getText();
			if (!_proxyData.getIsActive()) {
				quotes.setProxy(null, 0);
			} else {
				quotes.setProxy(_proxyData.getInetAdress(), Integer.valueOf(_proxyData.getPort()));
				quotes.setProxyAuthentication(_proxyData.getUsername(), _proxyData.getPassword());
			}
			_job = new FetchJob("Fetching Data", _viewer, quotes, stockIndex); //$NON‐NLS‐1$
			_job.setUser(true);
			_job.schedule();
		}
		// _fetchThread = new FetchThread(_viewer, quotes, stockIndex);
		// _fetchThread.start();
	}

	private IStockQuotes getCurrentSource() {
		if (_currentSource != null && _currentSource.getName().equals(_source.getText())) {
			return _currentSource;
		} else {
			if (_source.getText().equals(_yahooSource.getName())) {
				_currentSource = _yahooSource;
				_currentSource.reset();
				return _currentSource;
			} else {
				_currentSource = _simulationSource;
				_currentSource.reset();
				return _currentSource;
			}
		}

	}

	@PreDestroy
	public void dispose() {
		if (_job != null && _job.getState() != Job.NONE) {
			_job.cancel();
			try {
				_job.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}