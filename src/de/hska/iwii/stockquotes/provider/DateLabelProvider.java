package de.hska.iwii.stockquotes.provider;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import de.hska.iwii.stockquotes.model.TableData;

public class DateLabelProvider extends ColumnLabelProvider {
	
	@Override
	public String getText(Object element) {
		TableData data = ((TableData) element);
		if (data.getDate() != null) {
			return data.getDate().toString();
		}
		return null;
	}
}