package in.varadhismartek.patashalaerp.DashboardModule.VisitorModule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.R;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorViewHolder> {
    private Context context;
    ArrayList<VisitorModule> visitorModules;
    String rvType, currentDate;
    private int year, month, date;

    public VisitorAdapter(Context context, ArrayList<VisitorModule> visitorModules, String rvType) {
        this.context = context;
        this.visitorModules = visitorModules;
        this.rvType = rvType;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (rvType) {
            case Constant.RV_VISITOR_ROW:

                return new VisitorViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.visitor_row, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
        switch (rvType) {
            case Constant.RV_VISITOR_ROW:

                Calendar calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                date = calendar.get(Calendar.DAY_OF_MONTH);

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                currentDate = simpleDate.format(calendar.getTime());
                Log.i("currentDate**", "" + currentDate);

                if (currentDate.equals(visitorModules.get(position).getEntry_date())) {

                    holder.ll_main.setBackgroundColor(context.getResources().getColor(R.color.red));
                }
                if(!visitorModules.get(position).getVisitor_photo().equals("")) {
                  /*  Glide.with(context).load(Constant.IMAGE_LINK + visitorModules.get(position).getVisitor_photo()).
                            into(holder.visitor_image);
*/
                    Picasso.with(context).
                            load(Constant.IMAGE_LINK + visitorModules.get(position).getVisitor_photo())
                            .placeholder(R.drawable.patashala_logo)
                            .resize(100, 100)
                            .centerCrop()
                            .into(holder.visitor_image);

                }
                else {
                    holder.visitor_image.setImageResource(R.drawable.patashala_logo);
                }
                holder.visitDate.setText("Visit Date : " + visitorModules.get(position).getEntry_date());
                holder.visitTime.setText("Visit Time : " + visitorModules.get(position).getEntry_time());
                holder.visitID.setText("ID : " + visitorModules.get(position).getVisitor_id());
                holder.visitName.setText("Name : " + visitorModules.get(position).getVisitor_name());
                holder.visitPurpose.setText(visitorModules.get(position).getPurpose());
                break;

        }

    }

    @Override
    public int getItemCount() {
        switch (rvType) {
            case Constant.RV_VISITOR_ROW:
                return visitorModules.size();
        }
        return 0;
    }
}
