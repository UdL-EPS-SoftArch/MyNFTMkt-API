Feature: Make a Highest Bid Offer

  Background:
    Given There is a registered user with username "seller1" and password "password" and email "seller1@sample.app"
    Given I login as "seller1" with password "password"

## CREATE

  Scenario: Create one Highest Bid Offer
    When I make Highest Bid Offer I set a minimum price "5" and price to reserve "15" and select the expiration date "2022-11-03" .
    Then The response code is 201

  Scenario: Create one Highest Bid Offer bid without being logged in
    Given I'm not logged in
    When I make Highest Bid Offer I set a minimum price "5" and price to reserve "15" and select the expiration date "2022-11-03" .
    Then The response code is 401
    And The error message is "Unauthorized"

  Scenario Outline: : Create one Highest Bid Offer with mistakes
    Given I login as "seller1" with password "password"
    When I make Highest Bid Offer I set a minimum price "<minimumBid>" and price to reserve "<reservePrice>" and select the expiration date "<expiration>" .
    Then The response code is 400
    And The error message is "<errorMessage>"
    Examples:
      |minimumBid|reservePrice|expiration|errorMessage|
      |1|-5|2022-11-03|must be greater than or equal to 0.01|
      |-2|3|2022-11-03|must be greater than or equal to 0.01|
      |5|2|2022-11-03|To create this offer, the reserve price has to be higher than the minimum price.|
      |2|3|2021-02-03|The data has to be greater than the current data.|
    # TODO Pasa una data incorecta como 2022-33-56

  Scenario: For an NFT create a Highest Bid Offer
    When I register a new NFT with title "title", description "description", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    And Select the NTF "title" for option Highest Bid Offer .
    When I make Highest Bid Offer I set a minimum price "5" and price to reserve "15" and select the expiration date "2022-11-03" .
    Then The response code is 201
    And The Highest Bid Offer is associated with the NTF "title" .
















