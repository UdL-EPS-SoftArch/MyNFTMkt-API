package cat.udl.eps.softarch.mynftmkt.steps;

        import cat.udl.eps.softarch.mynftmkt.domain.NFT;
        import cat.udl.eps.softarch.mynftmkt.domain.User;
        import cat.udl.eps.softarch.mynftmkt.repository.NFTRepository;
        import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
        import io.cucumber.java.en.And;
        import io.cucumber.java.en.Given;
        import io.cucumber.java.en.When;
        import org.json.JSONObject;
        import org.springframework.http.MediaType;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.security.crypto.password.PasswordEncoder;
        import org.springframework.test.web.servlet.ResultMatcher;
        import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.Arrays;
        import java.util.List;
        import java.util.Optional;

        import static org.hamcrest.Matchers.contains;
        import static org.hamcrest.Matchers.is;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class FavoriteNFTStepDefs {
    final StepDefs stepDefs;
    final UserRepository userRepository;
    final NFTRepository nftRepository;

    public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    FavoriteNFTStepDefs(StepDefs stepDefs, UserRepository userRepository, NFTRepository nftRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
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

    @Transactional
    @When("I add the NFT with id {long} to the favorites of user {string}")
    public void iAddTheNFTWithIdToTheFavoritesOfUser(Long id, String username) throws Exception {
        Optional<NFT> nft = nftRepository.findById(id);
        stepDefs.result = stepDefs.mockMvc.perform(
                        patch("/users/{username}/favoriteNFTs", username)
                                .contentType("text/uri-list")
                                .content(nft.get().getUri())
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been added a NFT with id {long}, title {string}, description {string}, keywords {string}, category {string}, mediaType {string} and content {string} to favorite NFTs of user with the username {string}")
    public void itHasBeenAddedANFTWithIdTitleDescriptionKeywordsCategoryMediaTypeAndContentToFavoriteNFTsOfUserWithTheUsername(Long id, String title, String description, String keywords, String category, String mediaType, String content, String username) throws Exception {
        String path = "/nFTs/" + id;
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/users/{username}/favoriteNFTs", username)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.nFTs[0].uri", is(path)));
    }

    @Transactional
    @And("There is a registered NFT with id {long} in the list of favorites of user {string}")
    public void thereIsARegisteredNFTWithIdInTheListOfFavoritesOfUser(Long id, String username) {
        Optional<User> user = userRepository.findById(username);
        Optional<NFT> nft = nftRepository.findById(id);
        if(!user.get().getFavoriteNFTs().contains(nft)) {
            user.get().getFavoriteNFTs().add(nft.get());
            userRepository.save(user.get());
        }
    }


    @When("I remove the NFT with id {int} from the favorites of user {string}")
    public void iRemoveTheNFTWithIdFromTheFavoritesOfUser(int id, String username)  throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/users/{username}/favoriteNFTs/{id}",username,id)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been removed a NFT with id {long} from favorite NFTs of user with the username {string}")
    public void itHasBeenRemovedANFTWithIdTitleDescriptionKeywordsCategoryMediaTypeAndContentFromFavoriteNFTsOfUserWithTheUsername(Long id, String username) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/users/{username}/favoriteNFTs", username)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.nFTs").isEmpty());
    }

    @And("The favorite list of user {string} contains the NFTs with id {int} and id {int}")
    public void theFavoriteListOfUserContainsTheNFTsWithIdAndId(String username, int id1, int id2) throws Exception {
        String path1 = "/nFTs/" + id1;
        String path2 = "/nFTs/" + id2;
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/users/{username}/favoriteNFTs", username)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.nFTs[0].uri", is(path2)))
                .andExpect(jsonPath("$._embedded.nFTs[1].uri", is(path1)));
    }
}
