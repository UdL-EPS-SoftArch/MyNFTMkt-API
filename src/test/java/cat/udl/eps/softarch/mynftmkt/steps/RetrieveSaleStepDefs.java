package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.Sale;
import cat.udl.eps.softarch.mynftmkt.repository.SaleRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RetrieveSaleStepDefs {

    final StepDefs stepDefs;
    final SaleRepository saleRepository;
    public static String id;

    public RetrieveSaleStepDefs(StepDefs stepDefs, SaleRepository saleRepository) {

        this.stepDefs = stepDefs;
        this.saleRepository = saleRepository;

    }

    @And("It has been created a new sales")
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

    @When("I list all my Sale in the app.")
    public void iListAllTheExistingSaleRequestsInTheApp() throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/sales")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("There has been retrieved {int} Sale")
    public void thereHasBeenRetrievedRequest(int numSaleRequest) throws Throwable {
        stepDefs.result.andExpect(jsonPath("$._embedded.sales", hasSize(numSaleRequest)));
    }

    @When("I list all my own Sale")
    public void iListSaleRequestsOfMyOwnSale() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/sales")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

}

