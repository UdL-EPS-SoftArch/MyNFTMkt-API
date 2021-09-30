package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.FixedPriceOffer;

import cat.udl.eps.softarch.mynftmkt.domain.User;
import cat.udl.eps.softarch.mynftmkt.repository.AdminRepository;
import cat.udl.eps.softarch.mynftmkt.repository.FixedPriceOfferRepository;
import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class CreationFixedPriceOffer {

    int priceDesired;
    final StepDefs stepDefs;
    final UserRepository userRepository;
    final AdminRepository adminRepository;
    final FixedPriceOfferRepository fixedPriceOfferRepository;

    CreationFixedPriceOffer(StepDefs stepDefs, UserRepository userRepository, AdminRepository adminRepository, FixedPriceOfferRepository fixedPriceOfferRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.fixedPriceOfferRepository = fixedPriceOfferRepository;
    }

    @When("^I want to make Fixed Price Offer setting the price at (\\d+)$")
    public void saveNewPrice(int newPrice) throws Throwable {
        FixedPriceOffer offer = new FixedPriceOffer();
        offer.setPrice(newPrice);


        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/fixedPriceOffers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new JSONObject(
                                ).put("price", newPrice).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }





}
