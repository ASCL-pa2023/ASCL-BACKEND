package esgi.ascl.User.domain.entities;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {
    private final String value;

    public Password(String value) {
        Objects.requireNonNull(value);
        if (!isValid(value)) {
            throw new IllegalArgumentException("A password must contain 8 to 15 characters, 1 or more capital letter, 1 or more small letter, 1 or more number and at least one of these special characters: $ @ % * + - _ !.");
        }
        this.value = value;
    }

    /*
    regex source :
    https://codes-sources.commentcamarche.net/source/49715-validation-de-mot-de-passe
    A valid password must have
        - 8 to 15 characters
        - min 1 capital letter
        - min 1 small letter
        - min 1 number
        - at least one of these special characters: $ @ % * + - _ !
        - no more characters possible: no & no { for example)
     */
    private boolean isValid(String value){
        Pattern p = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[-+!*$@%_])([-+!*$@%_\\w]{8,15})$");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
