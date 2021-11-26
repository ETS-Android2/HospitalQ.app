package id.ac.umn.hospitalq;

public class BookingData {
    private String user_name, doktor_name, doctor_email;
    public BookingData(String user_name, String doktor_name, String doctor_email) {
        this.user_name = user_name;
        this.doktor_name = doktor_name;
        this.doctor_email = doctor_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDoktor_name() {
        return doktor_name;
    }

    public void setDoktor_name(String doktor_name) {
        this.doktor_name = doktor_name;
    }

    public String getDoctor_email() {
        return doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        this.doctor_email = doctor_email;
    }
}
