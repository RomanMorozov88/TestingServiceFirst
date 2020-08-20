package morozov.ru.services;

import morozov.ru.models.TestingServiceError;
import morozov.ru.services.sessionutil.SessionUtil;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateErrorService implements ErrorServices {

    private SessionUtil sessionUtil;

    @Autowired
    public HibernateErrorService(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Override
    public TestingServiceError saveError(TestingServiceError error) {
        return this.sessionUtil.sessionFunc(
                session -> {
                    TestingServiceError result = (TestingServiceError) session.save(error);
                    return result;
                }
        );
    }

    @Override
    public TestingServiceError getLastError() {
        return this.sessionUtil.sessionFunc(
                session -> {
                    Query query = session.createSQLQuery("select * from errors order by created desc limit 1;");
                    TestingServiceError result = (TestingServiceError) query.uniqueResult();
                    return result;
                }
        );
    }
}
