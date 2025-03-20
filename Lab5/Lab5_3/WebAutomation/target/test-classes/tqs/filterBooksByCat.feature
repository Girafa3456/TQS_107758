Feature: Filter books by category

  Scenario: Filter books by category "Fiction"
    Given I am on the bookstore homepage
    When I filter books by category "Fiction"
    Then I should see only books in the "Fiction" category
