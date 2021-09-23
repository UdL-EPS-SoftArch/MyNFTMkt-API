Feature: Register Bid
  In order to buy NFT
  As a Buyer
  I want to make a bid

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: Register new user
    Given There is no registered user with username "newuser"
    And I'm not logged in
    When I register a new user with username "newuser", email "newuser@sample.app" and password "password"
    Then The response code is 201
    And It has been created a user with username "newuser" and email "newuser@sample.app", the password is not returned
    And I can login with username "newuser" and password "password"