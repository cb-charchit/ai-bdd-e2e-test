@merchant=adminBot
Feature: Sync Invoice for Third party Integration

  Scenario: Admin syncs invoice for Third party Integration
    Given adminBot is a merchant
    And the merchant creates a new customer "Neha" with the email address "neha.s@example.com" and a "valid_visa_card"
    And the merchant has a plan "e2e_poc_plan" with the monthly amount of 10 USD
    And the merchant creates a "new" subscription for "Madhu" with the following values
      | item_price_ids                | e2e_poc_plan-USD-Monthly |
    When adminBot attempts to run sync job for <integration_name>
      | integration_name |
      | quickbooks       |
    Then the customer and invoice should be synced to third party



