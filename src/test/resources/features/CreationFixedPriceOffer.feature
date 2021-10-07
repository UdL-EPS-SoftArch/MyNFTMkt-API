Feature: Create an Offer with a Fixed Price
  In order to sell a NFT at an exact price
  As Seller
  I want to create an Offer with a Fixed Price


  Scenario: Create a new Fixed Price Offer
    Given There is a registered user with username "newuser" and password "password" and email "newuser@sample.app"
    And I login as "newuser" with password "password"
    When It has created a Fixed Price Offer with the price at 10
    Then The offer  matches the price, 10


  Scenario: Unable to create a new fixed price offer if not logged in
    When It has created a Fixed Price Offer with the price at 10
    Then The error message is "Unauthorized"

  Scenario: you are not able to modify a fixed price offer
    Given There is a registered user with username "newuser" and password "password" and email "newuser@sample.app"
    And I login as "newuser" with password "password"
    When It has created a Fixed Price Offer with the price at 10
    Then I try to modify the fixed price offer
    And The error message is "Forbidden"


  Scenario: You can't create a new fixed price offer with price lower than zero
    Given There is a registered user with username "newuser" and password "password" and email "newuser@sample.app"
    And I login as "newuser" with password "password"
    When It is not possible to create a fixed price offer with price -1
    Then The response code is 400

