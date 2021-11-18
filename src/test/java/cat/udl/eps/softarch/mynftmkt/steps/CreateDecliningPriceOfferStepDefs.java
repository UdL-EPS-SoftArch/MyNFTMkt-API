package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.DecliningPriceOffer;
import cat.udl.eps.softarch.mynftmkt.repository.NFTRepository;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateDecliningPriceOfferStepDefs {

    final StepDefs stepDefs;
    final NFTRepository nftRepository;

    public static String id;

    public CreateDecliningPriceOfferStepDefs(StepDefs stepDefs, NFTRepository nftRepository) {
        this.stepDefs = stepDefs;
        this.nftRepository = nftRepository;
    }


    @When("I create a Declining Price Offer with an starting price at ([\\d-.]+), an ending price at ([\\d-.]+) and an expiration to \"([^\"]*)\" days with the previous NFT.$")
    public void iCreateADecliningPriceOfferWithAnStartingPriceAtAnEndingPriceAtAndAnExpirationToDaysWithThePreviousNFT(BigDecimal starting, BigDecimal ending, String expiration) throws Exception {
        id = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                        get(id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());
        ZonedDateTime zdt = ZonedDateTime.now();
        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/decliningPriceOffers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new JSONObject().put("startingPrice", starting).toString())
                                .content(new JSONObject().put("endingPrice", ending).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("^I create a Declining Price Offer with negative price ([\\d-.]+)$")
    public void iCreateADecliningPriceOfferWithNegativePrice(BigDecimal incorrectPrice) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/decliningPriceOffers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new JSONObject().put("startingPrice", incorrectPrice).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        id = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

}
