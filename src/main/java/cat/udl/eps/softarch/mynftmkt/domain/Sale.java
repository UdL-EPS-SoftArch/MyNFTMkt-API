package cat.udl.eps.softarch.mynftmkt.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.ZonedDateTime;
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Sale extends UriEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ZonedDateTime dateTime;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private Bid BidSale;
}
