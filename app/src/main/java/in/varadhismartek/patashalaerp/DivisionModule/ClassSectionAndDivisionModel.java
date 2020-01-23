package in.varadhismartek.patashalaerp.DivisionModule;

public class ClassSectionAndDivisionModel {

    private String name;
    private boolean checked;



    public ClassSectionAndDivisionModel(String divisionName, boolean divisionChecked) {
        this.name = divisionName;
        this.checked = divisionChecked;
    }



    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
