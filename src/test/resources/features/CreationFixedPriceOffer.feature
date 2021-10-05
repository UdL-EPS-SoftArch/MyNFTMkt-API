Feature: Create an Offer with a Fixed Price
  In order to sell a NFT at an exact price
  As Seller
  I want to create an Offer with a Fixed Price


  Scenario: Create a new Fixed Price Offer
    Given There is a registered user with username "newuser" and password "password" and email "newuser@sample.app"
    And  I login as "admin" with password "password"
    When It has created a Fixed Price Offer with the price at 10
    Then The offer  matches the price, 10