package de.hska.iwii.stockquotes.provider;

import java.text.NumberFormat;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import de.hska.iwii.stockquotes.model.TableData;

public class DayLowPriceLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(Object element) {
		TableData data = (TableData) element;
		if (data.getDayLowPrice() != null) {
			NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
			return currencyFormatter.format(data.getDayLowPrice());
		}
		return null;
	}
}