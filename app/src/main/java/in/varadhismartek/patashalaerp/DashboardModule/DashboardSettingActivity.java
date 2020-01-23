package in.varadhismartek.patashalaerp.DashboardModule;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.varadhismartek.Indicator.CirclePageIndicator;
import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.DashboardModule.UpdateAdapter.GridViewAdapter;
import in.varadhismartek.patashalaerp.DashboardModule.extraUtils.ModuleModel;
import in.varadhismartek.patashalaerp.DashboardModule.extraUtils.ViewPagerAdapter;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import in.varadhismartek.patashalaerp.SelectModules.SelectModuleModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardSettingActivity extends AppCompatActivity implements TextWatcher {
    public static int item_grid_num = 8;
    public static int number_columns = 2;
    private ViewPager view_pager;
    private ViewPagerAdapter mAdapter;
    private List<ModuleModel> moduleList;
    private List<GridView> gridList = new ArrayList<>();
    private CirclePageIndicator indicator;
    ArrayList<String> name = new ArrayList<>();

    ImageView ivSetting,editSetting;
    APIService mApiService;
    AutoCompleteTextView edSearch;
    GridViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_setting);
        mApiService = ApiUtils.getAPIService();
        initViews();
        getModules();
        initDatas();
    }
    private void initViews() {
        view_pager =  findViewById(R.id.view_pager);

        edSearch = findViewById(R.id.et_search);
        ivSetting = findViewById(R.id.barriers_setting);
        editSetting = findViewById(R.id.edit_setting);
        mAdapter = new ViewPagerAdapter();
        view_pager.setAdapter(mAdapter);
        moduleList = new ArrayList<>();
        indicator =  findViewById(R.id.indicator);
        indicator.setVisibility(View.VISIBLE);
        indicator.setViewPager(view_pager);

        ivSetting.setVisibility(View.GONE);
        editSetting.setVisibility(View.GONE);
        edSearch.addTextChangedListener(this);
    }

    //private void initDatas(ArrayList<String> name) {
    private void initDatas() {
        Log.i("nameSize**",""+name.size());
        if (moduleList.size() > 0) {
            moduleList.clear();
        }
        if (gridList.size() > 0) {
            gridList.clear();
        }




      for (int i=0;i<name.size();i++){

          moduleList.add(new ModuleModel (R.drawable.admission,name.get(i)));
      }

       /* moduleList.add(new ModuleModel (R.drawable.assessment,"Assessment"));
        moduleList.add(new ModuleModel(R.drawable.admission,"HomeWork"));
        moduleList.add(new ModuleModel(R.drawable.time_table,"TimeTable"));
        moduleList.add(new ModuleModel(R.drawable.class_and_section,"Class & Section"));
        moduleList.add(new ModuleModel(R.drawable.transport,"Transport"));
        moduleList.add(new ModuleModel(R.drawable.schedule,"Schedule"));
        moduleList.add(new ModuleModel(R.drawable.assessment,"Assessment"));
        moduleList.add(new ModuleModel(R.drawable.admission,"Admission"));
        moduleList.add(new ModuleModel(R.drawable.time_table,"TimeTable"));
        moduleList.add(new ModuleModel(R.drawable.class_and_section,"Class & Section"));
        moduleList.add(new ModuleModel(R.drawable.transport,"Transport"));
        moduleList.add(new ModuleModel(R.drawable.schedule,"Schedule"));
        moduleList.add(new ModuleModel(R.drawable.assessment,"Assessment"));
        moduleList.add(new ModuleModel(R.drawable.admission,"Admission"));
        moduleList.add(new ModuleModel(R.drawable.time_table,"TimeTable"));
        moduleList.add(new ModuleModel(R.drawable.class_and_section,"Class & Section"));
        moduleList.add(new ModuleModel(R.drawable.transport,"Transport"));
        moduleList.add(new ModuleModel(R.drawable.schedule,"Schedule"));
        moduleList.add(new ModuleModel(R.drawable.assessment,"Assessment"));
        moduleList.add(new ModuleModel(R.drawable.admission,"Admission"));
        moduleList.add(new ModuleModel(R.drawable.time_table,"TimeTable"));
        moduleList.add(new ModuleModel(R.drawable.class_and_section,"Class & Section"));
        moduleList.add(new ModuleModel(R.drawable.transport,"Transport"));
        moduleList.add(new ModuleModel(R.drawable.schedule,"Schedule"));*/

        int pageSize = moduleList.size() % item_grid_num == 0
                ? moduleList.size() / item_grid_num
                : moduleList.size() / item_grid_num + 1;
        for (int i = 0; i < pageSize; i++) {
            GridView gridView = new GridView(this);
             adapter = new GridViewAdapter(DashboardSettingActivity.this,moduleList, i);
            gridView.setNumColumns(number_columns);
            gridView.setAdapter(adapter);
            gridList.add(gridView);
        }
        mAdapter.add(gridList);
    }

    private void getModules() {
        name.clear();
        mApiService.getModuleList(Constant.SCHOOL_ID).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try{
                    if (response.isSuccessful()){

                        Log.i("MAKER_CHECKER",""+response.body());
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = (String) object.get("status");

                        if (status.equalsIgnoreCase("success")) {

                            JSONObject jsonObject1 = object.getJSONObject("data");
                            Iterator<String> keys = jsonObject1.keys();

                            while (keys.hasNext()) {
                                String key = keys.next();
                                Log.d("module", key.toString());

                                JSONObject jsonObjectValue = jsonObject1.getJSONObject(key);
                                Log.d("MAKER_CHECKER", jsonObjectValue.toString());
                                String module_name = jsonObjectValue.getString("module_name");
                                boolean module_status = jsonObjectValue.getBoolean("active_status");
                                if (module_status){
                                    moduleList.add(new ModuleModel(R.drawable.admission,module_name));
                                    name.add(module_name);
                                }
                                initDatas();
                                //getModuleListInSpinner();


                            }
                        }

                    }else {
                        Log.i("SELECTMODULE","NO Data");
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.i("SELECTMODULEERROR",""+t.toString());
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
       // DashboardSettingActivity.this.adapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
