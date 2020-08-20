package morozov.ru.services.bdutil;

import morozov.ru.models.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class HibernateProfileService implements ProfileService {

    @PersistenceContext
    private EntityManager entityManager;

    public HibernateProfileService() {
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Profile saveProfile(Profile profile) {
        if (this.getByEmail(profile.getEmail()) == null) {
            this.entityManager.persist(profile);
            return profile;
        }
        return null;
    }

    @Override
    public List<Profile> getAll() {
        return this.entityManager
                .createQuery("select p from Profile p", Profile.class).getResultList();
    }

    @Override
    public Profile getByEmail(String email) {
        TypedQuery<Profile> query = this.entityManager
                .createQuery("select p from Profile p where upper(p.email) = upper(:param)", Profile.class);
        query.setParameter("param", email);
        return query
                .getResultList()
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public Profile getById(int id) {
        return this.entityManager
                .find(Profile.class, id);
    }

    @Override
    public Profile getLast() {
        Query query = entityManager
                .createQuery("select p from Profile p order by p.created desc");
        return (Profile) query
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
