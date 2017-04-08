package de.hska.iwii.stockquotes.provider;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import de.hska.iwii.stockquotes.model.TableData;

public class DayLowPriceLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(Object element) {
		return String.valueOf(((TableData) element).getDayLowPrice());
	}
}