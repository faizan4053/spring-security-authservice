package com.eldorado.authservice.stepdefinitions;

import static org.junit.Assert.assertEquals;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


class AppUser{
	
	public Boolean openWebsite(Boolean click) {
		return click;
	}
	
	public Boolean enterDetails(Boolean details) {
		return details;
	}
	
	public Boolean userNotHaveAdminAccess(Boolean access) {
		return access;
	}
	
}

class AppAdminUser{
	
	public Boolean adminOpenWebsite(Boolean click) {
		return click;
	}
	
	public Boolean enterDetails(Boolean details) {
		return details;
	}
	
	public Boolean adminAuthenticatedToProductListPage(Boolean access) {
		return access;
	}
	
	public Boolean abilityToPerformOtherAdminActivities(Boolean ability) {
		return ability;
	}
}

public class AuthSteps {
	
	
	private AppUser user;
	private Boolean resultUser;
	private AppAdminUser admin;
	private Boolean resultAdmin;
	
	@Given("user have valid credentials")
	public void usere_have_valid_credentials() {
		user=new AppUser();
	}

	@When("user open website")
	public void user_open_website() {
		this.resultUser=user.openWebsite(true);
	}

	@And("user entered username and password")
	public void entered_username_and_password() {
		this.resultUser = resultUser && user.enterDetails(true);
	}

	@Then("user should be authenticated to see the product list page")
	public void user_should_be_authenticated_to_see_the_product_list_page() {
		assertEquals(true, this.resultUser);
	}
	
	@And("should not have the access for Admin Features")
	public void should_not_have_the_access_for_admin_features() {
		this.resultUser=this.resultUser && user.userNotHaveAdminAccess(true);
		assertEquals(true,this.resultUser);
	}
	
	@Given("admin have valid credentials")
	public void admin_have_valid_credentials() {
		admin=new AppAdminUser();
	}

	@When("admin open website")
	public void admin_open_website() {
		resultAdmin=admin.adminOpenWebsite(true);
		resultAdmin=resultAdmin && admin.enterDetails(true) ;
	}
	
	@And("admin entered username and password")
	public void entered_username_and_password_admin() {
		this.resultAdmin = resultAdmin && admin.enterDetails(true);
	}


	@Then("admin should be authenticated to see the product list page")
	public void admin_should_be_authenticated_to_see_the_product_list_page() {
		assertEquals(true, resultAdmin);
	}

	@And("should have ability to perform other admin activities as well")
	public void should_have_ability_to_perform_other_admin_activities_as_well() {
		resultAdmin=resultAdmin && admin.abilityToPerformOtherAdminActivities(true);
	}
	
	
	
	@Given("user have invalid credentials")
	public void usere_have_invalid_credentials() {
		user=new AppUser();
		resultUser=user.openWebsite(true);
		
	}
	@And("entered invalid username and password")
	public void entered_invalid_username_and_password() {
		resultUser=resultUser && user.enterDetails(false);
	}

	@Then("user should not be authenticated")
	public void user_should_not_be_authenticated() {
		assertEquals(false, resultUser);
	}
}
