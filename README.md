# Prophecy Data Pipeline Testing Framework

A comprehensive testing framework for Prophecy data pipelines using Cucumber with Serenity BDD, following the design pattern: **Gherkin → Step Definitions → Step Classes → Page Objects**.

## 🏗️ Architecture Overview

This framework implements a layered architecture that promotes maintainability, reusability, and clear separation of concerns:

```
Gherkin Feature Files (Business Requirements)
    ↓
Step Definitions (Cucumber Glue Code)
    ↓
Step Classes (Business Logic Layer)
    ↓
Page Objects (UI Interaction Layer)
```

## 📋 Use Cases Covered

### 1. SSO Authentication Testing
- **ADFS/SSO Login**: Automated testing of Single Sign-On authentication flow
- **Session Management**: Timeout handling and session validation
- **Security Testing**: Authentication error scenarios and retry mechanisms

### 2. Pipeline Execution Testing
- **Stage-by-Stage Execution**: Execute and validate each pipeline stage individually
- **Data Validation**: Verify record counts and data quality at each stage
- **Error Handling**: Test pipeline failure scenarios and recovery
- **Performance Monitoring**: Track execution metrics and performance indicators

### 3. Custom Data Source Testing
- **Mock Data Integration**: Configure JSON, CSV, and database data sources
- **Schema Validation**: Automatic schema detection and mapping
- **Data Quality Checks**: Comprehensive data validation and quality metrics
- **Synthetic Data Generation**: Generate test data for large-scale testing

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Chrome/Firefox browser
- Git

### Installation

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd data-pipeline-testing2
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

3. **Configure test environment:**
   ```bash
   # Set environment variables
   export TEST_USERNAME="your-test-username"
   export TEST_PASSWORD="your-test-password"
   export ENVIRONMENT="dev"  # dev, staging, prod
   ```

### Running Tests

#### Run All Tests
```bash
mvn clean verify
```

#### Run Specific Test Suites
```bash
# Smoke tests only
mvn clean verify -Dtest=SmokeTestRunner

# Authentication tests only
mvn clean verify -Dtest=AuthenticationTestRunner

# Pipeline execution tests only
mvn clean verify -Dtest=PipelineTestRunner

# Data source tests only
mvn clean verify -Dtest=DataSourceTestRunner
```

#### Run Tests by Tags
```bash
# Run critical tests
mvn clean verify -Dcucumber.filter.tags="@critical"

# Run smoke tests
mvn clean verify -Dcucumber.filter.tags="@smoke"

# Run specific feature
mvn clean verify -Dcucumber.filter.tags="@authentication"
```

#### Environment-Specific Execution
```bash
# Development environment
mvn clean verify -Penvironment=dev

# Staging environment
mvn clean verify -Penvironment=staging

# Production environment
mvn clean verify -Penvironment=prod
```

## 📁 Project Structure

```
src/
├── test/
│   ├── java/com/prophecy/testing/
│   │   ├── pages/              # Page Object Model classes
│   │   │   ├── BasePage.java
│   │   │   ├── LoginPage.java
│   │   │   ├── DashboardPage.java
│   │   │   └── PipelinePage.java
│   │   ├── steps/              # Business logic layer
│   │   │   ├── AuthenticationSteps.java
│   │   │   ├── PipelineExecutionSteps.java
│   │   │   └── DataSourceSteps.java
│   │   ├── stepdefinitions/    # Cucumber glue code
│   │   │   ├── AuthenticationStepDefinitions.java
│   │   │   ├── PipelineExecutionStepDefinitions.java
│   │   │   └── DataSourceStepDefinitions.java
│   │   ├── runners/            # Test runners
│   │   │   ├── TestRunner.java
│   │   │   ├── SmokeTestRunner.java
│   │   │   └── AuthenticationTestRunner.java
│   │   ├── models/             # Data models
│   │   │   └── PipelineStage.java
│   │   ├── utils/              # Utility classes
│   │   │   └── TestDataManager.java
│   │   └── config/             # Configuration classes
│   │       └── TestConfiguration.java
│   └── resources/
│       ├── features/           # Gherkin feature files
│       │   ├── prophecy_authentication.feature
│       │   ├── pipeline_execution.feature
│       │   └── custom_data_source.feature
│       ├── testdata/           # Test data files
│       │   ├── customer_test_data.json
│       │   └── customer_test_data.csv
│       ├── config/             # Environment configurations
│       │   ├── test.properties
│       │   ├── test-dev.properties
│       │   ├── test-staging.properties
│       │   └── test-prod.properties
│       └── logback-test.xml    # Logging configuration
├── pom.xml                     # Maven configuration
└── serenity.properties         # Serenity BDD configuration
```

## 🔧 Configuration

### Environment Configuration
The framework supports multiple environments through property files:

- `test.properties` - Default configuration
- `test-dev.properties` - Development environment
- `test-staging.properties` - Staging environment  
- `test-prod.properties` - Production environment

