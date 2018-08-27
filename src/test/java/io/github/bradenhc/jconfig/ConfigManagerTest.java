package io.github.bradenhc.jconfig;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.configuration2.Configuration;
import org.junit.jupiter.api.Test;

class ConfigManagerTest {

	@Test
	void test() {
		String configFile = "test.cfg";
		Configuration config = ConfigManager.instance().get(configFile);
		
		assertNotNull(config);
		
		config.setProperty("test.property", "Hello World");
		ConfigManager.instance().save(configFile);
		
		assertTrue(true);
	}

}
