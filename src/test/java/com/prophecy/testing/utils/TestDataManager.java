package com.prophecy.testing.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Utility class for managing test data files and operations
 */
public class TestDataManager {
    
    private static final Logger logger = LoggerFactory.getLogger(TestDataManager.class);
    private static final String TEST_DATA_DIR = "src/test/resources/testdata";
    private static final String TEMP_DATA_DIR = "target/temp-testdata";
    
    private final ObjectMapper jsonMapper;
    private final CsvMapper csvMapper;
    
    public TestDataManager() {
        this.jsonMapper = new ObjectMapper();
        this.csvMapper = new CsvMapper();
        createTempDirectory();
    }
    
    /**
     * Get the full path to a test data file
     */
    public String getTestDataFilePath(String fileName) {
        Path filePath = Paths.get(TEST_DATA_DIR, fileName);
        File file = filePath.toFile();
        
        if (!file.exists()) {
            throw new RuntimeException("Test data file not found: " + filePath.toAbsolutePath());
        }
        
        return file.getAbsolutePath();
    }
    
    /**
     * Create a temporary JSON file with the provided data
     */
    public String createTempJSONFile(String jsonData) {
        try {
            // Validate JSON format
            jsonMapper.readTree(jsonData);
            
            // Create temporary file
            Path tempFile = Paths.get(TEMP_DATA_DIR, "temp_" + System.currentTimeMillis() + ".json");
            Files.write(tempFile, jsonData.getBytes());
            
            logger.info("Created temporary JSON file: {}", tempFile.toAbsolutePath());
            return tempFile.toAbsolutePath().toString();
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temporary JSON file", e);
        }
    }
    
    /**
     * Create a temporary CSV file with the provided data
     */
    public String createTempCSVFile(List<Map<String, Object>> data) {
        try {
            if (data.isEmpty()) {
                throw new IllegalArgumentException("Data list cannot be empty");
            }
            
            // Get headers from the first row
            Map<String, Object> firstRow = data.get(0);
            CsvSchema.Builder schemaBuilder = CsvSchema.builder();
            firstRow.keySet().forEach(schemaBuilder::addColumn);
            CsvSchema schema = schemaBuilder.setUseHeader(true).build();
            
            // Create temporary file
            Path tempFile = Paths.get(TEMP_DATA_DIR, "temp_" + System.currentTimeMillis() + ".csv");
            csvMapper.writerFor(List.class)
                    .with(schema)
                    .writeValue(tempFile.toFile(), data);
            
            logger.info("Created temporary CSV file: {}", tempFile.toAbsolutePath());
            return tempFile.toAbsolutePath().toString();
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temporary CSV file", e);
        }
    }
    
    /**
     * Read JSON data from file
     */
    public Map<String, Object> readJSONFile(String fileName) {
        try {
            String filePath = getTestDataFilePath(fileName);
            return jsonMapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + fileName, e);
        }
    }
    
    /**
     * Read CSV data from file
     */
    public List<Map<String, Object>> readCSVFile(String fileName) {
        try {
            String filePath = getTestDataFilePath(fileName);
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            return csvMapper.readerFor(Map.class)
                    .with(schema)
                    .<Map<String, Object>>readValues(new File(filePath))
                    .readAll();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + fileName, e);
        }
    }
    
    /**
     * Generate synthetic customer data
     */
    public List<Map<String, Object>> generateSyntheticCustomerData(int recordCount, double nullPercentage, double invalidPercentage) {
        // Implementation for generating synthetic data
        // This is a simplified version - in a real implementation, you might use libraries like Faker
        logger.info("Generating {} synthetic customer records with {}% null and {}% invalid data", 
                   recordCount, nullPercentage, invalidPercentage);
        
        // Return empty list for now - implement based on your specific needs
        return List.of();
    }
    
    /**
     * Validate JSON schema
     */
    public boolean validateJSONSchema(String jsonData, String expectedSchema) {
        try {
            // Basic validation - in a real implementation, you might use JSON Schema validation
            jsonMapper.readTree(jsonData);
            return true;
        } catch (Exception e) {
            logger.error("JSON schema validation failed", e);
            return false;
        }
    }
    
    /**
     * Validate CSV format
     */
    public boolean validateCSVFormat(String csvData, String delimiter, boolean hasHeader) {
        try {
            // Basic validation - check if CSV can be parsed
            String[] lines = csvData.split("\n");
            if (lines.length == 0) {
                return false;
            }
            
            // Check if all rows have the same number of columns
            int expectedColumns = lines[0].split(delimiter).length;
            for (String line : lines) {
                if (line.split(delimiter).length != expectedColumns) {
                    return false;
                }
            }
            
            return true;
        } catch (Exception e) {
            logger.error("CSV format validation failed", e);
            return false;
        }
    }
    
    /**
     * Create temporary directory for test data
     */
    private void createTempDirectory() {
        try {
            Path tempDir = Paths.get(TEMP_DATA_DIR);
            if (!Files.exists(tempDir)) {
                Files.createDirectories(tempDir);
                logger.info("Created temporary data directory: {}", tempDir.toAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temporary data directory", e);
        }
    }
    
    /**
     * Clean up temporary files
     */
    public void cleanupTempFiles() {
        try {
            Path tempDir = Paths.get(TEMP_DATA_DIR);
            if (Files.exists(tempDir)) {
                Files.walk(tempDir)
                        .filter(Files::isRegularFile)
                        .forEach(file -> {
                            try {
                                Files.delete(file);
                            } catch (IOException e) {
                                logger.warn("Failed to delete temporary file: {}", file, e);
                            }
                        });
                logger.info("Cleaned up temporary files");
            }
        } catch (IOException e) {
            logger.warn("Failed to cleanup temporary files", e);
        }
    }
    
    /**
     * Get test data directory path
     */
    public String getTestDataDirectory() {
        return TEST_DATA_DIR;
    }
    
    /**
     * Get temporary data directory path
     */
    public String getTempDataDirectory() {
        return TEMP_DATA_DIR;
    }
}