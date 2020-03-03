package in.varadhismartek.patashalaerp.DashboardModule.TransportModule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.GeneralClass.CustomSpinnerAdapter;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtilsPatashala;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVehicleFragment extends Fragment implements View.OnClickListener {
    APIService apiService;
    private String Tag = "Addvehicle";

    LinearLayout ll_first, ll_Second, ll_Third, ll_Fourth, ll_Fifth, ll_Six, ll_Seven;
    RelativeLayout rl_first, rl_Second, rl_Third, rl_Fourth, rl_Fifth, rl_Six, rl_Seven;
    ImageView ll_imageUp, ll_imageDown;

    private ArrayList<String> vehicletype = new ArrayList<>();
    private ArrayList<String> bodytype = new ArrayList<>();
    private Spinner vehicletypespin, bodytypespin;
    String str_vehicle_type, str_body_type;

    RecyclerView recyclerView, otherdocuments, recyclerfinancial, recyclervehiclefitness;

    TextView btnsave, bt_save, vehicle_id;

    EditText ed_regNo, ed_Name, ed_GpsDetails;

    EditText ed_ChasisNo, ed_EngineNo, ed_makerName, ed_ModelNo, ed_ManuYear, ed_SeatCapacity, ed_RegAutho, ed_RegState, ed_RegisteredDate, ed_PurchaseDate, ed_PreOwnerName, ed_Pre_Owner_PurDate, ed_Pre_Owner_Cost, ed_Pre_Owner_Remarks;


    Switch sw_Service, sw_Insurance,sw_Poll,sw_Fitness,sw_Tax,sw_OtherExp, sw_Veh_Pur;
    EditText ed_Ser_TFee, ed_Ser_RFee, ed_Ser_NextKM, ed_Ser_NextDay, ed_Ser_NextRenewalDate;

    EditText ed_Insurance_Type, ed_Insurance_No, ed_Insurance_Date, ed_Insurance_ReNewDate, ed_Insurance_NextReNewDate;
    EditText ed_Agent_ID, ed_Agent_Name, ed_Agent_CompanyName, ed_Agent_No, ed_Agent_Insur_No;

    EditText ed_Other_Insur_No, ed_Other_Insur_RcNo, ed_Other_Insur_NOCNo,
            ed_Poll_Cer_No, ed_Poll_Cer_RegDate, ed_Poll_Cer_RenewDate, ed_Fit_Cer_no, ed_Fit_Cer_RegDate, ed_Fit_Cer_RenewDate, ed_Tax_No, ed_Tax_RegDate, ed_Tax_Amount;


    EditText ed_Veh_Pur_Price, ed_Veh_Pur_BankName, ed_Veh_Pur_LoanAccountNo,
            ed_Veh_Pur_LoanAmount, ed_Veh_Pur_LoanInstRate, ed_Veh_Pur_DownPay, ed_Veh_Pur_RegDate, ed_Veh_Pur_Date, ed_Veh_Pur_LoanEndDate,
            ed_Veh_Pur_LoanEMI;

    EditText ed_Other_BankName, ed_Other_MainAmount, ed_Other_Reparing_No, ed_Other_Remarks;

    public AddVehicleFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_vehicle, container, false);

        initViews(view);
        initListeners();
        return view;

    }

    private void initListeners() {
        rl_first.setOnClickListener(this);
        rl_Second.setOnClickListener(this);
        rl_Third.setOnClickListener(this);
        rl_Fourth.setOnClickListener(this);
        rl_Fifth.setOnClickListener(this);
        rl_Six.setOnClickListener(this);
        rl_Seven.setOnClickListener(this);
        btnsave.setOnClickListener(this);

    }

    private void initViews(View v) {
        apiService = ApiUtilsPatashala.getService();

        btnsave = v.findViewById(R.id.btnsave);
        ll_first = v.findViewById(R.id.ll_first);
        ll_Second = v.findViewById(R.id.ll_Second);
        ll_Third = v.findViewById(R.id.ll_Third);
        ll_Fourth = v.findViewById(R.id.ll_Fourth);
        ll_Fifth = v.findViewById(R.id.ll_fifth);
        ll_Six = v.findViewById(R.id.ll_Six);
        ll_Seven = v.findViewById(R.id.ll_Seven);


        rl_first = v.findViewById(R.id.rl_first);
        rl_Second = v.findViewById(R.id.rl_Second);
        rl_Third = v.findViewById(R.id.rl_Third);
        rl_Fourth = v.findViewById(R.id.rl_Fourth);
        rl_Fifth = v.findViewById(R.id.rl_fifth);
        rl_Six = v.findViewById(R.id.rl_six);
        rl_Seven = v.findViewById(R.id.rl_Seven);

        ll_imageUp = v.findViewById(R.id.img_drop_down_up);
        ll_imageDown = v.findViewById(R.id.img_drop_down);
        vehicletypespin = v.findViewById(R.id.vehicle_type);

        vehicletypespin = v.findViewById(R.id.vehicle_type);
        bodytypespin = v.findViewById(R.id.bodytype_id);
        vehicle_id = v.findViewById(R.id.vehicle_id);


        ed_regNo = v.findViewById(R.id.ed_regNo);
        ed_Name = v.findViewById(R.id.ed_Name);
        ed_GpsDetails = v.findViewById(R.id.ed_GpsDetails);


        ed_ChasisNo = v.findViewById(R.id.ed_ChasisNo);
        ed_EngineNo = v.findViewById(R.id.ed_EngineNo);
        ed_makerName = v.findViewById(R.id.ed_makerName);
        ed_ModelNo = v.findViewById(R.id.ed_ModelNo);
        ed_ManuYear = v.findViewById(R.id.ed_ManuYear);
        ed_SeatCapacity = v.findViewById(R.id.ed_SeatCapacity);
        ed_RegAutho = v.findViewById(R.id.ed_RegAutho);
        ed_RegState = v.findViewById(R.id.ed_RegState);
        ed_RegisteredDate = v.findViewById(R.id.ed_RegisteredDate);
        ed_PurchaseDate = v.findViewById(R.id.ed_PurchaseDate);
        ed_PreOwnerName = v.findViewById(R.id.ed_PreOwnerName);
        ed_Pre_Owner_PurDate = v.findViewById(R.id.ed_Pre_Owner_PurDate);
        ed_Pre_Owner_Cost = v.findViewById(R.id.ed_Pre_Owner_Cost);
        ed_Pre_Owner_Remarks = v.findViewById(R.id.ed_Pre_Owner_Remarks);


        ed_Ser_TFee = v.findViewById(R.id.ed_Ser_TFee);
        ed_Ser_RFee = v.findViewById(R.id.ed_Ser_RFee);
        ed_Ser_NextKM = v.findViewById(R.id.ed_Ser_NextKM);
        ed_Ser_NextDay = v.findViewById(R.id.ed_Ser_NextDay);
        ed_Ser_NextRenewalDate = v.findViewById(R.id.ed_Ser_NextRenewalDate);
        sw_Service = v.findViewById(R.id.sw_Service);

        ed_Insurance_Type = v.findViewById(R.id.ed_Insurance_Type);
        ed_Insurance_No = v.findViewById(R.id.ed_Insurance_No);
        ed_Insurance_Date = v.findViewById(R.id.ed_Insurance_Date);
        ed_Insurance_ReNewDate = v.findViewById(R.id.ed_Insurance_ReNewDate);
        ed_Insurance_NextReNewDate = v.findViewById(R.id.ed_Insurance_NextReNewDate);
        sw_Insurance = v.findViewById(R.id.sw_Insurance);

        ed_Agent_ID = v.findViewById(R.id.ed_Agent_ID);
        ed_Agent_Name = v.findViewById(R.id.ed_Agent_Name);
        ed_Agent_CompanyName = v.findViewById(R.id.ed_Agent_CompanyName);
        ed_Agent_No = v.findViewById(R.id.ed_Agent_No);
        ed_Agent_Insur_No = v.findViewById(R.id.ed_Agent_Insur_No);


        ed_Other_Insur_No = v.findViewById(R.id.ed_Other_Insur_No);
        ed_Other_Insur_RcNo = v.findViewById(R.id.ed_Other_Insur_RcNo);
        ed_Other_Insur_NOCNo = v.findViewById(R.id.ed_Other_Insur_NOCNo);
        ed_Poll_Cer_No = v.findViewById(R.id.ed_Poll_Cer_No);
        ed_Poll_Cer_RegDate = v.findViewById(R.id.ed_Poll_Cer_RegDate);
        ed_Poll_Cer_RenewDate = v.findViewById(R.id.ed_Poll_Cer_RenewDate);
        sw_Poll = v.findViewById(R.id.sw_Poll);
        ed_Fit_Cer_no = v.findViewById(R.id.ed_Fit_Cer_no);
        ed_Fit_Cer_RegDate = v.findViewById(R.id.ed_Fit_Cer_RegDate);
        ed_Fit_Cer_RenewDate = v.findViewById(R.id.ed_Fit_Cer_RenewDate);
        ed_Tax_RegDate = v.findViewById(R.id.ed_Tax_RegDate);
        ed_Tax_No = v.findViewById(R.id.ed_Tax_No);
        ed_Tax_Amount = v.findViewById(R.id.ed_Tax_Amount);
        sw_Tax = v.findViewById(R.id.sw_Tax);
        sw_Veh_Pur = v.findViewById(R.id.sw_Veh_Pur);
        sw_Fitness = v.findViewById(R.id.sw_Fitness);


        ed_Veh_Pur_Price = v.findViewById(R.id.ed_Veh_Pur_Price);
        ed_Veh_Pur_BankName = v.findViewById(R.id.ed_Veh_Pur_BankName);
        ed_Veh_Pur_LoanAccountNo = v.findViewById(R.id.ed_Veh_Pur_LoanAccountNo);
        ed_Veh_Pur_LoanAmount = v.findViewById(R.id.ed_Veh_Pur_LoanAmount);
        ed_Veh_Pur_LoanInstRate = v.findViewById(R.id.ed_Veh_Pur_LoanInstRate);
        ed_Veh_Pur_DownPay = v.findViewById(R.id.ed_Veh_Pur_DownPay);
        ed_Veh_Pur_RegDate = v.findViewById(R.id.ed_Veh_Pur_RegDate);
        ed_Veh_Pur_Date = v.findViewById(R.id.ed_Veh_Pur_Date);
        ed_Veh_Pur_LoanEndDate = v.findViewById(R.id.ed_Veh_Pur_LoanEndDate);
        ed_Veh_Pur_LoanEMI = v.findViewById(R.id.ed_Veh_Pur_LoanEMI);


        sw_OtherExp = v.findViewById(R.id.sw_OtherExp);
        ed_Other_BankName = v.findViewById(R.id.ed_Other_BankName);
        ed_Other_MainAmount = v.findViewById(R.id.ed_Other_MainAmount);
        ed_Other_Reparing_No = v.findViewById(R.id.ed_Other_Reparing_No);
        ed_Other_Remarks = v.findViewById(R.id.ed_Other_Remarks);


        vehicletype.clear();
        bodytype.clear();

        vehicletype.add("BUS");
        vehicletype.add("AC BUS");
        vehicletype.add("MINI BUS");
        vehicletype.add("TRAVELLER");

        bodytype.add("NEW");
        bodytype.add("SECOND TYPE");

        CustomSpinnerAdapter customSpinnerAdaptervehicle = new CustomSpinnerAdapter(getActivity(), vehicletype, "#717071");
        vehicletypespin.setAdapter(customSpinnerAdaptervehicle);
        vehicletypespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_vehicle_type = parent.getItemAtPosition(position).toString();
                Log.d(Tag, str_vehicle_type);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomSpinnerAdapter customSpinnerAdapterbody = new CustomSpinnerAdapter(getActivity(), bodytype, "#717071");
        bodytypespin.setAdapter(customSpinnerAdapterbody);
        bodytypespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_body_type = parent.getItemAtPosition(position).toString();
                Log.d(Tag, str_body_type);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_first:
                if (ll_first.getVisibility() == View.VISIBLE) {

                    ll_first.setVisibility(View.GONE);
                    ll_imageUp.setVisibility(View.GONE);
                    ll_imageDown.setVisibility(View.VISIBLE);
                } else {
                    ll_first.setVisibility(View.VISIBLE);

                    ll_imageUp.setVisibility(View.VISIBLE);
                    ll_imageDown.setVisibility(View.GONE);
                }
                break;
            case R.id.rl_Second:
                if (ll_Second.getVisibility() == View.VISIBLE) {
                    ll_Second.setVisibility(View.GONE);
                } else {
                    ll_Second.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.rl_Third:
                if (ll_Third.getVisibility() == View.VISIBLE) {
                    ll_Third.setVisibility(View.GONE);
                } else {
                    ll_Third.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.rl_Fourth:
                if (ll_Fourth.getVisibility() == View.VISIBLE) {
                    ll_Fourth.setVisibility(View.GONE);
                } else {
                    ll_Fourth.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.rl_fifth:
                if (ll_Fifth.getVisibility() == View.VISIBLE) {
                    ll_Fifth.setVisibility(View.GONE);
                } else {
                    ll_Fifth.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.rl_six:
                if (ll_Six.getVisibility() == View.VISIBLE) {
                    ll_Six.setVisibility(View.GONE);
                } else {
                    ll_Six.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.rl_Seven:
                if (ll_Seven.getVisibility() == View.VISIBLE) {
                    ll_Seven.setVisibility(View.GONE);
                } else {
                    ll_Seven.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.btnsave:
                addVehicleAPI();
                break;

        }
    }

    private void addVehicleAPI() {

  /*      school_id:25a6ef3f-944c-4a18-ab06-535324e14f9f
        class_of_vehicle:Bus
        vehicle_id:ABCDEFR
        registration_certificate_no:53454

        GPS_Details:NA
        type_of_body:First Hand
        chassis_number:4355
        engine_number:6566
        maker_name:Force
        model_number:123AWR55

       @Field("insurance_type") String insurance_type,
        @Field("insurance_number") String insurance_number,
        @Field("insurance_date") String insurance_date,
        @Field("renewed_date") String renewed_date,
        @Field("next_renewal_date") String next_renewal_date,
        @Field("send_insurance_renewal_alert") String send_insurance_renewal_alert,
        @Field("agent_id") String agent_id,
        @Field("agent_name") String agent_name,

        @Field("insurance_company_name") String insurance_company_name,
        @Field("agent_contact_number") String agent_contact_number,
        @Field("insurance_company_contact_number") String insurance_company_contact_number,
        @Field("rc_number") String rc_number,
        @Field("NOC_number") String NOC_number,

                @Field("pollution_certificate_no") String pollution_certificate_no,
        @Field("pollution_certification_issue_date") String pollution_certification_issue_date,
        @Field("pollution_renewal_date") String pollution_renewal_date,
        @Field("send_pc_renewal_alert") String send_pc_renewal_alert,
        @Field("fitness_certificate_no") String fitness_certificate_no,
        @Field("fitness_certification_issue_date") String fitness_certification_issue_date,

         @Field("fitness_renewal_date") String fitness_renewal_date,
        @Field("send_fc_renewal_alert") String send_fc_renewal_alert,
        @Field("tax_permit_no") String tax_permit_no,
        @Field("tax_payable_date") String tax_payable_date,
        @Field("tax_permit_amount") String tax_permit_amount,
        @Field("loan_details") String loan_details,
        @Field("other_expenses") String other_expenses,
        @Field("added_employeeid") String added_employeeid
   */

       /* EditText ed_regNo,ed_Name,ed_GpsDetails;

        EditText ed_ChasisNo,ed_EngineNo,ed_makerName,ed_ModelNo
                ,ed_ManuYear,ed_SeatCapacity,ed_RegAutho,ed_RegState,ed_RegisteredDate
                ,ed_PurchaseDate,ed_PreOwnerName,ed_Pre_Owner_PurDate, ed_Pre_Owner_Cost,ed_Pre_Owner_Remarks;

          EditText ed_Ser_TFee, ed_Ser_RFee,ed_Ser_NextKM, ed_Ser_NextDay, ed_Ser_NextRenewalDate;
Switch sw_Service,sw_Insurance,sw_OtherExp,sw_Veh_Pur,sw_Fitness;

 EditText ed_Insurance_Type, ed_Insurance_No, ed_Insurance_Date, ed_Insurance_ReNewDate, ed_Insurance_NextReNewDate;

    EditText ed_Agent_ID,ed_Agent_Name,ed_Agent_CompanyName,ed_Agent_No,ed_Agent_Insur_No;

    EditText ed_Other_Insur_No,ed_Other_Insur_RcNo,ed_Other_Insur_NOCNo,

            ed_Poll_Cer_No,ed_Poll_Cer_RegDate,ed_Poll_Cer_RenewDate,sw_Poll
            ,ed_Fit_Cer_no,ed_Fit_Cer_RegDate,ed_Fit_Cer_RenewDate
            ,ed_Tax_No,ed_Tax_RegDate,ed_Tax_Amount,sw_Tax;

   EditText ed_Veh_Pur_Price, ed_Veh_Pur_BankName, ed_Veh_Pur_LoanAccountNo,
            ed_Veh_Pur_LoanAmount, ed_Veh_Pur_LoanInstRate, ed_Veh_Pur_DownPay, ed_Veh_Pur_RegDate, ed_Veh_Pur_Date, ed_Veh_Pur_LoanEndDate,
            ed_Veh_Pur_LoanEMI;
        */





        String str_ed_regNo, str_ed_GpsDetails, str_ed_ChasisNo, str_ed_EngineNo, str_ed_makerName,
                str_ed_ModelNo, str_ed_ManuYear, str_ed_SeatCapacity, str_ed_RegAutho, str_ed_RegState,
                str_ed_RegisteredDate="2019-12-10", str_ed_PurchaseDate="1998-10-10", str_ed_Ser_TFee, str_ed_Ser_RFee,
                str_ed_Ser_NextKM, str_ed_Ser_NextDay, str_ed_Ser_NextRenewalDate="1990-10-12", str_sw_Service;

        String str_ed_Insurance_Type, str_ed_Insurance_No, str_ed_Insurance_Date="1999-10-10",
                str_ed_Insurance_ReNewDate="2020-10-10",
                str_ed_Insurance_NextReNewDate="2020-10-01", str_sw_Insurance, str_ed_Agent_ID, str_ed_Agent_Name,
                str_ed_Agent_CompanyName, str_ed_Agent_No, str_ed_Agent_Insur_No, str_ed_Other_Insur_RcNo,
                str_ed_Other_Insur_NOCNo, str_ed_Poll_Cer_No;

        String str_ed_Poll_Cer_RegDate="2020-01-01", str_ed_Poll_Cer_RenewDate="2020-01-03", str_sw_Poll
                , str_ed_Fit_Cer_no, str_ed_Fit_Cer_RegDate="2019-01-02",
                str_ed_Fit_Cer_RenewDate="2019-05-20", str_sw_Fitness, str_ed_Tax_No, str_ed_Tax_RegDate="2019-01-05",
                str_ed_Tax_Amount, str_sw_Veh_Pur, str_sw_OtherExp;

        str_ed_Insurance_Type = ed_Insurance_Type.getText().toString();
        str_ed_Insurance_No = ed_Insurance_No.getText().toString();
       // str_ed_Insurance_Date = ed_Insurance_Date.getText().toString();
       // str_ed_Insurance_ReNewDate = ed_Insurance_ReNewDate.getText().toString();
       // str_ed_Insurance_NextReNewDate = ed_Insurance_NextReNewDate.getText().toString();


        str_ed_Agent_ID = ed_Agent_ID.getText().toString();
        str_ed_Agent_Name = ed_Agent_Name.getText().toString();
        str_ed_Agent_CompanyName = ed_Agent_CompanyName.getText().toString();
        str_ed_Agent_No = ed_Agent_No.getText().toString();
        str_ed_Agent_Insur_No = ed_Agent_Insur_No.getText().toString();

        str_ed_Other_Insur_RcNo = ed_Other_Insur_RcNo.getText().toString();
        str_ed_Other_Insur_NOCNo = ed_Other_Insur_NOCNo.getText().toString();
        str_ed_Poll_Cer_No = ed_Poll_Cer_No.getText().toString();
      //  str_ed_Poll_Cer_RegDate = ed_Poll_Cer_RegDate.getText().toString();
      //  str_ed_Poll_Cer_RenewDate = ed_Poll_Cer_RenewDate.getText().toString();

        str_ed_Fit_Cer_no = ed_Fit_Cer_no.getText().toString();
       // str_ed_Fit_Cer_RegDate = ed_Fit_Cer_RegDate.getText().toString();
       // str_ed_Fit_Cer_RenewDate = ed_Fit_Cer_RenewDate.getText().toString();



        str_ed_Tax_No = ed_Tax_No.getText().toString();
       // str_ed_Tax_RegDate = ed_Tax_RegDate.getText().toString();
        str_ed_Tax_Amount = ed_Tax_Amount.getText().toString();


        str_ed_regNo = ed_regNo.getText().toString();
        str_ed_GpsDetails = ed_GpsDetails.getText().toString();
        str_ed_ChasisNo = ed_ChasisNo.getText().toString();
        str_ed_EngineNo = ed_EngineNo.getText().toString();
        str_ed_makerName = ed_makerName.getText().toString();
        str_ed_ModelNo = ed_ModelNo.getText().toString();
        str_ed_ManuYear = ed_ManuYear.getText().toString();
        str_ed_SeatCapacity = ed_SeatCapacity.getText().toString();
        str_ed_RegAutho = ed_RegAutho.getText().toString();
        str_ed_RegState = ed_RegState.getText().toString();
       // str_ed_RegisteredDate = ed_RegisteredDate.getText().toString();
       // str_ed_PurchaseDate = ed_PurchaseDate.getText().toString();

        str_ed_Ser_TFee = ed_Ser_TFee.getText().toString();
        str_ed_Ser_RFee = ed_Ser_RFee.getText().toString();
        str_ed_Ser_NextKM = ed_Ser_NextKM.getText().toString();
        str_ed_Ser_NextDay = ed_Ser_NextDay.getText().toString();
      //  str_ed_Ser_NextRenewalDate = ed_Ser_NextRenewalDate.getText().toString();
        str_sw_Service = "true";
        str_sw_Insurance = "true";
        str_sw_Poll = "true";
        str_sw_Fitness = "true";
        str_sw_Veh_Pur = "true";
        str_sw_OtherExp = "true";


        System.out.println("Res*Error******1"+Constant.SCHOOL_ID);
        System.out.println("Res*Error******2"+ str_ed_regNo+"**"+ str_ed_GpsDetails+"**"+ str_body_type+"**"+
                str_ed_ChasisNo+"**"+ str_ed_EngineNo+"**"+ str_ed_makerName+"**"+ str_ed_ModelNo+"**"+ str_ed_ManuYear+"**"+
                str_ed_SeatCapacity+"**"+ str_ed_RegAutho+"**"+ str_ed_RegState+"**"+ str_ed_RegisteredDate+"**"+ str_ed_PurchaseDate);

        System.out.println("Res*Error******3"+ str_ed_Ser_TFee+"**"+ str_ed_Ser_RFee+"**"+ str_ed_Ser_NextKM+"**"+
                        str_ed_Ser_NextDay+"**"+str_ed_Ser_NextRenewalDate+"**"+ str_sw_Service);

        System.out.println("Res*Error******4"+ str_ed_Insurance_Type+"**"+ str_ed_Insurance_No+"**"+ str_ed_Insurance_Date+"**"+
                        str_ed_Insurance_ReNewDate+"**"+ str_ed_Insurance_NextReNewDate+"**"+  str_sw_Insurance);


        /*Switch sw_Service, sw_Insurance,sw_Poll,sw_Fitness,sw_Tax,sw_OtherExp, sw_Veh_Pur;*/

        apiService.addVehicle(Constant.SCHOOL_ID, str_vehicle_type, "ASDFGHJK", str_ed_regNo, str_ed_GpsDetails, str_body_type,
                str_ed_ChasisNo, str_ed_EngineNo, str_ed_makerName, str_ed_ModelNo, str_ed_ManuYear, str_ed_SeatCapacity, str_ed_RegAutho,
                str_ed_RegState, str_ed_RegisteredDate, str_ed_PurchaseDate,"3000",

                str_ed_Ser_TFee, str_ed_Ser_RFee, str_ed_Ser_NextKM, str_ed_Ser_NextDay,str_ed_Ser_NextRenewalDate, str_sw_Service,
                str_ed_Insurance_Type, str_ed_Insurance_No, str_ed_Insurance_Date, str_ed_Insurance_ReNewDate,
                str_ed_Insurance_NextReNewDate, str_sw_Insurance,

                str_ed_Agent_ID, str_ed_Agent_Name, str_ed_Agent_CompanyName, str_ed_Agent_No, str_ed_Agent_Insur_No,
                str_ed_Other_Insur_RcNo, str_ed_Other_Insur_NOCNo, str_ed_Poll_Cer_No, str_ed_Poll_Cer_RegDate,
                str_ed_Poll_Cer_RenewDate, str_sw_Poll,

                str_ed_Fit_Cer_no, str_ed_Fit_Cer_RegDate,str_ed_Fit_Cer_RenewDate, str_sw_Fitness,
                str_ed_Tax_No, str_ed_Tax_RegDate, str_ed_Tax_Amount, str_sw_Veh_Pur,
                str_sw_OtherExp, Constant.EMPLOYEE_BY_ID


                ).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                System.out.println("Res*"+response.body());
                System.out.println("Res*"+response.raw()+response.code());
                Log.i("Trans_addd",""+response.code()+response.raw()+response.body());

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println("ReErrors*"+t.toString());

            }
        });

    }
}


/*school_id:55e9cd8c-052a-46b1-b81c-14f85e11a8fe
class_of_vehicle:Bus
vehicle_id:3
registration_certificate_no:2
GPS_Details:g
type_of_body:First Hand
chassis_number:23
engine_number:24
maker_name:xyz
model_number:fnd
manufacturing_year:1970
seating_capacity:23
registering_authority:authority
registering_state:state
registered_date:2017-3-5
purchase_date:2019-6-7
purchase_cost:300000
previous_owner_name:abc
previous_owner_purchase_date:2018-4-5
previous_owner_purchase_cost:340000
prev_owner_related_other_details:no
free_service:3
remaining_service:2
next_service_in_kms:1200
next_service_in_days:30
last_servicing_date:2019-8-8
send_service_alert:true
insurance_type:def
insurance_number:3
insurance_date:2019-6-23
renewed_date:2020-4-5
next_renewal_date:2023-4-5
send_insurance_renewal_alert:false
agent_id:6
agent_name:xyz
insurance_company_name:hjk
agent_contact_number:12
insurance_company_contact_number:45
rc_number:f88n
NOC_number:n4hj24
pollution_certificate_no:n 5j45h
pollution_certification_issue_date:2019-6-23
pollution_renewal_date:2020-4-5
send_pc_renewal_alert:false
fitness_certificate_no:mnm4543
fitness_certification_issue_date:2019-6-23
fitness_renewal_date:2020-4-5
send_fc_renewal_alert:false
tax_permit_no:nm355
tax_payable_date:2020-4-5
tax_permit_amount:500
loan_details:false
bank_name:jk45n5
loan_account_no:j54k5
loan_amount:200000
loan_interest_rate:12.0
downpayment:200000
loan_approval_date:2019-6-23
loan_due_date:2020-4-5
loan_end_date:2021-5-6
loan_EMI_amount:24000
other_expenses:false
maintenance_amount:23000
repairing_charges:30000
remarks:rfgrf
added_employeeid:067df568-106d-4361-b47d-ad504eb36e7e*/

/*school_id:25a6ef3f-944c-4a18-ab06-535324e14f9f
class_of_vehicle:Bus
vehicle_id:ABCDEFR
registration_certificate_no:53454
GPS_Details:NA
type_of_body:First Hand
chassis_number:4355
engine_number:6566
maker_name:Force
model_number:123AWR55
manufacturing_year:2015
seating_capacity:23
registering_authority:authority
registering_state:state
registered_date:2017-03-05
purchase_date:2019-06-07
purchase_cost:300000

//previous_owner_name:
//previous_owner_purchase_date:
//previous_owner_purchase_cost:
//prev_owner_related_other_details:

free_service:3
remaining_service:2
next_service_in_kms:1200
next_service_in_days:30
last_servicing_date:2019-08-08
send_service_alert:true

insurance_type:def
insurance_number:3
insurance_date:2019-06-23
renewed_date:2020-04-05
next_renewal_date:2023-04-05
send_insurance_renewal_alert:false

agent_id:6
agent_name:xyz
insurance_company_name:hjk
agent_contact_number:12
insurance_company_contact_number:45

rc_number:78886
NOC_number:86999
pollution_certificate_no:57858
pollution_certification_issue_date:2019-06-23
pollution_renewal_date:2020-04-05
send_pc_renewal_alert:false


fitness_certificate_no:67869
fitness_certification_issue_date:2019-06-23
fitness_renewal_date:2020-04-05
send_fc_renewal_alert:false

tax_permit_no:8997t9
tax_payable_date:2020-04-05
tax_permit_amount:500
loan_details:false
//bank_name:
//loan_account_no:
//loan_amount:
//loan_interest_rate:
//downpayment:
//loan_approval_date:
//loan_due_date:
//loan_end_date:
//loan_EMI_amount:
other_expenses:false
//maintenance_amount:23000
//repairing_charges:30000
//remarks:None
added_employeeid:55a6cd5b-3a01-4043-8d52-c4156289422d*/