package in.varadhismartek.patashalaerp.DashboardModule.Assessment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.ClassAndSection.ClassSectionModel;
import in.varadhismartek.patashalaerp.GeneralClass.CustomSpinnerAdapter;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtilsPatashala;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_ScheduleBarriers extends Fragment implements View.OnClickListener {
    APIService apiService, mApiServicePatashala;
    private ArrayList<String> academicList, listDivision, listClass, listSection, sessionList, listSectionNew;
    Spinner sp_academic, spn_division, spn_class, spn_section;
    String strAcdaemicYear, acdaemicFromDate, acdaemicToDate, strFromDate, strToDate;
    private String strDiv, strSection, str_class;
    ArrayList<ClassSectionModel> modelArrayList;
    Button btnSubmit;
    RelativeLayout re_fromdate,re_todate;
    TextView tv_from_date,tv_to_date,tv_Name,tv_Type;
    EditText tv_schedule_title, tv_schedule_type, tv_schedule_name;
    Calendar calendar;
    String fromYear,toYear,strfromDate,str_toDate;
    Date date1,date2;;
    int year,month,date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_schedule, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initViews(view);
        getAcadmicYear();
        getDivisionApi();



        // initListener();

        /*school_id:55e9cd8c-052a-46b1-b81c-14f85e11a8fe
session_serial_no:1
schedule_type:Holiday
holiday_type:Festival
holiday_name:Diwali
division:PUC
classes:{"11_standard":["a","b"],"12_standard":["a","b","c"]}
from_date:2020-10-23
to_date:2020-10-30
added_employeeid:54ff8e7e-a3d7-482e-b464-d87b9bcfd1d7
academic_start_date:2020-1-8
academic_end_date:2021-1-7
message:Diwali break!!
image:File_upload*/
        return view;
    }

    private void initViews(View view) {
        apiService = ApiUtils.getAPIService();
        mApiServicePatashala = ApiUtilsPatashala.getService();
        sp_academic = view.findViewById(R.id.sp_academic);
        spn_division = view.findViewById(R.id.spn_division);
        spn_class = view.findViewById(R.id.spn_class);
        spn_section = view.findViewById(R.id.spn_section);
        btnSubmit = view.findViewById(R.id.btn_ok);
        tv_schedule_title = view.findViewById(R.id.tv_schedule_title);
        tv_schedule_type = view.findViewById(R.id.tv_schedule_type);
        tv_schedule_name = view.findViewById(R.id.tv_schedule_name);
        re_fromdate = view.findViewById(R.id.re_fromdate);
        re_todate = view.findViewById(R.id.re_todate);
        tv_from_date = view.findViewById(R.id.tv_from_date);
        tv_to_date = view.findViewById(R.id.tv_to_date);
        tv_Type = view.findViewById(R.id.tv_type);
        tv_Name = view.findViewById(R.id.tv_Name);

        btnSubmit.setOnClickListener(this);
        re_fromdate.setOnClickListener(this);
        re_todate.setOnClickListener(this);
        academicList = new ArrayList<>();
        listDivision = new ArrayList<>();
        listClass = new ArrayList<>();
        listSectionNew = new ArrayList<>();
        modelArrayList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DAY_OF_MONTH);

    }


    private void getAcadmicYear() {
        mApiServicePatashala.getAcademicYear(Constant.SCHOOL_ID).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                academicList.clear();
                if (response.isSuccessful()) {

                    try {
                        academicList.add("Academic Year");

                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String message = object.getString("message");
                        String status1 = object.getString("status");

                        if (status1.equalsIgnoreCase("Success")) {

                            JSONObject jsonData = object.getJSONObject("data");
                            Log.d("jwhdlff", jsonData.toString());

                            JSONObject academicYear = jsonData.getJSONObject("Academic_years");

                            Iterator<String> key = academicYear.keys();

                            while (key.hasNext()) {

                                JSONObject years = academicYear.getJSONObject(key.next());

                                String start_date = years.getString("start_date");
                                String end_date = years.getString("end_date");

                                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date fromYear = formater.parse(start_date);
                                    Date toYear = formater.parse(end_date);

                                    String from = formatterDate.format(fromYear);
                                    String to = formatterDate.format(toYear);

                                    String acadmicListData = from + " / " + to;
                                    academicList.add(acadmicListData);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                            setSpinnerForAcademicYear(academicList);

                        } else {
                            Log.d("ldafhhlka", response.code() + " " + message);
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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

    private void setSpinnerForAcademicYear(ArrayList<String> academicList) {


        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), academicList);
        sp_academic.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        sp_academic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strAcdaemicYear = sp_academic.getSelectedItem().toString();
                if (strAcdaemicYear.equalsIgnoreCase("Academic Year")) {

                } else {
                    String[] date = strAcdaemicYear.split("/");


                    acdaemicFromDate = date[0];
                    acdaemicToDate = date[1];

                    Log.i("ACD***", "" + acdaemicFromDate + acdaemicToDate);

                    SimpleDateFormat inputFormate = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat outputFormate = new SimpleDateFormat("dd-MM-yyyy");

                    try {
                        Date inputFromDate = inputFormate.parse(acdaemicFromDate);
                        Date inputToDate = inputFormate.parse(acdaemicToDate);

                        strFromDate = outputFormate.format(inputFromDate);
                        strToDate = outputFormate.format(inputToDate);


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getDivisionApi() {
        listDivision.clear();
        listDivision.add("Division");
        apiService.getDivisions(Constant.SCHOOL_ID).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                boolean statusDivision = false;
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = (String) object.get("status");

                        if (status.equalsIgnoreCase("success")) {
                            JSONArray jsonArray = object.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                String added_datetime = object1.getString("added_datetime");
                                String Id = object1.getString("id");
                                statusDivision = object1.getBoolean("status");
                                if (statusDivision) {
                                    String division = object1.getString("division");

                                    listDivision.add(division);
                                }


                            }
                            CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), listDivision);
                            spn_division.setAdapter(customSpinnerAdapter);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("GET_DIVISION_EXCEPTION", t.getMessage());

            }
        });

        spn_division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strDiv = spn_division.getSelectedItem().toString();
                //listClass.clear();
                //listSubject.clear();
                //listSectionNew.clear();

                JSONArray array = new JSONArray();
                JSONObject jsonObject = new JSONObject();

                try {
                    array.put(strDiv);
                    jsonObject.put("divisions", array);
                    Constant.DIVISION_NAME = strDiv;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getClassSectionList(jsonObject);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getClassSectionList(JSONObject jsonObject) {
        listClass.clear();
        listClass.add("Class");
        apiService.getClassSections(Constant.SCHOOL_ID, jsonObject).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful()) {
                    Log.i("CLASS_SECTIONDDD", "" + response.body());

                    try {

                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = (String) object.get("status");

                        if (status.equalsIgnoreCase("success")) {
                            JSONObject jsonObject1 = object.getJSONObject("data");
                            if (object.getJSONObject("data").toString().equals("{}")) {
                                // modelArrayList.clear();
                                //  customSpinnerAdapter.notifyDataSetChanged();
                                //   setSpinnerForClass();


                            } else {

                                JSONObject jsonObject2 = jsonObject1.getJSONObject(strDiv);
                                Iterator<String> keys = jsonObject2.keys();

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    JSONObject jsonObjectValue = jsonObject2.getJSONObject(key);
                                    String className = jsonObjectValue.getString("class_name");
                                    JSONArray jsonArray = jsonObjectValue.getJSONArray("sections");
                                    listSection = new ArrayList<>();

                                    String Section = "";

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Section = jsonArray.getString(i);
                                        listSection.add(Section);
                                    }

                                    listClass.add(className);

                                    modelArrayList.add(new ClassSectionModel(className, listSection));

                                }
                            }


                        }

                    } catch (JSONException je) {

                    }

                } else {

                    listClass.clear();
                    listClass.add("Class");

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), listClass);
        spn_class.setAdapter(customSpinnerAdapter);


        spn_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                str_class = spn_class.getSelectedItem().toString();

                listSectionNew.clear();
                listSectionNew.add("Section");

                boolean b = true;

                for (int j = 0; j < modelArrayList.size(); j++) {
                    if (modelArrayList.get(j).getClassName().contains(str_class)) {
                        listSectionNew.clear();

                        for (int k = 0; k < modelArrayList.get(j).getListSection().size(); k++) {

                            listSectionNew.add(modelArrayList.get(j).getListSection().get(k));

                        }
                    }
                }


                CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), listSectionNew);
                spn_section.setAdapter(customSpinnerAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spn_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                strSection = spn_section.getSelectedItem().toString();
                if (strSection.equalsIgnoreCase("Section")) {
                    Toast.makeText(getActivity(), "Select Section", Toast.LENGTH_SHORT).show();

                }
                //getSubject(strDiv, str_class);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.re_fromdate:
                fromdateDialog();
                break;

                case R.id.re_todate:
                    toDateDialog();
                break;
            case R.id.btn_ok:
                if (strAcdaemicYear.equalsIgnoreCase("Academic Year")) {
                    Toast.makeText(getActivity(), "Select Academic year", Toast.LENGTH_SHORT).show();
                } else if (strDiv.equalsIgnoreCase("Division")) {
                    Toast.makeText(getActivity(), "Select Division", Toast.LENGTH_SHORT).show();
                } else if (str_class.equalsIgnoreCase("Class")) {
                    Toast.makeText(getActivity(), "Select Class", Toast.LENGTH_SHORT).show();
                } else if (strSection.equalsIgnoreCase("Section")) {
                    Toast.makeText(getActivity(), "Select Section", Toast.LENGTH_SHORT).show();
                } else if (tv_schedule_title.getText().toString().isEmpty() || tv_schedule_title.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Schedule Title", Toast.LENGTH_SHORT).show();
                } else if (tv_schedule_type.getText().toString().isEmpty() || tv_schedule_type.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Schedule Type", Toast.LENGTH_SHORT).show();
                } else if (tv_schedule_name.getText().toString().isEmpty() || tv_schedule_name.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Schedule Name", Toast.LENGTH_SHORT).show();
                }else if(tv_from_date.getText().toString().equalsIgnoreCase("From Date")){
                    Toast.makeText(getActivity(), "Select from date", Toast.LENGTH_SHORT).show();

                }else if(tv_to_date.getText().toString().equalsIgnoreCase("To Date")){
                    Toast.makeText(getActivity(), "Select from date", Toast.LENGTH_SHORT).show();

                }else{
                    tv_Type.setText("");
                    tv_Type.setText(tv_schedule_title.getText().toString()+" Type");
                    tv_Type.setText("");
                    tv_Type.setText(tv_schedule_name.getText().toString()+" Name");
                }
                break;
        }
    }

    private void toDateDialog() {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

                toYear = yearFormat.format(calendar.getTime());
                str_toDate = simpleDateFormat.format(calendar.getTime());
                Log.d("CHECK_DATE", str_toDate);
                date2 = new Date();
                try {
                    date2 = simpleDateFormat.parse(str_toDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                tv_to_date.setText(str_toDate);

            }
        }, year, month, date);

        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }

    private void fromdateDialog() {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());


                fromYear = yearFormat.format(calendar.getTime());
                strfromDate = simpleDateFormat.format(calendar.getTime());
                Log.d("CHECK_DATE", strfromDate);
                date1 = new Date();
                try {
                    date1 = simpleDateFormat.parse(strfromDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                tv_from_date.setText(strfromDate);

            }
        }, year, month, date);

        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }
}
