package Models.User;

import Models.Schedule.Schedule;

public class User {
     private final Integer ID;
     private final String firstName;
     private final String surname;
     private final String email;
     private final String role;
     private Schedule schedule;
     
     public User(Integer ID, String firstName, String surname, String email, String role) {
          this.ID = ID;
          this.firstName = firstName;
          this.surname = surname;
          this.email = email;
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
