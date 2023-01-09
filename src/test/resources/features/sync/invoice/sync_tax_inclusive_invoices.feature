@merchant=Rajat @integration=quickbooks
Feature: Sync Invoices with Tax Inclusive Plans to Quickbooks

  Scenario: Rajat syncs ChargeBee invoice to QuickBooks with a FlatFee Pricing Model
    Given Rajat is an admin of ChargeBee site
    And he has a customer "Malasiya-Region" belong to "tax inclusive region"
    And he has a <item> with <amount> and currency "USD"
      | item           | amount |
      | FlatFee-Plan   | 100    |
      | FlatFee-Addon  | 10     |
      | FlatFee-Charge | 10     |
    And he creates a new subscription for "Malasiya-Region" with the following values
      | item_price_ids | FlatFee-Plan-USD-Monthly, FlatFee-Addon-USD-Monthly, FlatFee-Charge-USD-Monthly |
    When he attempts to run sync job for Quickbooks
    Then invoice with amount 132 should be synced to Quickbooks

  Scenario: Rajat syncs ChargeBee invoice to QuickBooks with a FlatFee Pricing Model
    Given Rajat is an admin of ChargeBee site
    And he has a customer "Malasiya-Region" belong to "tax inclusive region"
    And he has a <item> with <amount> and currency "USD"
      | item           | amount |
      | FlatFee-Plan   | 100    |
      | FlatFee-Addon  | 10     |
      | FlatFee-Charge | 10     |
    And he creates a new subscription for "Malasiya-Region" with the following values
      | item_price_ids | FlatFee-Plan-USD-Monthly, FlatFee-Addon-USD-Monthly, FlatFee-Charge-USD-Monthly |
    When he attempts to run sync job for Quickbooks
    Then invoice with amount 132 should be synced to Quickbooks