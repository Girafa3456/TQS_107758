import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    @FindBy(name = "fromPort")
    private WebElement fromPortDropdown;

    @FindBy(name = "toPort")
    private WebElement toPortDropdown;

    @FindBy(css = ".btn-primary")
    private WebElement findFlightsButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectDeparture(String city) {
        fromPortDropdown.sendKeys(city);
    }

    public void selectDestination(String city) {
        toPortDropdown.sendKeys(city);
    }

    public void clickFindFlights() {
        findFlightsButton.click();
    }
}
