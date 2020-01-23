package in.varadhismartek.patashalaerp.ScheduleModule;

import java.util.ArrayList;

public class MyScheduleModel {

    String date;
    ArrayList<ScheduleModel> scheduleModelList;

    public MyScheduleModel(String date, ArrayList<ScheduleModel> scheduleModelList) {
        this.date = date;
        this.scheduleModelList = scheduleModelList;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<ScheduleModel> getScheduleModelList() {
        return scheduleModelList;
    }
}
