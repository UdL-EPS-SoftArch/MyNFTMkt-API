package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.NFT;
import cat.udl.eps.softarch.mynftmkt.repository.NFTRepository;
import io.cucumber.core.gherkin.Step;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class NFTOwnerStepDefs {

    final StepDefs stepDefs;
    final NFTRepository nftRepository;
    String newResourcesUri;

    public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    NFTOwnerStepDefs(StepDefs stepDefs, NFTRepository nftRepository){
        this.stepDefs = stepDefs;
        this.nftRepository = nftRepository;
    }

    @And("It has been added a NFT with id {int}, title {string}, description {string}, keywords {string}, category {string}, mediaType {string} and content {string} to owned NFTs of user with the username {string}")
    public void itHasBeenAddedANFTWithIdTitleDescriptionKeywordsCategoryMediaTypeAndContentToOwnedNFTsOfUserWithTheUsername(long id, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String username) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/nFTs/{id}/owner", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(username)));


    }


    @When("I add the NFT with id {int} to the owned by user {string}")
    public void iAddTheNFTWithIdToTheOwnedByUser(long id, String user) throws Exception {
        Optional<NFT> nft = Optional.of(new NFT());
        nft = nftRepository.findById(id);
        stepDefs.result = stepDefs.mockMvc.perform(
                        // patch better than put to update only one field
                        put("/nFTs/{id}/owner", id)
                                .contentType("text/uri-list")
                                .content("/users/" + user)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("There is a registered NFT with id {int} and title {string}")
    public void thereIsARegisteredNFTWithIdAndTitle(long id, String title) {
        if (!nftRepository.existsById(id)) {
            NFT nft = new NFT();
            nft.setId(id);
            nft.setTitle(title);
            nftRepository.save(nft);
        }
    }

    @When("I change the owner of NFT with id {int} to user {string}")
    public void iChangeTheOwnerOfNFTWithIdToUser(long id, String newUser) throws Exception {
        Optional<NFT> nft = nftRepository.findById(id);
        stepDefs.result = stepDefs.mockMvc.perform(
                        put("/nFTs/{id}/owner", id)
                                .contentType("text/uri-list")
                                .content("/users/" + newUser)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("NFT with id {int}, title {string}, description {string}, keywords {string}, category {string}, mediaType {string} and content {string} now has owner {string}")
    public void nftWithIdTitleDescriptionKeywordsCategoryMediaTypeAndContentNowHasOwner(long id, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String newUser) throws Exception {

        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/nFTs/{id}/owner", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(newUser)));
    }
}

