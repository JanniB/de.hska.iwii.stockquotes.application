package de.hska.iwii.stockquotes.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

@Creatable
@Singleton
public class ProxyData {
	public static final String PROPERTY_IS_ACTIVE = "isActive"; //$NON-NLS-1$
	public static final String PROPERTY_INET_ADRESS = "inetAdress"; //$NON-NLS-1$
	public static final String PROPERTY_PORT = "port"; //$NON-NLS-1$
	public static final String PROPERTY_USERNAME = "username"; //$NON-NLS-1$
	public static final String PROPERTY_PASSWORD = "password"; //$NON-NLS-1$
	
	private boolean isActive;
	private String inetAdress;
	private String port;
	private String username;
	private String password;

	private transient PropertyChangeSupport support = new PropertyChangeSupport(this);

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		support.firePropertyChange(PROPERTY_IS_ACTIVE, this.isActive, this.isActive = isActive);
	}

	public String getInetAdress() {
		return inetAdress;
	}

	public void setInetAdress(String inetAdress) {
		support.firePropertyChange(PROPERTY_INET_ADRESS, this.inetAdress, this.inetAdress = inetAdress);
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		support.firePropertyChange(PROPERTY_PORT, this.port, this.port = port);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		support.firePropertyChange(PROPERTY_USERNAME, this.username, this.username = username);
	}

	public String getPassword() {
		return password;
	}
	
	public boolean isFilled() {
		return (inetAdress != null && port != null && username != null && password != null);
	}

	public void setPassword(String password) {
		support.firePropertyChange(PROPERTY_PASSWORD, this.password, this.password = password);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		support.removePropertyChangeListener(propertyName, listener);
	}
}
