import io.restassured.RestAssured;
import org.springframework.test.context.event.annotation.BeforeTestClass;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = AuthorController.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FunctionalTest {

    private final static String BASE_URI = "http://localhost";

    //    @LocalServerPort
    private int port;

    @BeforeTestClass
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }


}
