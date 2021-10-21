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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.is;
import java.util.List;
import java.util.Optional;

public class NFTOwnerStepDefs {

    final StepDefs stepDefs;
    final NFTRepository nftRepository;

    public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    NFTOwnerStepDefs(StepDefs stepDefs, NFTRepository nftRepository){
        this.stepDefs = stepDefs;
        this.nftRepository = nftRepository;
    }

    @Given("There is a registered NFT with id {long}, title {string}, description {string}, keywords {string}, category {string}, mediaType {string} and content {string}")
    public void thereIsARegisteredNFTWithIdTitleDescriptionKeywordsCategoryMediaTypeAndContent(Long id, String title, String description, String keywords, String category, String mediaType, String content) {
        if (!nftRepository.existsById(id)) {
            NFT nft = new NFT();
            nft.setId(id);
            nft.setTitle(title);
            nft.setDescription(description);
            nft.setKeywords(List.of(keywords.split(",")));
            nft.setCategory(category);
            nft.setMediaType(mediaType);
            nft.setContent(content);
            nftRepository.save(nft);
        }
    }

    @When("II add the NFT with id {long} to the owned by user {String}")
    public void iAddTheNFTWithId(Long id, String user) throws Exception {
        Optional<NFT> nft = Optional.of(new NFT());
        nft = nftRepository.findById(id);
        stepDefs.result = stepDefs.mockMvc.perform(
                        // patch better than put to update only one field
                        put("/nFTs/{id}/owner", user)
                                .contentType("text/uri")
                                .content(nft.get().getUri())
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been added a NFT with id {int}, title {string}, description {string}, keywords {string}, category {string}, mediaType {string} and content {string} to owned NFTs of user with the username {string}")
    public void itHasBeenAddedANFTWithIdTitleDescriptionKeywordsCategoryMediaTypeAndContentToOwnedNFTsOfUserWithTheUsername(long id, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String username) throws Exception {
        String path = "/nFTs/" + id + "/" + username;
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/nFTs/{id}/owner", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.nFTs[0].uri", is(path)));
    }

    @And("There is a registered NFT with id {int}")
    public void thereIsARegisteredNFTWithId(int arg0) {
    }

    @When("I remove the NFT with id {int} from the favorites of user {string}")
    public void iRemoveTheNFTWithIdFromTheOwnedOfUser(int arg0, String arg1) {
    }

    @And("It has been removed a NFT with id {int}, title {string}, description {string}, keywords {string}, category {string}, mediaType {string} and content {string} from owned NFTs of user with the username {string}")
    public void itHasBeenRemovedANFTWithIdTitleDescriptionKeywordsCategoryMediaTypeAndContentFromOwnedNFTsOfUserWithTheUsername(int arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
    }
}