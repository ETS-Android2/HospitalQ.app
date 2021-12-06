package id.ac.umn.hospitalq;

public class DoctorData {
    private String email, firstname, lastname, birthdate, alamat, departement, ppicture;

    public DoctorData(String email, String firstname, String lastname, String birthdate, String alamat, String departement, String ppicture){
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.alamat = alamat;
        this.departement = departement;
        this.ppicture = ppicture;
    }
    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPpicture() {
        return ppicture;
    }

    public void setPpicture(String ppicture) {
        this.ppicture = ppicture;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getDepartement() {
        return departement;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }
    public String namaLengkap(){
        return  this.getFirstname() + " " + this.getLastname();
    }
}
