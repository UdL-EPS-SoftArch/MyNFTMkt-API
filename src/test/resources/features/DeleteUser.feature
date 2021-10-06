Feature: Delete User
  In order to control who uses the app
  As an administrator
  I want to delete authorised users

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    Given There is a registered user with username "user1" and password "password1" and email "user1@sample.app"

  Scenario: Delete an existing user
    Given There is a registered user with username "user" and password "password" and email "gigituser@sample.app"
    And I login as "admin" with password "password"
    When I delete a user with username "user"
    Then The response code is 204
    And I cannot login with username "user" and password "password"

  Scenario: Delete user with non-existing username
    Given I login as "admin" with password "password"
    When I delete a user with username "user1"
    Then The response code is 404

  Scenario: Delete user "user" while being logged in as user "user"
    Given I login as "user" with password "password"
    When I delete a user with username "user"
    Then The response code is 204
    And I cannot login with username "user" and password "password"

  Scenario: Delete user "user" while being logged in as user "user1"
    Given I login as "user1" with password "password1"
    When I delete a user with username "user"
    Then The response code is 403

