package cat.udl.eps.softarch.mynftmkt.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Bid extends UriEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private ZonedDateTime dataTime;
    private BigDecimal price;
    //TODO NFT which is related to this bid
}
