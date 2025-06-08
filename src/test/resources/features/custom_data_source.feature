@datasource @mock-data
Feature: Custom Data Source Configuration and Mock Data Testing
  As a data engineer
  I want to configure custom data sources with mock data
  So that I can test pipelines with controlled test datasets

  Background:
    Given I am successfully logged into Prophecy
    And I have access to the pipeline workspace
    And I have opened the pipeline "Customer Data Processing Pipeline"

  @datasource @json @smoke
  Scenario: Configure JSON data source with mock data
    Given I want to configure a custom data source
    When I select "JSON" as the data source type
    And I upload the mock JSON data file "customer_test_data.json"
    And I configure the JSON schema mapping
    Then the data source should be successfully configured
    And I should see the data preview with sample records
    And the schema should be automatically detected

  @datasource @csv @smoke
  Scenario: Configure CSV data source with mock data
    Given I want to configure a custom data source
    When I select "CSV" as the data source type
    And I upload the mock CSV data file "customer_test_data.csv"
    And I configure the CSV parsing options:
      | Option          | Value     |
      | Delimiter       | ,         |
      | Header Row      | true      |
      | Quote Character | "         |
      | Encoding        | UTF-8     |
    Then the data source should be successfully configured
    And I should see the data preview with sample records
    And the column types should be correctly inferred

  @datasource @database @mock
  Scenario: Configure mock database data source
    Given I want to configure a custom data source
    When I select "Database" as the data source type
    And I configure a mock database connection with test data
    And I specify the SQL query for data extraction
    Then the data source should be successfully configured
    And I should see the query results preview
    And the connection should be validated

  @execution @mock-data @critical
  Scenario: Execute pipeline with JSON mock data and validate results
    Given I have configured a JSON data source with mock data:
      """
      {
        "customers": [
          {
            "id": 1,
            "name": "John Doe",
            "email": "john.doe@example.com",
            "age": 30,
            "city": "New York",
            "purchase_amount": 150.50
          },
          {
            "id": 2,
            "name": "Jane Smith",
            "email": "jane.smith@example.com",
            "age": 25,
            "city": "Los Angeles",
            "purchase_amount": 200.75
          },
          {
            "id": 3,
            "name": "Bob Johnson",
            "email": "bob.johnson@example.com",
            "age": 35,
            "city": "Chicago",
            "purchase_amount": 99.99
          }
        ]
      }
      """
    When I execute the pipeline with this mock data
    Then the "Data Ingestion" stage should process 3 records
    And the "Data Cleansing" stage should validate email formats
    And the "Data Enrichment" stage should add derived fields
    And the "Data Aggregation" stage should group by city
    And the final output should contain:
      | City        | Customer Count | Total Purchase Amount |
      | New York    | 1              | 150.50               |
      | Los Angeles | 1              | 200.75               |
      | Chicago     | 1              | 99.99                |

  @execution @mock-data @validation
  Scenario: Execute pipeline with CSV mock data and validate transformations
    Given I have configured a CSV data source with mock data containing:
      | customer_id | customer_name | email                    | age | city        | purchase_amount |
      | 1           | John Doe      | john.doe@example.com     | 30  | New York    | 150.50         |
      | 2           | Jane Smith    | jane.smith@example.com   | 25  | Los Angeles | 200.75         |
      | 3           | Bob Johnson   | bob.johnson@example.com  | 35  | Chicago     | 99.99          |
      | 4           | Alice Brown   | invalid-email            | 28  | Boston      | 175.25         |
      | 5           | Charlie Davis | charlie.davis@example.com| 42  | Seattle     | 300.00         |
    When I execute the pipeline with this mock data
    Then the "Data Ingestion" stage should process 5 records
    And the "Data Cleansing" stage should:
      | Action                    | Expected Result |
      | Validate email formats    | 1 invalid email |
      | Filter invalid records    | 4 valid records |
      | Standardize city names    | All cities normalized |
    And the "Data Enrichment" stage should:
      | Enrichment Rule           | Expected Result |
      | Add age group category    | Young/Middle/Senior |
      | Add purchase tier         | Low/Medium/High |
      | Add customer segment      | Based on age and purchase |
    And the "Data Aggregation" stage should produce summary statistics
    And all assertions should pass with detailed validation reports

  @datasource @schema-validation
  Scenario: Validate schema compatibility with mock data
    Given I have a pipeline expecting a specific data schema
    When I configure a custom data source with mock data
    And the mock data schema doesn't match the expected schema
    Then I should see schema validation warnings
    And I should be provided with schema mapping suggestions
    And I should be able to apply schema transformations
    When I apply the suggested schema mappings
    Then the pipeline should accept the mock data
    And the execution should proceed successfully

  @datasource @data-generation
  Scenario: Generate synthetic test data for pipeline testing
    Given I want to test the pipeline with large datasets
    When I use the synthetic data generator
    And I specify the data generation parameters:
      | Parameter       | Value           |
      | Record Count    | 10000          |
      | Data Pattern    | Customer Data   |
      | Null Percentage | 5%             |
      | Invalid Records | 2%             |
    Then synthetic test data should be generated
    And the data should match the specified patterns
    And I should be able to use this data as a pipeline source
    When I execute the pipeline with synthetic data
    Then all stages should handle the large dataset appropriately
    And performance metrics should be within acceptable limits