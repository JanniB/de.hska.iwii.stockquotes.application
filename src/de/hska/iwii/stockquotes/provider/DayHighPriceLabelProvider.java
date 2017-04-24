package de.hska.iwii.stockquotes.provider;

import java.text.NumberFormat;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import de.hska.iwii.stockquotes.model.TableData;

public class DayHighPriceLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(Object element) {
		TableData data = (TableData) element;
		if (data.getDayHighPrice() != null) {
			NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
			return currencyFormatter.format(data.getDayHighPrice());
		}
		return null;
	}
}
