package in.varadhismartek.patashalaerp.DashboardModule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.GeneralClass.CircleImageView;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_SchoolProfile extends Fragment implements View.OnClickListener {

    private ImageView iv_banner, iv_backBtn;
    CircleImageView iv_logo, iv_pocImage;
    APIService apiService;
    TextView tvTitle, tv_schoolname, address, address1, pincode;
    private String school_banner_image = "", school_logo = "", schoolname = "";
    LinearLayout ll_twitter, ll_facebook, ll_google;
    WebView webView1;
    String Remarks, Website, FaceBook_Link, YouTube, Twitter, LinkedIN, Google_link;
    TextView poc_name, POC_designation, POC_email, POC_mobile;
    String POC_Designation = "", Poc_Name, POC_Mobile_Number, poc_image, POC_Email, Class_From, Class_To, Email_ID;
    TextView tv_year, tv_Registration, tv_organisation_name, tv_schoolId, tv_mobile, tv_phone, tvclass_from, tvclass_to, tv_email;


    String school_id, Mobile_Number, Fixed_Landline_Number, organisation_name, Subscription_number_of_years,
            Organization_Registration_Number, Subscription_End_Date, Subscription_Start_Date;


    public Fragment_SchoolProfile() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_profile, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initview(view);

        getSchoolprofile();
        return view;
    }

    private void initview(View view) {
        apiService = ApiUtils.getAPIService();
        iv_backBtn = view.findViewById(R.id.iv_backBtn);
        iv_banner = view.findViewById(R.id.iv_banner);
        iv_logo = view.findViewById(R.id.iv_logo);
        iv_pocImage = view.findViewById(R.id.iv_pocImage);
        tvTitle = view.findViewById(R.id.tvTitle);
        tv_schoolname = view.findViewById(R.id.tv_schoolname);
        address = view.findViewById(R.id.address);
        address1 = view.findViewById(R.id.address1);
        pincode = view.findViewById(R.id.pincode);
        ll_twitter = view.findViewById(R.id.ll_twitter);
        ll_facebook = view.findViewById(R.id.ll_facebook);
        ll_google = view.findViewById(R.id.ll_google);
        webView1 = view.findViewById(R.id.webView1);

        tv_email = view.findViewById(R.id.tv_email);
        tvclass_from = view.findViewById(R.id.tvclass_from);
        tvclass_to = view.findViewById(R.id.tvclass_to);
        tv_mobile = view.findViewById(R.id.tv_mobile);
        tv_phone = view.findViewById(R.id.tv_phone);
        poc_name = view.findViewById(R.id.poc_name);
        POC_designation = view.findViewById(R.id.POC_designation);
        POC_email = view.findViewById(R.id.POC_email);
        POC_mobile = view.findViewById(R.id.POC_mobile);
        tv_year = view.findViewById(R.id.tv_year);
        tv_Registration = view.findViewById(R.id.tv_Registration);
        tv_schoolId = view.findViewById(R.id.tv_schoolId);
        tv_organisation_name = view.findViewById(R.id.tv_organisation_name);

        tvTitle.setText("School Profile");

        ll_twitter.setOnClickListener(this);
        ll_facebook.setOnClickListener(this);
        ll_google.setOnClickListener(this);
        iv_backBtn.setOnClickListener(this);
    }

    private void getSchoolprofile() {
        Log.d("NOTICE_API_Success", Constant.POC_Mobile_Number + Constant.SCHOOL_ID);
        apiService.get_school_details(Constant.POC_Mobile_Number, Constant.SCHOOL_ID).enqueue(new Callback<Object>() {


            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {

                    JSONObject object = null;
                    try {
                        object = new JSONObject(new Gson().toJson(response.body()));
                        String message = object.getString("message");
                        String status = object.getString("status");

                        if (status.equalsIgnoreCase("Success")) {
                            Log.d("NOTICE_API_Success", object.toString());

                            JSONObject keyData = object.getJSONObject("data");


                            if (keyData.isNull("school_banner_image")) {
                                school_banner_image = "";
                            } else {
                                school_banner_image = keyData.getString("school_banner_image");
                            }

                            if (keyData.isNull("school_logo")) {
                                school_logo = "";
                            } else {
                                school_logo = keyData.getString("school_logo");
                            }


                            if (!school_banner_image.equals("") || !school_banner_image.isEmpty()) {
                                Picasso.with(getActivity())
                                        .load(Constant.IMAGE_LINK + school_banner_image)
                                        .resize(150, 150)
                                        .into(iv_banner);
                            } else {
                                //  holder.studentImage.setBackgroundResource(R.drawable.user_icon);
                            }

                            if (!school_logo.equals("") || !school_logo.isEmpty()) {
                                Picasso.with(getActivity())
                                        .load(Constant.IMAGE_LINK + school_logo)
                                        .into(iv_logo);
                            } else {
                                //  holder.studentImage.setBackgroundResource(R.drawable.user_icon);
                            }
                            String Authorized_Email, Status,
                                    Agreed_Monthly_Charges, Monthly_Charges, Agreement_Number,
                                    Discount_ON_coupon, Agreement_Remarks,
                                    Agreement_Date;

                            tv_schoolname.setText(keyData.getString("organisation_name"));

                            String Address_Line_1, Address_Line_2, City, State, Pincode, Country, Trust_Registration_Number, Authorized_Mobile_Number;

                            Status = keyData.getString("Status");


                            Email_ID = keyData.getString("Email_ID");
                            Class_From = keyData.getString("Class_From");
                            Class_To = keyData.getString("Class_To");


                            Agreed_Monthly_Charges = keyData.getString("Agreed_Monthly_Charges");
                            Agreement_Number = keyData.getString("Agreement_Number");
                            Discount_ON_coupon = keyData.getString("Discount_ON_coupon");
                            Agreement_Remarks = keyData.getString("Agreement_Remarks");
                            Agreement_Date = keyData.getString("Agreement_Date");

                            Monthly_Charges = keyData.getString("Monthly_Charges");
                            Trust_Registration_Number = keyData.getString("Trust_Registration_Number");
                            Authorized_Mobile_Number = keyData.getString("Authorized_Mobile_Number");
                            Authorized_Email = keyData.getString("Authorized_Email");


                            Subscription_number_of_years = keyData.getString("Subscription_number_of_years");
                            Subscription_Start_Date = keyData.getString("Subscription_Start_Date");
                            Subscription_End_Date = keyData.getString("Subscription_End_Date");
                            school_id = keyData.getString("school_id");
                            organisation_name = keyData.getString("organisation_name");
                            Organization_Registration_Number = keyData.getString("Organization_Registration_Number");
                            Mobile_Number = keyData.getString("Mobile_Number");
                            Fixed_Landline_Number = keyData.getString("Fixed_Landline_Number");


                            Remarks = keyData.getString("Remarks");

                            POC_Designation = keyData.getString("POC_Designation");
                            POC_Mobile_Number = keyData.getString("POC_Mobile_Number");
                            Poc_Name = keyData.getString("Poc_Name");
                            POC_Email = keyData.getString("POC_Email");
                            poc_image = keyData.getString("poc_image");


                            Address_Line_1 = keyData.getString("Address_Line_1");
                            Address_Line_2 = keyData.getString("Address_Line_2");
                            Country = keyData.getString("Country");
                            City = keyData.getString("City");
                            State = keyData.getString("State");
                            Pincode = keyData.getString("Pincode");

                            Website = keyData.getString("Website");
                            YouTube = keyData.getString("YouTube");
                            Twitter = keyData.getString("Twitter");
                            Google_link = keyData.getString("Google_link");
                            LinkedIN = keyData.getString("LinkedIN");
                            FaceBook_Link = keyData.getString("FaceBook_Link");

                            address.setText(Address_Line_1 + ", " + Address_Line_2);
                            address1.setText(City + ", " + State);
                            pincode.setText(Pincode + ", " + Country);

                            tv_Registration.setText(Organization_Registration_Number);
                            tv_year.setText(Subscription_number_of_years);
                            tv_organisation_name.setText(organisation_name);
                            tv_schoolId.setText(school_id);
                            tv_phone.setText(Fixed_Landline_Number);
                            tvclass_from.setText(Class_From);
                            tvclass_to.setText(Class_To);
                            tv_mobile.setText(Mobile_Number);
                            tv_email.setText(Email_ID);

                            poc_name.setText(Poc_Name);
                            POC_designation.setText(POC_Designation);
                            POC_email.setText(POC_Email);
                            POC_mobile.setText(POC_Mobile_Number);


                            if (!poc_image.equals("") || !poc_image.isEmpty()) {
                                Picasso.with(getActivity())
                                        .load(Constant.IMAGE_LINK + poc_image)

                                        .into(iv_pocImage);
                            } else {
                                //  holder.studentImage.setBackgroundResource(R.drawable.user_icon);
                            }


                        }
                    } catch (JSONException je) {

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.ll_twitter:
                break;

            case R.id.ll_facebook:
                break;

            case R.id.ll_google:
          /*  WebSettings webSetting = webView1.getSettings();
            webSetting.setBuiltInZoomControls(true);
            webSetting.setJavaScriptEnabled(true);
            webView1.setWebViewClient(new WebViewClient());
            webView1.loadUrl(YouTube);*/
                break;
        }
    }
}
