package cat.udl.eps.softarch.mynftmkt.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import cat.udl.eps.softarch.mynftmkt.domain.DecliningPriceOffer;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DecliningPriceOfferRepository extends PagingAndSortingRepository<DecliningPriceOffer, Long> {
}
