package com;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BookSearchSteps {
    Library library = new Library();
    List<Book> result = new ArrayList<>();

    @ParameterType("([0-9]{1,2}) ([A-Za-z]+) ([0-9]{4})")
    public Date iso8601Date(String day, String month, String year) throws ParseException, java.text.ParseException {
        String dateString = day + " " + month + " " + year;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        return sdf.parse(dateString);
    }

    @Given("a book with the title {string}, written by {string}, published in {iso8601Date}")
    public void addNewBook(final String title, final String author, final Date published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @And("another book with the title {string}, written by {string}, published in {iso8601Date}")
    public void addAnotherBook(final String title, final String author, final Date published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void setSearchParameters(final Date from, final Date to) {
        result = library.findBooks(from, to);
    }

    @When("the customer searches for books written by {string}")
    public void theCustomerSearchesForBooksWrittenByAuthor(String author) {
        result = library.findBooksByAuthor(author);
    }

    @When("the customer searches for a book with title {string}")
    public void theCustomerSearchesForBooksWithTitle(String title) {
        result.add(library.findBookByTitle(title));
    }

    @Then("{int} books should have been found")
    public void verifyAmountOfBooksFound(final int booksFound) {
        assertThat(result.size(), equalTo(booksFound));
    }

    @Then("{int} book should have been found")
    public void verifyBookFound(final int booksFound) {
        assertThat(result.size(), equalTo(booksFound));
    }

    @And("Book {int} should have the title {string}")
    public void verifyBookAtPosition(final int position, final String title) {
        assertThat(result.get(position - 1).getTitle(), equalTo(title));
    }
}
