package in.varadhismartek.patashalaerp.DashboardModule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import in.varadhismartek.patashalaerp.DashboardModule.UpdateActivity.UpdateSelelectModuleActivity;
import in.varadhismartek.patashalaerp.DashboardModule.UpdateActivity.UpdateMakerCheckerActivity;
import in.varadhismartek.patashalaerp.DashboardModule.UpdateActivity.UpdateStaffBarriersActivity;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.DashboardModule.UpdateActivity.UpdateDepartmentActivity;
import in.varadhismartek.patashalaerp.DashboardModule.UpdateActivity.UpdateDivisionActivity;
import in.varadhismartek.patashalaerp.DashboardModule.UpdateActivity.UpdateRolesActivity;
import in.varadhismartek.patashalaerp.DashboardModule.UpdateActivity.UpdateWingsActivity;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonRoles,buttonDept,buttonWings,buttonDivision;
    private Button buttonSelectModule,buttonMakerChecker,buttonStaffBarriers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        buttonSelectModule = findViewById(R.id.selectModule);
        buttonMakerChecker = findViewById(R.id.maker_checker);
        buttonWings = findViewById(R.id.wings);
        buttonDept = findViewById(R.id.dept);
        buttonRoles = findViewById(R.id.roles);
        buttonDivision = findViewById(R.id.division);
        buttonStaffBarriers = findViewById(R.id.staff_barriers);

        buttonSelectModule.setOnClickListener(this);
        buttonMakerChecker.setOnClickListener(this);
        buttonWings.setOnClickListener(this);
        buttonDept.setOnClickListener(this);
        buttonRoles.setOnClickListener(this);
        buttonDivision.setOnClickListener(this);
        buttonStaffBarriers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectModule:
                Intent intentsModule = new Intent(SettingActivity.this, UpdateSelelectModuleActivity.class);
                startActivity(intentsModule);
                finish();
                break;
            case R.id.maker_checker:
                Intent intentmakerchecker = new Intent(SettingActivity.this, UpdateMakerCheckerActivity.class);
                startActivity(intentmakerchecker);
                finish();
                break;
            case R.id.wings:
                Intent intentWings = new Intent(SettingActivity.this, UpdateWingsActivity.class);
                startActivity(intentWings);
                finish();
                break;
            case R.id.dept:
                Intent intentDept = new Intent(SettingActivity.this, UpdateDepartmentActivity.class);
                startActivity(intentDept);
                finish();
                break;
            case R.id.roles:
                Intent intentRoles = new Intent(SettingActivity.this, UpdateRolesActivity.class);
                startActivity(intentRoles);
                finish();
                break;
            case R.id.division:
                Intent intentDivision = new Intent(SettingActivity.this, UpdateDivisionActivity.class);
                startActivity(intentDivision);
                finish();
                break;
            case R.id.staff_barriers:
                Intent intentStaffBarrier = new Intent(SettingActivity.this, UpdateStaffBarriersActivity.class);
                startActivity(intentStaffBarrier);
                finish();
                break;
        }
    }


}
