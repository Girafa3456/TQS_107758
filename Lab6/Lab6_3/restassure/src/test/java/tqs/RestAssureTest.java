package tqs;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestAssureTest {

    @Test
	void sampleTest() {
		assertTrue(true, "This is a sample test");
	}

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testToDosIsAvailable() {
        given().
            
        when().
                get("/todos").
        then().
                statusCode(200);
    }

    @Test
    public void testToDos4Title() {
        given().

        when().
                get("/todos/4").
        then().
                statusCode(200).
                
                body("title", equalTo("et porro tempora"));
    }

    @Test
    public void testAllTodosContainIds198And199() {
        Response response = 
        given().

        when().
                get("/todos").
        then().
                statusCode(200).
                extract().
                response();

        List<Integer> ids = response.jsonPath().getList("id");
        assertTrue(ids.contains(198), "Should contain id 198");
        assertTrue(ids.contains(199), "Should contain id 199");
    }

    @Test
    public void testAllTodosResponseTimeLessThan2Seconds() {
        given().

        when().
                get("/todos").
        
        then().
                statusCode(200).
                time(lessThan(2000L), TimeUnit.MILLISECONDS);
    }
}