package in.varadhismartek.patashalaerp.DashboardModule.timetable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.varadhismartek.patashalaerp.R;


public class TimeTableViewHolder extends RecyclerView.ViewHolder {

    TextView tvStartTime;
    public TimeTableViewHolder(@NonNull View itemView) {
        super(itemView);
        tvStartTime= itemView.findViewById(R.id.tvStartTime);
    }
}
