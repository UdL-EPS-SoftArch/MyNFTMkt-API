package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.HighestBidOffer;
import cat.udl.eps.softarch.mynftmkt.domain.NFT;
import cat.udl.eps.softarch.mynftmkt.repository.HighestBidOfferRepository;

import cat.udl.eps.softarch.mynftmkt.repository.NFTRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.mockito.internal.matchers.Null;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class HighestBidOfferStepDefs {

    final StepDefs stepDefs;
    final HighestBidOfferRepository highestBidOfferRepository;
    final NFTRepository ntfRepository;

    private String newResourceUri;
    private NFT ntf;
    private Long idNTF;

    HighestBidOfferStepDefs(StepDefs stepDefs, HighestBidOfferRepository highestBidOfferRepository, NFTRepository ntfRepository ) {
        this.stepDefs = stepDefs;
        this.highestBidOfferRepository = highestBidOfferRepository;
        this.ntfRepository = ntfRepository;
    }

    @When("^I make Highest Bid Offer I set a minimum price \"([^\"]*)\" and price to reserve \"([^\"]*)\" and select the expiration date \"([^\"]*)\" .$")
    public void makeHighestBidNFTOffer(BigDecimal minimumBid, BigDecimal reservePrice, String expiration) throws Throwable {
        HighestBidOffer highestBidOffer = new HighestBidOffer();

        highestBidOffer.setMinimumBid(minimumBid);
        highestBidOffer.setReservePrice(reservePrice);
        highestBidOffer.setExpiration(LocalDate.parse(expiration));
        highestBidOffer.setNft(ntf);

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/highestBidOffers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new JSONObject(
                                        stepDefs.mapper.writeValueAsString(highestBidOffer)
                                ).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

    }


    @When("^I remove a Higher Bid Offer with id \"([^\"]*)\" .$")
    public void i_remove_a_higher_bid_nft_bid(Long id) throws Throwable {
        if( id == 2)
            idNTF = highestBidOfferRepository.findById(id).get().getNft().getId();



        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/highestBidOffers/"+ id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("Do not delete the related NTF")
    public void do_not_delete_the_related_ntf() throws Throwable {
        NFT nft = ntfRepository.findById(idNTF).get();
        Assert.assertNotNull(nft);
    }





    @When("^I modify Highest Bid Offer with id \"([^\"]*)\" I set a minimum price \"([^\"]*)\" and price to reserve \"([^\"]*)\" and select the expiration date \"([^\"]*)\" .$")
    public void i_update_a_higher_bid_nft_bid(Long id, BigDecimal minimumBid, BigDecimal reservePrice, String expiration ) throws Throwable {
        HighestBidOffer highestBidOffer = new HighestBidOffer();

        highestBidOffer.setMinimumBid(minimumBid);
        highestBidOffer.setReservePrice(reservePrice);
        highestBidOffer.setExpiration(LocalDate.parse(expiration));



        stepDefs.result = stepDefs.mockMvc.perform(
                        put("/highestBidOffers/"+id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new JSONObject(
                                        stepDefs.mapper.writeValueAsString(highestBidOffer)
                                ).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("Select the NTF {string} for option Highest Bid Offer .")
    public void select_the_ntf(String string) {
        ntf = ntfRepository.findByTitle(string);
    }

    @When("The Highest Bid Offer is associated with the NTF {string} .")
    public void and_the_highest_bid_nft_offer_is_associated_with_the_ntf(String string) throws Exception {

        stepDefs.result = stepDefs.mockMvc.perform(
                        get(newResourceUri+"/nft")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.title", is(string)));

    }

}
