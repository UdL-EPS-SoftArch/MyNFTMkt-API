package cat.udl.eps.softarch.mynftmkt.handler;



import cat.udl.eps.softarch.mynftmkt.domain.*;


import cat.udl.eps.softarch.mynftmkt.exception.LowerDataException;
import cat.udl.eps.softarch.mynftmkt.exception.ReserverPriceException;
import cat.udl.eps.softarch.mynftmkt.repository.DecliningPriceOfferRepository;

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
public class DecliningPriceOfferEventHandler {

    final Logger logger = LoggerFactory.getLogger(Bid.class);


    final DecliningPriceOfferRepository DecliningPriceOfferRepository;

    public DecliningPriceOfferEventHandler(DecliningPriceOfferRepository DecliningPriceOfferRepository) {
        this.DecliningPriceOfferRepository = DecliningPriceOfferRepository;

    }

    @HandleBeforeCreate
    public void handleDecliningPriceOfferPreCreate(DecliningPriceOffer DecliningPriceOffer) {

        ZonedDateTime date = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        DecliningPriceOffer.setDateTime(date);
    }

    @HandleBeforeSave
    public void handleDecliningPriceOfferPreSave(DecliningPriceOffer DecliningPriceOffer) {
    }

    @HandleBeforeDelete
    public void handleDecliningPriceOfferPreDelete(DecliningPriceOffer DecliningPriceOffer) {
        logger.info("Before deleting: {}", DecliningPriceOffer.toString());
    }

    @HandleBeforeLinkSave
    public void handleDecliningPriceOfferPreLinkSave(DecliningPriceOffer DecliningPriceOffer, Object o) {
        logger.info("Before linking: {} to {}", DecliningPriceOffer.toString(), o.toString());
    }

    @HandleAfterCreate
    public void handleDecliningPriceOfferPostCreate(DecliningPriceOffer DecliningPriceOffer) {
        logger.info("After creating: {}", DecliningPriceOffer.toString());
    }

    @HandleAfterSave
    public void handleDecliningPriceOfferPostSave(DecliningPriceOffer DecliningPriceOffer) {
        logger.info("After updating: {}", DecliningPriceOffer.toString());
    }

    @HandleAfterDelete
    public void handleDecliningPriceOfferPostDelete(DecliningPriceOffer DecliningPriceOffer) {
        logger.info("After deleting: {}", DecliningPriceOffer.toString());
    }

    @HandleAfterLinkSave
    public void handleDecliningPriceOfferPostLinkSave(DecliningPriceOffer DecliningPriceOffer, Object o) {
        logger.info("After linking: {} to {}", DecliningPriceOffer.toString(), o.toString());
    }


}

