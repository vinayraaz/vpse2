package in.varadhismartek.patashalaerp.GalleryModule.Utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.varadhismartek.patashalaerp.GalleryModule.Models.GalleryImage;
import in.varadhismartek.patashalaerp.GalleryModule.ViewHolders.ImageViewHolder;
import in.varadhismartek.patashalaerp.R;


public class PlacesGalleryAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private List<GalleryImage> gallery = new ArrayList<>();
    private int standardMargin = 0;
    private String currentImageUrl = "";
    private Context context;
    private ImageView parent;
    private TextView size;
    private RecyclerView recyclerView;
    private OnClickCallback onClickCallback;
    private LanguageHelper languageHelper;

    public PlacesGalleryAdapter(Context context, List<String> gallery, ImageView parent, TextView size, RecyclerView recyclerView, LanguageHelper languageHelper) {
        this.parent = parent;
        this.context = context;
        this.size = size;
        this.recyclerView = recyclerView;
        this.standardMargin = ScreenUtils.convertDpToPixel(context, 2);
        this.languageHelper = languageHelper;

        if (gallery != null) {
            for (int i = 0; i < gallery.size(); i++) {
                GalleryImage galleryImage = new GalleryImage();
                galleryImage.setImageUrl(gallery.get(i));
                if (i == 0) {
                    galleryImage.setCenter(true);
                    currentImageUrl = galleryImage.getImageUrl();
                    populateParentImage();
                } else {
                    galleryImage.setCenter(false);
                }
                this.gallery.add(galleryImage);
            }

            setAmount(1, getItemCount());
            setOnClickListeners();
        }
    }

    public void setCallback(OnClickCallback onClickCallback) {
        this.onClickCallback = onClickCallback;
    }

    private void setAmount(int current, int size) {
        if (this.languageHelper == null) {
            this.size.setText(current + context.getString(R.string.out_of) + size);
        } else {
            this.size.setText(current + this.languageHelper.getOutOf() + size);
        }
    }

    private void populateParentImage() {
        Picasso.with(context)
                .load(currentImageUrl)
                .transform(new RoundedTransformation(20, 0))
                .into(parent, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(R.drawable.clj_no_image)
                                .transform(new RoundedTransformation(20, 0))
                                .into(parent);
                    }
                });
    }

    private void setOnClickListeners() {
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickCallback != null) {
                    onClickCallback.OnClick(currentImageUrl);
                }
            }
        });

        parent.setOnTouchListener(new OnSwipeTouchListener(context, new OnSwipeInterface() {
            @Override
            public void onSwipeRight() {
                //Toast.makeText(context, "swipeRight", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeLeft() {
                //Toast.makeText(context, "swipeLeft", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeTop() {
                //Toast.makeText(context, "swipeTop", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeBottom() {
                //  Toast.makeText(context, "swipeBottom", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.clj_item_gallery, viewGroup, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder viewHolder, final int i) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (i == 0) {
            layoutParams.bottomMargin = 0;
            layoutParams.topMargin = 0;
            layoutParams.rightMargin = standardMargin;
            layoutParams.leftMargin = 0;
        } else if (i == gallery.size() - 1) {
            layoutParams.bottomMargin = 0;
            layoutParams.topMargin = 0;
            layoutParams.rightMargin = 0;
            layoutParams.leftMargin = standardMargin;
        } else {
            layoutParams.bottomMargin = 0;
            layoutParams.topMargin = 0;
            layoutParams.rightMargin = standardMargin;
            layoutParams.leftMargin = standardMargin;
        }
        viewHolder.galleryImage.setLayoutParams(layoutParams);

        viewHolder.galleryImage.setColorFilter(0x77ffffff, PorterDuff.Mode.SRC_ATOP);
        viewHolder.galleryImage.invalidate();

        if (gallery.get(i).isCenter()) {
            viewHolder.galleryImage.clearColorFilter();
            viewHolder.galleryImage.invalidate();
        }

        viewHolder.galleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i > 0 && i < gallery.size()) {
                    recyclerView.smoothScrollToPosition(i);
                }
                updateCenter(i, viewHolder.galleryImage);

                currentImageUrl = gallery.get(i).getImageUrl();

                populateParentImage();
            }
        });

        Picasso.with(this.context)
                .load(gallery.get(i).getImageUrl())
                .transform(new RoundedTransformation(20, 0))
                .into(viewHolder.galleryImage, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(R.drawable.clj_no_image)
                                .transform(new RoundedTransformation(20, 0))
                                .into(viewHolder.galleryImage);
                    }
                });

    }

    private void updateCenter(int center, ImageView imageView) {
        if (gallery != null) {
            for (GalleryImage galleryImage : gallery) {
                galleryImage.setCenter(false);
            }

            gallery.get(center).setCenter(true);

            if (gallery.get(center).isCenter()) {
                imageView.clearColorFilter();
                imageView.invalidate();
            }

            setAmount(center + 1, getItemCount());
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (gallery != null) {
            return gallery.size();
        }
        return -1;
    }

}
