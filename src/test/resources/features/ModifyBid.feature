Feature: Modify a Bid
  In order to change my opinion of any bids that I could have made
  As a Buyer
  I want to modify any bids made by me for any NFT offer

  Background:
    Given There is a registered user with username "user1" and password "password" and email "user1@sample.app"
    And There is an NFT offer with an id of "1"
    And There is a bid made for the NFT offer with an id of "1"

    #TODO Scenarios: forbid user from deleting bids (can only change status to cancelled), change status



