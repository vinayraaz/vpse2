package in.varadhismartek.patashalaerp.ScheduleModule;

import java.io.Serializable;

public class ScheduleModel implements Serializable {

    private String date, type, division;

    public ScheduleModel(String date, String type, String division) {
        this.date = date;
        this.type = type;
        this.division = division;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getDivision() {
        return division;
    }



    String scheduleType, classess, section, message, image, time, toDate,eventTitle,addedFirstName,subject;

    public ScheduleModel(String scheduleType, String type, String date, String toDate, String time, String classess, String section, String division,
                         String message, String image, String addedFirstName, String eventTitle, String subject){

        this.scheduleType = scheduleType;
        this.type = type;
        this.date = date;
        this.toDate = toDate;
        this.time = time;
        this.classess = classess;
        this.section = section;
        this.division = division;
        this.message = message;
        this.image = image;
        this.addedFirstName = addedFirstName;
        this.eventTitle = eventTitle;
        this.subject = subject;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public String getClassess() {
        return classess;
    }

    public String getSection() {
        return section;
    }

    public String getMessage() {
        return message;
    }

    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }

    public String getToDate() {
        return toDate;
    }

    public String getAddedFirstName() {
        return addedFirstName;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getSubject() {
        return subject;
    }
}
