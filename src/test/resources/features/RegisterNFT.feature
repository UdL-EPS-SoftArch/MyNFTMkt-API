Feature: Register NFT
  In order to control NFT's in the app
  As an User
  I want to register authorised NFT's

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"

  Scenario: Register new NFT
    Given There is no registered NFT with id 1
    And I login as "user" with password "password"
    When I register a new NFT with title "title", description "description", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    Then The response code is 201
    And It has been created a NFT title "title", description "description", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"

  Scenario: Register new  invalid NFT
    Given There is no registered NFT with id 1
    And I login as "user" with password "password"
    When I register a new NFT with no title, description "description", keywords "<keyword>", category "category", mediaType "mediaType" and content "content"
    Then The response code is 400

