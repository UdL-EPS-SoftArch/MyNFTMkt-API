package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.domain.Sale;
import cat.udl.eps.softarch.mynftmkt.repository.BidRepository;
import cat.udl.eps.softarch.mynftmkt.repository.SaleRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CreateSaleStepDefs {

    final  StepDefs stepDefs;
    final  SaleRepository saleRepository;
    final  BidRepository bidRepository;

    public static String id;

    public CreateSaleStepDefs(StepDefs stepDefs, SaleRepository saleRepository, BidRepository bidRepository) {

        this.stepDefs = stepDefs;
        this.saleRepository = saleRepository;
        this.bidRepository = bidRepository;
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

    @And("It has been created a new sale")
    public void itHasBeenCreatedANewSale() throws Exception {
        id = stepDefs.result.andReturn().getResponse().getHeader("Location");
        stepDefs.result = stepDefs.mockMvc.perform(
                        get(id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @When("I create a new sale with the previous bid")
    public void iCreateANewSaleWithThePreviousBid() throws Exception {
        itHasBeenCreatedANewSale();
        Sale sale = new Sale();
        sale.setBidSale(bidRepository.findById(Long.parseLong(id.substring(id.lastIndexOf("/")+1))).get());
        stepDefs.result = stepDefs.mockMvc.perform(
                post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(sale))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}
