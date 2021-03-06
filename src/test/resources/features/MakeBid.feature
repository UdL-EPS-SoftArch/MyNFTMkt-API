Feature: Make a Bid
  In order to buy an NFT
  As a Buyer
  I want to make a bid for a specific NFT I want to purchase

  Background:
    Given There is a registered user with username "buyer1" and password "password" and email "buyer1@sample.app"
    And There is a registered user with username "seller1" and password "password" and email "seller1@sample.app"
    And There is an fixed NFT offer with a price of "2.0"

  Scenario: Make a new bid for an fixed NFT offer
    Given I login as "buyer1" with password "password"
    When I make a bid with a price of "2.0" for the NFT offer created
    Then The response code is 201
    And It has been created a bid with a price of "2.0" for the NFT offer
    And The status of the bid is "PURCHASED"
    And The bid is associated with "buyer1"
    And A sale has been created and associated with the bid

  Scenario: Make a new bid without being logged in
    Given I'm not logged in
    When I make a bid with a price of "2.00" for the NFT offer created
    Then The response code is 401
    And The error message is "Unauthorized"

  Scenario: Make a new bid with an invalid price
    Given I login as "buyer1" with password "password"
    When I make a bid with a price of "-5" for the NFT offer created
    Then The response code is 400
    And The error message is "must be greater than or equal to 0.01"

  Scenario: Make a new bid with an unmatching price
    Given I login as "buyer1" with password "password"
    When I make a bid with a price of "5.0" for the NFT offer created
    Then The response code is 400
    And The error message is "Bid's price should match the offer's price"

  Scenario: forbid user from deleting bids
    Given I login as "buyer1" with password "password"
    And I make a bid with a price of "2.0" for the NFT offer created
    When I try to delete the bid
    Then The response code is 403
    And The error message is "Forbidden"

  Scenario: forbid user from modifying bids by patch
    Given I login as "buyer1" with password "password"
    And I make a bid with a price of "2.0" for the NFT offer created
    When I try to modify the bid by doing a patch
    Then The response code is 403
    And The error message is "Forbidden"

  Scenario: forbid user from modifying bids by put
    Given I login as "buyer1" with password "password"
    And I make a bid with a price of "2.0" for the NFT offer created
    When I try to modify the bid by doing a put
    Then The response code is 403
    And The error message is "Forbidden"





  # Scenario: Como [usuario concreto]
#  quiero [realizar acci??n concreta]
 # para [resultado o beneficio]

 #   Given Cumplo una precondici??n
 #   When Ejecuto una acci??n
 #   Then Observo este resultado
 #   But No deber??a poder observar este otro resultado



