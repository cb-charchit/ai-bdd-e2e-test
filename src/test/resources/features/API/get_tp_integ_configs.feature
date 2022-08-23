@merchant=adminBot
Feature: Fetch Third party Integration Configuration

  Scenario: Admin fetches Third party Integration Configuration
    Given adminBot is a merchant
    When adminBot attempts to fetch <integration_name> Integration Configuration
      | integration_name |
      | quickbooks       |
    Then adminBot should able to fetch Integration Configuration



