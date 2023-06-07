package com.bestbuy.productinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.model.ProductPojo;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class ProductSteps {
    @Step("Creating product with name : {0}, type :{1}, price : {2}, upc :{3}, shipping : {4}, description : {5}, manufacturer :{6},model : {7}, url : {8}, image : {9}")

    public ValidatableResponse createProduct(String name, String type, double price, String upc, int shipping,
                                             String description, String manufacturer,String model,String url,String image){
    ProductPojo productPojo = ProductPojo.getProductPojo(name,type,price, upc, shipping, description,  manufacturer,model,url,image);

    return SerenityRest.given()
            .header("Content-Type","application/json")
            .when()
            .body(productPojo)
            .post(Path.PRODUCTS)
            .then();
    }
    @Step("Verify product is added by name :{0}")
    public Response getProductInfoByname(String name){
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_NAME + name);
    }
    @Step("Creating product with productId :{0} ,name : {1}, type :{2}, price : {3}, upc :{4}, shipping : {5}, description : {6}, manufacturer :{7},model : {8}, url : {9}, image : {10}")

    public Response upadateProductInfo(int productId, String name, String type, double price, String upc, int shipping,
                                       String description, String manufacturer, String model, String url, String image){

        ProductPojo productPojo = ProductPojo.getProductPojo(name,type,price, upc, shipping, description,  manufacturer,model,url,image);

        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .when()
                .body(productPojo)
                .patch(EndPoints.UPDATE_PRODUCT_BY_ID+productId);
    }
    @Step("Delete product by productid :{0}")
    public ValidatableResponse deleteProduct(int productId){
        return SerenityRest.given().log().all()
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID+productId)
                .then();
    }
    @Step("Get single product by productid :{0}")
    public ValidatableResponse getProductInfoById(int productId){

        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID+productId)
                .then();
    }
}
