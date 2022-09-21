@merchant=Rajat @integration=quickbooks
Feature: Sync Invoices to Quickbooks

    Scenario: Rajat syncs ChargeBee invoice to QuickBooks with a FlatFee Pricing Model
      Given Rajat is an admin of current ChargeBee site
      And he has a customer "John" with the email address "john@example.com"
      And he has a <item> with <amount> and currency "USD"
        | item           | amount |
        | FlatFee-Plan   | 100    |
        | FlatFee-Addon  | 10     |
        | FlatFee-Charge | 10     |
      And he creates a new subscription for "John" with the following values
        | item_price_ids | FlatFee-Plan-USD-Monthly, FlatFee-Addon-USD-Monthly, FlatFee-Charge-USD-Monthly |
      When he attempts to run sync job for Quickbooks
      Then invoice with amount 120 should be synced to Quickbooks


  Scenario: Rajat syncs ChargeBee invoice to QuickBooks with a PerUnit Pricing Model
    Given Rajat is an admin of current ChargeBee site
    And he has a customer "John" with the email address "john@example.com"
    And he has a <item> with <unit_amount> and <quantity> and currency "USD"
      | item           | unit_amount | quantity |
      | PerUnit-Plan   | 100         | 1        |
      | PerUnit-Addon  | 10          | 5        |
      | PerUnit-Charge | 10          | 10       |
    And he creates a new subscription for "John" with the following values
      | item_price_ids                      | PerUnit-Plan-USD-Monthly, PerUnit-Addon-USD-Monthly, PerUnit-Charge-USD-Monthly |
      | PerUnit-Plan-USD-Monthly.quantity   | 1                                                                               |
      | PerUnit-Addon-USD-Monthly.quantity  | 5                                                                               |
      | PerUnit-Charge-USD-Monthly.quantity | 10                                                                              |
    When he attempts to run sync job for Quickbooks
    Then invoice with amount 250 should be synced to Quickbooks


  Scenario Outline: Rajat syncs ChargeBee invoices to QuickBooks with Metered Pricing Model
    Given Rajat is an admin of current ChargeBee site
    And he has a customer "John" with the email address "john@example.com"
    And he has a metered pricing_model with the following tiers
      | tier        | price_per_unit |
      | 1 - 100     | 20             |
      | 101 - 200   | 10             |
      | 201 & above | 5              |
    And he creates a new subscription for "John" with the following values
      | item_price_ids                        | <item_price_id> |
      | Tiered-Plan-USD-Monthly.quantity      | 250             |
      | Tiered-Addon-USD-Monthly.quantity     | 10              |
      | Tiered-Charge-USD-Monthly.quantity    | 120             |
      | Volume-Plan-USD-Monthly.quantity      | 250             |
      | Volume-Addon-USD-Monthly.quantity     | 10              |
      | Volume-Charge-USD-Monthly.quantity    | 120             |
      | StairStep-Plan-USD-Monthly.quantity   | 250             |
      | StairStep-Addon-USD-Monthly.quantity  | 10              |
      | StairStep-Charge-USD-Monthly.quantity | 120             |
    When he attempts to run sync job for Quickbooks
    Then invoice with amount <excepted_amount> should be synced to Quickbooks
    Examples:
      | item_price_id                                                                        | excepted_amount |
      | Tiered-Plan-USD-Monthly,Tiered-Addon-USD-Monthly,Tiered-Charge-USD-Monthly           | 5650            |
      | StairStep-Plan-USD-Monthly,StairStep-Addon-USD-Monthly ,StairStep-Charge-USD-Monthly | 35              |
      | Volume-Plan-USD-Monthly,Volume-Addon-USD-Monthly ,Volume-Charge-USD-Monthly          | 2650            |








