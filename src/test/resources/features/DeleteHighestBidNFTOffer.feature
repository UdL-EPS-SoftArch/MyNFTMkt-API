Feature: DELETE a Highest Bid NFT Offer

  Background:
    Given There is a registered user with username "user1" and password "password" and email "user1@sample.app"
    Given I login as "user1" with password "password"
    When I make Highest Bid NFT Offer I set a minimum price "5" and price to reserve "15" and select the expiration date "2022-11-03" .


  Scenario: Delete a Highest Bid NFT Offer
    When I remove a Higher Bid NFT Bid with id "1" .
    Then The response code is 204


  Scenario: Delete a Highest Bid NFT Offer when not authenticated
    Given I'm not logged in
    When I delete a sale with id "1"
    Then The response code is 401


  Scenario: Delete a Highest Bid NFT Offer that does not exist
    And I login as "admin" with password "password"
    When I delete a sale with id "3"
    Then The response code is 404
