package cat.udl.eps.softarch.mynftmkt.handler;

import cat.udl.eps.softarch.mynftmkt.domain.*;
import cat.udl.eps.softarch.mynftmkt.exception.ForbiddenException;
import cat.udl.eps.softarch.mynftmkt.exception.UnmatchingPricesException;
import cat.udl.eps.softarch.mynftmkt.repository.BidRepository;
import cat.udl.eps.softarch.mynftmkt.repository.SaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.math.BigDecimal;
import java.util.List;

@Component
@RepositoryEventHandler
public class BidEventHandler {
    final Logger logger = LoggerFactory.getLogger(Bid.class);


    final BidRepository bidRepository;
    final SaleRepository saleRepository;

    public BidEventHandler(BidRepository bidRepository, SaleRepository saleRepository) {
        this.bidRepository = bidRepository;
        this.saleRepository = saleRepository;
    }

    @HandleBeforeCreate
    public void handleBidPreCreate(Bid bid) {
        logger.info("Before creating: {}", bid.toString());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bid.setBidder(user);
        if (bid.getOffer() instanceof FixedPriceOffer) {
            FixedPriceOffer offer = (FixedPriceOffer) bid.getOffer();
            if (bid.getPrice().compareTo(offer.getPrice()) != 0 && bid.getPrice().compareTo(new BigDecimal("0.01")) >= 0) {
                throw new UnmatchingPricesException();
            }
        } else if (bid.getOffer() instanceof DecliningPriceOffer) {
            DecliningPriceOffer offer = (DecliningPriceOffer) bid.getOffer();
            if (bid.getPrice().compareTo(offer.getStartingPrice()) < 0 && bid.getPrice().compareTo(new BigDecimal("0.01")) >= 0) {
                throw new UnmatchingPricesException();
            }
        } else if (bid.getOffer() instanceof HighestBidOffer) {
            HighestBidOffer offer = (HighestBidOffer) bid.getOffer();
            if (bid.getPrice().compareTo(offer.getMinimumBid()) < 0 && bid.getPrice().compareTo(new BigDecimal("0.01")) >= 0) {
                throw new UnmatchingPricesException();
            }
        }
    }

    @HandleBeforeSave
    public void handleBidPreSave(Bid bid) {
        logger.info("Before updating: {}", bid.toString());
    }

    @HandleBeforeDelete
    public void handleBidPreDelete(Bid bid) {
        logger.info("Before deleting: {}", bid.toString());
    }

    @HandleBeforeLinkSave
    public void handleBidPreLinkSave(Bid bid, Object o) {
        logger.info("Before linking: {} to {}", bid.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleBidPostCreate(Bid bid) {
        //logger.info("After creating: {}", bid.toString());
        ZonedDateTime date = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        bid.setDateTime(date);
        if (bid.getOffer() instanceof FixedPriceOffer) {
            bid.setStatus(Bid.StatusTypes.PURCHASED);
        } else if (bid.getOffer() instanceof HighestBidOffer) {
            HighestBidOffer offer = (HighestBidOffer) bid.getOffer();
            List<Bid> repositoryBids = bidRepository.findByOffer(offer);
            // Check bid against other's in the repository
            checkRepository(repositoryBids, bid);
            // Change status a PURCHASE if expiration date is equal to today's date
            LocalDate localDate = date.toLocalDate();
            if (bid.getStatus() == Bid.StatusTypes.ACTIVE && localDate.compareTo(offer.getExpiration()) == 0) {
                bid.setStatus(Bid.StatusTypes.PURCHASED);
            }
        } else if (bid.getOffer() instanceof DecliningPriceOffer) {
            DecliningPriceOffer offer = (DecliningPriceOffer) bid.getOffer();
            List<Bid> repositoryBids = bidRepository.findByOffer(offer);
            // PURCHASE if it's equal to the offer's price
            if (bid.getPrice().compareTo(offer.getPrice()) == 0) {
                bid.setStatus(Bid.StatusTypes.PURCHASED);
            } else {
                // Check bid against other's in the repository
                checkRepository(repositoryBids, bid);
                // Change status a PURCHASE if expiration date is equal to today's date
                if (bid.getStatus() == Bid.StatusTypes.ACTIVE && date.compareTo(offer.getExpiration()) == 0) {
                    bid.setStatus(Bid.StatusTypes.PURCHASED);
                }
            }
        } else {
            bid.setStatus(Bid.StatusTypes.ACTIVE);
        }
        // Create sale if bid's status is equal to PURCHASED
        if (bid.getStatus().equals(Bid.StatusTypes.PURCHASED)) {
            Sale sale = new Sale();
            sale.setDateTime(date);
            sale.setBidSale(bid);
            saleRepository.save(sale);
        }
        bidRepository.save(bid);
    }

    public void checkRepository(List<Bid> repositoryBids, Bid bid) {
        Bid activeBid = null;
        for (Bid repositoryBid : repositoryBids) {
            // Look if bid's price is lower than any of the bid on the repository
            if (repositoryBid.getPrice().compareTo(bid.getPrice()) > 0) {
                bid.setStatus(Bid.StatusTypes.SURPASSED);
                break;
            }
            // Store active Bid, so it can be replaced if this bid's price is greater
            if (repositoryBid.getStatus() == Bid.StatusTypes.ACTIVE)
                activeBid = repositoryBid;
        }
        // If bid's value is the greatest from the repository, then set it to ACTIVE
        if (bid.getStatus() != Bid.StatusTypes.SURPASSED) {
            bid.setStatus(Bid.StatusTypes.ACTIVE);
            if (activeBid != null)
                activeBid.setStatus(Bid.StatusTypes.SURPASSED);
        }
    }

    @HandleAfterSave
    public void handleBidPostSave(Bid bid) {
        logger.info("After updating: {}", bid.toString());
    }

    @HandleAfterDelete
    public void handleBidPostDelete(Bid bid) {
        logger.info("After deleting: {}", bid.toString());
    }

    @HandleAfterLinkSave
    public void handleBidPostLinkSave(Bid bid, Object o) {
        logger.info("After linking: {} to {}", bid.toString(), o.toString());
    }
}
