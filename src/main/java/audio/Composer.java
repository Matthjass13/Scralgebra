package audio;

public class Composer {
    private final String firstName;
    private final String lastName;
    public Composer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String toString() {
        return firstName + " " + lastName;
    }

}
