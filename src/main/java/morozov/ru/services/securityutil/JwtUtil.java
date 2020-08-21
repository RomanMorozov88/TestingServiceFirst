package morozov.ru.services.securityutil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import morozov.ru.services.staticsutil.ErrorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String INVALIDTOKEN = "Invalid token";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expired}")
    private int expired;

    @Autowired
    private ErrorUtil errorUtil;

    public JwtUtil() {
    }

    public String generateToken(String email) {
        Date date = Date
                .from(
                        LocalDate.now()
                                .plusDays(expired)
                                .atStartOfDay(ZoneId.systemDefault())
                                .toInstant()
                );
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            errorUtil.saveErrorMsg(INVALIDTOKEN);
            LOGGER.error(INVALIDTOKEN);
        }
        return false;
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String result = null;
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith(TOKEN_PREFIX)) {
            result = bearer.substring(TOKEN_PREFIX.length());
        }
        return result;
    }

}