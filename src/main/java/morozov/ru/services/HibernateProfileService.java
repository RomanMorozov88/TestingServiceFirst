package morozov.ru.services;

import morozov.ru.models.Profile;
import morozov.ru.services.sessionutil.SessionUtil;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HibernateProfileService implements ProfileService {

    private SessionUtil sessionUtil;

    @Autowired
    public HibernateProfileService(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Override
    public Profile saveProfile(Profile profile) {
        return this.sessionUtil.sessionFunc(
                session -> {
                    Profile result = (Profile) session.save(profile);
                    return result;
                }
        );
    }

    @Override
    public List<Profile> getAll() {
        return this.sessionUtil.sessionFunc(
                session -> {
                    return session.createQuery("from Profile ").list();
                }
        );
    }

    @Override
    public Profile getByEmail(String email) {
        return this.sessionUtil.sessionFunc(
                session -> {
                    Query query = session.createQuery("from Owner where email =:param ");
                    query.setParameter("param", email);
                    Profile result = (Profile) query.uniqueResult();
                    return result;
                }
        );
    }

    @Override
    public Profile getById(int id) {
        return this.sessionUtil.sessionFunc(
                session -> {
                    Query query = session.createQuery("from Owner where id =:param ");
                    query.setParameter("param", id);
                    Profile result = (Profile) query.uniqueResult();
                    return result;
                }
        );
    }

    @Override
    public Profile getLast() {
        return this.sessionUtil.sessionFunc(
                session -> {
                    Query query = session.createSQLQuery("select * from profiles order by created desc limit 1;");
                    Profile result = (Profile) query.uniqueResult();
                    return result;
                }
        );
    }
}
