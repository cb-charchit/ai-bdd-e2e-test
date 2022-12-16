@merchant=Rajat
Feature: Perform Full Sync Now operation

  Scenario: Rajat syncs entities via Sync Now button
    Given Rajat is an admin of ChargeBee site
    When he attempts to perform full sync by clicking on Sync Now button
    Then he should be able to perform full sync successfully

