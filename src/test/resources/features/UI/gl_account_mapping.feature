@merchant=Rajat
Feature: Map GL Account for Payments,Refunds and Invoice Line items

  Scenario: Rajat maps GL Account for Payments and Refunds
    Given Rajat is an admin of ChargeBee site
    When he attempts to map GL Account for different payment gateways
      | Gateway              | Currency | Account          |
      | Chargebee            | USD      | Clearing Account |
      | Stripe               | USD      | Clearing Account |
      | Offline_Transactions | USD      | Inventory Asset  |
    Then mapping should be saved in DB

    @Ignore
  Scenario: Rajat maps GL Account for Invoice Line Items
    Given Rajat is an admin of ChargeBee site
    When he attempts to map GL Account for different invoice Line Items
      | Invoice Line Item    | Account                      | UI Id        |
      | One time charges     | Billable Expense Income      | adhoc_id     |
      | Discounts            | As configured for Plan/Addon | discounts_id |
      | Bad debts(write-off) | As configured for Plan/Addon | bad_debts_id  |
      | Round off            | Uncategorized Expense        | round_off_id |
    Then mapping should be saved in DB






