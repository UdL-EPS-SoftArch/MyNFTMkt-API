Feature: NFT Owner
  As a user
  I want to own a NFT

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    Given There is a registered NFT with id 1, title "title", description "description", keywords "tag1, tag2, tag3", category "category", mediaType "mediaType" and content "content"

  Scenario: Add owned NFT to an existing user
    Given I login as "user" with password "password"
    When I add the NFT with id 1 to the owned by user "user"
    Then The response code is 201
    And It has been added a NFT with id 1, title "title", description "description", keywords "tag1, tag2, tag3", category "category", mediaType "mediaType" and content "content" to owned NFTs of user with the username "user"

  Scenario: Change owner from an NFT from an existing user to another user
    Given I login as "user" with password "password"
    And There is a registered user with username "newuser" and password "password"
    And There is a registered NFT with id 1
    And the owner of NFT with id 1 is "user"
    When I change the owner of NFT with id 1 from user "user" to user "newuser"
    Then The response code is 200
    And It has been changed ownership of NFT with id 1 to "newuser"

