Feature: Create an Offer
  In order to control who can create an offer
  Nobody can create an offer

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"

  Scenario: Not allowed to create an offer as an administrator
    Given I login as "admin" with password "password"
    When I create a fixed price offer with id 1
    Then The response code is 403

  Scenario: Not allowed to create an offer as a user
    Given I login as "user" with password "password"
    When I create a fixed price offer with id 1
    Then The response code is 403
