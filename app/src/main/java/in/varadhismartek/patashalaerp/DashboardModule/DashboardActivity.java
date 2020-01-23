package in.varadhismartek.patashalaerp.DashboardModule;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.DashboardModule.Assessment.Fragment_AddSubject;
import in.varadhismartek.patashalaerp.DashboardModule.Assessment.Fragment_AssessmentGrade;
import in.varadhismartek.patashalaerp.R;


public class DashboardActivity extends AppCompatActivity {
    SharedPreferences pref = null, pref2;
    SharedPreferences.Editor editor;
    boolean booleanStatus = false;
    String MenuName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.frame_container);

        pref =getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        pref2 = getSharedPreferences("SecurityStatus", Context.MODE_PRIVATE);
        editor =pref2.edit();
        editor.putString("ACTIVE_PAGE", "5");
        editor.commit();

        Constant.SCHOOL_ID = pref2.getString("SchoolId", "0");
        Constant.ACTIVE_PAGE = pref2.getString("ACTIVE_PAGE", "0");
        Constant.SCHOOL_ID = pref2.getString("SchoolId", "0");
        Constant.EMPLOYEE_BY_ID = pref2.getString("EmployeeId", "0");
        Constant.POC_NAME = pref2.getString("PocName", "0");
        Constant.POC_Mobile_Number = pref2.getString("PocMobileNo", "0");


            getSupportFragmentManager().beginTransaction().
                    replace(R.id.container, new Fragment_Dashboard()).addToBackStack(null).commit();


    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DashboardActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();    }



}
