package cat.udl.eps.softarch.mynftmkt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Bid's price should match the offer's price")
public class UnmatchingPricesException extends RuntimeException{
}
