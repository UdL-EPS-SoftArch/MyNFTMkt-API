Feature: Create an Offer with a Fixed Price
  In order to sell a NFT at an exact price
  As Seller
  I want to create an Offer with a Fixed Price


  Scenario: Create a new Fixed Price Offer
    Given I want to make Fixed Price Offer setting the price at 10
    When I register/create it
    Then My offer should match the price, 10, which I wanted since the start