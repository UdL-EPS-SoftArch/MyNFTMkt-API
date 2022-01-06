package cat.udl.eps.softarch.mynftmkt.config;


import cat.udl.eps.softarch.mynftmkt.domain.*;
import cat.udl.eps.softarch.mynftmkt.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Set;

@Configuration
@Profile("test")
public class BBDDInitialization {

    @Value("${default-password}")
    String defaultPassword;

    final UserRepository userRepository;
    final AdminRepository adminRepository;
    final NFTRepository nftRepository;
    final OfferRepository offerRepository;
    final BidRepository bidRepository;
    final SaleRepository saleRepository;

    public BBDDInitialization(UserRepository userRepository,
                              AdminRepository adminRepository,
                              NFTRepository nftRepository,
                              OfferRepository offerRepository,
                              BidRepository bidRepository,
                              SaleRepository saleRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.nftRepository = nftRepository;
        this.offerRepository = offerRepository;
        this.bidRepository = bidRepository;
        this.saleRepository = saleRepository;
    }

    @PostConstruct
    public void initializeDatabase() {
        // Sample Admin

        Admin admin = this.adminRepository.findById("admin").orElse(new Admin());
        if (admin.getId() == null) {
            admin.setEmail("admin@sample.app");
            admin.setUsername("admin");
            admin.setPassword(defaultPassword);
            admin.encodePassword();
            admin = userRepository.save(admin);
        }

        // Sample User
        User user = this.userRepository.findById("user").orElse(new User());
        if (user.getId() == null) {
            user.setEmail("user@sample.app");
            user.setUsername("user");
            user.setPassword(defaultPassword);
            user.encodePassword();
            user = userRepository.save(user);
        }
        //If the database is empty
        if(nftRepository.count() == 0) {
            User user2 = new User();
            user2.setEmail("user2@sample.app");
            user2.setUsername("user2");
            user2.setPassword(defaultPassword);
            user2.encodePassword();
            user2 = userRepository.save(user2);

            NFT nft = new NFT();
            nft.setTitle("Test NFT");
            nft.setDescription("This is the NFT for testing purposes");
            nft.setContent("https://lh3.googleusercontent.com/ujepnqpnL0nDQIHsWxlCXzyw4pf01yjz1Jmb4kAQHumJAPrSEj0-e3ABMZlZ1HEpJoqwOcY_kgnuJGzfXbd2Tijri66GXUtfN2MXQA=w600");
            nft.setCategory("This is the category for the NFT");
            nft.setKeywords(Arrays.asList("Keyword1", "Keyword2"));
            nft.setOwner(user);
            nft = nftRepository.save(nft);

            FixedPriceOffer fixedPriceOffer = new FixedPriceOffer();
            fixedPriceOffer.setNft(nft);
            fixedPriceOffer.setPrice(BigDecimal.TEN);
            fixedPriceOffer = this.offerRepository.save(fixedPriceOffer);

            ZonedDateTime purchaseDate = ZonedDateTime.of(2021, 10, 8, 10, 53,
                    0, 0, ZoneId.systemDefault());
            Bid bid = new Bid();
            bid.setOffer(fixedPriceOffer);
            bid.setBidder(user2);
            bid.setPrice(BigDecimal.TEN);
            bid.setStatus(Bid.StatusTypes.PURCHASED);
            bid.setDateTime(purchaseDate);
            bid = this.bidRepository.save(bid);

            Sale sale = new Sale();
            sale.setBidSale(bid);
            sale.setDateTime(purchaseDate);
            sale = this.saleRepository.save(sale);

            Set<NFT> favoriteNFTs = user.getFavoriteNFTs();
            favoriteNFTs.add(nft);
            user.setFavoriteNFTs(favoriteNFTs);
            user = this.userRepository.save(user);
        }
    }
}
