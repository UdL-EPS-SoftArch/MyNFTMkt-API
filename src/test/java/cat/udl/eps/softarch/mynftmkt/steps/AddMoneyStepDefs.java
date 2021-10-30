package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.Bid;
import cat.udl.eps.softarch.mynftmkt.domain.User;
import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;

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

        // Converting the string into BigDecimal
        BigDecimal bigDecimal = new BigDecimal(balance);

        // Adding money to the user
        BigDecimal addedBalance = user.orElse(new User()).getBalance().add(bigDecimal); // orElse is a non-exception get() method

        // JSONObject to patch the balance
        JSONObject balanceToBeAdded = new JSONObject();
        balanceToBeAdded.put("balance", addedBalance);

        stepDefs.result = stepDefs.mockMvc.perform(
                        patch("/users/{username}", username)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(balanceToBeAdded.toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
    @And("Given a user {string} has a balance of {string}")
    public void givenAUserHasABalanceOf(String username, String balance) {
        User user = userRepository.findById(username).orElse(new User()); // orElse is a non-exception get() method

        Assert.assertEquals("user \""
                + username + "\"should have a balance of 0", 0, user.getBalance().compareTo(BigDecimal.ZERO));
    }
}
