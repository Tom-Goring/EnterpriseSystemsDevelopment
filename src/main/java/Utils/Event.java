package Utils;

public class Event {

    private String doctorName;
    private String date;
    private String startTime;
    private String endTime;
    private String customerName;
    private String type;
    
     public Event(String doctorName, String date, String startTime, String endTime, String customerName, String type) {
        this.doctorName = doctorName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerName = customerName;
        this.type = type;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getType() {
        return type;
    }
    @Override
    public String toString() {
        return "Event{" + "doctorName=" + doctorName + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime + ", customerName=" + customerName + ", type=" + type + '}';
    }
}