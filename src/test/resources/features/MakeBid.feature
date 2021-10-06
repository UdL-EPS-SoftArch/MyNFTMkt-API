Feature: Make a Bid
  In order to buy an NFT
  As a Buyer
  I want to make a bid for a specific NFT I want to purchase

  Background:
    Given There is a registered user with username "user1" and password "password" and email "user1@sample.app"
    #TODO And There is an NFT offer made by "seller1"

  Scenario: Make a new bid (without relationships)
    Given I login as "user1" with password "password"
    When I make a bid with a price of "2.0" for the NFT offer
    Then The response code is 201
    And It has been created a bid with a price of "2.0" for the NFT offer
    And The status of the bid is "ACTIVE"

  Scenario: Make a new bid without being logged in
    Given I'm not logged in
    When I make a bid with a price of "2.00" for the NFT offer
    Then The response code is 401
    And The error message is "Unauthorized"

  Scenario: Make a new bid with an invalid price
    Given I login as "user1" with password "password"
    When I make a bid with a price of "-5" for the NFT offer
    Then The response code is 400
    And The error message is "must be greater than or equal to 0.01"

