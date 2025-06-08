package com.prophecy.testing.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Page Object for Prophecy Pipeline Page
 */
public class PipelinePage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(PipelinePage.class);
    
    // Locators for pipeline page elements
    private static final By PIPELINE_CANVAS = By.cssSelector("[data-testid='pipeline-canvas']");
    private static final By PIPELINE_STAGES = By.cssSelector("[data-testid='pipeline-stage']");
    private static final By PIPELINE_TITLE = By.cssSelector("[data-testid='pipeline-title']");
    private static final By EXECUTE_BUTTON = By.cssSelector("[data-testid='execute-pipeline']");
    private static final By EXECUTE_STAGE_BUTTON = By.cssSelector("[data-testid='execute-stage']");
    private static final By STAGE_STATUS = By.cssSelector("[data-testid='stage-status']");
    private static final By EXECUTION_PROGRESS = By.cssSelector("[data-testid='execution-progress']");
    private static final By RECORDS_PROCESSED = By.cssSelector("[data-testid='records-processed']");
    private static final By EXECUTION_METRICS = By.cssSelector("[data-testid='execution-metrics']");
    private static final By ERROR_DETAILS = By.cssSelector("[data-testid='error-details']");
    private static final By DATA_PREVIEW = By.cssSelector("[data-testid='data-preview']");
    private static final By STAGE_CONFIGURATION = By.cssSelector("[data-testid='stage-configuration']");
    
    // Data source configuration locators
    private static final By ADD_DATA_SOURCE_BUTTON = By.cssSelector("[data-testid='add-data-source']");
    private static final By DATA_SOURCE_TYPE_SELECTOR = By.cssSelector("[data-testid='data-source-type']");
    private static final By FILE_UPLOAD_INPUT = By.cssSelector("input[type='file']");
    private static final By SCHEMA_MAPPING_SECTION = By.cssSelector("[data-testid='schema-mapping']");
    private static final By DATA_SOURCE_PREVIEW = By.cssSelector("[data-testid='data-source-preview']");
    private static final By CONFIGURE_DATA_SOURCE_BUTTON = By.cssSelector("[data-testid='configure-data-source']");
    
    // Validation and quality check locators
    private static final By DATA_QUALITY_METRICS = By.cssSelector("[data-testid='data-quality-metrics']");
    private static final By VALIDATION_RESULTS = By.cssSelector("[data-testid='validation-results']");
    private static final By QUALITY_ISSUES = By.cssSelector("[data-testid='quality-issues']");
    
    public PipelinePage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Open a specific pipeline by name
     */
    public void openPipeline(String pipelineName) {
        By pipelineLink = By.xpath(String.format("//a[contains(text(), '%s')]", pipelineName));
        waitForElementToBeClickable(pipelineLink);
        safeClick(pipelineLink);
        waitForPipelineToLoad();
        logger.info("Opened pipeline: {}", pipelineName);
    }
    
    /**
     * Wait for pipeline canvas to load
     */
    public void waitForPipelineToLoad() {
        waitForElementToBeVisible(PIPELINE_CANVAS);
        waitForLoadingToComplete();
        logger.info("Pipeline canvas loaded");
    }
    
    /**
     * Check if pipeline canvas is displayed
     */
    public boolean isPipelineCanvasDisplayed() {
        return isElementDisplayed(PIPELINE_CANVAS);
    }
    
    /**
     * Get all pipeline stages
     */
    public List<WebElementFacade> getPipelineStages() {
        return getAllElements(PIPELINE_STAGES);
    }
    
    /**
     * Get pipeline stage by name
     */
    public WebElementFacade getPipelineStage(String stageName) {
        By stageLocator = By.xpath(String.format("//div[@data-testid='pipeline-stage' and contains(., '%s')]", stageName));
        return find(stageLocator);
    }
    
    /**
     * Execute a specific pipeline stage
     */
    public void executeStage(String stageName) {
        WebElementFacade stage = getPipelineStage(stageName);
        stage.click();
        
        // Click execute button for the stage
        By stageExecuteButton = By.xpath(String.format("//div[contains(., '%s')]//button[@data-testid='execute-stage']", stageName));
        waitForElementToBeClickable(stageExecuteButton);
        safeClick(stageExecuteButton);
        
        logger.info("Executed stage: {}", stageName);
    }
    
    /**
     * Execute the entire pipeline
     */
    public void executePipeline() {
        safeClick(EXECUTE_BUTTON);
        logger.info("Started pipeline execution");
    }
    
    /**
     * Wait for stage execution to complete
     */
    public void waitForStageExecutionToComplete(String stageName) {
        By stageStatusLocator = By.xpath(String.format("//div[contains(., '%s')]//div[@data-testid='stage-status']", stageName));
        
        wait.until(driver -> {
            String status = safeGetText(stageStatusLocator);
            return status.equals("Completed") || status.equals("Failed");
        });
        
        logger.info("Stage execution completed: {}", stageName);
    }
    
    /**
     * Get stage execution status
     */
    public String getStageStatus(String stageName) {
        By stageStatusLocator = By.xpath(String.format("//div[contains(., '%s')]//div[@data-testid='stage-status']", stageName));
        return safeGetText(stageStatusLocator);
    }
    
    /**
     * Get records processed count for a stage
     */
    public int getRecordsProcessed(String stageName) {
        By recordsLocator = By.xpath(String.format("//div[contains(., '%s')]//span[@data-testid='records-processed']", stageName));
        String recordsText = safeGetText(recordsLocator);
        return Integer.parseInt(recordsText.replaceAll("[^0-9]", ""));
    }
    
    /**
     * Get execution metrics for a stage
     */
    public Map<String, String> getExecutionMetrics(String stageName) {
        Map<String, String> metrics = new HashMap<>();
        
        By metricsSection = By.xpath(String.format("//div[contains(., '%s')]//div[@data-testid='execution-metrics']", stageName));
        WebElementFacade metricsElement = find(metricsSection);
        
        // Extract various metrics (this would depend on the actual UI structure)
        List<WebElementFacade> metricItems = metricsElement.thenFindAll(".metric-item");
        
        for (WebElementFacade item : metricItems) {
            String metricName = item.findElement(By.cssSelector(".metric-name")).getText();
            String metricValue = item.findElement(By.cssSelector(".metric-value")).getText();
            metrics.put(metricName, metricValue);
        }
        
        logger.info("Retrieved execution metrics for stage {}: {}", stageName, metrics);
        return metrics;
    }
    
    /**
     * Check if execution progress is displayed
     */
    public boolean isExecutionProgressDisplayed() {
        return isElementDisplayed(EXECUTION_PROGRESS);
    }
    
    /**
     * Get execution progress percentage
     */
    public int getExecutionProgress() {
        String progressText = safeGetText(EXECUTION_PROGRESS);
        return Integer.parseInt(progressText.replaceAll("[^0-9]", ""));
    }
    
    /**
     * Check if stage has errors
     */
    public boolean hasStageErrors(String stageName) {
        By errorLocator = By.xpath(String.format("//div[contains(., '%s')]//div[@data-testid='error-details']", stageName));
        return isElementDisplayed(errorLocator);
    }
    
    /**
     * Get error details for a stage
     */
    public String getStageErrorDetails(String stageName) {
        By errorLocator = By.xpath(String.format("//div[contains(., '%s')]//div[@data-testid='error-details']", stageName));
        return safeGetText(errorLocator);
    }
    
    /**
     * Configure custom data source
     */
    public void configureCustomDataSource() {
        safeClick(ADD_DATA_SOURCE_BUTTON);
        waitForElementToBeVisible(DATA_SOURCE_TYPE_SELECTOR);
        logger.info("Started configuring custom data source");
    }
    
    /**
     * Select data source type
     */
    public void selectDataSourceType(String dataSourceType) {
        safeClick(DATA_SOURCE_TYPE_SELECTOR);
        By typeOption = By.xpath(String.format("//option[text()='%s']", dataSourceType));
        safeClick(typeOption);
        logger.info("Selected data source type: {}", dataSourceType);
    }
    
    /**
     * Upload data file
     */
    public void uploadDataFile(String filePath) {
        find(FILE_UPLOAD_INPUT).sendKeys(filePath);
        waitForElementToBeVisible(DATA_SOURCE_PREVIEW);
        logger.info("Uploaded data file: {}", filePath);
    }
    
    /**
     * Configure schema mapping
     */
    public void configureSchemaMapping() {
        waitForElementToBeVisible(SCHEMA_MAPPING_SECTION);
        // Schema mapping configuration would depend on the specific UI
        logger.info("Configured schema mapping");
    }
    
    /**
     * Check if data preview is displayed
     */
    public boolean isDataPreviewDisplayed() {
        return isElementDisplayed(DATA_SOURCE_PREVIEW);
    }
    
    /**
     * Get data preview records count
     */
    public int getDataPreviewRecordsCount() {
        List<WebElementFacade> previewRows = find(DATA_SOURCE_PREVIEW).thenFindAll("tr");
        return previewRows.size() - 1; // Subtract header row
    }
    
    /**
     * Complete data source configuration
     */
    public void completeDataSourceConfiguration() {
        safeClick(CONFIGURE_DATA_SOURCE_BUTTON);
        waitForLoadingToComplete();
        logger.info("Completed data source configuration");
    }
    
    /**
     * Get data quality metrics
     */
    public Map<String, String> getDataQualityMetrics(String stageName) {
        Map<String, String> qualityMetrics = new HashMap<>();
        
        By qualitySection = By.xpath(String.format("//div[contains(., '%s')]//div[@data-testid='data-quality-metrics']", stageName));
        
        if (isElementDisplayed(qualitySection)) {
            WebElementFacade qualityElement = find(qualitySection);
            List<WebElementFacade> metricItems = qualityElement.thenFindAll(".quality-metric");
            
            for (WebElementFacade item : metricItems) {
                String metricName = item.findElement(By.cssSelector(".metric-name")).getText();
                String metricValue = item.findElement(By.cssSelector(".metric-value")).getText();
                qualityMetrics.put(metricName, metricValue);
            }
        }
        
        logger.info("Retrieved data quality metrics for stage {}: {}", stageName, qualityMetrics);
        return qualityMetrics;
    }
    
    /**
     * Check if validation results are displayed
     */
    public boolean areValidationResultsDisplayed(String stageName) {
        By validationLocator = By.xpath(String.format("//div[contains(., '%s')]//div[@data-testid='validation-results']", stageName));
        return isElementDisplayed(validationLocator);
    }
    
    /**
     * Get validation results
     */
    public List<String> getValidationResults(String stageName) {
        By validationLocator = By.xpath(String.format("//div[contains(., '%s')]//div[@data-testid='validation-results']", stageName));
        List<WebElementFacade> resultItems = find(validationLocator).thenFindAll(".validation-item");
        
        return resultItems.stream()
                .map(WebElementFacade::getText)
                .toList();
    }
    
    /**
     * Get pipeline title
     */
    public String getPipelineTitle() {
        return safeGetText(PIPELINE_TITLE);
    }
    
    /**
     * Check if stage configuration is displayed
     */
    public boolean isStageConfigurationDisplayed(String stageName) {
        WebElementFacade stage = getPipelineStage(stageName);
        stage.click();
        return isElementDisplayed(STAGE_CONFIGURATION);
    }
}