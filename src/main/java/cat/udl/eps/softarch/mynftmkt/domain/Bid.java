package cat.udl.eps.softarch.mynftmkt.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;


import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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

    @DecimalMin("0.01")
    private BigDecimal price;

    public enum StatusTypes {ACTIVE, PURCHASED, CANCELLED}

    private StatusTypes status;

    @ManyToOne
    private Offer offer;

    @ManyToOne
    private User createdBy;
}
