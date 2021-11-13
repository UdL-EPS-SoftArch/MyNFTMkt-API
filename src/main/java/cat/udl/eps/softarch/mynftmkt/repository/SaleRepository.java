package cat.udl.eps.softarch.mynftmkt.repository;

import cat.udl.eps.softarch.mynftmkt.domain.Bid;
import cat.udl.eps.softarch.mynftmkt.domain.Sale;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface SaleRepository extends PagingAndSortingRepository<Sale, Long> {
    List<Sale> findByBidSale(Bid bid);
}
