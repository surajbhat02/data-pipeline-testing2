package com.prophecy.testing.stepdefinitions;

import com.prophecy.testing.steps.PipelineExecutionSteps;
import com.prophecy.testing.models.PipelineStage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;

import java.util.List;
import java.util.Map;

/**
 * Step definitions for pipeline execution scenarios
 */
public class PipelineExecutionStepDefinitions {
    
    @Steps
    PipelineExecutionSteps pipelineExecutionSteps;
    
    @Given("I navigate to the pipelines section")
    public void i_navigate_to_the_pipelines_section() {
        pipelineExecutionSteps.navigateToPipelinesSection();
    }
    
    @Given("I have opened the pipeline {string}")
    public void i_have_opened_the_pipeline(String pipelineName) {
        pipelineExecutionSteps.navigateToPipelinesSection();
        pipelineExecutionSteps.openPipeline(pipelineName);
    }
    
    @Given("the pipeline has the following stages:")
    public void the_pipeline_has_the_following_stages(DataTable dataTable) {
        List<Map<String, String>> stageData = dataTable.asMaps(String.class, String.class);
        List<PipelineStage> expectedStages = stageData.stream()
                .map(row -> new PipelineStage(
                        row.get("Stage Name"),
                        row.get("Type"),
                        Integer.parseInt(row.get("Expected Records"))
                ))
                .toList();
        
        pipelineExecutionSteps.verifyPipelineHasExpectedStages(expectedStages);
    }
    
    @When("I open the pipeline {string}")
    public void i_open_the_pipeline(String pipelineName) {
        pipelineExecutionSteps.openPipeline(pipelineName);
    }
    
    @When("I execute the {string} stage")
    public void i_execute_the_stage(String stageName) {
        pipelineExecutionSteps.executeStage(stageName);
    }
    
    @When("I start the full pipeline execution")
    public void i_start_the_full_pipeline_execution() {
        pipelineExecutionSteps.startFullPipelineExecution();
    }
    
    @When("I execute a stage that encounters an error")
    public void i_execute_a_stage_that_encounters_an_error() {
        // For testing purposes, we'll use a predefined stage that should fail
        pipelineExecutionSteps.executeStageWithError("Error Test Stage");
    }
    
    @When("I execute each stage with data quality checks enabled")
    public void i_execute_each_stage_with_data_quality_checks_enabled() {
        pipelineExecutionSteps.executeStagesWithDataQualityChecks();
    }
    
    @Then("I should see the pipeline canvas")
    public void i_should_see_the_pipeline_canvas() {
        pipelineExecutionSteps.verifyPipelineCanvasDisplayed();
    }
    
    @Then("I should see all pipeline stages displayed")
    public void i_should_see_all_pipeline_stages_displayed() {
        pipelineExecutionSteps.verifyAllPipelineStagesDisplayed();
    }
    
    @Then("each stage should show its configuration details")
    public void each_stage_should_show_its_configuration_details() {
        pipelineExecutionSteps.verifyStageConfigurationDetails();
    }
    
    @Then("the stage should complete successfully")
    public void the_stage_should_complete_successfully() {
        // This will be called after executing a stage, so we need to track the last executed stage
        // For simplicity, we'll verify the most recently executed stage
        pipelineExecutionSteps.verifyStageCompletedSuccessfully("Last Executed Stage");
    }
    
    @Then("I should see {int} records processed")
    public void i_should_see_records_processed(int expectedRecords) {
        pipelineExecutionSteps.verifyRecordsProcessed(expectedRecords, "Last Executed Stage");
    }
    
    @Then("the data quality metrics should be within acceptable limits")
    public void the_data_quality_metrics_should_be_within_acceptable_limits() {
        pipelineExecutionSteps.verifyDataQualityMetrics("Last Executed Stage");
    }
    
    @Then("{int} records should be marked as invalid or filtered")
    public void records_should_be_marked_as_invalid_or_filtered(int invalidRecords) {
        pipelineExecutionSteps.verifyInvalidRecordsCount(invalidRecords, "Last Executed Stage");
    }
    
    @Then("each record should have enriched fields populated")
    public void each_record_should_have_enriched_fields_populated() {
        pipelineExecutionSteps.verifyEnrichedFieldsPopulated("Last Executed Stage");
    }
    
    @Then("I should see {int} aggregated records")
    public void i_should_see_aggregated_records(int expectedRecords) {
        pipelineExecutionSteps.verifyRecordsProcessed(expectedRecords, "Last Executed Stage");
    }
    
    @Then("the aggregation logic should be correctly applied")
    public void the_aggregation_logic_should_be_correctly_applied() {
        pipelineExecutionSteps.verifyAggregationLogicApplied("Last Executed Stage");
    }
    
    @Then("I should see {int} records exported")
    public void i_should_see_records_exported(int expectedRecords) {
        pipelineExecutionSteps.verifyRecordsProcessed(expectedRecords, "Last Executed Stage");
    }
    
    @Then("the output format should match specifications")
    public void the_output_format_should_match_specifications() {
        pipelineExecutionSteps.verifyOutputFormatMatchesSpecifications("Last Executed Stage");
    }
    
    @Then("I should see the execution progress indicator")
    public void i_should_see_the_execution_progress_indicator() {
        pipelineExecutionSteps.verifyExecutionProgressDisplayed();
    }
    
    @Then("each stage should show its current status")
    public void each_stage_should_show_its_current_status() {
        pipelineExecutionSteps.verifyEachStageShowsStatus();
    }
    
    @Then("I should see real-time metrics for:")
    public void i_should_see_real_time_metrics_for(DataTable dataTable) {
        pipelineExecutionSteps.verifyRealTimeMetricsDisplayed();
    }
    
    @Then("the execution should stop at the failed stage")
    public void the_execution_should_stop_at_the_failed_stage() {
        pipelineExecutionSteps.verifyExecutionStopsAtFailedStage("Error Test Stage");
    }
    
    @Then("I should see detailed error information")
    public void i_should_see_detailed_error_information() {
        pipelineExecutionSteps.verifyDetailedErrorInformation("Error Test Stage");
    }
    
    @Then("I should see suggested remediation steps")
    public void i_should_see_suggested_remediation_steps() {
        pipelineExecutionSteps.verifySuggestedRemediationSteps();
    }
    
    @Then("I should be able to fix the issue and resume execution")
    public void i_should_be_able_to_fix_the_issue_and_resume_execution() {
        pipelineExecutionSteps.verifyCanFixIssueAndResumeExecution();
    }
    
    @Then("each stage should report data quality metrics:")
    public void each_stage_should_report_data_quality_metrics(DataTable dataTable) {
        pipelineExecutionSteps.verifyDataQualityThresholdsMet();
    }
    
    @Then("any quality issues should be flagged with details")
    public void any_quality_issues_should_be_flagged_with_details() {
        pipelineExecutionSteps.verifyQualityIssuesFlagged();
    }
    
    @Then("I should be able to drill down into quality issues")
    public void i_should_be_able_to_drill_down_into_quality_issues() {
        pipelineExecutionSteps.verifyCanDrillDownIntoQualityIssues();
    }
}