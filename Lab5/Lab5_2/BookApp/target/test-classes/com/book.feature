Feature: Search books in the library by publication date
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.
 
  Scenario: Search books by publication year
    Given a book with the title 'One good book', written by 'Anonymous', published in 14 March 2013
      And another book with the title 'Some other book', written by 'Tim Tomson', published in 23 August 2014
      And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 01 January 2012
    When the customer searches for books published between 1 January 2013 and 1 January 2014
    Then 2 books should have been found
      And Book 1 should have the title 'Some other book'
      And Book 2 should have the title 'One good book'
    
  Scenario: Search books by author
    Given a book with the title 'One good book', written by 'Anonymous', published in 14 March 2013
      And another book with the title 'Some other book', written by 'Tim Tomson', published in 23 August 2014
      And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 01 January 2012
    When the customer searches for books written by 'Tim Tomson'
    Then 1 book should have been found
      And Book 1 should have the title 'Some other book'
    
  Scenario: Search books by title
    Given a book with the title 'One good book', written by 'Anonymous', published in 14 March 2013
      And another book with the title 'Some other book', written by 'Tim Tomson', published in 23 August 2014
      And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 01 January 2012
    When the customer searches for a book with title 'One good book'
    Then 1 book should have been found
      And Book 1 should have the title 'One good book'