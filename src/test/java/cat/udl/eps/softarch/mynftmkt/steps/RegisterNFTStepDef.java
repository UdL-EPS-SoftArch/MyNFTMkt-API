package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.NFT;
import cat.udl.eps.softarch.mynftmkt.repository.NFTRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class RegisterNFTStepDef {

    final StepDefs stepDefs;
    final NFTRepository nftRepository;
    String newResourcesUri;

    public RegisterNFTStepDef(StepDefs stepDefs, NFTRepository nftRepository){
        this.stepDefs = stepDefs;
        this.nftRepository = nftRepository;
    }


    @Given("There is no registered NFT with id {long}")
    public void thereIsNoRegisteredNFTWithId(long id) {
        Assert.assertFalse("NFT \""
                        +  id + "\"shouldn't exist",
                nftRepository.existsById(id));
    }

    @When("I register a new NFT with title {string}, description {string}, keywords {string}, category {string}, mediaType {string} and content {string}")
    public void iRegisterANewNFTWithTitleDescriptionKeywordsCategoryMediaTypeAndContent(String title, String description,
                                                                                        String keywords, String category,
                                                                                        String mediType, String content)
            throws Throwable{
        NFT nft = new NFT();
        nft.setTitle(title);
        nft.setDescription(description);
        nft.setKeywords(List.of(keywords.split(",")));
        nft.setCategory(category);
        nft.setMediaType(mediType);
        nft.setContent(content);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/nFTs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( stepDefs.mapper.writeValueAsString(nft))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());

        newResourcesUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

    @And("It has been created a NFT title {string}, description {string}, keywords {string}, category {string}, mediaType {string} and content {string}")
    public void itHasBeenCreatedANFTTitleDescriptionKeywordsCategoryMediaTypeAndContent(String title, String description,
                                                                                        String keywords, String category,
                                                                                        String mediType, String content)
            throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                        get(newResourcesUri)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(title)));
    }

    @When("I register a new NFT with no title, description {string}, keywords {string}, category {string}, mediaType {string} and content {string}")
    public void iRegisterANewNFTWithNoTitleDescriptionKeywordsCategoryMediaTypeAndContent(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }
}

