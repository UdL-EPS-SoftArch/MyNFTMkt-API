package cat.udl.eps.softarch.mynftmkt.repository;

import cat.udl.eps.softarch.mynftmkt.domain.NFT;
import cat.udl.eps.softarch.mynftmkt.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface NFTRepository extends PagingAndSortingRepository<NFT, Long> {
    public NFT findByTitle(@Param("title") String title);
}
