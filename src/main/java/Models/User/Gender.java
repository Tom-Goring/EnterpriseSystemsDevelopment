package Models.User;

import java.util.Locale;

public enum Gender {
    Male,
    Female,
    Other;

    public static Gender fromString(String gender) {
        gender = gender.toLowerCase(Locale.ROOT);
        switch(gender) {
            case "male":
                return Gender.Male;
            case "female":
                return Gender.Female;
            default:
                return Gender.Other;
        }
    }
}
