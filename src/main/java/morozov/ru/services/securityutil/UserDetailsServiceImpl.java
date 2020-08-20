package morozov.ru.services.securityutil;

import morozov.ru.models.Profile;
import morozov.ru.services.bdutil.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.HashSet;

public class UserDetailsServiceImpl  {

//    @Autowired
//    private ProfileService profileService;
//
//    public UserDetailsServiceImpl() {
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        Profile profile = this.profileService.getByEmail(s);
//        if (profile == null) {
//            throw new UsernameNotFoundException("Profile not found");
//        }
//        return new User(
//                profile.getEmail(),
//                null,
//                new HashSet<>(Arrays.asList(Roles.ROLE_USER))
//        );
//    }

}