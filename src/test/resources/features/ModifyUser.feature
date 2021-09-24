Feature: Modify User
  In order to change information about a user
  As an user
  I want to modify my information

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app" and name "name" and lastname "lastname"

  Scenario: Modify password
    Given I login as "user" with password "password"
    When I modify the password of the user "user" with "newpassword"
    Then The response code is 200
    And I can login with username "user" and password "newpassword"

  Scenario: Modify name
    Given I login as "user" with password "password"
    When I modify the name of the user "user" with "newname"
    Then The response code is 200
    And The name of the user "user" has been modified to "newname"

  Scenario: Modify lastname
    Given I login as "user" with password "password"
    When I modify the lastname of the user "newuser" with "newlastname"
    Then The response code is 200
    And The lastname of the user "user" has been modified to "newlastname"


