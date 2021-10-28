package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.repository.NFTRepository;
import io.cucumber.java.en.When;

public class CreateDecliningPriceOfferStepDefs {

    final StepDefs stepDefs;
    final NFTRepository nftRepository;

    public CreateDecliningPriceOfferStepDefs(StepDefs stepDefs, NFTRepository nftRepository) {
        this.stepDefs = stepDefs;
        this.nftRepository = nftRepository;
    }


    @When("I create a Declining Price Offer with an starting price at {string}, an ending price at {string} and an expiration to {string} days with the previous NFT.")
    public void iCreateADecliningPriceOfferWithAnStartingPriceAtAnEndingPriceAtAndAnExpirationToDaysWithThePreviousNFT(String starting, String ending, String expiration) {
    }
}
