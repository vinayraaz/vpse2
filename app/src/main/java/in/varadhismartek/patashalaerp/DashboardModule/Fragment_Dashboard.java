package in.varadhismartek.patashalaerp.DashboardModule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.DashboardModule.UpdateAdapter.FragmentDasboardAdapter;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;


public class Fragment_Dashboard extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context context;

    APIService mApiService;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> nameModule = new ArrayList<>();
    ImageView ivSetting, editSetting;
    boolean module_status = false;
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Fragment_Dashboard() {
        // Required empty public constructor
    }


    public static Fragment_Dashboard newInstance(String param1, String param2) {
        Fragment_Dashboard fragment = new Fragment_Dashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__dashboard, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initViews(view);
        initListener();
        //getModule();
        getModuleListInSpinner(nameModule);
        Log.i("MOBILE***",""+ Constant.POC_Mobile_Number);
        return view;
    }


    private void initListener() {

        ivSetting.setOnClickListener(this);
        editSetting.setOnClickListener(this);

    }

    private void initViews(View view) {
        nameModule.clear();
        mApiService = ApiUtils.getAPIService();

        nameModule.add("School Profile");
        nameModule.add("House");
        nameModule.add("Staff");
        nameModule.add("Student");
        nameModule.add("Subject");
        nameModule.add("Leave");
        nameModule.add("Gallery");
        nameModule.add("Homework");
        nameModule.add("Notice Board");
        nameModule.add("Question Bank");
        nameModule.add("Staff Attendance");
        nameModule.add("Student Attendance");

        nameModule.add("School Activity Barriers");
        nameModule.add("Notification");
        nameModule.add("Syllabus");
        nameModule.add("Add Teacher");
        nameModule.add("Birthday");
        nameModule.add("Schedule");
        nameModule.add("Fees");
        nameModule.add("TimeTable");

        // nameModule.add("Visitor");
        // nameModule.add("Attendance");

        recyclerView = view.findViewById(R.id.rcv_ModuleList);
        ivSetting = view.findViewById(R.id.barriers_setting);
        editSetting = view.findViewById(R.id.edit_setting);

        linearLayout     = view.findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.GONE);

    }
    /*private void getModule() {

        mApiService.getModuleList(Constant.SCHOOL_ID).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try{
                    if (response.isSuccessful()){
                        name.clear();
                        Log.i("MAKER_CHECKER",""+response.body());
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = (String) object.get("status");

                        if (status.equalsIgnoreCase("success")) {

                            JSONObject jsonObject1 = object.getJSONObject("data");
                            Iterator<String> keys = jsonObject1.keys();

                            while (keys.hasNext()) {
                                String key = keys.next();

                                JSONObject jsonObjectValue = jsonObject1.getJSONObject(key);
                                Log.d("MAKER_CHECKER_DATA", jsonObjectValue.toString());

                                module_status = jsonObjectValue.getBoolean("active_status");
                                if (module_status){
                                    String module_name = jsonObjectValue.getString("module_name");
                                    name.add(module_name);
                                }
                                getModuleListInSpinner(name);


                            }
                        }

                    }else {

                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.i("SELECTMODULEERROR",""+t.toString());
            }
        });
    }*/

    private void getModuleListInSpinner(ArrayList<String> nameModule) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        FragmentDasboardAdapter fragmentDasboardAdapter = new FragmentDasboardAdapter(getActivity(), nameModule);
        recyclerView.setAdapter(fragmentDasboardAdapter);
        fragmentDasboardAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.barriers_setting:
                Intent intent = new Intent(getActivity(), DashboardSettingActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.edit_setting:
                Intent intentEdit = new Intent(getActivity(), SettingActivity.class);
                getActivity().startActivity(intentEdit);
                break;

        }
    }


}

