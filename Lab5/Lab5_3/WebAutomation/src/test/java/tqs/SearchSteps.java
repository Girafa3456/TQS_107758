package tqs;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tqs.pages.BookstorePage;

public class SearchSteps {
    private WebDriver driver = new FirefoxDriver();
    private BookstorePage bookstorePage = new BookstorePage(driver);

    
    @Given("I am on the bookstore homepage")
    public void iAmOnTheBookstoreHomepage() {
        // Navigate to the bookstore homepage
        driver.get("https://cover-bookstore.onrender.com/");
    }

    @AfterEach
    public void closeBrowser(){
        if (driver != null){
            driver.quit();
        }
    }

    // Search for a book by title
    @When("I search for {string}")
    public void iSearchForBook(String bookTitle) {
        bookstorePage.searchForBook(bookTitle);
    }

    @Then("I should see {string} in the search results")
    public void iShouldSeeBookInResults(String bookTitle) {
        boolean isFound = bookstorePage.isBookInResults(bookTitle);
        Assertions.assertTrue(isFound, "The book '" + bookTitle + "' was not found in the search results.");
    }

    // Filter by category
    @When("I filter books by category {string}")
    public void iFilterBooksByCategory(String category) {
        bookstorePage.filterBooksByCategory(category);
    }

    @Then("I should see only books in the {string} category")
    public void iShouldSeeBooksInCategory(String category) {
        boolean areInCategory = bookstorePage.areBooksInCategory(category);
        Assertions.assertTrue(areInCategory, "Not all books are in the '" + category + "' category.");
    }

}
