package in.varadhismartek.patashalaerp.DashboardModule.LeaveModule;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.GeneralClass.CustomSpinnerAdapter;
import in.varadhismartek.patashalaerp.GeneralClass.ValidationViews;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaveApplyFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView ivBack;
    private Spinner spleaveType, spSession;

    private String[] session_type = {"--Select Session--", "AM", "PM"};

    String fromDate;
    String toDate;

    String currentDate, currentTime;
    String leaveType, subject, message, doorNo, street, locality, landmark, pinCode, city, state, country, mobileNo;
    String leaveLeadComment;

    EditText etDoorNo, etStreet, etLocality, etLandMark, etPinCode, etCity, etState, etCountry, etMobileNo, etSubject, etMessage, etComment;

    public long date_min = 0;
    ImageView imageView;
    ImageView upload_img;
    SimpleDateFormat dateFormat, timeFormat;
    String str_from_date = null, str_to_date = null;
    LinearLayout attach_layout, lL_permission, permission_layout_time;
    RelativeLayout leave_layout_to_date;
    TextView id, name, time_view, date_view, from_date, to_date, tv_leaveleft;
    private EditText upload_file;
    private Button button;
    private int year, month, date;
    private int PICK_PDF_CODE = 10;
    Uri filepathuri;
    String Role;
    Bundle bundle;

    APIService mApiService;
    ArrayList<String> spnList;
    ProgressDialog progressDialog;


    public LeaveApplyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave_apply, container, false);

        initViews(view);
        initListeners();
        getSchoolLeaveBarrierDetailsAPI();
        setSpinnerForSession();
        getCurrentDateAndTime();

        bundle = getArguments();
        if (bundle != null) {
            Role = bundle.getString("role");
            Log.d("bundlreol", Role + "");
        } else {
            // Role = Constant.MAKER;
        }

   /*     assert getContext() != null;
        Glide.with(getContext()).load("https://www.deccanherald.com/sites/dh/files/styles/article_detail/public/article_images/2019/04/18/donald%20trump%20reuters-1555603321.jpg?itok=_KF314LJ")
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);*/

        return view;
    }

    @SuppressLint("SimpleDateFormat")
    private void getCurrentDateAndTime() {

        Calendar calendar = Calendar.getInstance();
        timeFormat = new SimpleDateFormat("hh:mm a");
        currentTime = timeFormat.format(calendar.getTime());
        time_view.setText(currentTime);

        //setting the date in the textview based upon the system time
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DAY_OF_MONTH);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = dateFormat.format(calendar.getTime());
        date_view.setText(currentDate);
        from_date.setText(currentDate);
        to_date.setText(currentDate);

    }


    private void setSpinnerForLeaveType() {

        spnList.add(0, "-Select Leave-");
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), spnList, "Blue");
        spleaveType.setAdapter(adapter);
    }

    private void setSpinnerForSession() {

        assert getContext() != null;

        List<String> categories = new ArrayList<>(Arrays.asList(session_type));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSession.setAdapter(dataAdapter);
    }


    private void initListeners() {

        mApiService = ApiUtils.getAPIService();

        ivBack.setOnClickListener(this);

        from_date.setOnClickListener(this);
        to_date.setOnClickListener(this);
        spleaveType.setOnItemSelectedListener(this);
        upload_img.setOnClickListener(this);
        imageView.setOnClickListener(this);
        button.setOnClickListener(this);
    }


    private void initViews(View view) {

        ivBack = view.findViewById(R.id.ivBack);
        spleaveType = view.findViewById(R.id.leave_types);
        spSession = view.findViewById(R.id.leave_permission);

        name = view.findViewById(R.id.empname);
        id = view.findViewById(R.id.empid);
        date_view = view.findViewById(R.id.c_date_text);
        time_view = view.findViewById(R.id.c_time_text);

        from_date = view.findViewById(R.id.from_date_textview);
        to_date = view.findViewById(R.id.to_date_textview);

        etSubject = view.findViewById(R.id.subject_edittext);
        etMessage = view.findViewById(R.id.message_edittext);

        etDoorNo = view.findViewById(R.id.etDoorNo);
        etStreet = view.findViewById(R.id.etStreet);
        etLocality = view.findViewById(R.id.etLocality);
        etLandMark = view.findViewById(R.id.etLandMark);
        etPinCode = view.findViewById(R.id.etPinCode);
        etCity = view.findViewById(R.id.etCity);
        etState = view.findViewById(R.id.etState);
        etCountry = view.findViewById(R.id.etCountry);
        etMobileNo = view.findViewById(R.id.etMobileNo);
        etComment = view.findViewById(R.id.etComment);

        imageView = view.findViewById(R.id.img_profile);

        upload_file = view.findViewById(R.id.upload_file_text);
        upload_img = view.findViewById(R.id.upload_file_img);
        attach_layout = view.findViewById(R.id.attach_sick_img_layout);
        lL_permission = view.findViewById(R.id.permission_layout_date);
        leave_layout_to_date = view.findViewById(R.id.leave_layout_to_date);
        tv_leaveleft = view.findViewById(R.id.leave_left);
        button = view.findViewById(R.id.submit);

        spnList = new ArrayList<>();
        progressDialog = new ProgressDialog(getActivity());

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.from_date_textview:

                if (getActivity() != null) {

                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                            //getting the current datefragment from the datepicker
                            str_from_date = simpleDateFormat.format(calendar.getTime());


                            try {
                                Date date = simpleDateFormat.parse(str_from_date);
                                date_min = date.getTime() + 1;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            //setting the date to textview what ever selecting the date from the date picker
                            from_date.setText(str_from_date);

                            calendar.add(Calendar.DATE, 1);
                            str_to_date = String.valueOf(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(calendar.getTime()));
                            //setting the date to textview what ever selecting the date from the date picker
                            to_date.setText(str_to_date);

                        }
                    }, year, month, date);

                    dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    dialog.show();
                }

                break;


            case R.id.to_date_textview:

                if (getActivity() != null) {

                    DatePickerDialog dialog1 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                            str_to_date = simpleDateFormat.format(calendar.getTime());

                            to_date.setText(str_to_date);

                        }
                    }, year, month, date);

                    if (date_min == 0) {
                        dialog1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    } else {
                        dialog1.getDatePicker().setMinDate(date_min);
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, date);
                    calendar.add(Calendar.MONTH, 2);

                    Date date = calendar.getTime();
                    long max_date = date.getTime();
                    dialog1.getDatePicker().setMaxDate(max_date);
                    dialog1.show();
                }

                break;

            case R.id.upload_file_img:

                getPDF();

                break;


            case R.id.submit:

                if (spleaveType.getSelectedItemPosition() == 0) {
                    Toast.makeText(getContext(), "Select the option", Toast.LENGTH_SHORT).show();
                    spleaveType.requestFocus();
                } else if (!ValidationViews.EditTextNullValidate(etSubject)) {
                    etSubject.setError("Enter the subject");
                } else if (!ValidationViews.EditTextNullValidate(etMessage)) {
                    etMessage.setError("Enter valid msg");
                } else if (!ValidationViews.EditTextNullValidate(etDoorNo)) {
                    etDoorNo.setError("Enter DoorNo.");
                } else if (!ValidationViews.EditTextNullValidate(etLocality)) {
                    etLocality.setError("Enter Locality.");
                } else if (!ValidationViews.EditTextNullValidate(etLandMark)) {
                    etLandMark.setError("Enter LandMark.");
                } else if (!ValidationViews.EditTextNullValidate(etPinCode)) {
                    etPinCode.setError("Enter etPinCode.");
                } else if (!ValidationViews.EditTextNullValidate(etCity)) {
                    etCity.setError("Enter City.");
                } else if (!ValidationViews.EditTextNullValidate(etState)) {
                    etState.setError("Enter State.");
                } else if (!ValidationViews.EditTextNullValidate(etCountry)) {
                    etCountry.setError("Enter Country.");
                } else if (!ValidationViews.EditTextNumberOfDigitsValidate(etMobileNo, 10)) {
                    etMobileNo.setError("Enter MobileNo.");
                } else {


                    Toast.makeText(getActivity(), "Submit clicked...!", Toast.LENGTH_SHORT).show();
                    callToApi();

                    /*if(spleaveType.getSelectedItem().toString().equals("Causal Leave")){

                        Toast.makeText(getActivity(), "Causal Leave clicked...!", Toast.LENGTH_SHORT).show();
                        callToApi();
                    }
                    else if(spleaveType.getSelectedItem().toString().equals("Permission Leave")){

                        Toast.makeText(getActivity(), "Permission Leave clicked...!", Toast.LENGTH_SHORT).show();

                        if(spSession.getSelectedItemPosition() == 0) {
                            Toast.makeText(getActivity(), "Please Select Session", Toast.LENGTH_SHORT).show();
                            callToApi();
                        }
                    }
                    else if(spleaveType.getSelectedItem().toString().equals("Sick Leave")){

                        Toast.makeText(getActivity(), "Sick Leave clicked...!", Toast.LENGTH_SHORT).show();
                        callToApi();

                    }else if(spleaveType.getSelectedItem().toString().equals("Unpaid Leave")){

                        Toast.makeText(getActivity(), "Unpaid Leave clicked...!", Toast.LENGTH_SHORT).show();
                        callToApi();
                    }*/

                }

                break;

            case R.id.upload_file_text:
                break;

            case R.id.ivBack:
                assert getActivity() != null;
                getActivity().onBackPressed();
                break;
        }
    }

    private void callToApi() {

        String empId = "";


        String leaveType = spleaveType.getSelectedItem().toString();

        String sessionType = spSession.getSelectedItem().toString();

        if (sessionType.equals("--Select Session--"))
            sessionType = "";

        fromDate = from_date.getText().toString();
        toDate = to_date.getText().toString();

        subject = etSubject.getText().toString();
        message = etMessage.getText().toString();
        doorNo = etDoorNo.getText().toString();
        street = etStreet.getText().toString();
        locality = etLocality.getText().toString();
        landmark = etLandMark.getText().toString();
        pinCode = etPinCode.getText().toString();
        city = etCity.getText().toString();
        state = etState.getText().toString();
        country = etCountry.getText().toString();
        mobileNo = etMobileNo.getText().toString();
        leaveLeadComment = etComment.getText().toString();

        Log.d("DATA_LEAVE", empId + " " + currentDate + " " + currentTime + " " + leaveType + " Pending " + sessionType + " " + fromDate + " " +
                toDate + " " + subject + " " + message + " " + doorNo + " " + locality + " " + landmark + " " + state + " " + city + " " + country + " " + pinCode + " " + mobileNo + " ");

        applyEmployeeLeaveAPI();

    }

    private void getPDF() {

        if (getActivity() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getActivity().getPackageName()));
                startActivity(intent);
                return;
            }

            //creating an intent for file chooser
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(Intent.createChooser(intent, "Select PDFs"), PICK_PDF_CODE);

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();
        Log.d("spinner", "item selected is " + parent.getItemAtPosition(position));

        if (parent.getSelectedItemPosition() == 0) {

            leaveType = "";
            tv_leaveleft.setVisibility(View.GONE);
            from_date.setClickable(false);
            to_date.setClickable(false);
            from_date.setEnabled(false);
            to_date.setEnabled(false);
            attach_layout.setVisibility(View.GONE);
            lL_permission.setVisibility(View.INVISIBLE);
            leave_layout_to_date.setVisibility(View.VISIBLE);
        } else {
            leaveType = spleaveType.getSelectedItem().toString();
            tv_leaveleft.setText(leaveType);
            tv_leaveleft.setVisibility(View.GONE);
            from_date.setClickable(true);
            to_date.setClickable(true);
            from_date.setEnabled(true);
            to_date.setEnabled(true);
            attach_layout.setVisibility(View.GONE);
            lL_permission.setVisibility(View.INVISIBLE);
            leave_layout_to_date.setVisibility(View.VISIBLE);


            /*if (item.equalsIgnoreCase("Permission Leave")) {
                attach_layout.setVisibility(View.GONE);
                lL_permission.setVisibility(View.VISIBLE);
                from_date.setClickable(true);
                from_date.setEnabled(true);
                leave_layout_to_date.setVisibility(View.INVISIBLE);

            } else if (item.trim().equalsIgnoreCase("Vacation Leave")) {
                attach_layout.setVisibility(View.GONE);
                lL_permission.setVisibility(View.INVISIBLE);
                to_date.setClickable(true);
                permission_layout_time.setVisibility(View.GONE);
                from_date.setEnabled(true);
                from_date.setClickable(true);
                to_date.setEnabled(true);
                leave_layout_to_date.setVisibility(View.VISIBLE);
            } else if (item.equals("Sick Leave")){
                attach_layout.setVisibility(View.VISIBLE);
                from_date.setClickable(true);
                to_date.setClickable(true);
                from_date.setEnabled(true);
                to_date.setEnabled(true);
                lL_permission.setVisibility(View.INVISIBLE);
            }*/

            tv_leaveleft.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_CODE && resultCode == Activity.RESULT_OK) {
            //if a file is selected
            if (data.getData() != null) {
                filepathuri = data.getData();
                Log.d("ererwt4", filepathuri.toString() + "");
                upload_file.setText(new SimpleDateFormat("ddMMyyyyhhmm_ss", Locale.getDefault()).format(new Date()) + ".pdf");
            } else {
                Toast.makeText(getContext(), "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void applyEmployeeLeaveAPI() {

        Log.d("MY_RES_DATA", Constant.SCHOOL_ID + " " + Constant.EMPLOYEE_ID + " " + leaveType + " " + fromDate + " " + toDate + " " +
                message + " " + subject + " " + doorNo + " " + locality + " " + landmark + " " + pinCode + " " + city + " " + state + " " + country + " " + mobileNo);

        mApiService.applyEmployeeLeave(Constant.SCHOOL_ID, Constant.EMPLOYEE_BY_ID, leaveType, fromDate, toDate, message, subject,
                doorNo, locality, landmark, pinCode, city, state, country, mobileNo).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful()) {

                    Log.d("MY_RES_SUCC", response.body().toString());

                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = object.getString("status");
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    assert getActivity() != null;
                    getActivity().onBackPressed();

                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    Log.d("MY_RES_FAIL", String.valueOf(response.code()));

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("MY_RES_FAIL", t.getMessage());

            }
        });

    }

    private void getSchoolLeaveBarrierDetailsAPI() {

        spnList.clear();
        progressDialog.show();
        progressDialog.setMessage("Please Wait");
        mApiService.getSchoolLeaveBarrierDetails(Constant.SCHOOL_ID).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("SCHOOL_LEAVE_OBJ", ""+response.code());


                if (response.isSuccessful()) {

                    try {
                        JSONObject obj = new JSONObject(new Gson().toJson(response.body()));
                        Log.d("SCHOOL_LEAVE_OBJ", obj.toString());

                        JSONObject jsonData = obj.getJSONObject("data");
                        Log.d("SCHOOL_LEAVE_DATA", jsonData.toString());

                        Iterator<String> key = jsonData.keys();

                        while (key.hasNext()) {

                            String myKey = key.next();
                            Log.d("SCHOOL_LEAVE_KEY", myKey);

                            JSONObject keyData = jsonData.getJSONObject(myKey);
                            Log.d("SCHOOL_LEAVE_keyData", keyData.toString());

                            String leave_type = keyData.getString("leave_type");
                            String status = keyData.getString("status");

                            Log.d("SCHOOL_LEAVE_KEY_DATA", leave_type + " " + status);

                            if (status.equalsIgnoreCase("true"))
                                spnList.add(leave_type);
                        }

                        setSpinnerForLeaveType();
                        progressDialog.dismiss();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

}
