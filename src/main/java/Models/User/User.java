package Models.User;

//import java.sql.Date;

import java.util.Arrays;

public class User {
     private final Integer ID;
     private final String firstName;
     private final String surname;
     private final byte[] password;
     private final byte[] salt;
     private final String email;
     private final String role;
//     private String phoneNumber;
//
//     private String addressLine1;
//     private String addressLine2;
//     private String city;
//     private String postCode;
//
//     private Date dob;
//
//     private String gender;

     public User(Integer ID, String firstName, String surname, String email, byte[] password, byte[] salt, String role) {
          this.ID = ID;
          this.firstName = firstName;
          this.surname = surname;
          this.email = email;
          this.password = password;
          this.salt = salt;
          this.role = role;
     }

     public Integer getID() {
          return ID;
     }

     public String getFirstName() {
          return firstName;
     }

     public String getSurname() {
          return surname;
     }

     public byte[] getPassword() {
          return password;
     }

     public byte[] getSalt() {
          return salt;
     }

     public String getEmail() {
          return email;
     }

     public String getRole() { return role; }

     @Override
     public String toString() {
          return "User{" +
                  "ID=" + ID +
                  ", firstName='" + firstName + '\'' +
                  ", surname='" + surname + '\'' +
                  ", password=" + Arrays.toString(password) +
                  ", salt=" + Arrays.toString(salt) +
                  ", email='" + email + '\'' +
                  '}';
     }
}
