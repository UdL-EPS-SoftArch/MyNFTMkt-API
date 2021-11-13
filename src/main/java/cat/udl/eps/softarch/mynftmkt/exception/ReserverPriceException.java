package cat.udl.eps.softarch.mynftmkt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "To create this offer, the reserve price has to be higher than the minimum price.")
public class ReserverPriceException extends RuntimeException {
}
