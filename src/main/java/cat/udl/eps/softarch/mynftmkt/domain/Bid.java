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

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private ZonedDateTime dateTime;

    private BigDecimal price;

    public enum StatusTypes {ACTIVE, PURCHASED, CANCELLED}

    private StatusTypes status;

    /*@ManyToOne
    private Offer NFTOffer;

    @ManyToOne
    private User createdBy;*/
}
