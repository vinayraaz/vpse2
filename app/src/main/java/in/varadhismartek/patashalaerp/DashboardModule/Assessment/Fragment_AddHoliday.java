package in.varadhismartek.patashalaerp.DashboardModule.Assessment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.AddWing.AddWingsModel;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class Fragment_AddHoliday extends Fragment implements View.OnClickListener{

    private TextView tvTitle;
    private EditText edAddExam;
    private ImageView ivAddExam, ivSendExam, ivBack;
    private RecyclerView rcvAddWings;
    APIService apiService;
    private ArrayList<AddWingsModel> addExamArrayList;
    private ArrayList<String> examList = new ArrayList<>();
    AssessmentAdapter assessmentAdapter;
    int spanCount = 2;
    private Button buttonAdd;
    String editvalue;

    public Fragment_AddHoliday() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initViews(view);
        initListener();
        getHoliday();
        return view;

    }

    private void getHoliday() {

    }

    private void initListener() {
        ivAddExam.setOnClickListener(this);
        ivSendExam.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
    }

    private void initViews(View view) {
        apiService = ApiUtils.getAPIService();
        tvTitle = view.findViewById(R.id.tvTitle);
        edAddExam = view.findViewById(R.id.etAddWings);
        ivBack = view.findViewById(R.id.iv_backBtn);
        buttonAdd = view.findViewById(R.id.button_added);

        ivAddExam = view.findViewById(R.id.ivAddWings);
        ivSendExam = view.findViewById(R.id.iv_sendAddWings);
        rcvAddWings = view.findViewById(R.id.rcvAddWings);


        tvTitle.setText("Add Holiday");
        edAddExam.setHint("Add Holiday");

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        rcvAddWings.setLayoutManager(linearLayoutManager);
        rcvAddWings.setHasFixedSize(true);
        addExamArrayList = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAddWings:

                String value = "";
                editvalue = edAddExam.getText().toString();
                if (editvalue.equals("")) {
                    Toast.makeText(getActivity(), "Please enter the Value", Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                }else {
                    if (addExamArrayList.size()>0){
                        for (int i = 0; i < addExamArrayList.size(); i++) {
                            if (addExamArrayList.get(i).getWingsName().toLowerCase().equals(editvalue.toLowerCase())) {
                                value = addExamArrayList.get(i).getWingsName();
                            }
                        }

                        if (value.toLowerCase().equals(editvalue.toLowerCase())) {
                            Toast.makeText(getActivity(), "Already Exist", LENGTH_SHORT).show();
                        } else {
                            assessmentAdapter.newValues(editvalue);
                            examList.add(editvalue);
                            System.out.println("addExamArrayList**11  "+examList);

                        }

                        edAddExam.setText("");
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    else{

                        assessmentAdapter = new AssessmentAdapter(addExamArrayList,
                                getActivity(), ivSendExam, Constant.RV_EXAMS_ROW);
                        rcvAddWings.setAdapter(assessmentAdapter);
                        assessmentAdapter.newValues(editvalue);
                        edAddExam.setText("");
                        examList.add(editvalue);


                    }
                }

                break;
            case R.id.button_added:
                System.out.println("addExamArrayList**"+addExamArrayList.size()+"**"+editvalue+"**"+examList);

                JSONArray array = new JSONArray();
                JSONObject jsonObject = new JSONObject();

                for (int i = 0; i < examList.size(); i++) {
                    array.put(examList.get(i));

                }

                try {
                    jsonObject.put("exam_type", array);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (addExamArrayList.size()>0){
                 //   addHolidayAPIMethod(array);
                }


                System.out.println("objExam***"+array);
                System.out.println("objExam***"+jsonObject);






                break;
            case R.id.iv_backBtn:
                getActivity().onBackPressed();
                break;
        }
    }

    private void addHolidayAPIMethod(JSONArray array) {

        apiService.addExamType(Constant.SCHOOL_ID,array,Constant.EMPLOYEE_BY_ID)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.i("Exam_getlist *", "" + response.raw().request().url());
                        Log.i("Exam_getlist_R *", "" + response.body());
                        Log.i("Exam_getlist *", "" + response.code());
                        Log.i("Exam_getlist *", "" + Constant.SCHOOL_ID);
                        Log.i("Exam_getlist *", "" + Constant.EMPLOYEE_BY_ID);
                        if (response.isSuccessful()){
                            Toast.makeText(getActivity(),"Data have save successfully", LENGTH_SHORT).show();
                           // getExamType();
                        }

                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });
    }
}
