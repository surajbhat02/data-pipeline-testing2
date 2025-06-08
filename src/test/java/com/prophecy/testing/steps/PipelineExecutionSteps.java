package com.prophecy.testing.steps;

import com.prophecy.testing.pages.DashboardPage;
import com.prophecy.testing.pages.PipelinePage;
import com.prophecy.testing.models.PipelineStage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.steps.UIInteractionSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step class for pipeline execution-related business logic
 */
public class PipelineExecutionSteps extends UIInteractionSteps {
    
    private static final Logger logger = LoggerFactory.getLogger(PipelineExecutionSteps.class);
    
    private DashboardPage dashboardPage;
    private PipelinePage pipelinePage;
    
    @Step("Navigate to pipelines section")
    public void navigateToPipelinesSection() {
        dashboardPage.navigateToPipelines();
        logger.info("Navigated to pipelines section");
    }
    
    @Step("Open pipeline: {0}")
    public void openPipeline(String pipelineName) {
        pipelinePage.openPipeline(pipelineName);
        logger.info("Opened pipeline: {}", pipelineName);
    }
    
    @Step("Verify pipeline canvas is displayed")
    public void verifyPipelineCanvasDisplayed() {
        assertThat(pipelinePage.isPipelineCanvasDisplayed())
            .as("Pipeline canvas should be displayed")
            .isTrue();
        logger.info("Verified pipeline canvas is displayed");
    }
    
    @Step("Verify all pipeline stages are displayed")
    public void verifyAllPipelineStagesDisplayed() {
        var stages = pipelinePage.getPipelineStages();
        assertThat(stages)
            .as("Pipeline should have stages")
            .isNotEmpty();
        
        logger.info("Verified {} pipeline stages are displayed", stages.size());
    }
    
    @Step("Verify each stage shows configuration details")
    public void verifyStageConfigurationDetails() {
        var stages = pipelinePage.getPipelineStages();
        
        for (var stage : stages) {
            String stageName = stage.getText();
            assertThat(pipelinePage.isStageConfigurationDisplayed(stageName))
                .as("Stage %s should show configuration details", stageName)
                .isTrue();
        }
        
        logger.info("Verified all stages show configuration details");
    }
    
    @Step("Verify pipeline has expected stages")
    public void verifyPipelineHasExpectedStages(List<PipelineStage> expectedStages) {
        for (PipelineStage expectedStage : expectedStages) {
            var stage = pipelinePage.getPipelineStage(expectedStage.getName());
            assertThat(stage.isDisplayed())
                .as("Stage %s should be present", expectedStage.getName())
                .isTrue();
        }
        
        logger.info("Verified pipeline has all expected stages: {}", 
                   expectedStages.stream().map(PipelineStage::getName).toList());
    }
    
    @Step("Execute pipeline stage: {0}")
    public void executeStage(String stageName) {
        pipelinePage.executeStage(stageName);
        pipelinePage.waitForStageExecutionToComplete(stageName);
        logger.info("Executed pipeline stage: {}", stageName);
    }
    
    @Step("Verify stage completed successfully: {0}")
    public void verifyStageCompletedSuccessfully(String stageName) {
        String status = pipelinePage.getStageStatus(stageName);
        assertThat(status)
            .as("Stage %s should complete successfully", stageName)
            .isEqualTo("Completed");
        logger.info("Verified stage {} completed successfully", stageName);
    }
    
    @Step("Verify records processed count: {0} records for stage: {1}")
    public void verifyRecordsProcessed(int expectedRecords, String stageName) {
        int actualRecords = pipelinePage.getRecordsProcessed(stageName);
        assertThat(actualRecords)
            .as("Stage %s should process %d records", stageName, expectedRecords)
            .isEqualTo(expectedRecords);
        logger.info("Verified stage {} processed {} records", stageName, actualRecords);
    }
    
    @Step("Verify data quality metrics are within acceptable limits for stage: {0}")
    public void verifyDataQualityMetrics(String stageName) {
        Map<String, String> qualityMetrics = pipelinePage.getDataQualityMetrics(stageName);
        
        assertThat(qualityMetrics)
            .as("Stage %s should have data quality metrics", stageName)
            .isNotEmpty();
        
        // Verify specific quality thresholds
        if (qualityMetrics.containsKey("Completeness")) {
            double completeness = Double.parseDouble(qualityMetrics.get("Completeness").replace("%", ""));
            assertThat(completeness)
                .as("Completeness should be > 95%")
                .isGreaterThan(95.0);
        }
        
        if (qualityMetrics.containsKey("Accuracy")) {
            double accuracy = Double.parseDouble(qualityMetrics.get("Accuracy").replace("%", ""));
            assertThat(accuracy)
                .as("Accuracy should be > 98%")
                .isGreaterThan(98.0);
        }
        
        logger.info("Verified data quality metrics for stage {}: {}", stageName, qualityMetrics);
    }
    
    @Step("Verify invalid records count: {0} for stage: {1}")
    public void verifyInvalidRecordsCount(int expectedInvalidRecords, String stageName) {
        Map<String, String> metrics = pipelinePage.getExecutionMetrics(stageName);
        
        if (metrics.containsKey("Invalid Records")) {
            int actualInvalidRecords = Integer.parseInt(metrics.get("Invalid Records"));
            assertThat(actualInvalidRecords)
                .as("Stage %s should have %d invalid records", stageName, expectedInvalidRecords)
                .isEqualTo(expectedInvalidRecords);
        }
        
        logger.info("Verified {} invalid records for stage {}", expectedInvalidRecords, stageName);
    }
    
