package tqs.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BookstorePage {
    private WebDriver driver;

    public BookstorePage(WebDriver driver) {
        this.driver = driver;
    }

    private By searchBox = By.id("search-box-id");

    private By categoryDropdown = By.id("category-dropdown-id");

    public void searchForBook(String bookTitle) {
        WebElement searchElement = driver.findElement(searchBox);
        searchElement.sendKeys(bookTitle);
        searchElement.submit();
    }

    public void filterBooksByCategory(String category) {
        WebElement dropdown = driver.findElement(categoryDropdown);
        dropdown.click();
        WebElement categoryOption = driver.findElement(By.xpath("//option[text()='" + category + "']"));
        categoryOption.click();
    }
    
    public boolean isBookInResults(String bookTitle) {
        try {
            WebElement result = driver.findElement(By.xpath("//h2[contains(text(), '" + bookTitle + "')]"));
            return result != null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areBooksInCategory(String category) {
        try {
            List<WebElement> books = driver.findElements(By.xpath("//div[@class='book-category']"));
            for (WebElement book : books) {
                if (!book.getText().contains(category)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}