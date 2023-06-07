package com.bestbuy.storeinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.model.StorePojo;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class StoreSteps {
    @Step("Get all the store information")
    public ValidatableResponse getAllStoreInfo(){
        return  SerenityRest.given()
                .header("Content-Type","application/json")
                .when()
                .get()
                .then();
    }
    @Step("Create a new Store")
    public ValidatableResponse createANewStore(String name,String type,String address,String address2,String city,
                                String state,String zip,double lat, double lng,String hours ){

        StorePojo storePojo= StorePojo.getStorePojo(name, type, address, address2, city, state, zip, lat, lng, hours);

        return SerenityRest.given()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .when()
                .body(storePojo)
                .post(Path.STORES)
                .then();

    }
    @Step("Get the store information by store id : {0}")
    public ValidatableResponse getStoreInfoById(int storeid){
        return  SerenityRest.given()
                .header("Content-Type","application/json")
                .when()
                .get(EndPoints.GET_SINGLE_STOREINFO_BY_ID+storeid)
                .then();
    }
    @Step()
    public Response updateStoreInfo(int storeid, String name, String type, String address, String address2, String city,
                                    String state, String zip, double lat, double lng, String hours ){

        StorePojo storePojo= StorePojo.getStorePojo(name, type, address, address2, city, state, zip, lat, lng, hours);

        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .when()
                .body(storePojo)
                .patch(EndPoints.UPDATE_STORE_BY_ID+storeid);


    }

    @Step("Delete store information")
    public ValidatableResponse deleteStore(int storeid){
        return SerenityRest.given()
                .when()
                .delete(EndPoints.DELETE_STOREINFO_BY_ID+storeid)
                .then();

    }
}
