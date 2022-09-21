@merchant=Rajat @integration=quickbooks
Feature: Sync Invoices to Quickbooks

    Scenario: Rajat syncs ChargeBee invoices to QuickBooks with flatfee plan
      Given Rajat is an admin of current ChargeBee site
      And he has a customer "John" with the email address "john@example.com" and a "valid_visa_card"
      And he has a <item_type> with <unit_amount> and <quantity> and currency "USD"
        | item_type              | unit_amount | quantity |
        | flat_fee_plan          | 100         | 1        |
        | ff_recurring_addon     | 10          | 1        |
        | ff_non_recurring_addon | 10          | 1        |
      And he creates a "new" subscription for "John" with the following values
        | item_price_ids | flat_fee_plan-USD-Monthly,ff_non_recurring_addon-USD-Monthly,ff_recurring_addon-USD-Monthly |
      When he attempts to run sync job for "quickbooks"
      Then invoice with amount "120" should be synced to Quickbooks



    Scenario: Rajat syncs ChargeBee invoices to QuickBooks with per_unit plan
      Given Rajat is an admin of current ChargeBee site
      And he has a customer "John" with the email address "john@example.com" and a "valid_visa_card"
      And he has a <item_type> with <unit_amount> and <quantity> and currency "USD"
        | item_type     | unit_amount | quantity |
        | per_unit_plan | 10          | 10       |
      And he creates a "new" subscription for "John" with the following values
        | item_price_ids                     | per_unit_plan-USD-Monthly |
        | per_unit_plan-USD-Monthly.quantity | 10                        |
      When he attempts to run sync job for "quickbooks"
      Then invoice with amount "100" should be synced to Quickbooks

  Scenario Outline: Rajat syncs ChargeBee invoices to QuickBooks with metered plan
    Given Rajat is an admin of current ChargeBee site
    And he has a customer "John" with the email address "john@example.com" and a "valid_visa_card"
    And he has a metered pricing_model of quantity 250
      | tier        | price_per_unit |
      | 1 - 100     | 20             |
      | 100 - 200   | 10             |
      | 200 & above | 5              |
    And he creates a "new" subscription for "John" with the following values
      | item_price_ids |  <item_price_id>|
    When he attempts to run sync job for "quickbooks"
    Then invoice should be synced to Quickbooks with <excepted_amount>
    Examples:
      | item_price_id              | pricing_model | excepted_amount |
      | Tiered-Plan-USD-Monthly    | tiered        | 3250            |
      | StairStep-Plan-USD-Monthly | stairStep     | 5               |
      | Volume-Plan-USD-Monthly    | volume        | 1250            |





