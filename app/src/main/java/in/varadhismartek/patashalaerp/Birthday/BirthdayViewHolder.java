package in.varadhismartek.patashalaerp.Birthday;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.varadhismartek.patashalaerp.R;


class BirthdayViewHolder extends RecyclerView.ViewHolder {

    TextView tv_name,tv_id,tv_date;
    ImageView iv_image;

    public BirthdayViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_name = itemView.findViewById(R.id.tv_name);
        tv_id = itemView.findViewById(R.id.tv_id);
        tv_date = itemView.findViewById(R.id.tv_date);
        iv_image = itemView.findViewById(R.id.iv_image);
    }
}
