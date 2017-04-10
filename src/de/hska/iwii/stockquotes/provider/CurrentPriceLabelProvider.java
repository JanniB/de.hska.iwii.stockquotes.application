package de.hska.iwii.stockquotes.provider;

import java.text.NumberFormat;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import de.hska.iwii.stockquotes.model.TableData;
import de.hska.iwii.stockquotes.net.StockData.CurrentPriceChange;

public class CurrentPriceLabelProvider  extends ColumnLabelProvider {
	@Override
	public String getText(Object element) {
		TableData data = (TableData) element;
		if (data.getCurrentPrice() != null) {
			NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
			return currencyFormatter.format(data.getCurrentPrice());
		}
		return null;
	}
	
	@Override
	public Color getBackground(Object element) {
		Display display = Display.getCurrent();
		TableData data = (TableData) element;
		if (data.getCurrentPriceChange().equals(CurrentPriceChange.UP)) {
			return display.getSystemColor(SWT.COLOR_GREEN);
		} else if (data.getCurrentPriceChange().equals(CurrentPriceChange.DOWN)) {
			return display.getSystemColor(SWT.COLOR_RED);
		}
		return display.getSystemColor(SWT.COLOR_WHITE);
	}
}