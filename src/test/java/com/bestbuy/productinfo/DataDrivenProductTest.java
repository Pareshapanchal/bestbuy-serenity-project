package com.bestbuy.productinfo;

import com.bestbuy.testbase.TestBase;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

//@UseTestDataFrom("src/test/java/resources/testdata/productinfo.csv")
//@RunWith(SerenityParameterizedRunner.class)
public class DataDrivenProductTest extends TestBase {
    private String name;
    private String type;
    private double price;
    private int shipping;
    private String upc;
    private String description;
    private String manufacturer;
    private String model;
    private String url;
    private String image;
    int productId,pid;
    @Steps
    ProductSteps productSteps;
    @Title("This test will create a new Product")
    @Test
    public void test001(){
        ValidatableResponse response = productSteps.createProduct(name,type,price, upc, shipping, description,  manufacturer,model,url,image);
        productId= response.extract().path("id");
        response.log().all().statusCode(201);
        System.out.println("Product id :"+productId);
    }
    @Title("Verify if the product was added to the application")
    @Test
    public void test002() {
        Response response = productSteps.getProductInfoByname(name);
        pid= response.then().log().all().extract().body().path("data[0].id");
        response.then().log().all();
        System.out.println(pid);
        Assert.assertEquals(pid,productId);

    }

    @Title("This test will delete product")
    @Test
    public void test003(){
        productSteps.deleteProduct(productId).statusCode(200);

        ValidatableResponse response = productSteps.getProductInfoById(productId);
        response.log().all();
        response.statusCode(200);

    }

}
