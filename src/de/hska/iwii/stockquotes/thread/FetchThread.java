package de.hska.iwii.stockquotes.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;

import de.hska.iwii.stockquotes.model.TableData;
import de.hska.iwii.stockquotes.net.IStockQuotes;
import de.hska.iwii.stockquotes.net.StockData;

public class FetchThread extends Thread {
	private TableViewer _viewer;
	private IStockQuotes _quotes;
	private String _stockIndex;
	
	public FetchThread(TableViewer viewer, IStockQuotes quotes, String stockIndex) {
		_viewer = viewer;
		_quotes = quotes;
		_stockIndex = stockIndex;
		
	}

	@Override
	public void run() {
		ArrayList<TableData> tableDataModel = getContent(_quotes, _stockIndex);
		_viewer.getTable().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				_viewer.setInput(tableDataModel);
			}
			
		});
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
