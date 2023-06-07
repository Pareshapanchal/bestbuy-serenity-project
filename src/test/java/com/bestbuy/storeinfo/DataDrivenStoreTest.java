package com.bestbuy.storeinfo;

import com.bestbuy.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

//@UseTestDataFrom("src/test/java/resources/testdata/storeinfo.csv")
//@RunWith(SerenityParameterizedRunner.class)
public class DataDrivenStoreTest extends TestBase {
    private   String name;
    private   String type;
    private String address;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private double lat;
    private double lng;
    private String hours;
    int storeId;
    @Steps
    StoreSteps storeSteps;


    @Title("This test will create a new store record")
    @Test
    public void test001(){
        ValidatableResponse response= storeSteps.createANewStore(name, type, address, address2, city, state, zip, lat, lng, hours);
        response.log().all().statusCode(201);
        storeId= response.extract().path("id");
        System.out.println(storeId);
    }

    @Title("This test will verify the new created store information")
    @Test
    public void test002(){
        ValidatableResponse response= storeSteps.getStoreInfoById(storeId).log().all().statusCode(200);
        response.body("data[0].name",equalTo(name));

    }
    @Title("This test will delete store information")
    @Test
    public void test003(){
        storeSteps.deleteStore(storeId).log().all().statusCode(200);

    }


}
