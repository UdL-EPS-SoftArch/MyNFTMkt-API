package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.User;
import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DeleteUserStepDefs {
    final StepDefs stepDefs;
    final UserRepository userRepository;

    DeleteUserStepDefs(StepDefs stepDefs, UserRepository userRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
    }

   /* @Given("There is a registered user with username {string} and password {string} and email {string} and name {string} and lastname {string}")
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

    @When("I delete the user {string} with password {string}")
    public void iDeleteTheUserWith(String username, String password1) {
        if (userRepository.existsById(username)) {
            userRepository.deleteById(username);
        }
    }


*/
}
