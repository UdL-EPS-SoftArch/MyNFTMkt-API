package cat.udl.eps.softarch.mynftmkt.steps;
import cat.udl.eps.softarch.mynftmkt.repository.SaleRepository;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class DeleteSaleStepDefs {
    final  StepDefs stepDefs;
    final SaleRepository saleRepository;

    public static String id;

    public DeleteSaleStepDefs(StepDefs stepDefs, SaleRepository saleRepository) {
        this.stepDefs = stepDefs;
        this.saleRepository = saleRepository;
    }

    @When("I delete a sale with id {string}")
    public void iDeleteASaleWithId(String id) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/sales/"+ id)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}