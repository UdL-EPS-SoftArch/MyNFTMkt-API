Feature: FindByPrice
  In order to find the fixed rice offers of a range of prices
  As a User
  I want to get a list of fixed price offers

  Background:
    Given There is a registered user with username "newuser" and password "password" and email "newuser@sample.app"
    Given I login as "newuser" with password "password"
    Given It has created a Fixed Price Offer with the price at 10.0
    And I register a new NFT with title "pepito", description "pepito es un grande", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    And I create a Fixed Price Offer with the price at 1.0. of the NFT "pepito"
    And I register a new NFT with title "pepito1", description "pepito es un grande", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    And I create a Fixed Price Offer with the price at 15.0. of the NFT "pepito1"
    And I register a new NFT with title "pepito2", description "pepito es un grande", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    And I create a Fixed Price Offer with the price at 20.0. of the NFT "pepito2"
    And I register a new NFT with title "pepito3", description "pepito es un grande", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    And I create a Fixed Price Offer with the price at 50.0. of the NFT "pepito3"
    And I register a new NFT with title "pepito4", description "pepito es un grande", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    And I create a Fixed Price Offer with the price at 12.0. of the NFT "pepito4"
    And I register a new NFT with title "pepito5", description "pepito es un grande", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    And I create a Fixed Price Offer with the price at 100.0. of the NFT "pepito5"

    Given It has created a Fixed Price Offer with the price at 15.0
    Given It has created a Fixed Price Offer with the price at 20.0
    Given It has created a Fixed Price Offer with the price at 50.0
    Given It has created a Fixed Price Offer with the price at 12.0
    Given It has created a Fixed Price Offer with the price at 100.0

  Scenario: You can search by price
    When I search by price smaller or equal than 50.0
    Then I receive a list with 6 items


