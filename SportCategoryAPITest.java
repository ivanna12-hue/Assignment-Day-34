import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SportCategoryAPITest {

    private String token;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api-sportify-v1.herokuapp.com"; // contoh base URL, ganti sesuai dokumentasi

        // Login untuk ambil token
        Response response = given()
                .contentType("application/json")
                .body("{\"email\":\"syukron@gmail.com\",\"password\":\"syukron123\"}")
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(200)
                .body("data.token", notNullValue())
                .extract().response();

        token = response.path("data.token");
    }

    @Test
    public void testGetAllSportCategories() {
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/v1/sport-categories")
                .then()
                .statusCode(200)
                .body("data", notNullValue());
    }

    @Test
    public void testCreateSportCategory() {
        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{\"name\":\"Basketball\"}")
                .when()
                .post("/api/v1/sport-categories")
                .then()
                .statusCode(201)
                .body("message", equalTo("success create sport category"));
    }
}
