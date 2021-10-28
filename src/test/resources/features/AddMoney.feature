Feature: Add money
  As an administrator
  I want to control who can add money

  Background:
    Given There is a registered user with username "admin" and password "password" and email "admin@sample.app"
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    Given There is a registered user with username "user2" and password "password2" and email "user2@sample.app"

  Scenario: Add money to the user
    Given I login as "user" with password "password"
    When I add "5.0" money to the user "user"
    Then The response code is 204
    And It has been added a balance "5.0" to the user "user"

  Scenario: Add money to a non-existing user
    Given I login as "admin" with password "password"
    When I add "5.0" money to the user "user2"
    Then The response code is 404

  Scenario: A user wants to add money to the other user
    Given I login as "user" with password "password"
    When I add "5.0" money to the user "user2"
    Then The response code is 403

  Scenario: Retract money from the user
    Given I login as "user" with password "password"
    And I have more than "5.0" money
    When I retract "4.0" money from the user "user"
    Then the response code is 204
    And It has been retracted "4.0" money from the user "user"




