package in.varadhismartek.patashalaerp.DashboardModule.VisitorModule;

public class VisitorModule {
    private  String visitor_name = "", visitor_photo = "",entry_date="",visitor_uuid="",visitor_id="",
            entry_time="",purpose="",added_datetime="";


    public VisitorModule(String visitor_name, String visitor_photo, String entry_date, String visitor_uuid, String visitor_id, String entry_time, String purpose, String added_datetime) {
        this.visitor_name = visitor_name;
        this.visitor_photo = visitor_photo;
        this.entry_date = entry_date;
        this.visitor_uuid = visitor_uuid;
        this.visitor_id = visitor_id;
        this.entry_time = entry_time;
        this.purpose = purpose;
        this.added_datetime = added_datetime;
    }

    public String getVisitor_name() {
        return visitor_name;
    }

    public void setVisitor_name(String visitor_name) {
        this.visitor_name = visitor_name;
    }

    public String getVisitor_photo() {
        return visitor_photo;
    }

    public void setVisitor_photo(String visitor_photo) {
        this.visitor_photo = visitor_photo;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getVisitor_uuid() {
        return visitor_uuid;
    }

    public void setVisitor_uuid(String visitor_uuid) {
        this.visitor_uuid = visitor_uuid;
    }

    public String getVisitor_id() {
        return visitor_id;
    }

    public void setVisitor_id(String visitor_id) {
        this.visitor_id = visitor_id;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAdded_datetime() {
        return added_datetime;
    }

    public void setAdded_datetime(String added_datetime) {
        this.added_datetime = added_datetime;
    }
}
