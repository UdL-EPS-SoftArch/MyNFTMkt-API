package cat.udl.eps.softarch.mynftmkt.domain;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    public void setExpiration(LocalDateTime expiration) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        ZoneId timeZone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = LocalDateTime.parse(df.format(expiration), df).atZone(timeZone);
        this.expiration = zonedDateTime;

    }
}
