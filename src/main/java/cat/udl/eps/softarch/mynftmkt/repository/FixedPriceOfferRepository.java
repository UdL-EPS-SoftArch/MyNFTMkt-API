package cat.udl.eps.softarch.mynftmkt.repository;

import cat.udl.eps.softarch.mynftmkt.domain.FixedPriceOffer;
import cat.udl.eps.softarch.mynftmkt.domain.NFT;
import cat.udl.eps.softarch.mynftmkt.domain.Offer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigDecimal;
import java.util.List;


@RepositoryRestResource
public interface FixedPriceOfferRepository extends PagingAndSortingRepository<FixedPriceOffer, Long> {
    public List<FixedPriceOffer> findByPriceIsLessThanEqual(@Param("price") BigDecimal price);

}