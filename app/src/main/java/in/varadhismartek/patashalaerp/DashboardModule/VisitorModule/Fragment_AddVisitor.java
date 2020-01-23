package in.varadhismartek.patashalaerp.DashboardModule.VisitorModule;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.R;

public class Fragment_AddVisitor extends Fragment {
    String rvType, currentDate;
    private int year, month, date;
TextView tvDate,tvTime,tv_visitorId;
    int millisecond,second,minute,hour;
    public Fragment_AddVisitor() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_visitor, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initViews(view);
        initListener();
        currentDate();
        currentTime();
        visitorFormat();
       // ramdomNumber();


        return view;

    }

    private void ramdomNumber() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        String visitorID =  String.format("%06d", number);
        System.out.println("visitorID**"+visitorID);
        tv_visitorId.setText(visitorID);
    }

    private void visitorFormat() {
        Constant.VISITOR_FORMATE ="VS00";
    }

    private void currentDate() {
        Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = simpleDate.format(calendar.getTime());
        Log.i("currentDate**", "" + currentDate);
        tvDate.setText(currentDate);
    }

    private void currentTime() {
        Calendar cal = Calendar.getInstance();

         millisecond = cal.get(Calendar.MILLISECOND);
         second = cal.get(Calendar.SECOND);
         minute = cal.get(Calendar.MINUTE);
        //12 hour format
         hour = cal.get(Calendar.HOUR);
        //24 hour format
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);

        tvTime.setText(hour+":"+minute+":"+second);

    }

    private void initListener() {

    }

    private void initViews(View view) {
        tvDate = view.findViewById(R.id.tv_visitdate);
        tvTime = view.findViewById(R.id.tv_visitTime);
        tv_visitorId = view.findViewById(R.id.tv_visitorId);


    }
}
