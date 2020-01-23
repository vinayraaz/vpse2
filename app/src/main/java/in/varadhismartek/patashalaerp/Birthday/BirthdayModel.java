package in.varadhismartek.patashalaerp.Birthday;

public class BirthdayModel {

    private String firstName, lastName, customID, image, uuid, birthDate;

    public BirthdayModel(String firstName, String lastName, String customID, String image, String uuid, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.customID = customID;
        this.image = image;
        this.uuid = uuid;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCustomID() {
        return customID;
    }

    public String getImage() {
        return image;
    }

    public String getUuid() {
        return uuid;
    }

    public String getBirthDate() {
        return birthDate;
    }
}
