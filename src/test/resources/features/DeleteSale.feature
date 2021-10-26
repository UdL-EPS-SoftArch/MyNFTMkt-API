Feature: Delete sale
  As a admin
  I want to delete a sale

  Background:
    Given I login as "admin" with password "password"
    And I make a bid with a price of "10.0" for the NFT offer
    And I create a new sale with the previous bid

  Scenario: Admin deletes a sale
    Given I login as "admin" with password "password"
    When I delete a sale with id "2"
    Then The response code is 204

  Scenario: Delete a sale that does not exist
    And I login as "admin" with password "password"
    When I delete a sale with id "99"
    Then The response code is 404

  Scenario: Delete a sale when not authenticated
    Given I'm not logged in
    When I delete a sale with id "2"
    Then The response code is 401


