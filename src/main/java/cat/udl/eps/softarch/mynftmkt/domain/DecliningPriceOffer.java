package cat.udl.eps.softarch.mynftmkt.domain;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
public class DecliningPriceOffer extends Offer{

    @Min(value=0)
    private BigDecimal startingPrice;
    public BigDecimal getStartingPrice() {
        return startingPrice;
    }
    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    @Min(value=0)
    private BigDecimal endingPrice;
    public BigDecimal getEndingPrice() {
        return endingPrice;
    }
    public void setEndingPrice(BigDecimal endingPrice) {
        this.endingPrice = endingPrice;
    }

    private ZonedDateTime expiration;
    public ZonedDateTime getExpiration() {
        return expiration;
    }
    public void setExpiration(ZonedDateTime expiration) {
        this.expiration = expiration;
    }
}
