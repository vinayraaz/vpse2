package in.varadhismartek.patashalaerp.DashboardModule.VisitorModule;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.varadhismartek.patashalaerp.R;

public class VisitorViewHolder extends RecyclerView.ViewHolder {
    public TextView visitDate,visitTime,visitID,visitName,visitPurpose;
    public LinearLayout ll_main;
    public ImageView visitor_image;
    public VisitorViewHolder(View itemView) {
        super(itemView);
        ll_main =itemView.findViewById(R.id.ll_main);
        visitor_image =itemView.findViewById(R.id.visitor_image);
        visitDate =itemView.findViewById(R.id.visit_date);
        visitTime =itemView.findViewById(R.id.visit_time);
        visitID =itemView.findViewById(R.id.visit_id);
        visitName =itemView.findViewById(R.id.visit_name);
        visitPurpose =itemView.findViewById(R.id.visit_purpose);
    }
}
