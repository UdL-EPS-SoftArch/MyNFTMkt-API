package cat.udl.eps.softarch.mynftmkt.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class NFT extends UriEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    @ElementCollection
    private List<String> keywords;

    private String category;

    private String mediaType;

    private String content;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private User author;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private User owner;


}
