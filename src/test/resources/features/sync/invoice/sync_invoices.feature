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
      Then invoice should be synced to Quickbooks

    @Ignore
    Scenario: Rajat syncs ChargeBee invoices to QuickBooks with tiered plan
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
      Then invoice should be synced to Quickbooks




