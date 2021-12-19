package cat.udl.eps.softarch.mynftmkt.config;
import cat.udl.eps.softarch.mynftmkt.domain.DecliningPriceOffer;
import cat.udl.eps.softarch.mynftmkt.domain.Sale;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class ExposeIdConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry regs) {
        config.exposeIdsFor(Sale.class);
        config.exposeIdsFor(DecliningPriceOffer.class);
    }
}
