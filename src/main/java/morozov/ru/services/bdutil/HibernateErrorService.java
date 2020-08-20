package morozov.ru.services.bdutil;

import morozov.ru.models.TestingServiceError;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class HibernateErrorService implements ErrorServices {

    @PersistenceContext
    private EntityManager entityManager;

    public HibernateErrorService() {
    }

    @Override
    @Transactional
    public TestingServiceError saveError(TestingServiceError error) {
        this.entityManager.persist(error);
        return error;
    }

    @Override
    @Transactional(readOnly = true)
    public TestingServiceError getLastError() {
        Query query = entityManager
                .createQuery("select t from TestingServiceError t order by t.created desc");
        return (TestingServiceError) query
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
