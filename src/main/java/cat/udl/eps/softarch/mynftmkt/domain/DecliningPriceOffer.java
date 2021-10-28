package cat.udl.eps.softarch.mynftmkt.domain;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
public class DecliningPriceOffer extends Offer{

    private BigDecimal startingPrice;
    private BigDecimal endingPrice;
    private ZonedDateTime expiration;


}
