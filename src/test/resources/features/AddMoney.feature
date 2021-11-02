Feature: Add money
  As an administrator
  I want to control who can add money

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    Given There is a registered user with username "user2" and password "password2" and email "user2@sample.app"

  Scenario: Add money to a user with no balance
    Given I login as "user" with password "password"
    And User "user" has a balance of "0.0"
    When I add "5.0" money to the user "user"
    Then The response code is 200
    And It has been added a balance "5.0" to the user "user"

  Scenario: Add money to a user with balance
    Given I login as "user" with password "password"
    And User "user" has a balance of "5.0"
    When I add "5.0" money to the user "user"
    Then The response code is 200
    And It has been added a balance "10.0" to the user "user"

  Scenario: Add money to a non-existing user
    Given I login as "user" with password "password"
    When I add "5.0" money to the user "user420"
    Then The response code is 404

  Scenario: User wants to add money to another user
    Given I login as "user" with password "password"
    When I add "5.0" money to the user "user2"
    Then The response code is 403

  Scenario: Administrator wants to add money to a user
    Given I login as "admin" with password "password"
    When I add "5.0" money to the user "user"
    Then The response code is 200




