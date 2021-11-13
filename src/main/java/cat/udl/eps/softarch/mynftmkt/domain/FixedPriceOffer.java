package cat.udl.eps.softarch.mynftmkt.domain;

//Ask if I should add @Data and @EqualsAndHashCode (callSuper = true),
//since I'm not sure after reading that in classes that extend
//some other who already have it can cause problems.


//import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
//@Data
//@EqualsAndHashCode(callSuper = true)
public class FixedPriceOffer extends Offer{
    
    @Min(value=0)
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}
