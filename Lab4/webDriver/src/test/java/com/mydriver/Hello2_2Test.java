package com.mydriver;

import static org.slf4j.LoggerFactory.getLogger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import static java.lang.invoke.MethodHandles.lookup;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class Hello2_2Test {

    static final Logger log = getLogger(lookup().lookupClass());
    @Test
    void test(FirefoxDriver driver) {
        String url = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(url);

        String title = driver.getTitle();
        log.debug("The title of {} is {}");

        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
    }
}
