import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;

@ExtendWith(SeleniumJupiter.class)
public class Test{
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testFlightBooking() {
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(800, 800));

        // Initialize Page Objects
        HomePage homePage = new HomePage(driver);
        FlightsPage flightsPage = new FlightsPage(driver);
        PurchasePage purchasePage = new PurchasePage(driver);

        // Perform actions using Page Object methods
        homePage.selectDeparture("Portland");
        homePage.selectDestination("Dublin");
        homePage.clickFindFlights();

        flightsPage.selectFlight();

        purchasePage.fillPersonalDetails("Kevin", "123 Main St", "Miami", "Florida", "12345");
        purchasePage.fillPaymentDetails("4567 4567 3285 9971", "Kevin Smith");
        purchasePage.confirmPurchase();

        // Validate the title of the confirmation page
        assertThat(driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
    }
}
