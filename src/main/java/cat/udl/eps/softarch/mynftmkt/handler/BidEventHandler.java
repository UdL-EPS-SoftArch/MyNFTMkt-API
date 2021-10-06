package cat.udl.eps.softarch.mynftmkt.handler;

import cat.udl.eps.softarch.mynftmkt.domain.Bid;
import cat.udl.eps.softarch.mynftmkt.repository.BidRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;


@Component
@RepositoryEventHandler
public class BidEventHandler {
    final Logger logger = LoggerFactory.getLogger(Bid.class);


    final BidRepository bidRepository;
    
    public BidEventHandler(BidRepository bidRepository) {this.bidRepository = bidRepository;}

    @HandleBeforeCreate
    public void handleBidPreCreate(Bid bid) { logger.info("Before creating: {}", bid.toString()); }

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
        logger.info("After creating: {}", bid.toString());
        ZonedDateTime date = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        bid.setDateTime(date);
        bid.setStatus(Bid.StatusTypes.ACTIVE);
        bidRepository.save(bid);
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
