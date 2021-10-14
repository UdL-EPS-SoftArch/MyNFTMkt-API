package cat.udl.eps.softarch.mynftmkt.steps;

        import cat.udl.eps.softarch.mynftmkt.domain.NFT;
        import cat.udl.eps.softarch.mynftmkt.repository.NFTRepository;
        import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
        import io.cucumber.java.en.And;
        import io.cucumber.java.en.Given;
        import io.cucumber.java.en.When;
        import org.json.JSONObject;
        import org.springframework.http.MediaType;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.security.crypto.password.PasswordEncoder;

        import java.util.List;
        import java.util.Optional;

        import static org.hamcrest.Matchers.contains;
        import static org.hamcrest.Matchers.is;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

    @When("I add the NFT with id {long} to the favorites of user {string}")
    public void iAddTheNFTWithIdToTheFavoritesOfUser(Long id, String username) throws Exception {
        Optional<NFT> nft = Optional.of(new NFT());
        nft = nftRepository.findById(id);
        JSONObject newNFT = new JSONObject();
        newNFT.put("favoriteNFTs", nft);
        stepDefs.result = stepDefs.mockMvc.perform(
                        // patch better than put to update only one field
                        patch("/users/{username}", username)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(newNFT))
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has been added a NFT with id {long}, title {string}, description {string}, keywords {string}, category {string}, mediaType {string} and content {string} to favorite NFTs of user with the username {string}")
    public void itHasBeenAddedANFTWithIdTitleDescriptionKeywordsCategoryMediaTypeAndContentToFavoriteNFTsOfUserWithTheUsername(Long id, String title, String description, String keywords, String category, String mediaType, String content, String username) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/users/{username}", username)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.favoriteNFTs[*].id", contains(id)));
    }
}
