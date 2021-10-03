Feature: Modify a Bid
  In order to change my opinion of any bids that I could have made
  As a Buyer
  I want to modify any bids made by me for any NFT offer

  Background:
    Given There is a registered user with username "buyer1" and password "password" and email "buyer1@sample.app"
    And There is a registered user with username "seller1" and password "password" and email "seller1@sample.app"
    And There is an NFT offer with an id of "1" made by "seller1"
    And There is a bid, made by "buyer1", for the NFT offer with an id of "1"

    #TODO Scenarios: forbid user from deleting bids (can only change status to cancelled), change status

    #TODO Implement when the purchase feature gets implemented
#    Scenario: Change bid status to purchased
#      Given I login as "buyer1" with password "password"
#      When I finally get the NFT that I purchased with my bid
#      Then The status of the bid is "PURCHASED"

    #TODO Implement when the cancel feature gets implemented
#      Scenario: Change bid status to cancelled
#        Given I login as "buyer1" with password "password"
#        When I cancel my bid for the NFT offer with an id of "1"
#        Then The status of the bid is "CANCELLED"


