package Models.User;

import Models.Schedule.Schedule;

import java.sql.Date;

public class User {
     private final Integer ID;
     private final String firstName;
     private final String surname;
     private final String email;
     private final String role;
     private final Date DOB;
     private final Address address;
     private final Gender gender;
     private final Type type;
     private Schedule schedule;
     
     public User(Integer ID, String firstName, String surname, String email, String role, Date DOB, Address address, Gender gender, Type type) {
          this.ID = ID;
          this.firstName = firstName;
          this.surname = surname;
          this.email = email;
          this.role = role;
          this.DOB = DOB;
          this.address = address;
          this.gender = gender;
          this.type = type;
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

     public String getEmail() {
          return email;
     }

     public String getRole() {
          return role;
     }

     public boolean isMedicalStaff() {
          return (this.role.equals("nurse") || this.role.equals("doctor"));
     }
     
     public void setSchedule(Schedule schedule) {
         this.schedule = schedule;
     }

     public Schedule getSchedule()
     {
         return schedule;
     }

     public Date getDOB() {
          return DOB;
     }

     public Address getAddress() {
          return address;
     }

     public Gender getGender() {
          return gender;
     }

     public Type getType() {
          return type;
     }

     @Override
     public String toString() {
          return "User{" +
                  "ID=" + ID +
                  ", firstName='" + firstName + '\'' +
                  ", surname='" + surname + '\'' +
                  ", email='" + email + '\'' +
                  '}';
     }
}
