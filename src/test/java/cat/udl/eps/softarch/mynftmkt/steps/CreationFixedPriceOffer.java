package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.FixedPriceOffer;

import cat.udl.eps.softarch.mynftmkt.domain.User;
import cat.udl.eps.softarch.mynftmkt.repository.AdminRepository;
import cat.udl.eps.softarch.mynftmkt.repository.FixedPriceOfferRepository;
import cat.udl.eps.softarch.mynftmkt.repository.NFTRepository;
import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.http.HttpResponse;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreationFixedPriceOffer {

    String newResourcesUri;
    int priceDesired;
    final StepDefs stepDefs;
    final UserRepository userRepository;
    final AdminRepository adminRepository;
    final FixedPriceOfferRepository fixedPriceOfferRepository;
    final NFTRepository nftRepository;
    FixedPriceOffer offer = new FixedPriceOffer();

    CreationFixedPriceOffer(StepDefs stepDefs, UserRepository userRepository, AdminRepository adminRepository, FixedPriceOfferRepository fixedPriceOfferRepository, NFTRepository nftRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.fixedPriceOfferRepository = fixedPriceOfferRepository;
        this.nftRepository = nftRepository;
    }


    @When("^It has created a Fixed Price Offer with the price at ([\\d-.]+)$")
    public void saveNewPrice(BigDecimal newPrice) throws Throwable {
        //FixedPriceOffer offer = new FixedPriceOffer();
        offer.setPrice(newPrice);



        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/fixedPriceOffers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new JSONObject(
                                ).put("price", newPrice).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourcesUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

    }

    @When("^I create a Fixed Price Offer with the price at ([\\d-.]+) of the NFT \"([^\"]*)\"$")
    @Transactional
    public void saveNewPrice(BigDecimal newPrice, String title) throws Throwable {
        //FixedPriceOffer offer = new FixedPriceOffer();
        offer.setPrice(newPrice);
        offer.setNft(nftRepository.findByTitle(title));

        System.out.print("NFT AGAFAT:" + offer.getNft());


        stepDefs.result = stepDefs.mockMvc.perform(
                post("/fixedPriceOffers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( stepDefs.mapper.writeValueAsString(offer))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourcesUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

    }


    @Then("^I try to modify the fixed price offer")
    public void modifyTheFixedPriceOffer() throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                put(newResourcesUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(
                        ).put("price", 5).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print()

        );
    }

    @When("^It is not possible to create a fixed price offer with price ([\\d-.]+)$")
    public void checkNotCorrect(BigDecimal incorrectPrice) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/fixedPriceOffers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject(
                        ).put("price", incorrectPrice).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourcesUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

    }
    @Then("^The offer matches the price, ([\\d-.]+)$")
    public void checkPrice(BigDecimal priceToCheck) throws Throwable{

        stepDefs.result  = stepDefs.mockMvc.perform(
                        get(newResourcesUri)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(priceToCheck))
                .andDo(print());
    }


    @Then("^The offer NFT matches the NFT \"([^\"]*)\"$")
    public void checkPrice(String name) throws Throwable{
        System.out.print(newResourcesUri);
        stepDefs.result  = stepDefs.mockMvc.perform(
                get(newResourcesUri+"/nft/")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(name)))
                .andDo(print());
    }



}
