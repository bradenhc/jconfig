package io.github.bradenhc.jconfig;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.configuration2.Configuration;
import org.junit.jupiter.api.Test;

class ConfigManagerTest {

	private String configFile = "test.cfg";
	
	@Test
	void test() {
		
		Configuration config = ConfigManager.instance().get(configFile);
		
		assertNotNull(config);
		
		config.setProperty("test.property", "Hello World");
		ConfigManager.instance().save(configFile);
		
		assertTrue(true);
	}
	
	@Test
	void testGet() {
		Configuration config = ConfigManager.instance().get(configFile);
		
		String s = config.getString("test.property");
		
		assertEquals("Hello World", s);
		
	}

}
