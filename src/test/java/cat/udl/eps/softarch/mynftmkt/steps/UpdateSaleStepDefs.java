package cat.udl.eps.softarch.mynftmkt.steps;

import cat.udl.eps.softarch.mynftmkt.repository.SaleRepository;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class UpdateSaleStepDefs {
    final StepDefs stepDefs;
    final SaleRepository saleRepository;

    private String newUri;

    public UpdateSaleStepDefs(StepDefs stepDefs, SaleRepository saleRepository) {
        this.stepDefs = stepDefs;
        this.saleRepository = saleRepository;
    }

    @When("I change the date of the sale with id {string} to {string}")
    public void iChangeTheDateOfTheSaleWithIdTo(String id, String newDate) throws Throwable {
        ZonedDateTime newZonedDateTime = ZonedDateTime.parse(newDate);
        newUri = "/sales/"+ id;
        stepDefs.result = stepDefs.mockMvc.perform(
                patch(newUri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content((new JSONObject().put("dateTime", newZonedDateTime)).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }
}