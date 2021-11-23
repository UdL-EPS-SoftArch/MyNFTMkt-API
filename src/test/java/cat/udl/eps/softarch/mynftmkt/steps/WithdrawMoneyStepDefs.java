package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.User;
import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class WithdrawMoneyStepDefs {
    final StepDefs stepDefs;
    final UserRepository userRepository;

    WithdrawMoneyStepDefs(StepDefs stepDefs, UserRepository userRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
    }
    @When("I withdraw {string} money from the user {string}")
    public void iWithdrawMoneyFromUser(String balance, String username) throws Exception {
        Optional<User> user = userRepository.findById(username);

        // Converting the string into BigDecimal
        BigDecimal bigDecimal = new BigDecimal(balance);

        // Adding money to the user
        BigDecimal addedBalance = user.orElse(new User()).getBalance().subtract(bigDecimal); // orElse is a non-exception get() method

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
}
