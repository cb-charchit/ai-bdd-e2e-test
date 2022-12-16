@merchant=Rajat
Feature: Configure tracking categories for Invoice Line items

  Scenario: Rajat configures tracking categories for Invoice Line items
    Given Rajat is an admin of ChargeBee site
    When he attempts to configure tracking categories for the following line items
      | Invoice Line Item | Class Name    | UI Id      |
      | One time charges  | adhocClass    | adhoc     |
      | Discounts         | discountClass | discounts |
      | Round off         | roundOffClass | round_off |
    Then tracking category should be configured properly

