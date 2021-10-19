Feature: Favorite NFT
  In order to control NFT's in the app
  As a User
  I want to add or delete authorised NFT's

  Background:
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    Given There is a registered NFT with id 1, title "title", description "description", keywords "tag1, tag2, tag3", category "category", mediaType "mediaType" and content "content"


  Scenario: Add favorite NFT to an existing user
    Given I login as "user" with password "password"
    When I add the NFT with id 1 to the favorites of user "user"
    Then The response code is 201
    And It has been added a NFT with id 1, title "title", description "description", keywords "tag1, tag2, tag3", category "category", mediaType "mediaType" and content "content" to favorite NFTs of user with the username "user"


  Scenario: Delete favorite NFT from an existing user
    Given I login as "user" with password "password"
    When I remove the NFT with id 1 from the favorites of user "user"
    Then The response code is 204
    And It has been removed a NFT with id 1, title "title", description "description", keywords "tag1, tag2, tag3", category "category", mediaType "mediaType" and content "content" from favorite NFTs of user with the username "user"
