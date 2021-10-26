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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.math.BigDecimal;

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
        if (bid.getOffer() instanceof FixedPriceOffer ){
            FixedPriceOffer offer = (FixedPriceOffer) bid.getOffer();
            if(bid.getPrice().compareTo(offer.getPrice()) != 0 && bid.getPrice().compareTo(new BigDecimal("0.01")) >= 0 ) {
                throw new UnmatchingPricesException();
            }
        } /*else if (bid.getOffer() instanceof DecliningPriceOffer){
            FixedPriceOffer offer = (FixedPriceOffer) bid.getOffer();
            if (bid.getPrice().compareTo(offer.getPrice()) == -1 && bid.getPrice().compareTo(new BigDecimal("0.01")) >= 0 ){
                throw new UnmatchingPricesException();
            }
        }*/
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
        logger.info("After creating: {}", bid.toString());
        ZonedDateTime date = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        bid.setDateTime(date);
        if (bid.getOffer() instanceof FixedPriceOffer){
            bid.setStatus(Bid.StatusTypes.PURCHASED);
        }
        /*else if (bid.getOffer() instanceof DecliningPriceOffer){
            if ()
        }*/
        else{
            bid.setStatus(Bid.StatusTypes.ACTIVE);
        }

        if (bid.getStatus().equals(Bid.StatusTypes.PURCHASED)){
            Sale sale = new Sale();
            sale.setDateTime(date);
            sale.setBidSale(bid);
            saleRepository.save(sale);
        }
        bidRepository.save(bid);
        //TODO check all the bids, search for the ACTIVE and change the previous one to SURPASSED
        //TODO create sale after changing status to purchased
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
