package cat.udl.eps.softarch.mynftmkt.handler;

import cat.udl.eps.softarch.mynftmkt.domain.*;


import cat.udl.eps.softarch.mynftmkt.exception.LowerDataException;
import cat.udl.eps.softarch.mynftmkt.exception.ReserverPriceException;
import cat.udl.eps.softarch.mynftmkt.repository.FixedPriceOfferRepository;

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
public class FixedPriceOfferEventHandler {

    final Logger logger = LoggerFactory.getLogger(Bid.class);


    final FixedPriceOfferRepository FixedPriceOfferRepository;

    public FixedPriceOfferEventHandler(FixedPriceOfferRepository FixedPriceOfferRepository) {
        this.FixedPriceOfferRepository = FixedPriceOfferRepository;

    }

    @HandleBeforeCreate
    public void handleFixedPriceOfferPreCreate(FixedPriceOffer FixedPriceOffer) {

        ZonedDateTime date = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        FixedPriceOffer.setDateTime(date);
    }

    @HandleBeforeSave
    public void handleFixedPriceOfferPreSave(FixedPriceOffer FixedPriceOffer) {
    }

    @HandleBeforeDelete
    public void handleFixedPriceOfferPreDelete(FixedPriceOffer FixedPriceOffer) {
        logger.info("Before deleting: {}", FixedPriceOffer.toString());
    }

    @HandleBeforeLinkSave
    public void handleFixedPriceOfferPreLinkSave(FixedPriceOffer FixedPriceOffer, Object o) {
        logger.info("Before linking: {} to {}", FixedPriceOffer.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleFixedPriceOfferPostCreate(FixedPriceOffer FixedPriceOffer) {
        logger.info("After creating: {}", FixedPriceOffer.toString());
    }

    @HandleAfterSave
    public void handleFixedPriceOfferPostSave(FixedPriceOffer FixedPriceOffer) {
        logger.info("After updating: {}", FixedPriceOffer.toString());
    }

    @HandleAfterDelete
    public void handleFixedPriceOfferPostDelete(FixedPriceOffer FixedPriceOffer) {
        logger.info("After deleting: {}", FixedPriceOffer.toString());
    }

    @HandleAfterLinkSave
    public void handleFixedPriceOfferPostLinkSave(FixedPriceOffer FixedPriceOffer, Object o) {
        logger.info("After linking: {} to {}", FixedPriceOffer.toString(), o.toString());
    }


}
