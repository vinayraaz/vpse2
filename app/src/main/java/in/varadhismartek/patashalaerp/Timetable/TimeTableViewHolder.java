package in.varadhismartek.patashalaerp.Timetable;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.varadhismartek.patashalaerp.R;

public class TimeTableViewHolder extends RecyclerView.ViewHolder {
    public TextView tvPeriods,tvDays;
    public TimeTableViewHolder(View itemView) {
        super(itemView);
        tvPeriods = itemView.findViewById(R.id.tv_period_no);
        tvDays = itemView.findViewById(R.id.tv_days);
    }
}
