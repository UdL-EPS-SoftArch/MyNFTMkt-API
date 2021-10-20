package cat.udl.eps.softarch.mynftmkt.repository;

import cat.udl.eps.softarch.mynftmkt.domain.FixedPriceOffer;
import cat.udl.eps.softarch.mynftmkt.domain.Offer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface OfferRepository extends PagingAndSortingRepository<Offer, Long> {
}