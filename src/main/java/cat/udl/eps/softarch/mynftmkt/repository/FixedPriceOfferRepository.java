package cat.udl.eps.softarch.mynftmkt.repository;

import cat.udl.eps.softarch.mynftmkt.domain.FixedPriceOffer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface FixedPriceOfferRepository extends PagingAndSortingRepository<FixedPriceOffer, Integer> {
}