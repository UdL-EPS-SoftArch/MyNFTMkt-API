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
    And The status of the bid is "ACTIVE"
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





  # Scenario: Como [usuario concreto]
#  quiero [realizar acción concreta]
 # para [resultado o beneficio]

 #   Given Cumplo una precondición
 #   When Ejecuto una acción
 #   Then Observo este resultado
 #   But No debería poder observar este otro resultado



