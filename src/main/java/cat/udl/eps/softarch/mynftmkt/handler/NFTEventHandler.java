package cat.udl.eps.softarch.mynftmkt.handler;

import cat.udl.eps.softarch.mynftmkt.domain.NFT;
import cat.udl.eps.softarch.mynftmkt.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class NFTEventHandler {
    final Logger logger = LoggerFactory.getLogger(NFT.class);


    @HandleBeforeCreate
    public void handleNFTBeforeCreate(NFT nft){
        logger.info("Before creating NFT{}", nft.toString());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        nft.setAuthor(user);
        nft.setOwner(user);
    }
}
