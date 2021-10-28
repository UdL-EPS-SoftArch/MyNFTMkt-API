Feature: Create an Offer with a Declining Price
  In order to sell a NFT with an expiration date
  As Seller
  I want to create an Offer with a Declining Price

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.com"
    And I login as "admin" with password "password"
    And I register a new NFT with title "patata", description "J.M.G.", keywords "ideïa", category "category", mediaType "mediaType" and content "content"
    And The response code is 201

  Scenario: Create a new Declining Price Offer
    Given I login as "admin" with password "password"
    When I create a Declining Price Offer with an starting price at "1.0", an ending price at "1000.0" and an expiration to "30" days with the previous NFT.
    Then The response code is 201