### Key Configuration Properties
```properties
# WebDriver settings
webdriver.base.url=https://dev.prophecy.io
webdriver.driver=chrome
headless.mode=false

# Timeouts
prophecy.sso.timeout=60000
prophecy.pipeline.execution.timeout=300000

# Data quality thresholds
data.quality.completeness.threshold=95.0
data.quality.accuracy.threshold=98.0
```

## 📊 Test Data Management

### Static Test Data
- **JSON Files**: `src/test/resources/testdata/customer_test_data.json`
- **CSV Files**: `src/test/resources/testdata/customer_test_data.csv`

### Dynamic Test Data
The `TestDataManager` utility class provides:
- Temporary file creation for dynamic test data
- JSON and CSV data validation
- Synthetic data generation capabilities

### Example Usage
```java
TestDataManager testDataManager = new TestDataManager();

// Create temporary JSON file
String jsonData = "{ \"customers\": [...] }";
String tempFile = testDataManager.createTempJSONFile(jsonData);

// Read existing test data
List<Map<String, Object>> csvData = testDataManager.readCSVFile("customer_test_data.csv");
```

## 📈 Reporting

### Serenity Reports
After test execution, comprehensive HTML reports are generated:
```bash
# Generate reports
mvn serenity:aggregate

# View reports
open target/site/serenity/index.html
```

### Report Features
- **Test Results Overview**: Pass/fail statistics and trends
- **Step-by-Step Details**: Detailed execution flow with screenshots
- **Requirements Traceability**: Link between features and test results
- **Performance Metrics**: Execution times and performance data

## 🔒 Security Considerations

### Credential Management
- Use environment variables for sensitive data
- Never commit credentials to version control
- Implement secure credential storage for CI/CD

### Example Environment Variables
```bash
export TEST_USERNAME="test.user@company.com"
export TEST_PASSWORD="SecurePassword123!"
export STAGING_TEST_USERNAME="staging.user@company.com"
export PROD_TEST_USERNAME="prod.user@company.com"
```

### SSL and Security Testing
```properties
# Enable security testing
security.testing.enabled=true
ssl.verification.enabled=true
```

## 🚀 CI/CD Integration

### Jenkins Pipeline Example
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean verify -Penvironment=${ENVIRONMENT}'
            }
        }
        stage('Reports') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/site/serenity',
                    reportFiles: 'index.html',
                    reportName: 'Serenity Report'
                ])
            }
        }
    }
}
```

### GitHub Actions Example
```yaml
name: Prophecy Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 11
        uses: actions/setup-java@v2
        with:
          java-version: '11'
      - name: Run tests
        run: mvn clean verify
        env:
          TEST_USERNAME: ${{ secrets.TEST_USERNAME }}
          TEST_PASSWORD: ${{ secrets.TEST_PASSWORD }}
```

## 🧪 Writing New Tests

### 1. Create Feature File
```gherkin
@new-feature
Feature: New Feature Testing
  As a user
  I want to test new functionality
  So that I can ensure it works correctly

  @smoke
  Scenario: Test new feature
    Given I am logged into Prophecy
    When I perform a new action
    Then I should see the expected result
```

### 2. Implement Step Definitions
```java
@When("I perform a new action")
public void i_perform_a_new_action() {
    newFeatureSteps.performNewAction();
}
```

### 3. Create Step Class
```java
@Step("Perform new action")
public void performNewAction() {
    newFeaturePage.clickNewActionButton();
    // Business logic here
}
```

### 4. Extend Page Object
```java
public void clickNewActionButton() {
    safeClick(NEW_ACTION_BUTTON);
}
```

## 🔍 Debugging and Troubleshooting

### Common Issues

1. **WebDriver Issues**
   ```bash
   # Update WebDriver
   mvn clean install -U
   ```

2. **Element Not Found**
   - Check element locators in page objects
   - Verify wait conditions
   - Enable verbose logging

3. **Authentication Failures**
   - Verify credentials in environment variables
   - Check SSO configuration
   - Review timeout settings

### Debug Mode
```bash
# Enable debug logging
mvn clean verify -Dverbose.logging=true -Ddebug.mode=true
```

### Screenshots and Logs
- Screenshots are automatically captured on failures
- Detailed logs are available in `target/logs/test.log`
- Serenity reports include step-by-step screenshots

## 🤝 Contributing

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/new-feature`
3. **Follow the established patterns**: Gherkin → Step Definitions → Step Classes → Page Objects
4. **Write comprehensive tests**: Include positive, negative, and edge cases
5. **Update documentation**: Keep README and code comments current
6. **Submit a pull request**: Include clear description of changes

### Code Standards
- Follow Java naming conventions
- Use meaningful variable and method names
- Add appropriate logging and error handling
- Include Javadoc for public methods
- Maintain consistent indentation and formatting

## 📚 Additional Resources

- [Serenity BDD Documentation](http://serenity-bdd.info/)
- [Cucumber Documentation](https://cucumber.io/docs)
- [Selenium WebDriver Documentation](https://selenium-python.readthedocs.io/)
- [Maven Documentation](https://maven.apache.org/guides/)

## 📞 Support

For questions, issues, or contributions:
- Create an issue in the repository
- Contact the QA team
- Review existing documentation and examples

---

**Happy Testing! 🎯**
