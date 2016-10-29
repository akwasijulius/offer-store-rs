@tag
Feature: Product Management
 As a user I want to be able to use this services for  
 The management of products in the store - create - update - read and delete 

  @tag1
  Scenario: Save new Product
    Given We want to save a new product
    When we recieve a post request with name "Swag Shoes" description "A very swag shoe" and price 29
    Then we expect a succes message
    And the Id of the saved product
    
