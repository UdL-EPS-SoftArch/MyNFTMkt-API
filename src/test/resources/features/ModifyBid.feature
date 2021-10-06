Feature: Modify a Bid
  In order to change my opinion of any bids that I could have made
  As a Buyer
  I want to modify any bids made by me for any NFT offer

  Background:
    Given There is a registered user with username "buyer1" and password "password" and email "buyer1@sample.app"
    #TODO And There is an NFT offer made by "seller1"
  
    Scenario: forbid user from deleting bids
      Given I login as "buyer1" with password "password"
      And I make a bid with a price of "2.0" for the NFT offer
      When I try to delete the bid
      Then The response code is 403
      And The error message is "Forbidden"

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


