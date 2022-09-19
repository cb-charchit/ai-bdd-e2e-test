@merchant=Rajat
Feature: Verify Invoice for QBO

  Scenario: Admin verifies ChargeBee invoice is synced to QBO
    Given Rajat is an admin of the domain
    And he has synced invoice "inv" to QBO
    When he attempts to verify if invoice is present in "quickbooks"
    Then the invoice should be present in QBO



