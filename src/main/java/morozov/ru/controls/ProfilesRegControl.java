package morozov.ru.controls;

import morozov.ru.models.Profile;
import morozov.ru.models.controlsutil.forregistration.MessageUtil;
import morozov.ru.models.controlsutil.forregistration.MsgForRespRegUtil;
import morozov.ru.models.controlsutil.forregistration.ProfileForReqRegUtil;
import morozov.ru.models.controlsutil.forregistration.ProfileForRespRegUtil;
import morozov.ru.services.bdutil.ProfileService;
import morozov.ru.services.validateemailutil.ValidateEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

@RestController
public class ProfilesRegControl {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profiles/last")
    public Profile getLastProfile(HttpServletResponse response) {
        response.setStatus(200);
        Profile result = this.profileService.getLast();
        return result;
    }

    @GetMapping("/profiles/{ID}")
    public Profile getProfileById(@PathVariable int ID, HttpServletResponse response) {
        response.setStatus(200);
        Profile result = this.profileService.getById(ID);
        return result;
    }

    @GetMapping("/profiles")
    public List<Profile> getAllProfiles(HttpServletResponse response) {
        response.setStatus(200);
        List<Profile> result = this.profileService.getAll();
        return result;
    }

    @PostMapping(value = "/profiles/set", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageUtil getRegistration(
            @RequestBody ProfileForReqRegUtil inputProfile,
            HttpServletResponse response
    ) {
        MessageUtil result = new MsgForRespRegUtil();

        if (ValidateEmail.checkEmail(inputProfile.getEmail())) {
            Profile buffer = this.constructProfile(inputProfile);
            buffer = this.profileService.saveProfile(buffer);
            if (buffer == null) {
                response.setStatus(403);
                result.setData("Email already exist");
            } else {
                response.setStatus(200);
                result = new ProfileForRespRegUtil();
                result.setData(buffer.getId());
            }
        } else {
            response.setStatus(400);
            result.setData("Wrong email");
        }
        return result;
    }

    private Profile constructProfile(ProfileForReqRegUtil inputProfile) {
        Profile result = new Profile();
        result.setName(inputProfile.getName());
        result.setEmail(inputProfile.getEmail());
        result.setAge(inputProfile.getAge());
        result.setCreated(new Timestamp(System.currentTimeMillis()));
        return result;
    }

}
