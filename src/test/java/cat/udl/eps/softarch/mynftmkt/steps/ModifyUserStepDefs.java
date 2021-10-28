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
            user.setCurrency("euro");
            user.encodePassword();
            userRepository.save(user);
        }
    }

    @When("I modify the password of the user {string} with {string}")
    public void iModifyThePasswordOfTheUserWith(String username, String password) throws Exception {
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

    @When("I modify the name of the user {string} with {string}")
    public void iModifyTheNameOfTheUserWith(String username, String name) throws Exception {
        JSONObject newName = new JSONObject();
        newName.put("name", name);
        stepDefs.result = stepDefs.mockMvc.perform(
                // patch better than put to update only one field
                patch("/users/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newName.toString())
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
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

    @When("I modify the lastname of the user {string} with {string}")
    public void iModifyTheLastnameOfTheUserWith(String username, String lastname) throws Exception {
        JSONObject newLastName = new JSONObject();
        newLastName.put("lastname", lastname);
        stepDefs.result = stepDefs.mockMvc.perform(
                        // patch better than put to update only one field
                        patch("/users/{username}", username)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(newLastName.toString())
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("The lastname of the user {string} has been modified to {string}")
    public void theLastnameOfTheUserHasBeenModifiedTo(String username, String lastname) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/users/{username}", username)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.lastname", is(lastname)));
    }

    @When("I modify the email of the user {string} with {string}")
    public void iModifyTheEmailOfTheUserWith(String username, String email) throws Exception {
        JSONObject newEmail = new JSONObject();
        newEmail.put("email", email);
        stepDefs.result = stepDefs.mockMvc.perform(
                        // patch better than put to update only one field
                        patch("/users/{username}", username)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(newEmail.toString())
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }

    @And("The email of the user {string} has been modified to {string}")
    public void theEmailOfTheUserHasBeenModifiedTo(String username, String email) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/users/{username}", username)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.email", is(email)));
    }
}


