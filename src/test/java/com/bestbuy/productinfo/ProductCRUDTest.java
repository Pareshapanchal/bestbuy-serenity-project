package com.bestbuy.productinfo;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTags;
import org.junit.Assert;
import org.junit.Test;

//@RunWith(SerenityRunner.class)
public class ProductCRUDTest extends TestBase {

    static String name = "Duracell"+ TestUtils.getRandomValue();
    static String type ="HardGood";
    static double price= 5.99;
    static int shipping =0 ;
    static String upc="041333424019" ;
    static String description="Compatible with select electronic devices; AAA size; DURALOCK Power Preserve technology; 4-pack";
    static String manufacturer="Duracell";
    static String model="MN2400B4Z";
    static String url="http://www.bestbuy.com/site/duracell-aaa-batteries-4-pack/43900.p?id=1051384074145&skuId=43900&cmp=RMXCC";
    static String image="http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";
    static int productId,pid;
    @Steps
    ProductSteps productSteps;
    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SMOKE"),
    })
    @Title("This test will create a new Product")
    @Test
    public void test001(){
        ValidatableResponse response = productSteps.createProduct(name,type,price, upc, shipping, description,  manufacturer,model,url,image);
        productId= response.extract().path("id");
         response.log().all().statusCode(201);
        System.out.println("Product id :"+productId);
    }
    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SMOKE"),
    })
  @Title("Verify if the product was added to the application")
  @Test
    public void test002() {
      Response response = productSteps.getProductInfoByname(name);
        pid= response.then().log().all().extract().body().path("data[0].id");
      response.then().log().all();
      System.out.println(pid);
      Assert.assertEquals(pid,productId);

    }
    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SMOKE"),
    })
    @Title("This test will update the product information and verify the updated information")
    @Test
    public void test003(){
        name = name+ "_upadated";
        price =9.99;
        shipping=9;
        Response response = productSteps.upadateProductInfo(productId,name,type,price, upc, shipping, description,  manufacturer,model,url,image);
        response.prettyPrint();
        String actual_name = response.jsonPath().get("name").toString();
        Assert.assertEquals(name, actual_name.replace("[","").replace("]",""));

        String actual_price= response.jsonPath().get("price").toString();
        Assert.assertEquals(200,response.statusCode());

    }
    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SMOKE"),
    })
    @Title("This test will delete product")
    @Test
    public void test004(){
         productSteps.deleteProduct(productId).statusCode(200);

         ValidatableResponse response = productSteps.getProductInfoById(productId);
        response.log().all();
        response.statusCode(200);

    }

}
