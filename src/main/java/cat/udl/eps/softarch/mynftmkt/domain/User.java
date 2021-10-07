package cat.udl.eps.softarch.mynftmkt.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "AcademicRecruitUser") //Avoid collision with system table User in Postgres
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends UriEntity<String> implements UserDetails {

    public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    private String username;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    private String name;

    private String lastname;

    private BigDecimal balance;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Length(min = 8, max = 256)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean passwordReset;

    public void encodePassword() {
        this.password = passwordEncoder.encode(this.password);
    }

    @OneToMany(mappedBy = "user")
    private List<Bid> bids;

    // Will be uncommented when NFT and Settings class will exist

    /*@OneToOne(mappedBy = "user")
    private Settings settings;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Favorites",
            joinColumns = @JoinColumn(name = "nft_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "username"))
    private List<NFT> favoriteNFTs;

    @OneToMany(mappedBy = "user")
    private List<NFT> ownedNFTs;
    */

    @Override
    public String getId() {
        return this.username;
    }

    public void setId(String id) { this.username = id; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    @JsonValue(value = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
    }

}
