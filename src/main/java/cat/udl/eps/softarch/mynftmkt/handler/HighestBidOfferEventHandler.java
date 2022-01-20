package cat.udl.eps.softarch.mynftmkt.handler;

import cat.udl.eps.softarch.mynftmkt.domain.*;


import cat.udl.eps.softarch.mynftmkt.exception.LowerDataException;
import cat.udl.eps.softarch.mynftmkt.exception.ReserverPriceException;
import cat.udl.eps.softarch.mynftmkt.repository.HighestBidOfferRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.DecimalMin;
import java.time.LocalDate;
import java.time.ZoneId;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.zip.DataFormatException;

@Component
@RepositoryEventHandler
public class HighestBidOfferEventHandler {

    final Logger logger = LoggerFactory.getLogger(Bid.class);


    final HighestBidOfferRepository highestBidOfferRepository;

    public HighestBidOfferEventHandler(HighestBidOfferRepository highestBidOfferRepository) {
        this.highestBidOfferRepository = highestBidOfferRepository;

    }

    @HandleBeforeCreate
    public void handleHighestBidOfferPreCreate(HighestBidOffer highestBidOffer) {


        if ((highestBidOffer.getReservePrice().compareTo(highestBidOffer.getMinimumBid()) <= 0) && (highestBidOffer.getReservePrice().compareTo(new BigDecimal("0.01")) >= 0 && highestBidOffer.getMinimumBid().compareTo(new BigDecimal("0.01")) >= 0)) {
            throw new ReserverPriceException();
        }

        LocalDate currentData = LocalDate.now();

        if (highestBidOffer.getExpiration().compareTo(currentData) < 0) {
            throw new LowerDataException();
        }

        ZonedDateTime date = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        highestBidOffer.setDateTime(date);


    }

    @HandleBeforeSave
    public void handleHighestBidOfferPreSave(HighestBidOffer highestBidOffer) {
        if ((highestBidOffer.getReservePrice().compareTo(highestBidOffer.getMinimumBid()) <= 0) && (highestBidOffer.getReservePrice().compareTo(new BigDecimal("0.01")) >= 0 && highestBidOffer.getMinimumBid().compareTo(new BigDecimal("0.01")) >= 0)) {
            throw new ReserverPriceException();
        }

        LocalDate currentData = LocalDate.now();

        if (highestBidOffer.getExpiration().compareTo(currentData) < 0) {
            throw new LowerDataException();
        }
    }

    @HandleBeforeDelete
    public void handleHighestBidOfferPreDelete(HighestBidOffer highestBidOffer) {
        logger.info("Before deleting: {}", highestBidOffer.toString());
    }

    @HandleBeforeLinkSave
    public void handleHighestBidOfferPreLinkSave(HighestBidOffer highestBidOffer, Object o) {
        logger.info("Before linking: {} to {}", highestBidOffer.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleHighestBidOfferPostCreate(HighestBidOffer highestBidOffer) {
        logger.info("After creating: {}", highestBidOffer.toString());
    }

    @HandleAfterSave
    public void handleHighestBidOfferPostSave(HighestBidOffer highestBidOffer) {
        logger.info("After updating: {}", highestBidOffer.toString());
    }

    @HandleAfterDelete
    public void handleHighestBidOfferPostDelete(HighestBidOffer highestBidOffer) {
        logger.info("After deleting: {}", highestBidOffer.toString());
    }

    @HandleAfterLinkSave
    public void handleHighestBidOfferPostLinkSave(HighestBidOffer highestBidOffer, Object o) {
        logger.info("After linking: {} to {}", highestBidOffer.toString(), o.toString());
    }


}
