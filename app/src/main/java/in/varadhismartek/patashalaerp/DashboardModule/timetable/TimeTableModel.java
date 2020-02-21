package in.varadhismartek.patashalaerp.DashboardModule.timetable;

import java.util.ArrayList;

public class TimeTableModel {

    String startTime,typeOfPeriod;
    ArrayList<String> arrayListDays;

String dayInitial,start_time,subject,teacher;
    public TimeTableModel(String startTime, String typeOfPeriod) {
        this.startTime = startTime;
        this.typeOfPeriod = typeOfPeriod;
    }

    public TimeTableModel(ArrayList<String> arrayListDays) {
        this.arrayListDays = arrayListDays;
    }

    public TimeTableModel(String dayInitial, String start_time, String subject, String teacher) {
        this.dayInitial=dayInitial;
        this.start_time=start_time;
        this.subject=subject;
        this.teacher=teacher;

    }

    public String getDayInitial() {
        return dayInitial;
    }

    public void setDayInitial(String dayInitial) {
        this.dayInitial = dayInitial;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getTypeOfPeriod() {
        return typeOfPeriod;
    }

    public ArrayList<String> getArrayListDays() {
        return arrayListDays;
    }
}
