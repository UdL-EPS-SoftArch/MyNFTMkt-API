Feature: Create an Offer with a Fixed Price
  In order to sell a NFT at an exact price
  As Seller
  I want to create an Offer with a Fixed Price


  Scenario: Create a new Fixed Price Offer
    Given There is a registered user with username "newuser" and password "password" and email "newuser@sample.app"
    And I login as "newuser" with password "password"
    When It has created a Fixed Price Offer with the price at 10.0
    Then The offer matches the price, 10.0


  Scenario: Unable to create a new fixed price offer if not logged in
    When It has created a Fixed Price Offer with the price at 10.0
    Then The error message is "Unauthorized"

  Scenario: You are not able to modify a fixed price offer
    Given There is a registered user with username "newuser" and password "password" and email "newuser@sample.app"
    And I login as "newuser" with password "password"
    When It has created a Fixed Price Offer with the price at 10.0
    Then I try to modify the fixed price offer
    And The error message is "Forbidden"


  Scenario: You can't create a new fixed price offer with price lower than zero
    Given There is a registered user with username "newuser" and password "password" and email "newuser@sample.app"
    And I login as "newuser" with password "password"
    When It is not possible to create a fixed price offer with price -1.0
    Then The response code is 400



  Scenario: Create a new Fixed Price Offer with a linked NFT
    Given There is a registered user with username "newuser" and password "password" and email "newuser@sample.app"
    And There is no registered NFT with id 1
    And I login as "newuser" with password "password"
    And I register a new NFT with title "pepito", description "pepito es un grande", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    And The response code is 201
    When I create a Fixed Price Offer with the price at 10.0. of the NFT "pepito"
    Then The offer matches the price, 10.0.
    And The offer NFT matches the NFT "pepito"

  Scenario: Create a Fixed Price Offer with an already linked NFT to another offer
    Given There is a registered user with username "newuser" and password "password" and email "newuser@sample.app"
    And There is no registered NFT with id 1
    And I register a new NFT with title "pepito", description "pepito es un grande", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    And I login as "newuser" with password "password"
    And I create a Fixed Price Offer with the price at 10.0. of the NFT "pepito"
    When I create a Fixed Price Offer with the price at 10.0. of the NFT "pepito"
    Then The response code is 403