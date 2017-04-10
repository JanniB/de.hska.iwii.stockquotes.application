package de.hska.iwii.stockquotes.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.TableViewer;

import de.hska.iwii.stockquotes.model.TableData;
import de.hska.iwii.stockquotes.net.IStockQuotes;
import de.hska.iwii.stockquotes.net.StockData;

public class FetchJob extends Job {
	private TableViewer _viewer;
	private IStockQuotes _quotes;
	private String _stockIndex;

	public FetchJob(String name, TableViewer viewer, IStockQuotes quotes, String stockIndex) {
		super(name);
		_viewer = viewer;
		_quotes = quotes;
		_stockIndex = stockIndex;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		ArrayList<TableData> tableDataModel = getContent(_quotes, _stockIndex);
		_viewer.getTable().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				_viewer.setInput(tableDataModel);
			}
			
		});
		return Status.OK_STATUS;
	}

	public ArrayList<TableData> getContent(IStockQuotes source, String stockIndex) {
		ArrayList<TableData> tableDataModel = new ArrayList<>();
		try {
			List<StockData> data = source.requestDataSync(stockIndex);
			if (data.size() > 0) {
				for (StockData d : data) {
					tableDataModel.add(new TableData(d.getCompanyShortName(), d.getCompanyName(), d.getCurrentPrice(),
							d.getDayHighPrice(), d.getDayLowPrice(), d.getDate(), d.getPriceChange()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tableDataModel;
	}

}
