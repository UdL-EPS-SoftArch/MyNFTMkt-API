Feature: Retrieve Sale
  In order to see all sale created in the application
  As areuser i want to list all

  Background:
    Given I login as "admin" with password "password"
    And I make a bid with a price of "10.0" for the NFT offer
    And I create a new sale with the previous bid
    And I make a bid with a price of "5.0" for the NFT offer
    And I create a new sale with the previous bid

  Scenario: Retrieve all sale
    Given I login as "admin" with password "password"
    When I list all my Sale in the app.
    Then The response code is 200
    And There has been retrieved 2 Sale


  Scenario: List all Sales as user
    Given I login as "admin" with password "password"
    When I list all my Sale in the app.
    Then The response code is 200
    And There has been retrieved 2 Sale

  Scenario: List empty Sales when not authenticated
    Given I'm not logged in
    When I list all my Sale in the app.
    Then The response code is 401
