package cat.udl.eps.softarch.mynftmkt.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Sale extends UriEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ZonedDateTime dateTime;
}
