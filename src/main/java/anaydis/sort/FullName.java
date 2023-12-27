package anaydis.sort;

public class FullName {
    private final String firstname;
    private final String lastname;

    FullName(String lastname,String firstname){
        this.firstname = firstname;
        this.lastname = lastname;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
}