package cat.udl.eps.softarch.mynftmkt.handler;

import cat.udl.eps.softarch.mynftmkt.domain.Sale;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RepositoryEventHandler
public class SaleEventHandler {

    @HandleBeforeCreate
    public void handleSalePreCreate(Sale sale){
        ZonedDateTime now = ZonedDateTime.now();
        sale.setDateTime(now);
    }
}
