Feature: Modify User
  In order to keep updated the information about a user
  As an administrator
  I want to modify information about a user

  Background:
    Given There is a registered administrator with username "admin" and password "password" and email "admin@sample.app"
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    Given There is a registered user with username "user1" and password "password1" and email "user1@sample.app"

  Scenario: Modify password
    Given I login as "admin" with password "password"
    When I modify the password of the user "user" with "newpassword"
    Then The response code is 204
    And I can login with username "user" and password "newpassword"

  Scenario: Modify name
    Given I login as "admin" with password "password"
    When I modify the name of the user "user" with "newname"
    Then The response code is 204
    And The name of the user "user" has been modified to "newname"

  Scenario: Modify lastname
    Given I login as "admin" with password "password"
    When I modify the lastname of the user "user" with "newlastname"
    Then The response code is 204
    And The lastname of the user "user" has been modified to "newlastname"

  Scenario: Modify email
    Given I login as "admin" with password "password"
    When I modify the email of the user "user" with "newemail@sample.app"
    Then The response code is 204
    And The email of the user "user" has been modified to "newemail@sample.app"

  Scenario: Modify name of a user that doesn't exist
    Given I login as "admin" with password "password"
    When I modify the name of the user "user2" with "newname"
    Then The response code is 404

  Scenario: Modify password without being logged in
    Given I'm not logged in
    When I modify the password of the user "user" with "newpassword"
    Then The response code is 401

  Scenario: Modify password of user "user" being logged in as user "user1"
    Given I login as "user1" with password "password1"
    When I modify the password of the user "user" with "newpassword"
    Then The response code is 403

  Scenario: Modify password of user "user" being logged in as user "user"
    Given I login as "user" with password "password"
    When I modify the password of the user "user" with "newpassword"
    Then The response code is 204
    And I can login with username "user" and password "newpassword"