package cat.udl.eps.softarch.mynftmkt.repository;

import cat.udl.eps.softarch.mynftmkt.domain.Bid;
import cat.udl.eps.softarch.mynftmkt.domain.Offer;
import cat.udl.eps.softarch.mynftmkt.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BidRepository extends PagingAndSortingRepository<Bid, Long> {
    List<Bid> findByBidder(User user);
    List<Bid> findByNFTOffer(Offer offer);
}
