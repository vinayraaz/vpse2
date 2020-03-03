package in.varadhismartek.patashalaerp.TransportModule;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.Utils.Utilities;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtilsPatashala;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransportVechileDetails extends Fragment {
    APIService apiServicePatashala;
    String strVehicleUUID = "", strVehicleID = "";
    Utilities utilities;

    public TransportVechileDetails() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transport_vechile_details, container, false);

        initViews(view);
        initListeners();
        getVehicleDetails();

        return view;
    }


    private void initListeners() {

    }

    private void initViews(View view) {
        apiServicePatashala = ApiUtilsPatashala.getService();
        Bundle bundle = getArguments();
        if (bundle != null) {
            strVehicleUUID = bundle.getString("VEHICLE_UUID");
            strVehicleID = bundle.getString("VEHICLE_ID");
        }


    }

    private void getVehicleDetails() {
        utilities.displayProgressDialog(getActivity(), "Please wait ...", false);
        apiServicePatashala.getSchoolBusDetails(Constant.SCHOOL_ID, strVehicleUUID).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    utilities.cancelProgressDialog();

                    try {

                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = (String) object.get("status");

                        if (status.equalsIgnoreCase("success")) {
                            if (object.getJSONObject("data").toString().equals("{}")) {
                                utilities.cancelProgressDialog();


                            } else {
                                JSONObject jsonObject1 = object.getJSONObject("data");
                                Iterator<String> keys = jsonObject1.keys();
                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    JSONObject jsonVehicleDetails = jsonObject1.getJSONObject("vehicle_details");


                                }

                            }
                        }
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        utilities.cancelProgressDialog();
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
                utilities.cancelProgressDialog();
                Log.i("ERROR**", "" + t.toString());
            }
        });
    }

}
