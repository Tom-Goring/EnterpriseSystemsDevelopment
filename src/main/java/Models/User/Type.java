package Models.User;

import java.util.Locale;

public enum Type {
    PrivatePatient,
    PublicPatient,
    Other;

    public static Type fromString(String type) {
        type = type.toLowerCase(Locale.ROOT);
        switch(type) {
            case "private":
                return Type.PrivatePatient;
            case "public":
                return Type.PublicPatient;
            default:
                return Type.Other;
        }
    }
}
