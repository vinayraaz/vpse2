package in.varadhismartek.patashalaerp.DashboardModule.UpdateAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.DivisionModule.AddDivisionActivity;
import in.varadhismartek.patashalaerp.DivisionModule.ClassDivisionHolder;
import in.varadhismartek.patashalaerp.DivisionModule.ClassSectionAndDivisionModel;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import in.varadhismartek.patashalaerp.DashboardModule.UpdateActivity.UpdateDivisionActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDivisionAdapter extends RecyclerView.Adapter<ClassDivisionHolder> {


    private ArrayList<ClassSectionAndDivisionModel> arrayList;
    private ArrayList<String> list;
    private Context mContext;
    private ImageView iv_sendAddDivision, iv_sendAddSubmit;
    private ArrayList<String> checkedArrayList;
    private ArrayList<String> uncheckedArrayList;
    private String recyclerTag;
    private Button bt_select_all, button_added;
    Dialog dialog;


    private APIService mApiService;

    public UpdateDivisionAdapter(ArrayList<ClassSectionAndDivisionModel> arrayList, Context mContext,
                                 Button button_added, String recyclerTag, Button bt_select_all) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.button_added = button_added;
        this.recyclerTag = recyclerTag;
        this.bt_select_all = bt_select_all;
        checkedArrayList = new ArrayList<>();
        uncheckedArrayList = new ArrayList<>();

        mApiService = ApiUtils.getAPIService();

    }

    @NonNull
    @Override
    public ClassDivisionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (recyclerTag) {

            case Constant.RV_DIVISION_CARD:
                return new ClassDivisionHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.select_module_row_card, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final ClassDivisionHolder holder, final int i) {
        switch (recyclerTag) {

            case Constant.RV_DIVISION_CARD:

                holder.check_box.setText(arrayList.get(i).getName());
                boolean flag = arrayList.get(i).isChecked();

                if (flag) {
                    holder.check_box.setBackgroundColor(Color.parseColor("#72d548"));
                    holder.check_box.setTextColor(Color.WHITE);
                    checkedArrayList.add(arrayList.get(i).getName());
                    Log.d("AaCheckedArrayList", "" + checkedArrayList.size());

                } else {
                    holder.check_box.setBackgroundColor(Color.WHITE);
                    holder.check_box.setTextColor(Color.BLACK);
                    checkedArrayList.remove(arrayList.get(i).isChecked());
                    Log.d("AaCheckedArrayList1", "" + checkedArrayList.size());

                }

                holder.check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (!arrayList.get(i).isChecked()) {
                            checkedArrayList.add(holder.check_box.getText().toString());
                            arrayList.get(i).setChecked(true);

                            Log.d("Checked", "" + checkedArrayList.size());
                            holder.check_box.setBackgroundColor(Color.parseColor("#72d548"));
                            holder.check_box.setTextColor(Color.WHITE);
                        } else {
                            checkedArrayList.remove(holder.check_box.getText().toString());
                            arrayList.get(i).setChecked(false);
                            Log.d("Unchecked", "" + checkedArrayList.size());
                            holder.check_box.setBackgroundColor(Color.WHITE);
                            holder.check_box.setTextColor(Color.BLACK);
                        }

                        Log.d("CheckedItem", "" + checkedArrayList.size());
                        String divisionName = arrayList.get(i).getName();
                        String divisionStatus = String.valueOf(arrayList.get(i).isChecked());

                        Log.d("CheckedItem", "" + checkedArrayList.size());
                        Log.d("moduleName", "" + divisionName);
                        Log.d("moduleStatus", "" + divisionStatus);
                        JSONArray jsonDivision = new JSONArray();
                        JSONObject jsonObject = new JSONObject();
                        try {

                            jsonObject.put("division", divisionName);
                            jsonObject.put("status", divisionStatus);
                            jsonDivision.put(jsonObject);
                        } catch (JSONException je) {

                        }

                        Log.d("jsonDivision", "" + jsonDivision);
                        divisionStatusUpdate(jsonDivision);

                    }
                });


                bt_select_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String status = bt_select_all.getText().toString();
                        if (status.equalsIgnoreCase("Select All")) {

                            if (uncheckedArrayList.size() > 0) {
                                uncheckedArrayList.clear();
                            }

                            bt_select_all.setText("Unselect All");
                            bt_select_all.setBackgroundResource(R.drawable.add_back);
                            bt_select_all.setTextColor(Color.WHITE);

                            if (checkedArrayList.size() > 0)
                                checkedArrayList.clear();

                            for (int i = 0; i < arrayList.size(); i++) {
                                arrayList.get(i).setChecked(true);
                                checkedArrayList.add(arrayList.get(i).getName());

                                Log.d("asdasd", "" + checkedArrayList.size());

                            }
                            notifyDataSetChanged();

                        } else if (status.equalsIgnoreCase("Unselect All")) {

                            bt_select_all.setText("Select All");
                            bt_select_all.setBackgroundResource(R.drawable.btn_round_shape_grey);
                            bt_select_all.setTextColor(mContext.getResources().getColor(R.color.colorBlack));

                            for (int i = 0; i < arrayList.size(); i++) {

                                arrayList.get(i).setChecked(false);

                            }

                            if (checkedArrayList.size() > 0)
                                checkedArrayList.clear();

                            notifyDataSetChanged();

                        }
                    }
                });

                button_added.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (uncheckedArrayList.size() > 0) {
                            uncheckedArrayList.clear();
                        }

                        if (checkedArrayList.size() > 0) {
                            checkedArrayList.clear();
                        }

                        for (ClassSectionAndDivisionModel module : arrayList) {

                            if (!module.isChecked()) {
                                uncheckedArrayList.add(module.getName());
                            } else if (module.isChecked()) {

                                checkedArrayList.add(module.getName());
                            }

                        }


                        JSONArray array = new JSONArray();
                        for (int i = 0; i < checkedArrayList.size(); i++) {
                            array.put(checkedArrayList.get(i));
                        }

                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("data", array);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }



                        mApiService.addDivision(Constant.SCHOOL_ID, array.toString(), Constant.EMPLOYEE_BY_ID)
                                .enqueue(new Callback<Object>() {
                                    @Override
                                    public void onResponse(Call<Object> call, Response<Object> response) {

                                        if (response.isSuccessful()) {
                                            Toast.makeText(mContext, "Added Successfully", Toast.LENGTH_SHORT).show();


                                        } else {

                                            Log.d("DIVISION_FAIL", "" + response.body());
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<Object> call, Throwable t) {
                                        Log.d("DIVISION_Exception", t.getMessage());

                                    }
                                });
                    }

                });

                holder.check_box.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        openDialogForUpdate(holder.check_box.getText().toString());
                        notifyDataSetChanged();
                        return false;
                    }
                });

                break;


        }

    }


    private void divisionStatusUpdate(JSONArray jsonDivision) {
        mApiService.updateDivisionStatus(Constant.SCHOOL_ID, jsonDivision, Constant.EMPLOYEE_BY_ID)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.i("RESPONSE**",""+response.body());
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });
    }

    private void openDialogForUpdate(final String division) {
        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.update_delete_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText etOldDivision = dialog.findViewById(R.id.etOldRole);
        TextView tvTitle =dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("Division");
        final EditText newDivision = dialog.findViewById(R.id.etNewRole);
        Button updateDivision = dialog.findViewById(R.id.btnUpdateRole);
        final Button deleteDivision = dialog.findViewById(R.id.btnDeleteRole);

        etOldDivision.setText(division);
        etOldDivision.setFocusable(false);
        etOldDivision.setFocusableInTouchMode(false);


        updateDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String updateDivision = newDivision.getText().toString();
                if (!updateDivision.equals("")) {
                    updateDivisionApi(division, updateDivision);

                }

            }
        });


        deleteDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //deleteRoleApi(role);

            }
        });


        dialog.show();
    }

    private void updateDivisionApi(String division, String updateDivision) {

        mApiService.updateDivisionName(Constant.SCHOOL_ID, division, updateDivision,Constant.EMPLOYEE_BY_ID)
                .enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
//                dialog.dismiss();

                if (response.isSuccessful()) {
                    try {
                        JSONObject json1 = (new JSONObject(new Gson().toJson(response.body())));
                        String status = (String) json1.get("status");

                        if (status.equalsIgnoreCase("Success")) {
                            Toast.makeText(mContext,"Update Successfully",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Intent intent = new Intent(mContext, UpdateDivisionActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);


                        }
                    } catch (JSONException je) {

                    }
                }
                else {
                    dialog.dismiss();
                    if (String.valueOf(response.code()).equals("409")) {
                        Toast.makeText(mContext, "Role name already exists", Toast.LENGTH_SHORT).show();

                    }
                    if (String.valueOf(response.code()).equals("404")) {
                        Toast.makeText(mContext, "Old role name does not exists", Toast.LENGTH_SHORT).show();

                    }

                }



                Log.d("updatedRoles", "" + response.body());

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
