package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.Offer;
import cat.udl.eps.softarch.mynftmkt.repository.NFTRepository;
import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class CreateOfferStepDefs {
    String newResourcesUri;
    final StepDefs stepDefs;

    public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    CreateOfferStepDefs(StepDefs stepDefs) {
        this.stepDefs = stepDefs;
    }
    @When("I create an offer with id {long}")
    public void iCreateAnOfferWithId(Long id) throws Exception {
        Offer offer = new Offer();
        offer.setId(id);
        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/offers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(offer))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourcesUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }

}
