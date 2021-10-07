Feature: Update sale
  As a -
  I want to update a sale

  Scenario: Update sale date when not authenticated
    Given I'm not logged in
    When I change the date of the sale with id "1" to "00:00:00"
    Then The response code is 401
    And It has not been updated any sale

  Scenario: Update sale date
    Given I login as "admin" with password "password"
    When I change the date of the sale with id "1" to "00:00:00"
    Then The response code is 200
    And The previously updated sale has now date "00:00:00"

  Scenario: Update sale date to blank
    Given I login as "admin" with password "password"
    When I change the date of the sale with id "1" to ""
    Then The response code is 400
    And The error message is "must not be blank"
