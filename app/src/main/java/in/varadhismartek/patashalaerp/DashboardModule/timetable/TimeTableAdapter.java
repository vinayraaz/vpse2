package in.varadhismartek.patashalaerp.DashboardModule.timetable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.R;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableViewHolder> {

    ArrayList<TimeTableModel> arrayList;
    Context mContext;
    String tag;
    ArrayList<String> arrayListDate;
    HashMap<String, ArrayList<TimeTableModel>> hashArrayList;
    String strStatTime,strType;

    public TimeTableAdapter(ArrayList<TimeTableModel> arrayList, Context mContext, String tag) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.tag = tag;
    }

    public TimeTableAdapter(Context mContext,ArrayList<String> arrayListDate,  String tag) {
        this.arrayListDate = arrayListDate;
        this.mContext = mContext;
        this.tag = tag;
    }
/*
    public TimeTableAdapter(HashMap<String, ArrayList<TimeTableModel>> hashArrayList, Context mContext, String tag) {
        this.hashArrayList= hashArrayList;
        this.mContext=mContext;
        this.tag=tag;

    }*/

    @NonNull
    @Override
    public TimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        switch (tag) {
            case Constant.PERIOD_TIMINGS:
                return new TimeTableViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.period_timing_row, viewGroup, false));

            case Constant.DAYS:
                return new TimeTableViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.period_timing_row, viewGroup, false));

            default: return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableViewHolder timeTableViewHolder, int i) {

        switch (tag){

            case Constant.PERIOD_TIMINGS:

                timeTableViewHolder.tvStartTime.setText(arrayList.get(i).getStartTime());
               /* Iterator myVeryOwnIterator = hashArrayList.keySet().iterator();

                while(myVeryOwnIterator.hasNext()) {
                    String key=(String)myVeryOwnIterator.next();
                    for (int j=0;j<hashArrayList.get(key).get(i).getArrayListDays().size();j++){
                         strStatTime =hashArrayList.get(key).get(j).getStartTime();
                         strType =hashArrayList.get(key).get(i).getTypeOfPeriod();
                    }
                    //String value=(String)hashArrayList.get(key).get();
                    //Toast.makeText(ctx, "Key: "+key+" Value: "+value, Toast.LENGTH_LONG).show();
                }
                timeTableViewHolder.tvStartTime.setText(strStatTime+"\n"+strType);
*/
                break;


            case Constant.DAYS:

                timeTableViewHolder.tvStartTime.setText(arrayListDate.get(i));

                break;

        }

    }

    @Override
    public int getItemCount() {
        switch (tag){

            case Constant.PERIOD_TIMINGS:
                return arrayList.size();

            case Constant.DAYS:
                return arrayListDate.size();

              /*  case Constant.PERIOD_TIMINGS:
                return hashArrayList.size();*/

                default:return 0;
        }
    }
}
