@merchant=adminBot
Feature: Fetch Third party Integration Configuration

  Scenario Outline: Admin fetches Third party Integration Configuration
    Given adminBot is a merchant
    When adminBot attempts to fetch particular Integration Configuration
      | integration_name   |
      | <integration_name> |
    Then adminBot should able to fetch Integration Configuration
    Examples:
      | integration_name |
      | quickbooks       |
      | netsuite         |




