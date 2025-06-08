@pipeline @execution
Feature: Pipeline Stage-by-Stage Execution and Validation
  As a data engineer
  I want to execute data pipelines stage by stage
  So that I can validate data transformations at each step

  Background:
    Given I am successfully logged into Prophecy
    And I have access to the pipeline workspace

  @pipeline @smoke
  Scenario: Open and view pipeline structure
    Given I navigate to the pipelines section
    When I open the pipeline "Customer Data Processing Pipeline"
    Then I should see the pipeline canvas
    And I should see all pipeline stages displayed
    And each stage should show its configuration details

  @execution @validation @critical
  Scenario: Execute pipeline stage by stage with validation
    Given I have opened the pipeline "Customer Data Processing Pipeline"
    And the pipeline has the following stages:
      | Stage Name          | Type           | Expected Records |
      | Data Ingestion      | Source         | 1000            |
      | Data Cleansing      | Transform      | 950             |
      | Data Enrichment     | Transform      | 950             |
      | Data Aggregation    | Transform      | 100             |
      | Data Export         | Sink           | 100             |
    When I execute the "Data Ingestion" stage
    Then the stage should complete successfully
    And I should see 1000 records processed
    And the data quality metrics should be within acceptable limits
    When I execute the "Data Cleansing" stage
    Then the stage should complete successfully
    And I should see 950 records processed
    And 50 records should be marked as invalid or filtered
    When I execute the "Data Enrichment" stage
    Then the stage should complete successfully
    And I should see 950 records processed
    And each record should have enriched fields populated
    When I execute the "Data Aggregation" stage
    Then the stage should complete successfully
    And I should see 100 aggregated records
    And the aggregation logic should be correctly applied
    When I execute the "Data Export" stage
    Then the stage should complete successfully
    And I should see 100 records exported
    And the output format should match specifications

  @execution @monitoring
  Scenario: Monitor pipeline execution progress
    Given I have opened the pipeline "Customer Data Processing Pipeline"
    When I start the full pipeline execution
    Then I should see the execution progress indicator
    And each stage should show its current status
    And I should see real-time metrics for:
      | Metric Type        | Display Format |
      | Records Processed  | Count          |
      | Processing Speed   | Records/sec    |
      | Memory Usage       | MB             |
      | Execution Time     | Duration       |
      | Error Count        | Count          |

  @execution @error-handling
  Scenario: Handle pipeline execution errors
    Given I have opened the pipeline "Customer Data Processing Pipeline"
    When I execute a stage that encounters an error
    Then the execution should stop at the failed stage
    And I should see detailed error information
    And I should see suggested remediation steps
    And I should be able to fix the issue and resume execution

  @validation @data-quality
  Scenario: Validate data quality at each stage
    Given I have opened the pipeline "Customer Data Processing Pipeline"
    When I execute each stage with data quality checks enabled
    Then each stage should report data quality metrics:
      | Quality Metric     | Threshold |
      | Completeness       | > 95%     |
      | Accuracy           | > 98%     |
      | Consistency        | > 99%     |
      | Validity           | > 97%     |
    And any quality issues should be flagged with details
    And I should be able to drill down into quality issues