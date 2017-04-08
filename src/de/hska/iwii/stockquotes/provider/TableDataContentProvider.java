package de.hska.iwii.stockquotes.provider;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.hska.iwii.stockquotes.model.TableData;
public class TableDataContentProvider implements IStructuredContentProvider {

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object inputElement) {
		return ((List<TableData>) inputElement).toArray();
	}

	@Override
	public void dispose() {
		// TODO
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO
	}

}
