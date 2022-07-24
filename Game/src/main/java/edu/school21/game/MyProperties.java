package edu.school21.game;

import java.io.IOException;
import java.util.Properties;

public class MyProperties {
	private final String pathFileProperties;

	private Properties properties = null;

	public MyProperties(String path) {
		this.pathFileProperties = path;
		initProperties();
	}

	private void initProperties() {
		this.properties = new Properties();

		try {
			this.properties.load(getClass().getResourceAsStream("application-dev.properties"));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	public String getProperty(String name) {
		if (this.properties != null) {
			return this.properties.getProperty(name);
		}
		return null;
	}
}
