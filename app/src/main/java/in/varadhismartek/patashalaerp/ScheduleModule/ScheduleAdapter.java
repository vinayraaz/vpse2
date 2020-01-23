package in.varadhismartek.patashalaerp.ScheduleModule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.R;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {

    private Context mContext;
    private ArrayList<String> strList;
    private String recyclerTag;
    ArrayList<MyScheduleModel> myScheduleList;
    ArrayList<ScheduleModel> scheduleList;

    public ScheduleAdapter(Context mContext, ArrayList<String> strList, String recyclerTag) {
        this.mContext = mContext;
        this.strList = strList;
        this.recyclerTag = recyclerTag;

    }

    public ScheduleAdapter(ArrayList<MyScheduleModel> myScheduleList, Context mContext, String recyclerTag) {
        this.mContext = mContext;
        this.recyclerTag = recyclerTag;
        this.myScheduleList = myScheduleList;

    }

    public ScheduleAdapter(ArrayList<ScheduleModel> scheduleList, String recyclerTag, Context mContext) {
        this.mContext = mContext;
        this.recyclerTag = recyclerTag;
        this.scheduleList = scheduleList;

    }


    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        switch (recyclerTag){

            case Constant.RV_LAYOUT_SCHEDULE_ROW_FRONT:
                return new ScheduleViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.layout_schedule_front, viewGroup, false));

            case Constant.RV_SCHEDULE_ROW_FRONT:
                return new ScheduleViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.layout_schedule_row, viewGroup, false));

          /*  case Constant.RV_SCHEDULE_DIVISION_ROW:
                return new ScheduleViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.chip_layout, viewGroup, false));


            case Constant.RV_SCHEDULE_ATTACHMENT_ROW:
                return new ScheduleViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.attachment_image_row, viewGroup, false));

            case Constant.RV_SCHEDULE_VIEW_EXAM_ROW:
                return new ScheduleViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.exam_view_schedule_row, viewGroup, false));

            case Constant.RV_ADD_SCHEDULE_CLASS_CHIP:
                return new ScheduleViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.class_chip, viewGroup, false));

            case Constant.RV_ADD_SCHEDULE_ADD_AGENDA:
                return new ScheduleViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.add_agenda_row, viewGroup, false));

            case Constant.RV_ADD_SCHEDULE_EXAM_DATE_TIME:
                return new ScheduleViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.add_exam_row, viewGroup, false));
*/

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, final int i) {

        ScheduleActivity scheduleActivity = (ScheduleActivity) mContext;

        switch (recyclerTag){

            case Constant.RV_LAYOUT_SCHEDULE_ROW_FRONT:

                String fromDate = myScheduleList.get(i).getDate();

                holder.tv_date.setText(fromDate);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat monthFormat = new SimpleDateFormat("dd-MMM-yyyy");

                Date date1 = null;

                try {
                    date1 = simpleDateFormat.parse(fromDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                holder.tv_date.setText(monthFormat.format(date1).toString());

                ScheduleAdapter adapter = new ScheduleAdapter(myScheduleList.get(i).getScheduleModelList(),Constant.RV_SCHEDULE_ROW_FRONT, mContext);
                holder.recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
                holder.recycler_view.setNestedScrollingEnabled(false);
                holder.recycler_view.setAdapter(adapter);

                break;

            case Constant.RV_SCHEDULE_ROW_FRONT:

                if (scheduleList.get(i).getImage().length()>0){
                    Glide.with(mContext)
                            .load(Constant.IMAGE_LINK+scheduleList.get(i).getImage())
                            .into(holder.iv_event_image);
                }

                holder.tv_event_title.setText(scheduleList.get(i).getType());
                holder.tv_division.setText(scheduleList.get(i).getDivision());
                holder.tv_fromDate.setText(scheduleList.get(i).getDate());
                holder.tv_toDate.setText(scheduleList.get(i).getToDate());
                holder.tv_event_type.setText(scheduleList.get(i).getScheduleType());

                holder.ll_schedule_row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "ll_schedule_row Clicked...!", Toast.LENGTH_SHORT).show();

                        Bundle bundle = new Bundle();

                        bundle.putSerializable("ScheduleModel", scheduleList.get(i));

                        //scheduleActivity.loadFragment(Constant.SCHEDULE_DETAILS_FRAGMENT, bundle);

                    }
                });

               break;

          /*  case Constant.RV_SCHEDULE_DIVISION_ROW:

                holder.tv_division_name.setText(Constant.ARRAY_LIST_SCHEDULE_DIVISION.get(i));
                holder.iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Constant.ARRAY_LIST_SCHEDULE_DIVISION.remove(i);
                        notifyDataSetChanged();
                    }
                });

                break;

            case Constant.RV_SCHEDULE_ATTACHMENT_ROW:

                Bitmap bitmap = BitmapFactory.decodeFile(strList.get(i));

                holder.iv_attach.setImageBitmap(bitmap);
                holder.iv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        strList.remove(i);
                        notifyDataSetChanged();
                    }
                });
                break;

            case Constant.RV_SCHEDULE_VIEW_EXAM_ROW:

               /* holder.tv_subject.setText();
                holder.tv_date.setText();
                holder.tv_time.setText();*/

              //  break;

       /*     case Constant.RV_ADD_SCHEDULE_CLASS_CHIP:
                holder.tv_className.setText(strList.get(i));

                break;

            case Constant.RV_ADD_SCHEDULE_ADD_AGENDA:

                if(i == 0){
                    holder.ll_agenda_row.setBackgroundResource(R.color.green);

                }else if (i%2 != 0){
                    holder.ll_agenda_row.setBackgroundResource(R.color.white);

                }

                holder.ll_agenda_row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case Constant.RV_ADD_SCHEDULE_EXAM_DATE_TIME:
                break;
*/

        }
    }

    @Override
    public int getItemCount() {

        switch (recyclerTag) {

            case Constant.RV_LAYOUT_SCHEDULE_ROW_FRONT:
                return myScheduleList.size();

            case Constant.RV_SCHEDULE_ROW_FRONT:
                return scheduleList.size();

       /*     case Constant.RV_SCHEDULE_DIVISION_ROW:
                return strList.size();

            case Constant.RV_SCHEDULE_ATTACHMENT_ROW:
                return strList.size();

            case Constant.RV_SCHEDULE_VIEW_EXAM_ROW:
                return 5;

            case Constant.RV_ADD_SCHEDULE_CLASS_CHIP:
                return strList.size();

            case Constant.RV_ADD_SCHEDULE_ADD_AGENDA:
                return 5;

            case Constant.RV_ADD_SCHEDULE_EXAM_DATE_TIME:
                return 5;*/
        }

        return 0;
    }

}
