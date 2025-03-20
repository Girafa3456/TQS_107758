Feature: Search books in the online bookstore

  Scenario: Search for a book by title
    Given I am on the bookstore homepage
    When I search for "A Game of Thrones"
    Then I should see "A Game of Thrones" in the search results