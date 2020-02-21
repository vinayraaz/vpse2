package in.varadhismartek.patashalaerp.DashboardModule.timetable;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.GeneralClass.CustomSpinnerAdapter;
import in.varadhismartek.patashalaerp.GeneralClass.DateConvertor;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtilsPatashala;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeTableActivity extends AppCompatActivity {
    TextView tvSubject;
    View child;
    APIService mApiService;
    APIService mApiServiceP;
    private DateConvertor convertor;
    String school_id = "9ecf9e65-2ae5-40c7-9a1c-68bf6ade801f";
    Spinner spDivision, spAcademicYr, spSession, spClass, spSection;
    ArrayList<String> academicList;
    ArrayList<String> sessionList;
    ArrayList<String> divisionList;
    RecyclerView rcvTimingsStart, rcvDays;
    ArrayList<TimeTableModel> arrayList;
    Map<String, List<TimeTableModel>> listHashMap;

    String divisionSchl, session_serial_no, academic_start_date;
    TimeTableAdapter timeTableAdapter;
    Button btnViewTimeTable;
    String strStatTime, timeConvert;

    ArrayList<String> arrayListDays;
    ArrayList<String> classList;
    ArrayList<String> sectionList;
    String str_class = "";
    String str_section = "";
    String strStatTime1, strSub1;
    LinearLayout llMonday, llTuesday, llWednesday, llThursday, llFriday, llSaturday, llSunday;
    List<TimeTableModel> tableModels = new ArrayList<>();
    Dialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFullScreenActivity();
        setContentView(R.layout.activity_time_table);


        rcvTimingsStart = findViewById(R.id.rcvTimingsStart);
        llMonday = findViewById(R.id.llMonday);
        llTuesday = findViewById(R.id.llTuesday);
        llWednesday = findViewById(R.id.llWednesday);
        llThursday = findViewById(R.id.llThursday);
        llFriday = findViewById(R.id.llFriday);
        llSaturday = findViewById(R.id.llSaturday);
        llSunday = findViewById(R.id.llSunday);

        rcvDays = findViewById(R.id.rcvDays);
        academicList = new ArrayList<>();
        sessionList = new ArrayList<>();
        divisionList = new ArrayList<>();
        arrayList = new ArrayList<>();
        arrayListDays = new ArrayList<>();
        mApiService = ApiUtilsPatashala.getService();
        mApiServiceP = ApiUtils.getAPIService();
        convertor = new DateConvertor();
        classList = new ArrayList<>();
        sectionList = new ArrayList<>();
        listHashMap = new HashMap<String, List<TimeTableModel>>();

        dialogSelectionToViewTimeTable();


    }

    private void getFullScreenActivity() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    private void dialogSelectionToViewTimeTable() {

        dialog = new Dialog(TimeTableActivity.this);
        dialog.setContentView(R.layout.time_table_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        spDivision = dialog.findViewById(R.id.spDivision);
        spAcademicYr = dialog.findViewById(R.id.spAcademicYr);
        spSession = dialog.findViewById(R.id.spSession);
        spClass = dialog.findViewById(R.id.spClass);
        spSection = dialog.findViewById(R.id.spSection);
        btnViewTimeTable = dialog.findViewById(R.id.btnViewTimeTable);
        dialog.show();
        getArrangeTimetableBarriers();
        setDaysRecyclerView();

        btnViewTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    private void setDaysRecyclerView() {

        arrayListDays.add("MON");
        arrayListDays.add("TUE");
        arrayListDays.add("WED");
        arrayListDays.add("THU");
        arrayListDays.add("FRI");
        arrayListDays.add("SAT");
        arrayListDays.add("SUN");

        /*timeTableAdapter = new TimeTableAdapter(TimeTableActivity.this,arrayListDays,Constant.DAYS);
        rcvDays.setLayoutManager(new LinearLayoutManager(TimeTableActivity.this,LinearLayoutManager.VERTICAL,false));
        rcvDays.setAdapter(timeTableAdapter);*/


    }

    private void getArrangeTimetableBarriers() {


        mApiService.getAchoolAcademicYears(school_id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful()) {

                    if (academicList.size() > 0)
                        academicList.clear();


                    try {

                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String message = object.getString("message");
                        String status1 = object.getString("status");
                        academicList.add(0, "Academic Year");

                        if (status1.equalsIgnoreCase("Success")) {

                            JSONObject jsonData = object.getJSONObject("data");
                            Log.d("jwhdlff", jsonData.toString());

                            JSONObject academicYear = jsonData.getJSONObject("Academic_years");

                            Iterator<String> key = academicYear.keys();

                            while (key.hasNext()) {

                                JSONObject years = academicYear.getJSONObject(key.next());

                                String start_date = years.getString("start_date");
                                String end_date = years.getString("end_date");

                                String from = convertor.getDateByTZ(start_date);
                                String to = convertor.getDateByTZ(end_date);


                                Log.d("lakfblkas", from + " " + to);

                                String fromYear = from.substring(from.length() - 4);
                                String toYear = to.substring(to.length() - 4);

                                Log.d("year", fromYear + " " + toYear);


                                academicList.add(from + "/" + to);

                                addlistIntoAcademicSpinner(academicList, fromYear, toYear);


                            }


                            //setSpinnerForAcademicYear(academicList);

                        } else {
                            Log.d("ldafhhlka", response.code() + " " + message);
                            Toast.makeText(TimeTableActivity.this, message, Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject object = new JSONObject(response.errorBody().string());
                        String message = object.getString("message");
                        Log.d("ADMIN_API", message);
                        Toast.makeText(TimeTableActivity.this, message, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {

            }
        });

    }

    private void addlistIntoAcademicSpinner(ArrayList<String> academicList, String fromYr, String toYr) {

        //academicList.add(0, "Academic Year");

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(fromYr + "-" + toYr);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(TimeTableActivity.this, academicList, "Blue");
        spAcademicYr.setAdapter(adapter);


        spAcademicYr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (i > 0) {
                    String[] date = spAcademicYr.getSelectedItem().toString().split("/");

                    String fromDate = date[0];

                    academic_start_date = fromDate;

                    String toDate = date[1];

                    getSessionAPI(fromDate, toDate);

                    spDivision.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void getSessionAPI(String from, String to) {

        mApiService.getSchoolSessions(school_id, from, to).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {

                if (response.isSuccessful()) {
                    try {

                        if (sessionList.size() > 0)
                            sessionList.clear();
                        sessionList.add(0, "Session");

                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        Log.d("JSON", "onResponse: " + object.toString());
                        String message = object.getString("message");
                        String status1 = object.getString("status");

                        if (status1.equalsIgnoreCase("Success")) {

                            JSONObject jsonData = object.getJSONObject("data");

                            JSONObject sessionsData = jsonData.getJSONObject("sessions");

                            Iterator<String> key = sessionsData.keys();

                            while (key.hasNext()) {

                                JSONObject keyData = sessionsData.getJSONObject(key.next());

                                int session_serial_no = keyData.getInt("session_serial_no");
                                Log.d("sessionSerialNO", String.valueOf(session_serial_no));

                                sessionList.add(session_serial_no + "");
                            }

                            setSpinnerForSession(sessionList);

                        } else {
                            Log.d("ldafhhlkafhd", response.code() + " " + message);
                            Toast.makeText(TimeTableActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject object = new JSONObject(response.errorBody().string());
                        String message = object.getString("message");
                        Log.d("ADMIN_API2", message);
                        Toast.makeText(TimeTableActivity.this, message, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }

    private void setSpinnerForSession(ArrayList<String> sessionList) {

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(TimeTableActivity.this, sessionList, "Blue");
        spSession.setAdapter(adapter);

        spSession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    String session = spSession.getSelectedItem().toString();
                    Log.d("session", "" + session);
                    session_serial_no = session;
                    getDivision();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void getDivision() {

        mApiServiceP.getDivisions(school_id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful()) {
                    Log.i("DIVISION**GET", "" + response.body());

                    if (divisionList.size() > 0)
                        divisionList.clear();

                    divisionList.add(0, "Division");
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = (String) object.get("status");

                        if (status.equalsIgnoreCase("success")) {
                            JSONArray jsonArray = object.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                String added_datetime = object1.getString("added_datetime");
                                String Id = object1.getString("id");
                                boolean statusDivision = object1.getBoolean("status");

                                String division = object1.getString("division");
                                String school_id = object1.getString("school_id");
                                String added_by_id = object1.getString("added_by_id");

                                divisionList.add(division);

                                Log.d("MY_DATA", added_datetime + " " + Id + " " + statusDivision + " " +
                                        division + " " + school_id + " " + added_by_id);

                                setDivisionToSpinner(divisionList);


                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(TimeTableActivity.this, "No Data", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("GET_DIVISION_EXCEPTION", t.getMessage());

            }
        });
    }

    private void setDivisionToSpinner(ArrayList<String> divisionList) {

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(TimeTableActivity.this, divisionList, "Blue");
        spDivision.setAdapter(adapter);

        spDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    String division = spDivision.getSelectedItem().toString();

                    divisionSchl = division;

                    Log.d("division", "" + division);


                    getAllClasses();

                    get_arranged_timetable_barrier();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void getAllClasses() {


        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            jsonArray.put("All");
            object.put("divisions", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Class_api_2", object.toString());

        if (classList.size() > 0) {
            classList.clear();
        }

        classList.add(0, "-Select Class-");

        mApiServiceP.getClassSections(school_id, object).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {

                Log.d("responsejsonn", "" + response.code());

                if (response.isSuccessful()) {

                    try {
                        JSONObject obj = new JSONObject(new Gson().toJson(response.body()));
                        String status = obj.getString("status");


                        if (status.equalsIgnoreCase("Success")) {
                            JSONObject jsonData = obj.getJSONObject("data");

                            Log.d("JsonObject", jsonData.toString());
                            Iterator<String> key = jsonData.keys();

                            while (key.hasNext()) {

                                String myDivisionKey = key.next();
                                JSONObject keyData = jsonData.getJSONObject(myDivisionKey);
                                Iterator<String> classKey = keyData.keys();

                                while (classKey.hasNext()) {
                                    JSONObject classData = keyData.getJSONObject(classKey.next());
                                    String class_name = classData.getString("class_name");
                                    classList.add(class_name);
                                    JSONArray jsonArray1 = classData.getJSONArray("sections");
                                    Log.d("classData_array", jsonArray1.toString());

                                    ArrayList<String> sections = new ArrayList<>();

                                    for (int i = 0; i < jsonArray1.length(); i++) {

                                        sections.add(jsonArray1.getString(i));
                                        Log.d("classData_array " + i, jsonArray1.getString(i));
                                    }

                                }
                            }
                            setSpinnerForClass();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject object = new JSONObject(response.errorBody().string());
                        String message = object.getString("message");
                        Log.d("create_API", message);
                        Toast.makeText(TimeTableActivity.this, message, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {

            }
        });

    }

    private void setSpinnerForClass() {

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(TimeTableActivity.this, classList, "Blue");
        spClass.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    str_class = spClass.getSelectedItem().toString();
                    Log.d("Class", "" + str_class);

                    getAllSection();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void getAllSection() {

        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            jsonArray.put("All");
            object.put("divisions", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Class_api_2", object.toString());

        if (sectionList.size() > 0) {
            sectionList.clear();
        }

        sectionList.add(0, "-Section-");

        mApiServiceP.getClassSections(school_id, object).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject obj = new JSONObject(new Gson().toJson(response.body()));
                        String status = obj.getString("status");

                        if (status.equalsIgnoreCase("Success")) {
                            JSONObject jsonData = obj.getJSONObject("data");
                            Iterator<String> key = jsonData.keys();

                            while (key.hasNext()) {

                                String myDivisionKey = key.next();
                                JSONObject keyData = jsonData.getJSONObject(myDivisionKey);
                                Iterator<String> classKey = keyData.keys();

                                while (classKey.hasNext()) {
                                    JSONObject classData = keyData.getJSONObject(classKey.next());
                                    String class_name = classData.getString("class_name");

                                    if (class_name.equals(str_class)) {

                                        JSONArray jsonArray1 = classData.getJSONArray("sections");
                                        Log.d("classData_array", jsonArray1.toString());

                                        for (int i = 0; i < jsonArray1.length(); i++) {

                                            sectionList.add(jsonArray1.getString(i));
                                            Log.d("classData_array " + i, jsonArray1.getString(i));
                                        }
                                    }
                                }
                            }
                            setSpinnerForSection();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject object = new JSONObject(response.errorBody().string());
                        String message = object.getString("message");
                        Log.d("create_API", message);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {

            }
        });

    }

    private void setSpinnerForSection() {


        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(TimeTableActivity.this, sectionList, "Blue");
        spSection.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        spSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {

                    str_section = spSection.getSelectedItem().toString();

                    Log.d("Section", str_section);

                    getCompleteTimeTable();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getCompleteTimeTable() {

        Log.d("ValueApi", school_id + " " + str_class + " " + session_serial_no + " " + academic_start_date + " " + str_section);

        mApiService.getClassSectionTimeTable(school_id, str_class, session_serial_no, academic_start_date, str_section).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {


                if (response.isSuccessful()) {
                    try {
                        JSONObject obj = new JSONObject(new Gson().toJson(response.body()));
                        Log.d("responseTT", obj + "");

                        String status = obj.getString("status");

                        if (status.equalsIgnoreCase("Success")) {
                            //JSONObject jsonData = obj.getJSONObject("data");

                            JSONArray jsonArray = obj.getJSONArray("data");

                            Log.d("jsonSize", jsonArray.length() + "");


                            tableModels.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String day = jsonObject.getString("Day");

                                String dayInitial = day.substring(0, 3);
                                String start_time = jsonObject.getString("start_time");
                                String subject = jsonObject.getString("subject");
                                String teacher = jsonObject.getString("teacher");
                                tableModels.add(new TimeTableModel(dayInitial, start_time, subject, teacher));

                                for (int j = 0; j < arrayList.size(); j++) {

                                    child = getLayoutInflater().inflate(R.layout.timetable_teacher_subject, null);

                                    TextView tvTeacherName = child.findViewById(R.id.tvTeacherName);
                                    tvSubject = child.findViewById(R.id.tvSubject);
                                    String timeConvert = getTime(arrayList.get(j).getStartTime());

                                  /*  if(dayInitial.equalsIgnoreCase("Mon") && timeConvert.equalsIgnoreCase(jsonArray.getJSONObject(i).getString("start_time"))){
                                        Log.d("startTimeMon", timeConvert+""+subject+" "+teacher);
                                       // tvTeacherName.setText(teacher);
                                        tvSubject.setText(subject.substring(0,3));
                                        llMonday.addView(child);

                                    }*/

                                    if (dayInitial.equalsIgnoreCase("Tue") && timeConvert.equalsIgnoreCase(jsonArray.getJSONObject(i).getString("start_time"))) {
                                        Log.d("startTimeTue", timeConvert + "" + subject + " " + teacher);
                                        // tvTeacherName.setText(teacher);
                                        tvSubject.setText(subject.substring(0, 3));
                                        llTuesday.addView(child);

                                    }

                                    if (dayInitial.equalsIgnoreCase("Wed") && timeConvert.equalsIgnoreCase(jsonArray.getJSONObject(i).getString("start_time"))) {
                                        Log.d("startTimeWed", timeConvert + "" + subject + " " + teacher);
                                        //tvTeacherName.setText(teacher);
                                        tvSubject.setText(subject.substring(0, 3));
                                        llWednesday.addView(child);

                                    }

                                    if (dayInitial.equalsIgnoreCase("Thu") && timeConvert.equalsIgnoreCase(jsonArray.getJSONObject(i).getString("start_time"))) {
                                        Log.d("startTimeThu", timeConvert + "" + subject + " " + teacher);
                                        //tvTeacherName.setText(teacher);
                                        tvSubject.setText(subject.substring(0, 3));
                                        llThursday.addView(child);

                                    }

                                    if (dayInitial.equalsIgnoreCase("Fri") && timeConvert.equalsIgnoreCase(jsonArray.getJSONObject(i).getString("start_time"))) {
                                        Log.d("startTimeFri", timeConvert + "" + subject + " " + teacher);
                                        //tvTeacherName.setText(teacher);
                                        tvSubject.setText(subject.substring(0, 3));
                                        llFriday.addView(child);

                                    }

                                    if (dayInitial.equalsIgnoreCase("Sat") && timeConvert.equalsIgnoreCase(jsonArray.getJSONObject(i).getString("start_time"))) {
                                        Log.d("startTimeSat", timeConvert + "" + subject + " " + teacher);
                                        //tvTeacherName.setText(teacher);
                                        tvSubject.setText(subject.substring(0, 3));
                                        llSaturday.addView(child);

                                    }

                                    if (dayInitial.equalsIgnoreCase("Sun") && timeConvert.equalsIgnoreCase(jsonArray.getJSONObject(i).getString("start_time"))) {
                                        Log.d("startTimeSun", timeConvert + "" + subject + " " + teacher);
                                        //tvTeacherName.setText(teacher);
                                        tvSubject.setText(subject.substring(0, 3));
                                        llSunday.addView(child);
                                    }


                                }


                            }


                            Gson g = new Gson();
                            System.out.println("listHashMap_Data***" + g.toJson(listHashMap));
                            System.out.println("listHashMap***" + listHashMap.size() + "***" + tableModels.size());
                            String strDay, strSt, strSub2;

                         /*   for (int p = 0; p <= tableModels.size(); p++) {
                                System.out.println("Sub***" + tableModels.get(p).getSubject() + "**" + tableModels.get(p).getStart_time());
                                strDay = tableModels.get(p).getDayInitial();
                                strSt = tableModels.get(p).getStart_time();
                                strSub2 = tableModels.get(p).getSubject();
                                System.out.println("Sub***1*" + strDay + "**" + strSt + "**" + strSub2);*/

                            Iterator myVeryOwnIterator = listHashMap.keySet().iterator();
                            while (myVeryOwnIterator.hasNext()) {
                                String keyData = (String) myVeryOwnIterator.next();

                                // System.out.println("Sub***1AA*" + tableModels.get(p).getSubject()+"**"+ tableModels.get(p).getStart_time());


                                for (int j = 0; j < listHashMap.get(keyData).size(); j++) {
                                    if ((keyData.equalsIgnoreCase("Mon") && tableModels.get(j).getDayInitial().equalsIgnoreCase("Mon"))) {
                                        // System.out.println("Sub***1A*" + tableModels.get(j).getSubject()+"**"+ tableModels.get(j).getStart_time());
                                        System.out.println("Date**2*" + listHashMap.get(keyData).get(j).getStartTime());
                                        System.out.println("Date**2*" + listHashMap.get(keyData).get(j).getTypeOfPeriod());
                                        String strStatTime = listHashMap.get(keyData).get(j).getStartTime();
                                        timeConvert = getTime(strStatTime);

                                        System.out.println("Sub***1B*" + timeConvert + "**" + tableModels.get(j).getStart_time() + "**" + tableModels.get(j).getSubject());

                                               /* if (timeConvert.equalsIgnoreCase(tableModels.get(j).getStart_time())) {
                                                    System.out.println("Sub***1A*" +timeConvert+"**" +tableModels.get(j).getSubject() + "**" + tableModels.get(j).getStart_time());

                                                }*/
                                    } else if ((keyData.equalsIgnoreCase("Tue") && tableModels.get(j).getDayInitial().equalsIgnoreCase("tue"))) {
                                        System.out.println("Sub***1C*" + timeConvert + "**" + tableModels.get(j).getStart_time()+ "**" + tableModels.get(j).getSubject());

                                    }
                                }

                            }

                            //   }
                            for (int i = 0; i < tableModels.size(); i++) {

                             /*   Iterator myVeryOwnIterator = listHashMap.keySet().iterator();
                                while (myVeryOwnIterator.hasNext()) {
                                    String keyData = (String) myVeryOwnIterator.next();

                                    for (int j = 0; j < listHashMap.get(keyData).size(); j++) {
                                        System.out.println("Date**2*" + listHashMap.get(keyData).get(j).getStartTime());
                                        System.out.println("Date**2*" + listHashMap.get(keyData).get(j).getTypeOfPeriod());
                                        String strStatTime = listHashMap.get(keyData).get(j).getStartTime();
                                        timeConvert = getTime(strStatTime);
                                        System.out.println("Date**2A*" + timeConvert+"***"+strSt+"**"+strSub2);
                                        if((strDay.equalsIgnoreCase("Mon") && keyData.equalsIgnoreCase("Mon")) &&
                                                (timeConvert.equalsIgnoreCase(strSt))) {
                                            System.out.println("Date**2AA*" + timeConvert+"***"+strSt+"**"+strSub2);
                                            tvSubject.setText(strSub2.substring(0,3));
                                            llMonday.addView(child);

                                        }else {
                                            String strValue ="NoData";
                                            tvSubject.setText(strValue.substring(0,3));
                                            llMonday.addView(child);
                                        }

                                    }


                                }*/

                            }


                        } else {

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }

    private String getTime(String startTime) {

        try {
            SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm a");
            Date date = inFormat.parse(startTime);
            SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm:ss");
            String goal = outFormat.format(date);
            return goal;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void get_arranged_timetable_barrier() {

        Date initDate = null;
        try {
            initDate = new SimpleDateFormat("dd-mm-yyyy").parse(academic_start_date);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
            academic_start_date = formatter.format(initDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        Log.d("ValuesTime", "" + school_id + " " + divisionSchl + " " + session_serial_no + " " + academic_start_date);
        listHashMap.clear();
        mApiServiceP.getArrangeTimetableBarriers(school_id, divisionSchl, session_serial_no, academic_start_date).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful()) {

                    JSONObject object = null;
                    try {
                        object = new JSONObject(new Gson().toJson(response.body()));
                        String status1 = object.getString("status");


                        if (status1.equalsIgnoreCase("Success")) {

                            JSONObject jsonData = object.getJSONObject("data");
                            Log.d("data", "" + jsonData.toString());


                            Iterator<String> key = jsonData.keys();

                            while (key.hasNext()) {

                                String myKey = key.next();
                                Log.d("dataA", "" + myKey);

                                JSONObject keyData = jsonData.getJSONObject(myKey);

                                String start_time = keyData.getString("start_time");
                                String type_of = keyData.getString("type_of");
                                Log.d("dataAA", "" + type_of + " " + start_time);

                                arrayList.add(new TimeTableModel(start_time, type_of));
                                listHashMap.put("Mon", arrayList);
                                listHashMap.put("Tue", arrayList);
                                listHashMap.put("Wed", arrayList);
                                listHashMap.put("Thu", arrayList);
                                listHashMap.put("Fri", arrayList);
                                listHashMap.put("Sat", arrayList);
                                listHashMap.put("Sun", arrayList);

                            }
                            Gson g = new Gson();
                            System.out.println("Data***" + g.toJson(listHashMap));
                            System.out.println("Data***" + listHashMap.size());

                            setTimeToRecyclerView(arrayList);
                            // setTimeToRecyclerView(listHashMap);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    private void setTimeToRecyclerView(ArrayList<TimeTableModel> arrayList) {

        timeTableAdapter = new TimeTableAdapter(arrayList, TimeTableActivity.this, Constant.PERIOD_TIMINGS);
        rcvTimingsStart.setLayoutManager(new LinearLayoutManager(TimeTableActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rcvTimingsStart.setAdapter(timeTableAdapter);

    }


}
