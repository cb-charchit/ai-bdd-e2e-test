@merchant=adminBot
@ignore
Feature: Run Sync Job for Third party Integration

  Scenario: Admin runs sync job for Third party Integration
    Given adminBot is a merchant
    When adminBot attempts to run sync job for <integration_name>
      | integration_name |
      | quickbooks       |
    Then adminBot should be able to run sync successfully



