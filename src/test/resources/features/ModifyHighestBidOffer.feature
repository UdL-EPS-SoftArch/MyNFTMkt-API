Feature: Modify a Highest Bid Offer

  Background:
    Given There is a registered user with username "seller1" and password "password" and email "seller1@sample.app"
    Given I login as "seller1" with password "password"
    When I make Highest Bid Offer I set a minimum price "5" and price to reserve "15" and select the expiration date "2022-11-03" .

  Scenario: Modify a Highest Bid NFT Offer
    When I modify Highest Bid Offer with id "1" I set a minimum price "4" and price to reserve "12" and select the expiration date "2022-11-10" .
    Then The response code is 200


  Scenario: Modify a Highest Bid NFT Offer when not authenticated
    Given I'm not logged in
    When I modify Highest Bid Offer with id "1" I set a minimum price "4" and price to reserve "12" and select the expiration date "2022-11-10" .
    Then The response code is 401


  Scenario: Modify a Highest Bid NFT Offer that does not exist
    Given I login as "user1" with password "password"
    When I modify Highest Bid Offer with id "3" I set a minimum price "4" and price to reserve "12" and select the expiration date "2022-11-10" .
    Then The response code is 403

  Scenario Outline: : Create one Highest Bid NFT Offer erro
    When I modify Highest Bid Offer with id "1" I set a minimum price "<minimumBid>" and price to reserve "<reservePrice>" and select the expiration date "<expiration>" .
    Then The response code is 400
    And The error message is "<errorMessage>"
    Examples:
      |minimumBid|reservePrice|expiration|errorMessage|
      |1|-5|2022-11-03|must be greater than or equal to 0.01|
      |-2|3|2022-11-03|must be greater than or equal to 0.01|
      |5|2|2022-11-03|To create this offer, the reserve price has to be higher than the minimum price.|
      |2|3|2021-02-03|The data has to be greater than the current data.|
    # TODO Pasa una data incorecta como 2022-33-56