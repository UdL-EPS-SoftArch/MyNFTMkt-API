package cat.udl.eps.softarch.mynftmkt.handler;

import cat.udl.eps.softarch.mynftmkt.domain.NFT;
import cat.udl.eps.softarch.mynftmkt.domain.User;
import cat.udl.eps.softarch.mynftmkt.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

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

    @HandleBeforeDelete
    public void handleNFTTBeforeDelete(NFT nft){
        logger.info("Before deleting NFT{}", nft.toString());
        if (!userIsOwner(nft) & !userIsAdmin()){
            throw new ForbiddenException();
        }
    }

    @HandleBeforeSave
    public void handleNFTTBeforeSave(NFT nft){
        logger.info("Before editing NFT{}", nft.toString());
        if (!userIsOwner(nft) & !userIsAdmin()){
            throw new ForbiddenException();
        }
    }

    private boolean userIsAdmin(){
        Collection<? extends GrantedAuthority> userAuthority = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return userAuthority.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }

    private boolean userIsOwner(NFT nft){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId().equals(nft.getOwner());
    }
}
