package dsw.finApp.exception.errorType;

import dsw.finApp.exception.response.ErrorInfo;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericException extends RuntimeException {

    private ErrorInfo errorInfo;

    private HttpStatus status;
}
