package edu.school21.game;

import java.io.IOException;
import java.io.InputStream;
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
			InputStream inputStream = getClass().getResourceAsStream(this.pathFileProperties);
			if (inputStream == null) {
				System.err.println(this.pathFileProperties + " file not found");
				System.exit(-1);
			}
			this.properties.load(inputStream);
		} catch (IOException e ) {
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
