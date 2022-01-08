package cat.udl.eps.softarch.mynftmkt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "The data has to be greater than the current data.")
public class LowerDataException  extends RuntimeException {
}
