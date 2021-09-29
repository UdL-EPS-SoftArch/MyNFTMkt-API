Feature: Make a Bid
  In order to buy an NFT
  As a Buyer
  I want to make a bid for a specific NFT I want to purchase

  Background:
    Given There is a registered user with username "user1" and password "password" and email "user1@sample.app"

  Scenario: Make a new bid
    #TODO move to background if case of "sold out" is not needed
    Given There is an NFT offer with an id of "1"
    And I login as "user1" with password "password"
    When I make a bid with a price of "2.00" and a date of "02-02-2022" for the NFT offer with an id of "1"
    Then The response code is 201
    And It has been created a bid with a price of "2.00" and a date of "02-02-2022" for the NFT offer with an id of "1"

    #TODO For a NFT offer, can there be multiple bids done by the same user?
    #TODO If a NFT has been sold, does the NFT offer disappear from the DB? If no, do we need to create a scenario for it?

  Scenario: Make a new bid without being logged in
    Given There is an NFT offer with an id of "1"
    When I make a bid with a price of "2.00" and a date of "02-02-2022" for the NFT offer with an id of "1"
    Then The response code is ?? //TODO error code?
    And The error message is "Log in in order to make your bid"
    And It has not been created a bid with a price of "2.00" and a date of "02-02-2022" for the NFT offer with an id of "1"

  Scenario: Make a new bid with an invalid price
    Given There is an NFT offer with an id of "1"
    And I login as "user1" with password "password"
    When I make a bid with a price of "0" and a date of "02-02-2022" for the NFT offer with an id of "1"
    Then The response code is ?? //TODO error code?
    And The error message is "Invalid price"
    And It has not been created a bid with a price of "0" and a date of "02-02-2022" for the NFT offer with an id of "1"

  Scenario: Make a new bid with an invalid date
    Given There is an NFT offer with an id of "1"
    And I login as "user1" with password "password"
    When I make a bid with a price of "2.00" and a date of "0-00-0000" for the NFT offer with an id of "1"
    Then The response code is ?? //TODO error code?
    And The error message is "Invalid date"
    And It has not been created a bid with a price of "2.00" and a date of "0-00-0000" for the NFT offer with an id of "1"