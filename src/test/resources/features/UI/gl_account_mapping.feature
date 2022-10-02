@merchant=Rajat
Feature: Map GL Account for Payments,Refunds and Invoice Line items

  Scenario: Rajat maps GL Account for Payments and Refunds
    Given Rajat is an admin of ChargeBee site
    When he attempts to map GL Account for different payment gateways
      | Gateway              | Currency | Account          |
      | Chargebee            | USD      | Clearing Account |
      | Offline_Transactions | USD      | Inventory Asset  |
    Then mapping should be saved in DB

  #Scenario: Rajat maps GL Account for Invoice Line Items
   # Given Rajat is an admin of ChargeBee site
    #When he attempts to map GL Account for different invoice Line Items
    #Then mapping should be saved in DB






