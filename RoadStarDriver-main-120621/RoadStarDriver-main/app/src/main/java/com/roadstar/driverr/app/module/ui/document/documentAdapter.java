package com.roadstar.driverr.app.module.ui.document;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.models.documentModel;

import java.util.ArrayList;

public class documentAdapter extends RecyclerView.Adapter<documentAdapter.MyViewHolder>  {

    Activity activity;
    ArrayList<documentModel> documentsLists;

    public documentAdapter(Activity mActivity, ArrayList<documentModel> array) {
        this.documentsLists = array;
        this.activity = mActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_list_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        documentModel documentsList = documentsLists.get(position);

        holder.tvNameOfDocument.setText(documentsList.getDocument_name());
        String fullImageUrl = activity.getString(R.string.BASE_URL_IMAGES)+documentsList.getUrl();

        if (documentsList.getUrl() != null)

            Glide
                    .with(activity)
                    .load(fullImageUrl)/*
                    .apply(new RequestOptions().override(0, 100))*/
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(24)))
                    .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return documentsLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvNameOfDocument;

        public MyViewHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.vehicle_image);
            tvNameOfDocument = itemView.findViewById(R.id.tv_attach_photo);
        }
    }

}
