package morozov.ru.services;

import morozov.ru.models.Profile;

import java.util.List;

public interface ProfileService {

    Profile saveProfile(Profile profile);
    List<Profile> getAll();
    Profile getByEmail(String email);
    Profile getById(int id);
    Profile getLast();
}