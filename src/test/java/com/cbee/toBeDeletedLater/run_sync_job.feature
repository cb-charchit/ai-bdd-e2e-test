@merchant=Rajat
@ignore
Feature: Run Sync Job for Third party Integration

  Scenario: Admin runs sync job for Third party Integration
    Given Rajat is an admin of the domain
    When he attempts to run sync job for <integration_name>
      | integration_name |
      | quickbooks       |
    Then he checked and found that sync job completed successfully



