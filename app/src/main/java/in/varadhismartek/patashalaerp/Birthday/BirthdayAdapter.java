package in.varadhismartek.patashalaerp.Birthday;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.R;

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayViewHolder> {

    String recyclerTag;
    Context context;
    ArrayList<BirthdayModel> list;

    public BirthdayAdapter(ArrayList<BirthdayModel> list, Context context, String recyclerTag ) {
        this.recyclerTag = recyclerTag;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BirthdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        switch (recyclerTag){

            case Constant.RV_BIRTHDAY_ROW:
                return new BirthdayViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.birthday_card_layout, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayViewHolder holder, int position) {

        switch (recyclerTag){

            case Constant.RV_BIRTHDAY_ROW:

                String firstName = list.get(position).getFirstName();
                String lastName = list.get(position).getLastName();

                holder.tv_name.setText(firstName+" "+lastName);
                holder.tv_id.setText(list.get(position).getCustomID());


                String date = list.get(position).getBirthDate();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dateFormatOut = new SimpleDateFormat("dd-MMMM");

                try {
                    Date input = dateFormat.parse(date);
                    String output = dateFormatOut.format(input);

                    holder.tv_date.setText(output);


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!list.get(position).getImage().equals("")){
                    Glide.with(context).load(Constant.IMAGE_URL+list.get(position).getImage()).into(holder.iv_image);
                }


                break;
        }
    }

    @Override
    public int getItemCount() {

        switch (recyclerTag){

            case Constant.RV_BIRTHDAY_ROW:
                return list.size();
        }
        return 0;
    }
}
