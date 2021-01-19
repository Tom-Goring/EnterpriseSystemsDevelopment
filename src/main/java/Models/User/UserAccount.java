package Models.User;

public class UserAccount extends User {
    private final byte[] password;
    private final byte[] salt;
    private boolean active;
    public UserAccount(
            User user,
            byte[] password,
            byte[] salt,
            boolean active
    ) {
        super(
                user.getID(),
                user.getFirstName(),
                user.getSurname(),
                user.getEmail(),
                user.getRole(),
                user.getDOB(),
                user.getAddress(),
                user.getGender(),
                user.getType()
        );
        this.password = password;
        this.salt = salt;
        this.active = active;
    }

    public byte[] getPassword() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public boolean isActive() { return active; }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static boolean isPrivilegedRole(String role) {
        return role.equals("admin") || role.equals("doctor") || role.equals("nurse");
    }
}
