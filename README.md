# JConfig: Configuration file manager for Java applications
JConfig provides a very simple wrapper around the Apache Commons configuration and property file libraries. On top of the already provided functionality, JConfig offers dynamic config file creation.


### Maven
```xml
<dependency>
    <groupId>io.github.bradenhc</groupId>
    <artifactId>jconfig</artifactId>
    <version>1.0.0</version>
</dependency>
```

*Note: the above version is the latest stable version available*

### Example

**app.cfg**

```text
app.name = Test App
```

**MyApp.java**

```java
import io.github.bradenhc.jconfig.ConfigManager;
import org.apache.commons.configuration2.Configuration;

class MyApp {
	
	Configuration config = ConfigManager.instance().get("app.cfg");
	
	public MyApp(){
		String name = config.getString("app.name");
		System.out.println("Welcome to " + name);
	}
	
}
```