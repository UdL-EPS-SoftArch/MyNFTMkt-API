Feature: Modify a Bid
  In order to change my opinion of any bids that I could have made
  As a Buyer
  I want to modify any bids made by me for any NFT offer

  Background:
    Given There is a registered user with username "user1" and password "password" and email "user1@sample.app"
    And There is an NFT offer with an id of "1"
    And There is a bid made for the NFT offer with an id of "1"

    Scenario: Cancel a bid
      Given I login as "user1" with password "password"
      When I cancel the bid with an id of "1" for the NFT offer with an id of "1"
      Then The response code is 201
      And The bid with an id of "1" for the NFT offer with an id of "1" no longer exists on the DB

      #TODO Modify price?
