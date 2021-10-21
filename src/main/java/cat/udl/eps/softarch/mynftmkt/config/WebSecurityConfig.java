package cat.udl.eps.softarch.mynftmkt.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Value("${allowed-origins}")
        String[] allowedOrigins;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/identity").authenticated()
                .antMatchers(HttpMethod.POST, "/users").anonymous()
                .antMatchers(HttpMethod.PATCH, "/users/*").authenticated()
                .antMatchers(HttpMethod.PUT, "/users/*").authenticated()
                .antMatchers(HttpMethod.POST, "/users/*").denyAll()
                //added to deny the modifications to  fixedPriceOffers
                .antMatchers(HttpMethod.PUT, "/fixedPriceOffers/*").denyAll()
                .antMatchers(HttpMethod.PATCH, "/fixedPriceOffers/*").denyAll()
                //Only admin and author can delete-cancel a fixed Price offer, but we don't know the author yet
                .antMatchers(HttpMethod.DELETE, "/fixedPriceOffers/*").denyAll()

                .antMatchers(HttpMethod.DELETE, "/bids/*").denyAll()
                .antMatchers(HttpMethod.POST, "/**/*").authenticated()
                .antMatchers(HttpMethod.PUT, "/**/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/**/*").authenticated()

                .antMatchers(HttpMethod.PATCH, "/sales/*").authenticated()
                .antMatchers(HttpMethod.GET, "/sales").authenticated()

                .anyRequest().permitAll()
                .and()
                .httpBasic().realmName("MyNFTMkt")
                .and()
                .cors()
                .and()
                .csrf().disable();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Arrays.asList(allowedOrigins));
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
            corsConfiguration.setAllowCredentials(true);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfiguration);
            return source;
        }

        @Bean
        public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
            return new SecurityEvaluationContextExtension();
        }
    }


