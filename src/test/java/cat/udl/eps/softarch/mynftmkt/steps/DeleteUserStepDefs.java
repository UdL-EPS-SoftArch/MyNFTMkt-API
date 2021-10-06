package cat.udl.eps.softarch.mynftmkt.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import io.cucumber.java.en.When;

public class DeleteUserStepDefs {
    final StepDefs stepDefs;
    final UserRepository userRepository;
    DeleteUserStepDefs(StepDefs stepDefs, UserRepository userRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
    }
    @When("I delete a user with username {string}")
    public void iDeleteAUserWithUsernameEmailAndPassword(String username) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/users/{username}",username)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}