package cat.udl.eps.softarch.mynftmkt.steps;
import cat.udl.eps.softarch.mynftmkt.domain.Bid;
import cat.udl.eps.softarch.mynftmkt.repository.BidRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BidStepDefs {
    final StepDefs stepDefs;
    final BidRepository bidRepository;

    private String newResourceUri;


    BidStepDefs(StepDefs stepDefs, BidRepository bidRepository) {
        this.stepDefs = stepDefs;
        this.bidRepository = bidRepository;
        //TODO Repositories
    }

    @When("^I make a bid with a price of \"([^\"]*)\" for the NFT offer$")
    public void makeBid(BigDecimal price) throws Throwable {
        Bid bid = new Bid();
        bid.setPrice(price);
        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/bids")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new JSONObject(
                                        stepDefs.mapper.writeValueAsString(bid)
                                ).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("^It has been created a bid with a price of \"([^\"]*)\" for the NFT offer$")
    public void ItHasBeenCreatedABidWithAPrice(BigDecimal price) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get(newResourceUri)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.price").value(price))
                //.andExpect(jsonPath("$.NFTOffer", is(offerID))) //TODO
                .andExpect(jsonPath("$.dateTime", startsWith(ZonedDateTime.now(ZoneId.of("Europe/Paris")).format(DateTimeFormatter.ISO_LOCAL_DATE))));
    }

    @And("^The status of the bid is \"([^\"]*)\"$")
    public void TheStatusOfTheBidIs(String status) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get(newResourceUri)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(Bid.StatusTypes.valueOf(status).toString())));
    }

    @When("^I try to delete the bid$")
    public void deleteBid() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete(newResourceUri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}


