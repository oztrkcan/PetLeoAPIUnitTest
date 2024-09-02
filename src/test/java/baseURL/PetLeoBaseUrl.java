package baseURL;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class PetLeoBaseUrl {

    protected RequestSpecification specPetLeo;

    @BeforeClass
    public void setUpBaseUrl() {
        specPetLeo = new RequestSpecBuilder()
                .setBaseUri("https://api-development.petleo.de")
                .build();
    }
}

