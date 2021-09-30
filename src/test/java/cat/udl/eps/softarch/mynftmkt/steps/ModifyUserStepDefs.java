package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.User;
import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ModifyUserStepDefs {
    final StepDefs stepDefs;
    final UserRepository userRepository;
    public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    ModifyUserStepDefs(StepDefs stepDefs, UserRepository userRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
    }

    @Given("There is a registered user with username {string} and password {string} and email {string} and name {string} and lastname {string}")
    public void thereIsARegisteredUserWithUsernameAndPasswordAndEmailAndNameAndLastname(String username, String password, String email, String name, String lastname) {
        if (!userRepository.existsById(username)) {
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setLastname(lastname);
            user.encodePassword();
            userRepository.save(user);
        }
    }

    @When("I modify the password of the user {string} with {string}")
    public void iModifyThePasswordOfTheUserWith(String username, String password) throws Exception {
        if(userRepository.existsById(username)){
            String passwordEncoded = passwordEncoder.encode(password);
            JSONObject newPassword = new JSONObject();
            newPassword.put("password", passwordEncoded);
            stepDefs.result = stepDefs.mockMvc.perform(
                    // patch better than put to update only one field
                    patch("/users/{username}", username)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newPassword.toString())
                            .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print());
        }
    }

    @When("I modify the name of the user {string} with {string}")
    public void iModifyTheNameOfTheUserWith(String username, String name) throws Exception {
        if (userRepository.existsById(username)) {
            JSONObject newname = new JSONObject();
            newname.put("name", name);
            stepDefs.result = stepDefs.mockMvc.perform(
                    // patch better than put to update only one field
                    patch("/users/{username}", username)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newname.toString())
                            .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print());
        }
    }

    @And("The name of the user {string} has been modified to {string}")
    public void theNameOfTheUserHasBeenModifiedTo(String username, String name) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/users/{username}", username)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)));
    }
}


