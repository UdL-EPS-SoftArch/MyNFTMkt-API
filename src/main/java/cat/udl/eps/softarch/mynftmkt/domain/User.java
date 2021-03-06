package cat.udl.eps.softarch.mynftmkt.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "AcademicRecruitUser") //Avoid collision with system table User in Postgres
@Data
@EqualsAndHashCode(callSuper = true, exclude = "favoriteNFTs")
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
    @Min(0)
    private BigDecimal balance = BigDecimal.ZERO;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Length(min = 8, max = 256)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean passwordReset;

    public void encodePassword() {
        this.password = passwordEncoder.encode(this.password);
    }

    // Settings entity
    @NotBlank
    private String currency = "euro";

    private Boolean darkMode;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<NFT> favoriteNFTs = new HashSet<>();

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
