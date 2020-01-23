package in.varadhismartek.patashalaerp.Timetable;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.R;

class TimeTableAdapter extends RecyclerView.Adapter<TimeTableViewHolder> {
    Context context;
    ArrayList<PeriodTableModel> tableModels = new ArrayList<>();
    ArrayList<String> daysArrayList = new ArrayList<>();
    String rvType;
    public TimeTableAdapter(Context context, ArrayList<PeriodTableModel> periodTableModels, String rvType) {
        this.context =context;
        this.tableModels= periodTableModels;
        this.rvType =rvType;
        notifyDataSetChanged();
    }

    public TimeTableAdapter(ArrayList<String> dayArrayList, Context context, String rvType) {
        this.daysArrayList = dayArrayList;
        this.context=context;
        this.rvType= rvType;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public TimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (rvType){
            case Constant.RV_TIMEYABLE_PERIODS:
                return new TimeTableViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.periods_rows, parent, false));

            case Constant.RV_TIMEYABLE_DAYS:
                return new TimeTableViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.days_rows, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableViewHolder holder, int position) {
        switch (rvType){
            case Constant.RV_TIMEYABLE_PERIODS:
                String strType= tableModels.get(position).getType_of();
                String strStartTime = tableModels.get(position).getStart_time();
                if (strType.equalsIgnoreCase("Prayer")){
                    holder.tvPeriods.setText(strStartTime);
                    holder.tvPeriods.setTextColor(Color.parseColor("#FF0000"));
                }
                else  if (strType.equalsIgnoreCase("Period")){
                    holder.tvPeriods.setText(strStartTime);
                    holder.tvPeriods.setTextColor(Color.parseColor("#52b155"));
                }
                else  if (strType.equalsIgnoreCase("Break")){
                    holder.tvPeriods.setText(strStartTime);
                    holder.tvPeriods.setTextColor(Color.parseColor("#097db7"));
                }
                else  if (strType.equalsIgnoreCase("Lunch")){
                    holder.tvPeriods.setText(strStartTime);
                    holder.tvPeriods.setTextColor(Color.parseColor("#f2790d"));
                }
                break;

            case Constant.RV_TIMEYABLE_DAYS:
                holder.tvDays.setText(daysArrayList.get(position));
                break;
        }




    }

    @Override
    public int getItemCount() {
        switch (rvType){
            case Constant.RV_TIMEYABLE_PERIODS:
                return tableModels.size();
            case Constant.RV_TIMEYABLE_DAYS:
                return daysArrayList.size();
        }
        return 0;
    }
}