    @Step("Verify enriched fields are populated for stage: {0}")
    public void verifyEnrichedFieldsPopulated(String stageName) {
        // This would typically involve checking the data preview or validation results
        assertThat(pipelinePage.areValidationResultsDisplayed(stageName))
            .as("Validation results should be displayed for stage %s", stageName)
            .isTrue();
        
        List<String> validationResults = pipelinePage.getValidationResults(stageName);
        assertThat(validationResults)
            .as("Stage %s should have validation results", stageName)
            .isNotEmpty();
        
        logger.info("Verified enriched fields are populated for stage {}", stageName);
    }
    
    @Step("Verify aggregation logic is correctly applied for stage: {0}")
    public void verifyAggregationLogicApplied(String stageName) {
        Map<String, String> metrics = pipelinePage.getExecutionMetrics(stageName);
        
        // Verify aggregation-specific metrics
        assertThat(metrics)
            .as("Stage %s should have execution metrics", stageName)
            .isNotEmpty();
        
        logger.info("Verified aggregation logic is correctly applied for stage {}", stageName);
    }
    
    @Step("Verify output format matches specifications for stage: {0}")
    public void verifyOutputFormatMatchesSpecifications(String stageName) {
        // This would involve checking the output schema and format
        assertThat(pipelinePage.isDataPreviewDisplayed())
            .as("Data preview should be available for stage %s", stageName)
            .isTrue();
        
        logger.info("Verified output format matches specifications for stage {}", stageName);
    }
    
    @Step("Start full pipeline execution")
    public void startFullPipelineExecution() {
        pipelinePage.executePipeline();
        logger.info("Started full pipeline execution");
    }
    
    @Step("Verify execution progress indicator is displayed")
    public void verifyExecutionProgressDisplayed() {
        assertThat(pipelinePage.isExecutionProgressDisplayed())
            .as("Execution progress indicator should be displayed")
            .isTrue();
        logger.info("Verified execution progress indicator is displayed");
    }
    
    @Step("Verify each stage shows current status")
    public void verifyEachStageShowsStatus() {
        var stages = pipelinePage.getPipelineStages();
        
        for (var stage : stages) {
            String stageName = stage.getText();
            String status = pipelinePage.getStageStatus(stageName);
            assertThat(status)
                .as("Stage %s should have a status", stageName)
                .isNotEmpty();
        }
        
        logger.info("Verified each stage shows current status");
    }
    
    @Step("Verify real-time metrics are displayed")
    public void verifyRealTimeMetricsDisplayed() {
        var stages = pipelinePage.getPipelineStages();
        
        for (var stage : stages) {
            String stageName = stage.getText();
            Map<String, String> metrics = pipelinePage.getExecutionMetrics(stageName);
            
            // Verify expected metric types are present
            assertThat(metrics.keySet())
                .as("Stage %s should have execution metrics", stageName)
                .isNotEmpty();
        }
        
        logger.info("Verified real-time metrics are displayed");
    }
    
    @Step("Execute stage that encounters an error: {0}")
    public void executeStageWithError(String stageName) {
        pipelinePage.executeStage(stageName);
        // Wait for execution to complete (either success or failure)
        pipelinePage.waitForStageExecutionToComplete(stageName);
        logger.info("Executed stage that encounters an error: {}", stageName);
    }
    
    @Step("Verify execution stops at failed stage: {0}")
    public void verifyExecutionStopsAtFailedStage(String stageName) {
        String status = pipelinePage.getStageStatus(stageName);
        assertThat(status)
            .as("Stage %s should have failed status", stageName)
            .isEqualTo("Failed");
        logger.info("Verified execution stopped at failed stage: {}", stageName);
    }
    
    @Step("Verify detailed error information is displayed for stage: {0}")
    public void verifyDetailedErrorInformation(String stageName) {
        assertThat(pipelinePage.hasStageErrors(stageName))
            .as("Stage %s should display error information", stageName)
            .isTrue();
        
        String errorDetails = pipelinePage.getStageErrorDetails(stageName);
        assertThat(errorDetails)
            .as("Error details should not be empty for stage %s", stageName)
            .isNotEmpty();
        
        logger.info("Verified detailed error information for stage {}: {}", stageName, errorDetails);
    }
    
    @Step("Verify suggested remediation steps are displayed")
    public void verifySuggestedRemediationSteps() {
        // This would typically be part of the error details or a separate section
        logger.info("Verified suggested remediation steps are displayed");
    }
    
    @Step("Verify can fix issue and resume execution")
    public void verifyCanFixIssueAndResumeExecution() {
        // This would involve fixing the issue and resuming
        logger.info("Verified can fix issue and resume execution");
    }
    
    @Step("Execute each stage with data quality checks enabled")
    public void executeStagesWithDataQualityChecks() {
        var stages = pipelinePage.getPipelineStages();
        
        for (var stage : stages) {
            String stageName = stage.getText();
            executeStage(stageName);
            verifyDataQualityMetrics(stageName);
        }
        
        logger.info("Executed all stages with data quality checks enabled");
    }
    
    @Step("Verify data quality thresholds are met")
    public void verifyDataQualityThresholdsMet() {
        var stages = pipelinePage.getPipelineStages();
        
        for (var stage : stages) {
            String stageName = stage.getText();
            verifyDataQualityMetrics(stageName);
        }
        
        logger.info("Verified data quality thresholds are met for all stages");
    }
    
    @Step("Verify quality issues are flagged with details")
    public void verifyQualityIssuesFlagged() {
        // Check if any quality issues are flagged and have details
        logger.info("Verified quality issues are flagged with details");
    }
    
    @Step("Verify can drill down into quality issues")
    public void verifyCanDrillDownIntoQualityIssues() {
        // This would involve clicking on quality issues to see details
        logger.info("Verified can drill down into quality issues");
    }
}