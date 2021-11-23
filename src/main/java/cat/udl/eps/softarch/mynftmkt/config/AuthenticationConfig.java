package cat.udl.eps.softarch.mynftmkt.config;

import cat.udl.eps.softarch.mynftmkt.domain.*;
import cat.udl.eps.softarch.mynftmkt.repository.AdminRepository;
import cat.udl.eps.softarch.mynftmkt.repository.SaleRepository;
import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

import java.time.ZonedDateTime;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

  @Value("${default-password}")
  String defaultPassword;

  final BasicUserDetailsService basicUserDetailsService;
  final UserRepository userRepository;
  final AdminRepository adminRepository;
  final SaleRepository saleRepository;

  public AuthenticationConfig(BasicUserDetailsService basicUserDetailsService, UserRepository userRepository,
                              AdminRepository adminRepository, SaleRepository saleRepository) {
    this.basicUserDetailsService = basicUserDetailsService;
    this.userRepository = userRepository;
    this.adminRepository = adminRepository;
    this.saleRepository = saleRepository;
  }

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(basicUserDetailsService)
            .passwordEncoder(User.passwordEncoder);

    // Sample Admin
    if (!adminRepository.existsById("admin")) {
      Admin admin = new Admin();
      admin.setEmail("admin@sample.app");
      admin.setUsername("admin");
      admin.setPassword(defaultPassword);
      admin.encodePassword();
      userRepository.save(admin);
    }

    // Sample User
    if (!userRepository.existsById("user")) {
      User user = new User();
      user.setEmail("user@sample.app");
      user.setUsername("user");
      user.setPassword(defaultPassword);
      user.encodePassword();
      userRepository.save(user);
    }

    Sale sale1 = new Sale();
    ZonedDateTime now = ZonedDateTime.now();
    sale1.setDateTime(now);
    saleRepository.save(sale1);

    Sale sale2 = new Sale();
    sale2.setDateTime(now);
    saleRepository.save(sale2);


  }
}
