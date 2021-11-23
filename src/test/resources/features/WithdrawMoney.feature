Feature: Add money
  As an administrator
  I want to control who can withdraw money

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    Given There is a registered user with username "user2" and password "password2" and email "user2@sample.app"

  Scenario: Withdraw money with no balance
    Given I login as "user" with password "password"
    And User "user" has a balance of "0.0"
    When I withdraw "5.0" money from the user "user"
    Then The response code is 400

  Scenario: Withdraw more money than user has
    Given I login as "user" with password "password"
    And User "user" has a balance of "5.0"
    When I withdraw "10.0" money from the user "user"
    Then The response code is 400

  Scenario: Withdraw money
    Given I login as "user" with password "password"
    And User "user" has a balance of "5.0"
    When I withdraw "2.0" money from the user "user"
    Then The response code is 200
    And It has been added a balance "3.0" to the user "user"

  Scenario: Withdraw money from a non-existing user
    Given I login as "user" with password "password"
    When I withdraw "5.0" money from the user "user420"
    Then The response code is 404

  Scenario: User wants to withdraw money from another user
    Given I login as "user" with password "password"
    When I withdraw "5.0" money from the user "user2"
    Then The response code is 403

  Scenario: Administrator wants to withdraw money from user
    Given I login as "admin" with password "password"
    And User "user" has a balance of "5.0"
    When I withdraw "5.0" money from the user "user"
    Then The response code is 200

  Scenario: Administrator wants to withdraw money from user that doesn't have enough money
    Given I login as "admin" with password "password"
    And User "user" has a balance of "4.0"
    When I withdraw "5.0" money from the user "user"
    Then The response code is 400




