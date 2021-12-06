package id.ac.umn.hospitalq;

public class AntrianData {
    private String doktor_name, doktor_email, current_antrian, antrian;

    public AntrianData(String doktor_name, String current_antrian, String antrian, String doktor_email) {
        this.doktor_email = doktor_email;
        this.doktor_name = doktor_name;
        this.current_antrian = current_antrian;
        this.antrian = antrian;
    }

    public String getDoktor_name() {
        return doktor_name;
    }

    public void setDoktor_name(String doktor_name) {
        this.doktor_name = doktor_name;
    }

    public String getDoktor_email() {
        return doktor_email;
    }

    public void setDoktor_email(String doktor_email) {
        this.doktor_email = doktor_email;
    }

    public String getCurrent_antrian() {
        return current_antrian;
    }

    public void setCurrent_antrian(String current_antrian) {
        this.current_antrian = current_antrian;
    }

    public String getAntrian() {
        return antrian;
    }

    public void setAntrian(String antrian) {
        this.antrian = antrian;
    }
}
