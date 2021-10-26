package cat.udl.eps.softarch.mynftmkt.steps;
import cat.udl.eps.softarch.mynftmkt.domain.FixedPriceOffer;

import cat.udl.eps.softarch.mynftmkt.domain.User;
import cat.udl.eps.softarch.mynftmkt.repository.AdminRepository;
import cat.udl.eps.softarch.mynftmkt.repository.FixedPriceOfferRepository;
import cat.udl.eps.softarch.mynftmkt.repository.NFTRepository;
import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class FindByPriceSteps {
    String newResourcesUri;
    List<FixedPriceOffer> fixedPriceOffersList;
    final StepDefs stepDefs;
    final UserRepository userRepository;
    final AdminRepository adminRepository;
    final FixedPriceOfferRepository fixedPriceOfferRepository;
    FixedPriceOffer offer = new FixedPriceOffer();

    FindByPriceSteps(StepDefs stepDefs, UserRepository userRepository, AdminRepository adminRepository, FixedPriceOfferRepository fixedPriceOfferRepository, NFTRepository nftRepository) {
        this.stepDefs = stepDefs;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.fixedPriceOfferRepository = fixedPriceOfferRepository;
    }
    @When("^I search by price smaller or equal than ([\\d-.]+)$")
    public void searchByPrice(BigDecimal priceToSearch){
        fixedPriceOffersList=fixedPriceOfferRepository.findAllByPriceIsLessThanEqual(priceToSearch);
    }
    @Then("^I receive a list with (\\d+) items$")
    public void checkListOfPriceSize (int size) throws Throwable{
        System.out.print(fixedPriceOffersList.get(0).getPrice());
        assertEquals(size,fixedPriceOffersList.size());
    }

}
