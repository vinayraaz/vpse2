package in.varadhismartek.patashalaerp.DashboardModule.UpdateActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.Adapters.RecyclerAdapter;
import in.varadhismartek.patashalaerp.AddDepartment.Data;
import in.varadhismartek.patashalaerp.GeneralClass.CustomSpinnerAdapter;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import in.varadhismartek.patashalaerp.DashboardModule.DashboardActivity;
import in.varadhismartek.patashalaerp.DashboardModule.UpdateRecyclerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDepartmentActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_enter;
    ImageButton add_image;
    Button button_added;
    RecyclerView recycler_view;
    ArrayList<Data> list;
    ArrayList<String> checker;
    ArrayList<String> maker;
    UpdateRecyclerAdapter recyclerAdapter;
    ArrayList<String> checkedArrayList;
    ArrayList<String> unCheckedArrayList;
    Spinner wingsSpinner;
    ImageView ivAddDept, ivBack;
    APIService mApiService;


    ArrayList<String> arrayListWings;


    private ProgressDialog progressDialog = null;
    private String wingsValue;

    boolean wings_status=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_department);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        wingsSpinner = findViewById(R.id.spinnerForTypeOfWings);
        ivAddDept = findViewById(R.id.iv_sendAddDept);
        //ivAddDept.setVisibility(View.GONE);
        ivAddDept.setOnClickListener(this);
        list = new ArrayList<>();
        checker = new ArrayList<>();
        checkedArrayList = new ArrayList<>();
        unCheckedArrayList = new ArrayList<>();
        maker = new ArrayList<>();
        arrayListWings = new ArrayList<>();
        mApiService = ApiUtils.getAPIService();
        edit_enter = findViewById(R.id.edit_enter);
        add_image = findViewById(R.id.add_image);
        button_added = findViewById(R.id.button_added);
        ivBack = findViewById(R.id.iv_backBtn);
        button_added.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        recycler_view = findViewById(R.id.recycler_view);

        setSpinnerForRolesType();
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setHasFixedSize(true);


        //This is for Add the new Selectable values

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addValuesIntoList();
                InputMethodManager imm = (InputMethodManager) UpdateDepartmentActivity.this.getSystemService
                        (Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }

        });



    }

    private void addValuesIntoList() {
        /*String value = "";
        String editvalue = edit_enter.getText().toString();
        if (editvalue.equals("")) {
            Toast.makeText(UpdateDepartmentActivity.this, "Please enter the Value", Toast.LENGTH_SHORT).show();
        } else {

            list.add(new Data(editvalue, true));
            recyclerAdapter = new UpdateRecyclerAdapter(this, list, Constant.DEPARTMENT_FRAG);
            recycler_view.setAdapter(recyclerAdapter);
            edit_enter.setText("");

        }*/
        String value = "";
        String editvalue = edit_enter.getText().toString();
        if (editvalue.equals("")) {
            Toast.makeText(UpdateDepartmentActivity.this, "Please enter the Value", Toast.LENGTH_SHORT).show();
        } else if (wingsSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(UpdateDepartmentActivity.this, "Please select wings", Toast.LENGTH_SHORT).show();
        } else {
            if (list.size() > 0) {
                boolean b = true;
                for (int i = 0; i < list.size(); i++) {
                    if ((list.get(i).getName()).contains(editvalue)) {
                        b = false;
                        Toast.makeText(UpdateDepartmentActivity.this, "Already added", Toast.LENGTH_SHORT).show();
                    }
                }
                if (b) {
                    list.add(new Data(editvalue, true));

                }
            } else {
                list.add(new Data(editvalue, true));
            }
            recyclerAdapter = new UpdateRecyclerAdapter(this, list, Constant.DEPARTMENT_FRAG);
            recycler_view.setAdapter(recyclerAdapter);
            edit_enter.setText("");


        }
    }

    private void setSpinnerForRolesType() {

        arrayListWings.add(0, "Select Wings");
        mApiService.gettingWings(Constant.SCHOOL_ID).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {

                    try {
                        JSONObject json1 = (new JSONObject(new Gson().toJson(response.body())));
                        String status = (String) json1.get("status");

                        if (status.equalsIgnoreCase("Success")) {

                            JSONObject jsonObject1 = json1.getJSONObject("data");
                            Iterator<String> keys = jsonObject1.keys();

                            while (keys.hasNext()) {
                                String key = keys.next();
                                Log.d("jsonSliderKey", key.toString());

                                JSONObject jsonObjectValue = jsonObject1.getJSONObject(key);
                                Log.d("jsonSliderValue", jsonObjectValue.toString());

                                String wing_name = jsonObjectValue.getString("wing_name");
                                wings_status = jsonObjectValue.getBoolean("status");

                                if (wings_status) {
                                    arrayListWings.add(wing_name);
                                }

                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(UpdateDepartmentActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(this, arrayListWings);
        wingsSpinner.setAdapter(customSpinnerAdapter);
        wingsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                JSONArray array = new JSONArray();
                JSONObject jsonObject = new JSONObject();


               // if (position != 0) {
                    wingsValue = parent.getSelectedItem().toString();
                    Constant.wingName = wingsValue;
                    try {
                        array.put(Constant.wingName);
                        jsonObject.put("wings", array);
                        Constant.DEPART_NAME = Constant.wingName;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


            /*    } else {
                    try {
                        Constant.DEPART_NAME = "departments";
                        array.put("All");
                        jsonObject.put("wings", array);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("jsonObject", "" + jsonObject);*/
                checkDepartmentValues(jsonObject);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void checkDepartmentValues(JSONObject jsonObject) {

        if (list.size() != 0) {
            list.clear();
            recyclerAdapter.notifyDataSetChanged();
        }

        mApiService.gettingDept(Constant.SCHOOL_ID, jsonObject).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful()) {
                    try {
                        list.clear();
                        JSONObject json1 = (new JSONObject(new Gson().toJson(response.body())));
                        String status = (String) json1.get("status");
                        if (status.equalsIgnoreCase("Success")) {
                            JSONObject jsonObject1 = json1.getJSONObject("data");

                            JSONObject jsonObject2 = jsonObject1.getJSONObject(Constant.DEPART_NAME);

                            Iterator<String> keys = jsonObject2.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject jsonObjectValue = jsonObject2.getJSONObject(key);
                                String wing_name = jsonObjectValue.getString("name");
                                boolean wings_status = Boolean.parseBoolean(jsonObjectValue.getString("status"));
                                if (wings_status){
                                    list.add(new Data(wing_name, wings_status));
                                }


                                recyclerAdapter = new UpdateRecyclerAdapter(UpdateDepartmentActivity.this, list,
                                        Constant.DEPARTMENT_FRAG);
                                recycler_view.setAdapter(recyclerAdapter);

                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("DEPT_FAIL", String.valueOf(response.code()));
                    if (String.valueOf(response.code()).equals("409")) {
                        Toast.makeText(UpdateDepartmentActivity.this, "No Records", Toast.LENGTH_SHORT).show();
                        list.clear();

                    }
                    if (String.valueOf(response.code()).equals("404")) {
                        Toast.makeText(UpdateDepartmentActivity.this, "No Records", Toast.LENGTH_SHORT).show();
                        list.clear();

                    }

                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backBtn:
                onBackPressed();
                break;
            case R.id.button_added:

                Log.d("liseSize", list.size() + "");

                if (wingsSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(UpdateDepartmentActivity.this, "Please select wings", Toast.LENGTH_SHORT).show();
                } else {

                    //Log.d("WingsDept", wingsValue);
                    for (int i = 0; i < list.size(); i++) {
                        Log.d("deptName", list.get(i).getName());
                        Data data = list.get(i);
                        if (data.isSelect()) {
                            Log.d("deptNameChecked", "" + data.getName());
                        }

                    }

                }

                break;

            case R.id.iv_sendAddDept:


                JSONArray array = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    Data data = list.get(i);
                    if (data.isSelect()) {
                        Log.d("deptNameChecked", "" + data.getName());
                        array.put(data.getName());
                    }
                }

                JSONObject departments = new JSONObject();
                for (int i = 0; i <= list.size(); i++) {

                    JSONObject objDeptments = new JSONObject();

                    try {

                        objDeptments.put("name", array.get(i));
                        objDeptments.put("status", "true");

                        departments.put("" + i, objDeptments);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                JSONObject obj = new JSONObject();
                try {
                    obj.put("departments", departments);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                mApiService.addingDept(Constant.SCHOOL_ID, wingsValue, obj, Constant.EMPLOYEE_BY_ID).enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {

                        if (response.isSuccessful()) {
                            Log.d("askjdhb", "" + response.body());
                            Toast.makeText(UpdateDepartmentActivity.this, "Added Successfully. ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(UpdateDepartmentActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });

                if (list.size() > 0) {
                    Intent in = new Intent(UpdateDepartmentActivity.this, DashboardActivity.class);
                    startActivity(in);
                } else {
                    Toast.makeText(UpdateDepartmentActivity.this, "Please Add Department", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateDepartmentActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
