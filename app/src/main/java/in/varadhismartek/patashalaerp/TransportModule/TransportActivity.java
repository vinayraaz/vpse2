package in.varadhismartek.patashalaerp.DashboardModule.TransportModule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.DashboardModule.Syllabus.AddSyllabusFragment;
import in.varadhismartek.patashalaerp.DashboardModule.Syllabus.SyllabusInboxFragment;
import in.varadhismartek.patashalaerp.DashboardModule.Syllabus.SyllabusViewFragment;
import in.varadhismartek.patashalaerp.R;

public class TransportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_container);

        loadFragment(Constant.ADD_VEHICLE_FRAGMENT, null);
       // loadFragment(Constant.TRANSPORT_INBOX_FRAGMENT, null);
    }

    public void loadFragment(String fragmentString, Bundle bundle) {

        switch (fragmentString) {

            case Constant.ADD_VEHICLE_FRAGMENT:
                callFragment(new AddVehicleFragment(), Constant.ADD_VEHICLE_FRAGMENT, null, bundle);
                break;

                case Constant.TRANSPORT_INBOX_FRAGMENT:
                callFragment(new TransportInboxFragment(), Constant.TRANSPORT_INBOX_FRAGMENT, null, bundle);
                break;

                case Constant.TRANSPORT_DETAILS_FRAGMENT:
                callFragment(new TransportVechileDetails(), Constant.TRANSPORT_DETAILS_FRAGMENT, null, bundle);
                break;



        }
    }


    private void callFragment(Fragment fragment, String tag, String addBackStack, Bundle bundle) {

        if (bundle != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.container, fragment, tag)
                    .addToBackStack(addBackStack)
                    .commit();
            fragment.setArguments(bundle);

        }else{

            if (Constant.TRANSPORT_INBOX_FRAGMENT.equals(tag)){
                getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, fragment, tag)
                        .commit();
            }
            else {
                getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, fragment, tag)
                        .addToBackStack(addBackStack)
                        .commit();
            }
        }
    }
}
