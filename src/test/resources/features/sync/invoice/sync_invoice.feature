@merchant=Rajat
Feature: Sync Invoice for Third party Integration

  Scenario: Admin syncs ChargeBee invoice to Integration
    Given Rajat is an admin of the domain
    And he creates a new customer "Neha" with the email address "neha.s@example.com" and a "valid_visa_card"
    And he has a plan "e2e_poc_plan" with the monthly amount of 10 USD
    And he creates a "new" subscription for "Neha" with the following values
      | item_price_ids                | e2e_poc_plan-USD-Monthly |
    When he attempts to run sync job for <integration_name>
      | integration_name |
      | quickbooks       |
    Then the customer and invoice should be synced to third party



