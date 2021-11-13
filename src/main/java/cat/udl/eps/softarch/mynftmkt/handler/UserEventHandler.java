package cat.udl.eps.softarch.mynftmkt.handler;

import cat.udl.eps.softarch.mynftmkt.domain.User;
import cat.udl.eps.softarch.mynftmkt.exception.ForbiddenException;
import cat.udl.eps.softarch.mynftmkt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RepositoryEventHandler
public class UserEventHandler {

    final Logger logger = LoggerFactory.getLogger(User.class);

    final UserRepository userRepository;

    public UserEventHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @HandleBeforeCreate
    public void handleUserPreCreate(User player) { logger.info("Before creating: {}", player.toString()); }

    /**
     * @param player
     * check that a user can only modify their own data, except if this user is an administrator
     */
    @HandleBeforeSave
    public void handleUserPreSave(User player) {
        logger.info("Before updating: {}", player.toString());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> userAuthority = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean sameId = user.getId().equals(player.getId());
        boolean rolePlayerIsAdmin = userAuthority.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (!sameId & !rolePlayerIsAdmin){
            throw new ForbiddenException();
        }
    }

    /**
     * @param player
     * check that a user can only delete their own account, except if this user is an administrator
     */
    @HandleBeforeDelete
    public void handleUserPreDelete(User player) {
        logger.info("Before deleting: {}", player.toString());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> userAuthority = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean sameId = user.getId().equals(player.getId());
        boolean rolePlayerIsAdmin = userAuthority.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (!sameId & !rolePlayerIsAdmin){
            throw new ForbiddenException();
        }
    }

    @HandleBeforeLinkSave
    public void handleUserPreLinkSave(User player, Object o) {
        logger.info("Before linking: {} to {}", player.toString(), o.toString());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> userAuthority = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean sameId = user.getId().equals(player.getId());
        boolean rolePlayerIsAdmin = userAuthority.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (!sameId & !rolePlayerIsAdmin){
            throw new ForbiddenException();
        }
    }

    @HandleBeforeLinkDelete
    public void handleUserPreLinkDelete(User player, Object o) {
        logger.info("Before linked deleting: {} to {}", player.toString(), o.toString());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> userAuthority = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean sameId = user.getId().equals(player.getId());
        boolean rolePlayerIsAdmin = userAuthority.stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        if (!sameId & !rolePlayerIsAdmin){
            throw new ForbiddenException();
        }
    }

    @HandleAfterCreate
    public void handleUserPostCreate(User player) {
        logger.info("After creating: {}", player.toString());
        player.encodePassword();
        userRepository.save(player);
    }

    @HandleAfterSave
    public void handleUserPostSave(User player) {
        logger.info("After updating: {}", player.toString());
        if (player.isPasswordReset()) {
            player.encodePassword();
            player.setPasswordReset(false);
        }
        userRepository.save(player);
    }

    @HandleAfterDelete
    public void handleUserPostDelete(User player) {
        logger.info("After deleting: {}", player.toString());
    }

    @HandleAfterLinkSave
    public void handleUserPostLinkSave(User player, Object o) {
        logger.info("After linking: {} to {}", player.toString(), o.toString());
    }
}
