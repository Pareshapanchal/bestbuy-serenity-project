package com.bestbuy.storeinfo;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTags;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class StoreCRUDTest extends TestBase {
    static int storeId;
    static String name = "Man" + TestUtils.randomStr(5);
    static String type = "BigBox";
    static String address = TestUtils.getRandomValue() + "," + "Pinner" + TestUtils.randomStr(4);
    static String address2 = "Pinner";
    static String city = "London";
    static String state = "Middex";
    static String zip = "HA5 5IU";
    static double lat = 50.231256;
    static double lng = -63.235;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    @Steps
    StoreSteps storeSteps;

    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SANITY"),
    })
    @Title("This test will return all the store information ")
    @Test
    public void test001() {
        storeSteps.getAllStoreInfo().log().all().statusCode(200);
    }

    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SMOKE"),
    })
    @Title("This test will create a new store record")
    @Test
    public void test002() {
        ValidatableResponse response = storeSteps.createANewStore(name, type, address, address2, city, state, zip, lat, lng, hours);
        response.log().all().statusCode(201);
        storeId = response.extract().path("id");
        System.out.println(storeId);
    }

    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SMOKE"),
    })
    @Title("This test will verify the new created store information")
    @Test
    public void test003() {
        ValidatableResponse response = storeSteps.getStoreInfoById(storeId).log().all().statusCode(200);


    }

    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SMOKE"),
    })
    @Title("This test will update store information")
    @Test
    public void test004() {
        name = "Stanmore harrow church";
      Response response= storeSteps.updateStoreInfo(storeId, name, type, address, address2, city, state, zip, lat, lng, hours);
       response.then().log().all().statusCode(200);
      // String actualname= response.then().extract().path("name");
       // Assert.assertEquals(name,actualname.replace("[","").replace("]",""));

    }

    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SMOKE"),
    })
    @Title("This test will delete store information")
    @Test
    public void test005() {
        storeSteps.deleteStore(storeId).log().all().statusCode(200);

    }
}
