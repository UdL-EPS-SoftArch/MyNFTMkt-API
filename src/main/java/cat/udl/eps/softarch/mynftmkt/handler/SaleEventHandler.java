package cat.udl.eps.softarch.mynftmkt.handler;

import cat.udl.eps.softarch.mynftmkt.domain.Sale;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;

import java.time.ZonedDateTime;

public class SaleEventHandler {

    @HandleBeforeCreate
    public void handleSalePreCreate(Sale sale){
        ZonedDateTime now = ZonedDateTime.now();
        sale.setDateTime(now);
    }
}
