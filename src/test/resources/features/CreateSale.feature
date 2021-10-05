Feature : Crate sale
  As a user
  I want to create a sale

  Scenario: Create new sale
    Given There is a registered provider with username "provider" and password "password" and email "prov@gmail.com"
    And I login as "provider" with password "password"
    When I create a new dataset with title "title" and description "description"
    Then The response code is 201
    And It has been created a dataset with title "title" and description "description" and is provided by "provider"

  Scenario: Create new sale when not authenticated
    Given I'm not logged in
    When I create a new sale with long "date"
    Then The response code is 401
    And It has not been created any sale