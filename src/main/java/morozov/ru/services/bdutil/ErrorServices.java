package morozov.ru.services.bdutil;

import morozov.ru.models.TestingServiceError;

public interface ErrorServices {

    TestingServiceError saveError(TestingServiceError error);
    TestingServiceError getLastError();
}