package morozov.ru.services.staticsutil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateEmail {

    private static final String emailPattern = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";

    public static boolean checkEmail(String email) {
        boolean result = false;
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        String buffer = null;
        while (matcher.find()) {
            buffer = matcher.group();
        }
        if (buffer != null) {
            result = true;
        }
        return result;
    }

}