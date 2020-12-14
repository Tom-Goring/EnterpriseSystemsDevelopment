package Models.User;

public class UserAccount extends User {
    private final byte[] password;
    private final byte[] salt;
    public UserAccount(Integer ID, String firstName, String surname, String email, byte[] password, byte[] salt, String role) {
        super(ID, firstName, surname, email, role);
        this.password = password;
        this.salt = salt;
    }

    public byte[] getPassword() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }
}
