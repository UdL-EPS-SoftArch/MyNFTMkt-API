Feature: DELETE a Highest Bid Offer

  Background:
    Given There is a registered user with username "seller1" and password "password" and email "seller1@sample.app"
    Given I login as "seller1" with password "password"
    When I register a new NFT with title "nft1", description "description", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    And Select the NTF "nft1" for option Highest Bid Offer .
    When I make Highest Bid Offer I set a minimum price "5" and price to reserve "15" and select the expiration date "2022-11-03" .

  Scenario: Delete a Highest Bid Offer
    When I remove a Higher Bid Offer with id "2" .
    Then The response code is 204
    And Do not delete the related NTF

  Scenario: Delete a Highest Bid Offer when not authenticated
    Given I'm not logged in
    When I remove a Higher Bid Offer with id "2" .
    Then The response code is 401


  Scenario: Delete a Highest Bid NFT Offer that does not exist
    Given I login as "user1" with password "password"
    When I remove a Higher Bid Offer with id "5" .
    Then The response code is 404


