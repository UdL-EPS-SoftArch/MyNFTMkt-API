Feature: Update sale
  As a -
  I want to update a sale

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.com"
    And I login as "admin" with password "password"
    And I make a bid with a price of "2.0" for the NFT offer
    And I create a new sale with the previous bid

  Scenario: Update sale date when not authenticated
    Given I'm not logged in
    When I change the date of the sale with id "1" to "2021-01-01T00:00:00.000+01:00[Europe/Paris]"
    Then The response code is 401

  Scenario: Update sale date
    Given I login as "admin" with password "password"
    When I change the date of the sale with id "1" to "2021-01-01T00:00:00.000+01:00[Europe/Paris]"
    Then The response code is 200
