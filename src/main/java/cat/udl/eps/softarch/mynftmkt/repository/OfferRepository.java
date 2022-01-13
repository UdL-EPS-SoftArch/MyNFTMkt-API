package cat.udl.eps.softarch.mynftmkt.repository;

import cat.udl.eps.softarch.mynftmkt.domain.NFT;
import cat.udl.eps.softarch.mynftmkt.domain.Offer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OfferRepository extends PagingAndSortingRepository<Offer, Long> {
    public boolean existsByNft(@Param("nft") NFT nft);
    public List<Offer> findByNftOrderByDateTime(@Param("nft") NFT nft);
}
