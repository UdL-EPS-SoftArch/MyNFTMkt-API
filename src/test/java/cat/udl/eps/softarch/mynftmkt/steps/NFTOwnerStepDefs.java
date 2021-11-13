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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    public void itHasBeenAddedANFTWithIdTitleDescriptionKeywordsCategoryMediaTypeAndContentToOwnedNFTsOfUserWithTheUsername(int arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
    }

    @And("There is a registered NFT with id {int}")
    public void thereIsARegisteredNFTWithId(int arg0) {
    }

    @And("It has been removed a NFT with id {int}, title {string}, description {string}, keywords {string}, category {string}, mediaType {string} and content {string} from owned NFTs of user with the username {string}")
    public void itHasBeenRemovedANFTWithIdTitleDescriptionKeywordsCategoryMediaTypeAndContentFromOwnedNFTsOfUserWithTheUsername(long id, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String user) throws Exception {

        String path = "/nFTs/" + newResourcesUri + "/owner";
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/nFTs/{id}/owner", newResourcesUri)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.nFTs[0].uri/owner", is(user)));


    }

    @When("I add the NFT with id {int} to the owned by user {string}")
    public void iAddTheNFTWithIdToTheOwnedByUser(Long id, String user) throws Exception {
        Optional<NFT> nft = Optional.of(new NFT());
        nft = nftRepository.findById(id);
        stepDefs.result = stepDefs.mockMvc.perform(
                        // patch better than put to update only one field
                        put("/nFTs/{id}/owner", user)
                                .contentType("text/uri")
                                .content(nft.get().getUri())
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        newResourcesUri = stepDefs.result.andReturn().getResponse().getHeader("Location");

    }
}

