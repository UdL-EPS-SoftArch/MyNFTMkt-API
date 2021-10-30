package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.User;
import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class AddMoneyStepDefs {
    final StepDefs stepDefs;
    final UserRepository userRepository;

    AddMoneyStepDefs(StepDefs stepDefs, UserRepository userRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
    }
    @When("I add {string} money to the user {string}")
    public void iAddMoneyToUser(String balance, String username) throws Exception {
        Optional<User> user = userRepository.findById(username);
        BigDecimal bigDecimal = new BigDecimal(balance);
        JSONObject newBalance = new JSONObject();
        newBalance.put("balance",user.get().getBalance().add(bigDecimal));

        stepDefs.result = stepDefs.mockMvc.perform(
                        patch("/users/{username}", username)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(newBalance.toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
