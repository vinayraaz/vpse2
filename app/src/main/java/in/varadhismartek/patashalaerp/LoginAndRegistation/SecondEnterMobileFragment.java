package in.varadhismartek.patashalaerp.LoginAndRegistation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.Interface.RetroFitInterface;
import in.varadhismartek.patashalaerp.R;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SecondEnterMobileFragment extends Fragment implements View.OnClickListener {
    EditText edt_mobile_username, password, mobile_number;
    Button btn_next, get_otp;
    TextView tv_step_number;
    ImageView img_type_people;
    TextView forgot_password;
    CardView user_mobile, enter_mobile;
    String user_name, pasword, mobil_number;
    ProgressDialog dialog;
    CountryCodePicker countryCodePicker;
    AlertDialog aDialog;
    RetroFitInterface retroFitInterface;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;



    public SecondEnterMobileFragment() {
        // Required empty public constructor
    }

    public static SecondEnterMobileFragment newInstance(String param1, String param2) {
        SecondEnterMobileFragment fragment = new SecondEnterMobileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enter_mobile__number_, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        retroFitInterface = RetrofitInstantClient.getRetrofitInstance().create(RetroFitInterface.class);
        preferences = getActivity().getSharedPreferences("login_pref",Context.MODE_PRIVATE);
        editor = preferences.edit();
        initViews(view);

        setStepOneSelected();
        CheckAudienceType(Constant.audience_type);
        return view;


    }

    public void initViews(View view) {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.please_wait));
        edt_mobile_username = view.findViewById(R.id.edit_username);
        password = view.findViewById(R.id.edit_enter);
        forgot_password = view.findViewById(R.id.tv_forgot_password);
        user_mobile = view.findViewById(R.id.username_mobile);
        enter_mobile = view.findViewById(R.id.cv_reset_mobile_no);
        btn_next = view.findViewById(R.id.login_for_management_fab);
        tv_step_number = view.findViewById(R.id.number1);
        img_type_people = view.findViewById(R.id.img_managament_logo);
        get_otp = view.findViewById(R.id.btn_get_otp);
        mobile_number = view.findViewById(R.id.edt_mobile_number);
        forgot_password.setOnClickListener(this);
        get_otp.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        countryCodePicker = view.findViewById(R.id.ccp_otp);


    }

    public void setStepOneSelected() {
        tv_step_number.setBackgroundResource(R.drawable.step_selected);

    }

    public void CheckAudienceType(String audience_type) {
        switch (audience_type) {

            case "Management":
                img_type_people.setImageResource(R.drawable.management_logo);
                break;
            case "Staff":
                img_type_people.setImageResource(R.drawable.staff_logo);
                break;
            case "Parent":
                img_type_people.setImageResource(R.drawable.parent_logo);
                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.login_for_management_fab:
                user_name = edt_mobile_username.getText().toString().trim();
                pasword = password.getText().toString();
                if (user_name.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.please_enter_your_registered_mobile_no), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pasword.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.please_enter_your_registered_mobile_no), Toast.LENGTH_SHORT).show();
                    return;
                }
                //Check Authentication
                dialog.setMessage(getString(R.string.authenticating_please_wait));
                dialog.show();


                Log.d("Response", "onResponse: " + user_name + "sajdhsjka" + pasword);

                Call<JsonElement> call = retroFitInterface.loginWithPassword(user_name, pasword);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                                String str = jsonObject.getString(Constant.STATUS);
                                Log.d("response", "" + response.body());

                                Constant.SCHOOL_ID=jsonObject.getJSONObject("data")
                                        .getString("school_id");
                                Constant.POC_NAME=jsonObject.getJSONObject("data")
                                        .getString("Poc_Name");
                                Constant.POC_Mobile_Number=jsonObject.getJSONObject("data")
                                        .getString("POC_Mobile_Number");

                                Log.d("NUMBERRRRRRRRRRRR", "" +  Constant.POC_Mobile_Number);


                                Call<JsonElement> call1 = retroFitInterface.getEmployeeId(Constant.SCHOOL_ID,
                                        Constant.POC_Mobile_Number, Constant.POC_NAME);
                                call1.enqueue(new Callback<JsonElement>() {
                                    @Override
                                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                                        if (response.isSuccessful()) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                                                String str = jsonObject.getString(Constant.STATUS);
                                                Log.d("response11", "" + response.body());
                                                if (str.equalsIgnoreCase("Success")){

                                                 Constant.EMPLOYEE_BY_ID = jsonObject.getJSONObject("data").getString
                                                   ("employee_uuid");
                                                    Constant.SCHOOL_ID=jsonObject.getJSONObject("data").getString("schoolid");


                                                    Log.d("responseSSSS", "" +  Constant.SCHOOL_ID);


                                                    editor.putString(Constant.SCHOOL_ID,Constant.SCHOOL_ID);
                                                    editor.putString(Constant.EMPLOYEE_BY_ID,Constant.EMPLOYEE_BY_ID);
                                                    editor.putString(Constant.POC_NAME,Constant.POC_NAME);
                                                    editor.putString(Constant.POC_Mobile_Number,Constant.POC_Mobile_Number);
                                                    editor.commit();

                                                }
                                            } catch (JSONException j) {

                                            }
                                        }

                                        Log.d("responseSSSS11", "" +  Constant.POC_Mobile_Number);
                                    }

                                    @Override
                                    public void onFailure(Call<JsonElement> call, Throwable t) {

                                    }
                                });


                               Constant.SCHOOL_ID = preferences.getString(Constant.SCHOOL_ID,"");
                               Constant.EMPLOYEE_BY_ID = preferences.getString(Constant.EMPLOYEE_BY_ID,"");
                               Constant.POC_NAME = preferences.getString(Constant.POC_NAME,"");
                               Constant.POC_Mobile_Number = preferences.getString(Constant.POC_Mobile_Number,"");
                                Log.d("empId**ID", "" +  Constant.EMPLOYEE_BY_ID+Constant.POC_Mobile_Number);

                                switch (str) {
                                    case Constant.SUCCESS:
                                        dialog.dismiss();
                                        LoginScreenActivity loginScreenActivity2 = (LoginScreenActivity) getActivity();
                                        Constant.poc_mob = jsonObject.getJSONObject("data").get("POC_Mobile_Number").toString();

                                        InputMethodManager imm = (InputMethodManager)getActivity().
                                                getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                        Log.d("poc", user_name);
                                        assert loginScreenActivity2 != null;
                                        loginScreenActivity2.loadFragment(Constant.FOURTH_MPIN_FRAGMENT, null);

                                        break;
                                    case Constant.MOBILE_DOESNOT_EXIST:
                                        dialog.dismiss();
                                        new AlertDialog.Builder(getContext())
                                                .setTitle(getString(R.string.oops))
                                                .setMessage(getString(R.string.mobile_not_registered))
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })

                                                // A null listener allows the button to dismiss the dialog and take no further action.
                                                .setNegativeButton(android.R.string.no, null)
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                                        break;
                                    case "Wrong password":
                                        dialog.dismiss();
                                        new AlertDialog.Builder(getContext())
                                                .setTitle(getString(R.string.oops))
                                                .setMessage("You Entered wrong password")
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                                // A null listener allows the button to dismiss the dialog and take no further action.
                                                .setNegativeButton(android.R.string.no, null)
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                                        break;
                                    default:
                                        dialog.dismiss();
                                        new AlertDialog.Builder(getContext())
                                                .setTitle(getString(R.string.oops))
                                                .setMessage(getString(R.string.something_is_wrong))
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                                // A null listener allows the button to dismiss the dialog and take no further action.
                                                .setNegativeButton(android.R.string.no, null)
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                                        break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        else {

                            Toast.makeText(getActivity(), R.string.inValid_username_pass, Toast.LENGTH_SHORT).show();
                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            dialog.dismiss();

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), R.string.inValid_username_pass, Toast.LENGTH_SHORT).show();
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                    }
                });
                //next button is pressed take action
                break;
            case R.id.tv_forgot_password:

                user_mobile.setVisibility(View.GONE);
                enter_mobile.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_get_otp:
                Log.d("ccp", countryCodePicker.getSelectedCountryCodeWithPlus());
                mobil_number = mobile_number.getText().toString();
                if (mobil_number.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.please_enter_your_registered_mobile_no), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mobil_number.length() != 10) {
                    Toast.makeText(getContext(), getString(R.string.please_enter_your_registered_mobile_no), Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.setMessage(getString(R.string.please_wait));
                dialog.show();
                Call<JsonElement> call2 = retroFitInterface.forgot_pass(mobil_number);
                call2.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                            String str = jsonObject.getString(Constant.STATUS);
                            Log.d("response", "" + response.body());
                            switch (str) {
                                case Constant.SUCCESS:
                                    dialog.dismiss();
                                    Constant.mobile_number = mobil_number;
                                    LoginScreenActivity loginScreenActivity2 = (LoginScreenActivity) getActivity();
                                    assert loginScreenActivity2 != null;
                                    loginScreenActivity2.loadFragment(Constant.THIRD_OTP_ENTER_FRAGMENT, null);
                                    break;
                                case Constant.MOBILE_DOESNOT_EXIST:
                                    dialog.dismiss();
                                    new AlertDialog.Builder(getContext())
                                            .setTitle(getString(R.string.oops))
                                            .setMessage(getString(R.string.mobile_not_registered))
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })

                                            // A null listener allows the button to dismiss the dialog and take no further action.
                                            .setNegativeButton(android.R.string.no, null)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                    break;
                                default:
                                    dialog.dismiss();
                                    new AlertDialog.Builder(getContext())
                                            .setTitle(getString(R.string.oops))
                                            .setMessage(getString(R.string.something_is_wrong))
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            // A null listener allows the button to dismiss the dialog and take no further action.
                                            .setNegativeButton(android.R.string.no, null)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                    break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
                break;

        }
    }

    public static class RetrofitInstantClient {
        private static Retrofit retrofit;
        private static final String BASE_URL = "http://13.233.109.165:8000/school/";

        public static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL).client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }
}
