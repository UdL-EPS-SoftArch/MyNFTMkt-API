Feature: Favorite NFT
  In order to control NFT's in the app
  As a User
  I want to add or delete authorised NFT's

  Background:
    Given There is a registered user with username "admin" and password "password" and email "admin@sample.app"
    Given There is a registered user with username "user" and password "password" and email "user@sample.app"
    Given There is a registered user with username "user2" and password "password2" and email "user2@sample.app"
    Given There is a registered NFT with id 1, title "title", description "description", keywords "tag1, tag2, tag3", category "category", mediaType "mediaType" and content "content"
    Given There is a registered NFT with id 2, title "title1", description "description1", keywords "tag4, tag5, tag6", category "category1", mediaType "mediaType1" and content "content1"

  Scenario: Add favorite NFT to an existing user
    Given I login as "user" with password "password"
    When I add the NFT with id 1 to the favorites of user "user"
    Then The response code is 204
    And It has been added a NFT with id 1, title "title", description "description", keywords "tag1, tag2, tag3", category "category", mediaType "mediaType" and content "content" to favorite NFTs of user with the username "user"

  Scenario: Delete favorite NFT from an existing user
    Given I login as "user" with password "password"
    And There is a registered NFT with id 1 in the list of favorites of user "user"
    When I remove the NFT with id 1 from the favorites of user "user"
    Then The response code is 204
    And It has been removed a NFT with id 1 from favorite NFTs of user with the username "user"

   Scenario: Add an NFT to the favorites of a user who already has another in his list
     Given I login as "user" with password "password"
     And There is a registered NFT with id 2 in the list of favorites of user "user"
     When I add the NFT with id 1 to the favorites of user "user"
     Then The response code is 204

    Scenario: Add an NFT to the favorites of a non existing user
      Given I login as "admin" with password "password"
      When I add the NFT with id 1 to the favorites of user "user3"
      Then The response code is 404

    Scenario: A user wants to add one NFT to the favorites of an other user
      Given I login as "user" with password "password"
      When I add the NFT with id 1 to the favorites of user "user2"
      Then The response code is 403


