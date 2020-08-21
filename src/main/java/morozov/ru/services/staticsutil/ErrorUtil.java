package morozov.ru.services.staticsutil;

import morozov.ru.models.TestingServiceError;
import morozov.ru.services.bdutil.ErrorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Класс с единственным методом, сохраняющим текст нужной нам ситуации(ошибки) в БД.
 */
@Component
public class ErrorUtil {

    @Autowired
    private ErrorServices errorServices;

    public void saveErrorMsg(String errorMsg) {
        TestingServiceError error = new TestingServiceError();
        error.setMsg(errorMsg);
        error.setCreated(new Timestamp(System.currentTimeMillis()));
        this.errorServices.saveError(error);
    }

}