@merchant=Rajat
Feature: Perform Unlink Integration Operation

  Scenario: Rajat unlinks the integration via UI
    Given Rajat is an admin of ChargeBee site
    When he attempts to unlink "quickbooks"
    Then he should be able to unlink the integration successfully

