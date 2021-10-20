package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.NFT;
import cat.udl.eps.softarch.mynftmkt.repository.NFTRepository;
import io.cucumber.core.gherkin.Step;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

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

    @When("I add the NFT with id {long}")
    public void iAddTheNFTWithId(Long id) {
        /*Optional<NFT> nft = Optional.of(new NFT());
        nft = nftRepository.findById(id);
        JSONObject newNFT = new JSONObject();
        newNFT.put("favoriteNFTs", nft);
        stepDefs.result = stepDefs.mockMvc.perform(
                        // patch better than put to update only one field
                        patch("/users/{username}", username)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(newNFT))
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());*/
    }

    @And("It has been added a NFT with id {int}, title {string}, description {string}, keywords {string}, category {string}, mediaType {string} and content {string} to owned NFTs of user with the username {string}")
    public void itHasBeenAddedANFTWithIdTitleDescriptionKeywordsCategoryMediaTypeAndContentToOwnedNFTsOfUserWithTheUsername(int arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
    }

    @And("There is a registered NFT with id {int}")
    public void thereIsARegisteredNFTWithId(int arg0) {
    }

    @When("I remove the NFT with id {int} from the favorites of user {string}")
    public void iRemoveTheNFTWithIdFromTheFavoritesOfUser(int arg0, String arg1) {
    }

    @And("It has been removed a NFT with id {int}, title {string}, description {string}, keywords {string}, category {string}, mediaType {string} and content {string} from owned NFTs of user with the username {string}")
    public void itHasBeenRemovedANFTWithIdTitleDescriptionKeywordsCategoryMediaTypeAndContentFromOwnedNFTsOfUserWithTheUsername(int arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
    }
}
