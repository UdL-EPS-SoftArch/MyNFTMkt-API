Feature: Delete sale
  As a admin
  I want to delete a sale

  Background:
    Given I login as "admin" with password "password"
    When I create a new sale
    And It has been created a new sale

  Scenario: Admin deletes a sale
    Given I login as "admin" with password "password"
    When I delete a sale with id "1"
    Then The response code is 204

  Scenario: Delete a sale that does not exist
    And I login as "admin" with password "password"
    When I delete a sale with id "99"
    Then The response code is 404

  Scenario: Delete a sale when not authenticated
    Given I'm not logged in
    When I delete a sale with id "1"
    Then The response code is 401


