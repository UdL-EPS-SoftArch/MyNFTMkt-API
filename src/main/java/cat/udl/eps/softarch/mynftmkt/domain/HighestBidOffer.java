package cat.udl.eps.softarch.mynftmkt.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class HighestBidOffer extends Offer {

    @DecimalMin("0.01")
    private BigDecimal minimumBid;
    @DecimalMin("0.01")
    private BigDecimal reservePrice;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate expiration;


}
