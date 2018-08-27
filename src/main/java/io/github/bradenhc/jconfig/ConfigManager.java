package io.github.bradenhc.jconfig;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;

public class ConfigManager {

	private static final String sLocalConfigDirectory = "etc" + File.separator;
	private static final String sHomeConfigDirectory = System.getProperty("user.home") + File.separator + ".config"
			+ File.separator;
	private boolean mLookLocallyForConfig = true;

	private final Map<String, FileBasedConfigurationBuilder<FileBasedConfiguration>> mConfigurations = new HashMap<>();

	private static ConfigManager sSingleton = null;
	private static final Object sMutex = new Object();

	public static ConfigManager instance() {
		ConfigManager cm = sSingleton;
		if (cm == null) {
			synchronized (sMutex) {
				if (cm == null) {
					cm = sSingleton = new ConfigManager();
				}
			}
		}
		return cm;
	}

	public ConfigManager() {

	}

	public Configuration get(String filename) {

		FileBasedConfigurationBuilder<FileBasedConfiguration> builder = mConfigurations.get(filename);
		if (builder == null) {
			String filepath = createIfNotExist(filename);
			if(filepath == null) {
				return null;
			}
			Parameters params = new Parameters();
			builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
					.configure(params.properties().setFileName(filepath)
							.setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
			mConfigurations.put(filename, builder);
		}
		try {
			return builder.getConfiguration();
		} catch (ConfigurationException e) {
			System.err.println("Failed to get configuration for " + filename + ": " + e.getMessage());
			return null;
		}
	}

	public boolean save(String filename) {
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder = mConfigurations.get(filename);
		if (builder != null) {
			try {
				builder.save();
				return true;
			} catch (ConfigurationException e) {
				System.err.println("Failed to save configuration to " + filename + ": " + e.getMessage());
			}
		}
		return false;
	}

	private String createIfNotExist(String filename) {
		// Create the file if it doesn't exist
		String dir = mLookLocallyForConfig ? sLocalConfigDirectory : sHomeConfigDirectory;
		String filepath = dir + filename;
		File f = new File(filepath);
		if(!f.exists()) {
			try {
				FileUtils.forceMkdirParent(f);
				f.createNewFile();
			} catch (IOException e) {
				System.err.println("Failed to create file: " + filename);
				return null;
			}
		}
		return filepath;
	}

}
