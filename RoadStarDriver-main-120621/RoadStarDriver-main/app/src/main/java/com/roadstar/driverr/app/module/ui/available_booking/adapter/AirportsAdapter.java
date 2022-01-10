package com.roadstar.driverr.app.module.ui.available_booking.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.resp.DocumentsListResp;

import java.util.ArrayList;

public class AirportsAdapter extends RecyclerView.Adapter<AirportsAdapter.MyViewHolder> {

    Activity activity;
    ArrayList<DocumentsListResp> documentsLists;
    Click _click;

    public AirportsAdapter(Activity mActivity, ArrayList<DocumentsListResp> array,Click click) {
        this.documentsLists = array;
        this.activity = mActivity;
        this._click = click;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_documents, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DocumentsListResp documentsList = documentsLists.get(position);
        holder.tvNameOfDocument.setText(documentsList.getName());

        holder.tvNameOfDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _click.selectAirport(documentsList.getName());
            }
        });


    }

    @Override
    public int getItemCount() {
        return documentsLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvNameOfDocument;

        public MyViewHolder(final View itemView) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.image);
            tvNameOfDocument = itemView.findViewById(R.id.tvDocumentName);

//            tvNameOfDocument.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    imagePickerClick.selectImage(getAdapterPosition());
//                }
//            });
        }
    }

//    public void setImageClickListner(final Click imageClickListner) {
//        imagePickerClick = imageClickListner;
//    }


    public interface Click {
        void selectAirport(String position);
    }

    public void updateImage() {
        AirportsAdapter.this.notifyDataSetChanged();
    }

}
