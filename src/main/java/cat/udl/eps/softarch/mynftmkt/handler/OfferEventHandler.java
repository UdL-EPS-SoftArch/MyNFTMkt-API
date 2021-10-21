package cat.udl.eps.softarch.mynftmkt.handler;

import cat.udl.eps.softarch.mynftmkt.domain.Offer;
import cat.udl.eps.softarch.mynftmkt.domain.User;
import cat.udl.eps.softarch.mynftmkt.exception.ForbiddenException;
import cat.udl.eps.softarch.mynftmkt.repository.OfferRepository;
import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;


@Component
@RepositoryEventHandler
public class OfferEventHandler {

    final Logger logger = LoggerFactory.getLogger(Offer.class);

    final OfferRepository offerRepository;

    public OfferEventHandler(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @HandleBeforeCreate
    public void handleUserPreCreate(Offer newOffer) {
        if(offerRepository.existsByNft(newOffer.getNft())){
            throw new ForbiddenException();
        }
    }



}
