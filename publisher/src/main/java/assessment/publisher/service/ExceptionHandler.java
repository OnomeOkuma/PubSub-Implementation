package assessment.publisher.service;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public ResponseEntity<?> catchException(Exception exception) {
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body("Failed");
    }

}
