@merchant=Rajat @integration=quickbooks
Feature: Sync Invoices to Quickbooks
  Scenario Outline: Rajat syncs ChargeBee invoice to QuickBooks with a FlatFee Pricing Model
    Given Rajat is an admin of ChargeBee site
    And he has a customer "<customer_id>" with the email address "john@example.com"
    #And he has a customer "John" with the email address "john@example.com"
    And he has a <item> with <amount> and currency "USD"
      | item           | amount |
      | FlatFee-Plan   | 100    |
      | FlatFee-Addon  | 10     |
      | FlatFee-Charge | 10     |
    And he creates a new subscription for "<customer_id>" with the following values
      | item_price_ids | FlatFee-Plan-USD-Monthly, FlatFee-Addon-USD-Monthly, FlatFee-Charge-USD-Monthly |
    When he attempts to run sync job for Quickbooks
    #Then invoice with amount 120 should be synced to Quickbooks
    Then invoice with amount <excepted_amount> should be synced to Quickbooks
    Examples:
      | customer_id        | excepted_amount |
      | John               | 120             |
      | US-Region-Customer | 132             |
      | Malasiya-Region    | 132             |
