Feature: Add money
  As an administrator
  I want to control who can add money

  Background:
    Given There is a registered user with username "admin" and password "password" and email "admin@sample.app"
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"

  Scenario: Add money to the user
    Given I login as "user" with password "password"
    When I add "5.0" money to the user "user"
    Then The response code is 204
    And It has been added a balance "5.0" to the user "user"