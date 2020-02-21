package in.varadhismartek.patashalaerp.Timetable;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeTableMain_Activity extends AppCompatActivity {
    APIService apiService;
    ArrayList<PeriodTableModel> periodTableModels = new ArrayList<>();
    RecyclerView recyclerView_periods,recyclerView_days;
    ArrayList<String>dayArrayList= new ArrayList<>();

    String duration,start_time,addedUUID,end_time,type_of;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_layout);
        recyclerView_periods = findViewById(R.id.recyclerView);
        recyclerView_days = findViewById(R.id.recyclerView_day);
        dayArrayList.clear();


        dayArrayList.add("MON");
        dayArrayList.add("TUS");
        dayArrayList.add("WED");
        dayArrayList.add("THU");
        dayArrayList.add("FRI");
        dayArrayList.add("SAT");

        TimeTableMain_Activity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); //set orientation
        apiService = ApiUtils.getAPIService();
        getPeriodMethod();

    }

    private void getPeriodMethod() {
        apiService.getPeriods(Constant.SCHOOL_ID,"Primary","1","2020-06-08")
                .enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                System.out.println("ResponseTable***"+response.body()+"***"+response.code());
                if(response.isSuccessful()){
                    periodTableModels.clear();
                    try {
                        JSONObject json1 = (new JSONObject(new Gson().toJson(response.body())));
                        String status = (String) json1.get("status");
                        if (status.equalsIgnoreCase("Success")) {

                            JSONObject jsonObject1 = json1.getJSONObject("data");
                            Iterator<String> keys = jsonObject1.keys();

                            while (keys.hasNext()) {
                                String key = keys.next();

                                JSONObject dataJson = jsonObject1.getJSONObject(key);



                                 duration= dataJson.getString("duration");
                                 start_time= dataJson.getString("start_time");
                                 addedUUID= dataJson.getString("added_employee_uuid");
                                 end_time= dataJson.getString("end_time");
                                 type_of= dataJson.getString("type_of");
                                periodTableModels.add(new PeriodTableModel(duration,start_time,addedUUID,end_time,type_of));

                            }
                            if (periodTableModels.size()>0){
                                setRecyclerViewDay();
                            }
                            setRecyclerView(periodTableModels);
                        }
                    }catch (JSONException je){

                    }


                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }

    private void setRecyclerViewDay() {
        recyclerView_days.setHasFixedSize(true);
        TimeTableAdapter  periodsAdapter = new TimeTableAdapter(
                dayArrayList,TimeTableMain_Activity.this, Constant.RV_TIMEYABLE_DAYS);


        recyclerView_days.setLayoutManager(new LinearLayoutManager(TimeTableMain_Activity.this,
                LinearLayoutManager.VERTICAL, false));

        recyclerView_days.setAdapter(periodsAdapter);
    }

    private void setRecyclerView(ArrayList<PeriodTableModel> periodTableModels) {
        recyclerView_periods.setHasFixedSize(true);
        TimeTableAdapter  periodsAdapter = new TimeTableAdapter(TimeTableMain_Activity.this,
                periodTableModels, Constant.RV_TIMEYABLE_PERIODS);


        recyclerView_periods.setLayoutManager(new LinearLayoutManager(TimeTableMain_Activity.this,
                LinearLayoutManager.HORIZONTAL, false));

        recyclerView_periods.setAdapter(periodsAdapter);















//        recyclerView_periods.setHasFixedSize(true);


        /* recyclerView = (RecyclerView) findViewById(R.id.recycler);

        imageModelArrayList = eatFruits();
        adapter = new FruitAdapter(this, imageModelArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
 */



        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
/*
           TimeTableAdapter  periodsAdapter = new TimeTableAdapter(this, dayDataList, Constant.TAG_DAYS);
            rv_days.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            rv_days.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL));
            rv_days.setItemAnimator(new DefaultItemAnimator());
            rv_days.setAdapter(daysAdapter);*/

    }
}
