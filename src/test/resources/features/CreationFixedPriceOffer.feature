Feature: Create an Offer with a Fixed Price
  In order to sell a NFT at an exact price
  As Seller
  I want to create an Offer with a Fixed Price


  Scenario: Create a new Fixed Price Offer
    Given There is a registered user with username "newuser" and password "password" and email "newuser@sample.app"
    And  I login as "admin" with password "password"
    When I want to make Fixed Price Offer setting the price at 10
    Then My offer should match the price, 10, which I wanted since the start