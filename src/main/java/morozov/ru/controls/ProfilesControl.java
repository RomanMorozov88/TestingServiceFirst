package morozov.ru.controls;

import morozov.ru.models.Profile;
import morozov.ru.models.controlsutil.forregistration.MessageUtil;
import morozov.ru.models.controlsutil.forregistration.MsgForRespUtil;
import morozov.ru.models.controlsutil.forregistration.ProfileForReqRegUtil;
import morozov.ru.models.controlsutil.forregistration.ProfileForRespRegUtil;
import morozov.ru.services.bdutil.ProfileService;
import morozov.ru.services.securityutil.JwtUtil;
import morozov.ru.services.validateemailutil.ValidateEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@RestController
public class ProfilesControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfilesControl.class);
    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private ProfileService profileService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/profiles/last")
    public Profile getLastProfile(HttpServletResponse response) {
        response.setStatus(200);
        Profile result = this.profileService.getLast();
        return result;
    }

    @GetMapping("/profiles/{ID}")
    public Profile getProfileById(@PathVariable int ID, HttpServletResponse response) {
        Profile result = this.profileService.getById(ID);
        if (result == null) {
            try {
                response.sendRedirect("/profiles/notfound");
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
        response.setStatus(200);
        return result;
    }

    @GetMapping("/profiles")
    public List<Profile> getAllProfiles(HttpServletResponse response) {
        List<Profile> result = this.profileService.getAll();
        response.setStatus(200);
        return result;
    }

    @GetMapping("/profiles/notfound")
    public MessageUtil getNotFound(HttpServletResponse response) {
        response.setStatus(404);
        MessageUtil result = new MsgForRespUtil();
        result.setData("Not found");
        return result;
    }

    @PostMapping(value = "/profiles/set", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageUtil getRegistration(
            @RequestBody ProfileForReqRegUtil inputProfile,
            HttpServletResponse response
    ) {
        MessageUtil result = new MsgForRespUtil();

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
                String token = this.combineToken(buffer.getEmail());
                response.setHeader(AUTHORIZATION, token);
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

    private String combineToken(String email) {
        String result = null;
        if (email != null) {
            result = TOKEN_PREFIX + jwtUtil.generateToken(email);
        }
        return result;
    }

}
