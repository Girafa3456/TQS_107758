package com.mydriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchasePage {
    private WebDriver driver;

    @FindBy(id = "inputName")
    private WebElement nameField;

    @FindBy(id = "address")
    private WebElement addressField;

    @FindBy(id = "city")
    private WebElement cityField;

    @FindBy(id = "state")
    private WebElement stateField;

    @FindBy(id = "zipCode")
    private WebElement zipCodeField;

    @FindBy(id = "creditCardNumber")
    private WebElement creditCardNumberField;

    @FindBy(id = "nameOnCard")
    private WebElement nameOnCardField;

    @FindBy(css = ".btn-primary")
    private WebElement purchaseButton;

    public PurchasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillPersonalDetails(String name, String address, String city, String state, String zipCode) {
        nameField.sendKeys(name);
        addressField.sendKeys(address);
        cityField.sendKeys(city);
        stateField.sendKeys(state);
        zipCodeField.sendKeys(zipCode);
    }

    public void fillPaymentDetails(String cardNumber, String nameOnCard) {
        creditCardNumberField.sendKeys(cardNumber);
        this.nameOnCardField.sendKeys(nameOnCard);
    }

    public void confirmPurchase() {
        purchaseButton.click();
    }
}
