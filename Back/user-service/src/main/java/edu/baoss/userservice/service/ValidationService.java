package edu.baoss.userservice.service;

import edu.baoss.userservice.exceptions.ValidationException;
import edu.baoss.userservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationService {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-.]+@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    private static final String PLAIN_Text_PATTERN =
            "^\\s*[a-zA-Z0-9а-яёАЯЁ_]+(?:\\s*[a-zA-Z0-9а-яёАЯЁ_]+)*\\s*$";
    private static final String LOGIN_PATTERN =
            "^[A-Za-z0-9-._]+$";

    public boolean stringValidation(String str, int minSize, int maxSize) {
        if (str != null && str.length() >= minSize && str.length() <= maxSize) {
            return true;
        } else {
            throw new ValidationException("Bad data");
        }
    }

    public boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) return stringValidation(email, 6, 30);
        else throw new ValidationException("Incorrect email");
    }

    public boolean plainTextValidation(String text, int minSize, int maxSize) {
        if (text == null || minSize == text.length()) return false;
        Pattern pattern = Pattern.compile(PLAIN_Text_PATTERN);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) return stringValidation(text, minSize, maxSize);
        else {
            throw new ValidationException("Incorrect data");
        }
    }

    public boolean loginValidation(String login) {
        Pattern pattern = Pattern.compile(LOGIN_PATTERN);
        Matcher matcher = pattern.matcher(login);
        if (matcher.matches()) return stringValidation(login, 2, 20);
        else throw new ValidationException("Incorrect login");
    }

    public boolean passwordValidation(String pass) {
        return stringValidation(pass, 3, 50);
    }

    public boolean userValidation(User user, boolean validatePassword) {
        if (validatePassword)
            return loginValidation(user.getLogin()) &&
                emailValidation(user.getEmail()) &&
                passwordValidation(user.getPassword());
        return loginValidation(user.getLogin()) &&
                emailValidation(user.getEmail());
    }
}
