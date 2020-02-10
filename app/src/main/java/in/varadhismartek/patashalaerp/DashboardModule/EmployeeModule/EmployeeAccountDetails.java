package in.varadhismartek.patashalaerp.DashboardModule.EmployeeModule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtilsPatashala;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAccountDetails extends Fragment {
    APIService apiServicePataShala;
    String employeeID;
    String accountStatus, employee_uuid, employee_image, employee_lastname, ESI_number,
            bank_name, account_number, salary, employee_firstname, employee_customid,
            employee_role, branch_name, PF_number, IFSC_code, account_holder_name;

    public EmployeeAccountDetails() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_view, container, false);
        initView(view);
        initListener();
        getBundle();
        getEmployeeAccountDetails();
        return view;
    }

    private void getBundle() {
        Bundle bundle = getArguments();

        employeeID = bundle.getString("EMPUUID");
        Constant.EMPLOYEE_ID = employeeID;
        Log.i("Constant*EMPLOYEE_ID1", Constant.EMPLOYEE_ID);


    }

    private void getEmployeeAccountDetails() {
        apiServicePataShala.getEmployeeAccountDetails(Constant.SCHOOL_ID, Constant.EMPLOYEE_ID).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = object.getString("status");
                        String message = object.getString("message");

                        if (status.equalsIgnoreCase("Success")) {

                            JSONArray jsonData = object.getJSONArray("data");
                            for (int i = 0; i < jsonData.length(); i++) {
                                JSONObject jsonObject = jsonData.getJSONObject(i);

                                if (!jsonObject.isNull("status")) {
                                    accountStatus = jsonObject.getString("status");
                                }
                                if (!jsonObject.isNull("employee_uuid")) {
                                    employee_uuid = jsonObject.getString("employee_uuid");
                                }
                                if (!jsonObject.isNull("employee_image")) {
                                    employee_image = jsonObject.getString("employee_image");
                                }
                                if (!jsonObject.isNull("employee_lastname")) {
                                    employee_lastname = jsonObject.getString("employee_lastname");
                                }
                                if (!jsonObject.isNull("ESI_number")) {
                                    ESI_number = jsonObject.getString("ESI_number");
                                }

                                if (!jsonObject.isNull("bank_name")) {
                                    bank_name = jsonObject.getString("bank_name");
                                }
                                if (!jsonObject.isNull("account_number")) {
                                    account_number = jsonObject.getString("account_number");
                                }
                                if (!jsonObject.isNull("salary")) {
                                    salary = jsonObject.getString("salary");
                                }
                                if (!jsonObject.isNull("employee_firstname")) {
                                    employee_firstname = jsonObject.getString("employee_firstname");
                                }
                                if (!jsonObject.isNull("employee_customid")) {
                                    employee_customid = jsonObject.getString("employee_customid");
                                }
                                if (!jsonObject.isNull("employee_role")) {
                                    employee_role = jsonObject.getString("employee_role");
                                }
                                if (!jsonObject.isNull("branch_name")) {
                                    branch_name = jsonObject.getString("branch_name");
                                }
                                if (!jsonObject.isNull("PF_number")) {
                                    PF_number = jsonObject.getString("PF_number");
                                }
                                if (!jsonObject.isNull("IFSC_code")) {
                                    IFSC_code = jsonObject.getString("IFSC_code");
                                }
                                if (!jsonObject.isNull("account_holder_name")) {
                                    account_holder_name = jsonObject.getString("account_holder_name");
                                }


                            }

                        }
                    } catch (JSONException je) {
                        je.printStackTrace();
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
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void initView(View view) {
        apiServicePataShala = ApiUtilsPatashala.getService();


    }

    private void initListener() {

    }
}
