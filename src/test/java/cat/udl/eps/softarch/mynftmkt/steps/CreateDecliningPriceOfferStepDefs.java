package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.DecliningPriceOffer;
import cat.udl.eps.softarch.mynftmkt.repository.NFTRepository;
import io.cucumber.java.en.When;
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


    @When("I create a Declining Price Offer with an starting price at {string}, an ending price at {string} and an expiration to {string} days with the previous NFT.")
    public void iCreateADecliningPriceOfferWithAnStartingPriceAtAnEndingPriceAtAndAnExpirationToDaysWithThePreviousNFT(String starting, String ending, String expiration) throws Exception {
        id = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                        get(id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());
        DecliningPriceOffer dpo = new DecliningPriceOffer();
        dpo.setStartingPrice(BigDecimal.valueOf(Long.parseLong(starting)));
        dpo.setEndingPrice(BigDecimal.valueOf(Long.parseLong(ending)));
        ZonedDateTime zdt = ZonedDateTime.now();
        dpo.setNft(nftRepository.findById(Long.parseLong(id.substring(id.lastIndexOf("/")+1))).get());
        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/decliningPriceOffers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(dpo))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
