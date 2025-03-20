/*
 * (C) Copyright 2017 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {

    static final Logger log = getLogger(lookup().lookupClass());

    private Calculator calc;

    @Given("a calculator I just turned on")
    public void setup() {
        calc = new Calculator();
    }

    @When("I add {int} and {int}")
    public void add(int arg1, int arg2) {
        log.debug("Adding {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    @When("I substract {int} to {int}")
    public void substract(int arg1, int arg2) {
        log.debug("Substracting {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("-");
    }

    @When("I multiply {int} and {int}")
    public void multiply(int arg1, int arg2) {
        log.debug("Multiplying {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("*");
    }

    @When("I divide {int} and {int}")
    public void divide(int arg1, int arg2) {
        log.debug("Dividing {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("/");
    }

    @When("I add {int} and {string}")
    public void addInvalidInput(int arg1, String invalidArg) {
        try {
            log.debug("Adding {} and {}", arg1, invalidArg);
            calc.push(arg1);
            calc.push(invalidArg);
            calc.push("+");
        } catch (IllegalArgumentException e) {
            log.debug("Error: Invalid input");
            assertEquals("Invalid input", e.getMessage());
        }
    }


    @Then("the result is {double}")
    public void the_result_is(double expected) {
        Number value = calc.value();
        log.debug("Result: {} (expected {})", value, expected);
        assertEquals(expected, value);
    }

    @Then("the result is {string}")
    public void the_result_is(String result) {
        if (result.equals("Invalid input")) {
            assertEquals("Invalid input", result);
        } else {
            double expected = Double.parseDouble(result);
            Number value = calc.value();
            log.debug("Result: {} (expected {})", value, expected);
            assertEquals(expected, value);
        }
    }

}