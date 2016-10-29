package com.jog.apps.wp.offerstore.rest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import cucumber.api.java.en.And;

public class ProductStepDef {
  @Given("^We want to save a new product$")
  public void given_we_want_to_save_new_product() throws Throwable {
  }

  @When("^we recieve a post request with name \"([^\"]*)\" description \"([^\"]*)\" and price (\\d+)$")
  public void when_we_reieve_a_post_request(String productName, String description, double price) throws Throwable {
	  assertThat(productName, is("Swag Shoes"));
	  assertThat(description, is("A very swag shoe"));
	  assertThat(price, is(29.0));
  }

  @Then("^we expect a succes message$")
  public void then_we_expect_a_succes_message() throws Throwable {
  }

  @And("^the Id of the saved product$")
  public void and_the_Id_of_the_saved_product() throws Throwable {
  }

}
