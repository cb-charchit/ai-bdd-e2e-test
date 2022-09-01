@merchant=Rajat
Feature: Fetch Third party Integration Configuration

  Scenario Outline: Admin fetches Third party Integration Configuration
    Given Rajat is an admin of the domain
    When he attempts to fetch particular Integration Configuration
      | integration_name   |
      | <integration_name> |
    Then he should be able to fetch Integration Configuration
    Examples:
      | integration_name |
      | quickbooks       |
      | netsuite         |




