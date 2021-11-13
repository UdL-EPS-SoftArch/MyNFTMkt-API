Feature: Make a Highest Bid NFT Offer

  Background:
    Given There is a registered user with username "user1" and password "password" and email "user1@sample.app"
    Given I login as "user1" with password "password"
## CREATE
  Scenario: Create one Highest Bid NFT Offer
    When I make Highest Bid NFT Offer I set a minimum price "5" and price to reserve "15" and select the expiration date "2022-11-03" .
    Then The response code is 201

  Scenario: Create one Highest Bid NFT Offer bid without being logged in
    Given I'm not logged in
    When I make Highest Bid NFT Offer I set a minimum price "5" and price to reserve "15" and select the expiration date "2022-11-03" .
    Then The response code is 401
    And The error message is "Unauthorized"

  Scenario Outline: : Create one Highest Bid NFT Offer erro
    When I make Highest Bid NFT Offer I set a minimum price "<minimumBid>" and price to reserve "<reservePrice>" and select the expiration date "<expiration>" .
    Then The response code is 400
    And The error message is "<errorMessage>"
    Examples:
      |minimumBid|reservePrice|expiration|errorMessage|
      |1|-5|2022-11-03|must be greater than or equal to 0.01|
      |-2|3|2022-11-03|must be greater than or equal to 0.01|
      |5|2|2022-11-03|To create this offer, the reserve price has to be higher than the minimum price.|
      |2|3|2021-02-03|The data has to be greater than the current data.|
    # TODO Pasa una data incorecta como 2022-33-56

  Scenario: Create one Highest Bid NFT Offer + NTF
    When I register a new NFT with title "title", description "description", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    And Select the NTF "title" .
    When I make Highest Bid NFT Offer I set a minimum price "5" and price to reserve "15" and select the expiration date "2022-11-03" .
    Then The response code is 201
    And The Highest Bid NFT Offer is associated with the NTF "title" .


  Scenario: Create one Highest Bid NFT Offer + Bid
    When I register a new NFT with title "title", description "description", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    And Select the NTF "title" for option Highest Bid NFT Offer .
    When I make Highest Bid NFT Offer I set a minimum price "5" and price to reserve "15" and select the expiration date "2022-11-03" .
    Then The response code is 201
    And The Highest Bid NFT Offer is associated with the NTF "title" .
    And Select the NTF "title" for option Highest Bid NFT Offer .
    When I make a bid with a price of "2.0" for the NFT offer created














