package com.prophecy.testing.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration class for test settings
 */
public class TestConfiguration {
    
    private static final Logger logger = LoggerFactory.getLogger(TestConfiguration.class);
    private static TestConfiguration instance;
    private Properties properties;
    
    private TestConfiguration() {
        loadProperties();
    }
    
    public static TestConfiguration getInstance() {
        if (instance == null) {
            synchronized (TestConfiguration.class) {
                if (instance == null) {
                    instance = new TestConfiguration();
                }
            }
        }
        return instance;
    }
    
    private void loadProperties() {
        properties = new Properties();
        
        // Load default properties
        loadPropertiesFromFile("config/test.properties");
        
        // Load environment-specific properties
        String environment = System.getProperty("environment", "dev");
        loadPropertiesFromFile("config/test-" + environment + ".properties");
        
        // Override with system properties
        properties.putAll(System.getProperties());
    }
    
    private void loadPropertiesFromFile(String fileName) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) {
                properties.load(input);
                logger.info("Loaded properties from: {}", fileName);
            } else {
                logger.warn("Properties file not found: {}", fileName);
            }
        } catch (IOException e) {
            logger.warn("Failed to load properties from: {}", fileName, e);
        }
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                logger.warn("Invalid integer value for property {}: {}", key, value);
            }
        }
        return defaultValue;
    }
    
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }
    
    // Specific configuration getters
    public String getBaseUrl() {
        return getProperty("webdriver.base.url", "https://dev.prophecy.io");
    }
    
    public String getBrowser() {
        return getProperty("webdriver.driver", "chrome");
    }
    
    public boolean isHeadlessMode() {
        return getBooleanProperty("headless.mode", false);
    }
    
    public int getImplicitWaitTimeout() {
        return getIntProperty("webdriver.timeouts.implicitlywait", 10000);
    }
    
    public int getExplicitWaitTimeout() {
        return getIntProperty("webdriver.wait.for.timeout", 30000);
    }
    
    public int getSSOTimeout() {
        return getIntProperty("prophecy.sso.timeout", 60000);
    }
    
    public int getPipelineExecutionTimeout() {
        return getIntProperty("prophecy.pipeline.execution.timeout", 300000);
    }
    
    public int getDataValidationTimeout() {
        return getIntProperty("prophecy.data.validation.timeout", 120000);
    }
    
    public String getTestDataDirectory() {
        return getProperty("test.data.dir", "src/test/resources/testdata");
    }
    
    public String getTestUsername() {
        return getProperty("test.username", System.getenv("TEST_USERNAME"));
    }
    
    public String getTestPassword() {
        return getProperty("test.password", System.getenv("TEST_PASSWORD"));
    }
    
    public String getEnvironment() {
        return getProperty("environment", "dev");
    }
    
    public boolean isParallelExecution() {
        return getBooleanProperty("parallel.execution", false);
    }
    
    public int getThreadCount() {
        return getIntProperty("thread.count", 2);
    }
    
    public boolean isTakeScreenshotsOnFailure() {
        return getBooleanProperty("screenshots.on.failure", true);
    }
    
    public boolean isVerboseLogging() {
        return getBooleanProperty("verbose.logging", false);
    }
    
    public String getReportOutputDirectory() {
        return getProperty("report.output.dir", "target/site/serenity");
    }
    
    // Security-related configurations
    public boolean isSecurityTestingEnabled() {
        return getBooleanProperty("security.testing.enabled", false);
    }
    
    public boolean isSSLVerificationEnabled() {
        return getBooleanProperty("ssl.verification.enabled", true);
    }
    
    public String getProxyHost() {
        return getProperty("proxy.host");
    }
    
    public int getProxyPort() {
        return getIntProperty("proxy.port", 8080);
    }
    
    // Performance testing configurations
    public int getMaxResponseTime() {
        return getIntProperty("performance.max.response.time", 5000);
    }
    
    public int getMaxMemoryUsage() {
        return getIntProperty("performance.max.memory.usage", 1024);
    }
    
    public double getMaxCPUUsage() {
        String value = getProperty("performance.max.cpu.usage", "80.0");
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            logger.warn("Invalid CPU usage value: {}", value);
            return 80.0;
        }
    }
    
    // Data quality thresholds
    public double getDataCompletenessThreshold() {
        String value = getProperty("data.quality.completeness.threshold", "95.0");
        return Double.parseDouble(value);
    }
    
    public double getDataAccuracyThreshold() {
        String value = getProperty("data.quality.accuracy.threshold", "98.0");
        return Double.parseDouble(value);
    }
    
    public double getDataConsistencyThreshold() {
        String value = getProperty("data.quality.consistency.threshold", "99.0");
        return Double.parseDouble(value);
    }
    
    public double getDataValidityThreshold() {
        String value = getProperty("data.quality.validity.threshold", "97.0");
        return Double.parseDouble(value);
    }
}