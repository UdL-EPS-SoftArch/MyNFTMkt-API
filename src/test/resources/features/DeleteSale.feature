Feature: Delete sale
  As a admin
  I want to delete a sale

  Scenario: Admin deletes a sale
    Given I login as "admin" with password "password"
    When I delete a sale with id "1"
    Then The response code is 204

