package cat.udl.eps.softarch.mynftmkt.domain;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
public class DecliningPriceOffer extends Offer{

    @Min(value=0)
    private BigDecimal startingPrice;

    @Min(value=0)
    private BigDecimal endingPrice;
    private ZonedDateTime expiration;


}
