package in.varadhismartek.patashalaerp.DashboardModule.VisitorModule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtilsPatashala;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Visitor extends Fragment implements View.OnClickListener {
    APIService apiService;
    RecyclerView recycler_view;
    FloatingActionButton fab_button;
    TextView tvTitle;
    ImageView iv_backBtn;

    String visitor_name = "", visitor_photo = "", entry_date = "", visitor_uuid = "", visitor_id = "",
            entry_time = "", purpose = "", added_datetime = "";
    ArrayList<VisitorModule> visitorModules = new ArrayList<>();

    public Fragment_Visitor() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visitor_list, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initViews(view);
        initListener();
        getvisitor();

        return view;
    }

    private void getvisitor() {
        apiService.getVisitor("55e9cd8c-052a-46b1-b81c-14f85e11a8fe").enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.i("RESP***", "" + response.body() + response.code());

                if (response.isSuccessful()) {
                    visitorModules.clear();
                    try {

                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = object.getString("status");

                        if (status.equalsIgnoreCase("Success")) {

                            JSONObject jsonData = object.getJSONObject("data");
                            Log.d("ADMIN_API_DATA", jsonData.toString());

                            Iterator<String> key = jsonData.keys();

                            while (key.hasNext()) {

                                String myKey = key.next();
                                JSONObject keyData = jsonData.getJSONObject(myKey);


                                if (keyData.isNull("visitor_name")) {
                                    visitor_name = "";
                                } else {
                                    visitor_name = keyData.getString("visitor_name");
                                }

                                if (keyData.isNull("visitor_photo")) {
                                    visitor_photo = "";
                                } else {
                                    visitor_photo = keyData.getString("visitor_photo");
                                }

                                if (keyData.isNull("added_datetime")) {
                                    added_datetime = "";
                                } else {
                                    added_datetime = keyData.getString("added_datetime");
                                }

                                if (keyData.isNull("purpose")) {
                                    purpose = "";
                                } else {
                                    purpose = keyData.getString("purpose");
                                }

                                if (keyData.isNull("entry_time")) {
                                    entry_time = "";
                                } else {
                                    entry_time = keyData.getString("entry_time");
                                }

                                if (keyData.isNull("visitor_id")) {
                                    visitor_id = "";
                                } else {
                                    visitor_id = keyData.getString("visitor_id");
                                }

                                if (keyData.isNull("visitor_uuid")) {
                                    visitor_uuid = "";
                                } else {
                                    visitor_uuid = keyData.getString("visitor_uuid");
                                }

                                if (keyData.isNull("entry_date")) {
                                    entry_date = "";
                                } else {
                                    entry_date = keyData.getString("entry_date");
                                }


                                visitorModules.add(new VisitorModule(visitor_name, visitor_photo, entry_date,
                                        visitor_uuid, visitor_id, entry_time, purpose, added_datetime));
                            }

                            setRecyclerView(visitorModules);
                        }


                    } catch (JSONException je) {

                    }


                } else {
                    Toast.makeText(getActivity(), "No have Visitor's record", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }


    private void initListener() {
        fab_button.setOnClickListener(this);
        iv_backBtn.setOnClickListener(this);
    }

    private void initViews(View view) {
        apiService = ApiUtilsPatashala.getService();
        recycler_view = view.findViewById(R.id.recycler_view);
        fab_button = view.findViewById(R.id.fab_button);
        tvTitle = view.findViewById(R.id.tvTitle);
        iv_backBtn = view.findViewById(R.id.iv_backBtn);
        tvTitle.setText("Visitor List");

    }

    private void setRecyclerView(ArrayList<VisitorModule> visitorModules) {
        System.out.println("visitorModules***" + visitorModules.size());
        VisitorAdapter visitorAdapter = new VisitorAdapter(getActivity(), visitorModules, Constant.RV_VISITOR_ROW);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.setAdapter(visitorAdapter);
        visitorAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_button:
                final VisitorActivity visitorActivity = (VisitorActivity)getActivity();
                visitorActivity.loadFragment(Constant.ADD_VISITOR,null);
                break;

                case R.id.iv_backBtn:
               getActivity().onBackPressed();
                break;
        }
    }
}
