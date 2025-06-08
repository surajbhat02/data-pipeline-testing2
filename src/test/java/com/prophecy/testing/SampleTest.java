package com.prophecy.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sample test to verify framework setup
 */
public class SampleTest {
    
    @Test
    @DisplayName("Framework Setup Verification")
    public void testFrameworkSetup() {
        // Simple test to verify the framework is set up correctly
        String projectName = "Prophecy Data Pipeline Testing";
        assertThat(projectName)
            .as("Project name should be set correctly")
            .isEqualTo("Prophecy Data Pipeline Testing");
    }
    
    @Test
    @DisplayName("Test Data Manager Initialization")
    public void testDataManagerInitialization() {
        // Test that TestDataManager can be instantiated
        com.prophecy.testing.utils.TestDataManager testDataManager = 
            new com.prophecy.testing.utils.TestDataManager();
        
        assertThat(testDataManager)
            .as("TestDataManager should be instantiated successfully")
            .isNotNull();
        
        assertThat(testDataManager.getTestDataDirectory())
            .as("Test data directory should be configured")
            .isEqualTo("src/test/resources/testdata");
    }
    
    @Test
    @DisplayName("Configuration Loading")
    public void testConfigurationLoading() {
        // Test that configuration can be loaded
        com.prophecy.testing.config.TestConfiguration config = 
            com.prophecy.testing.config.TestConfiguration.getInstance();
        
        assertThat(config)
            .as("Configuration should be loaded successfully")
            .isNotNull();
        
        assertThat(config.getBrowser())
            .as("Browser configuration should be set")
            .isNotEmpty();
    }
}