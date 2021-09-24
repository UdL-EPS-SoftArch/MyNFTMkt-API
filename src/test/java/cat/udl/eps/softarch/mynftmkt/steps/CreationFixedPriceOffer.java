package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.FixedPriceOffer;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class CreationFixedPriceOffer {
    FixedPriceOffer offer;
    int priceDesired;

    @Given("^I want to make Fixed Price Offer setting the price at (\\d+)$")
    public void saveNewPrice(int newPrice) throws Throwable {
        priceDesired = newPrice;
    }

    @When("^I register/create it$")
    public void setPriceOffer() throws Throwable {
        offer = new FixedPriceOffer();
        offer.setPrice(priceDesired);
    }

    @Then("^My offer should match the price, (\\d+), which I wanted since the start$")
    public void checkChangedPrice(int newPrice) throws Throwable {
        assertEquals(offer.getPrice(),newPrice);
    }
}
