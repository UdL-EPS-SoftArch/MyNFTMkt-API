package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.Sale;
import cat.udl.eps.softarch.mynftmkt.repository.SaleRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.http.MediaType;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class CreateSaleStepDefs {

    final  StepDefs stepDefs;
    final  SaleRepository saleRepository;

    public CreateSaleStepDefs(StepDefs stepDefs, SaleRepository saleRepository) {

        this.stepDefs = stepDefs;
        this.saleRepository = saleRepository;

    }

    @When("^I create a new sale$")
    public void CreateSale() throws Throwable {

        Sale sale = new Sale();
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(sale))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("It has not been created any sale")
    public void itHasNotBeenCreateAnySale() {
        Assert.assertTrue(saleRepository.count()==0);
    }
}
