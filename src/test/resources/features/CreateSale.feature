Feature: Crate sale
  As a user
  I want to create a sale

  Scenario: Create new sale when not authenticated
    Given I'm not logged in
    When I create a new sale
    Then The response code is 401
    And It has not been created any sale

  Scenario: Create new sale
    Given I login as "admin" with password "password"
    When I create a new sale
    Then The response code is 201
    And It has been created a new sale

  Scenario: Create new sale with relationed bid
    Given I login as "admin" with password "password"
    And I make a bid with a price of "10.0" for the NFT offer
    When I create a new sale with the previous bid
    Then The response code is 201
    And It has been created a new sale

