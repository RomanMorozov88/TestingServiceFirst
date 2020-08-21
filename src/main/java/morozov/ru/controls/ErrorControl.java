package morozov.ru.controls;

import morozov.ru.models.TestingServiceError;
import morozov.ru.services.bdutil.ErrorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ErrorControl {

    @Autowired
    private ErrorServices errorServices;

    @GetMapping("/error/last")
    public TestingServiceError getLastError(HttpServletResponse response) {
        response.setStatus(200);
        TestingServiceError result = this.errorServices.getLast();
        return result;
    }

}