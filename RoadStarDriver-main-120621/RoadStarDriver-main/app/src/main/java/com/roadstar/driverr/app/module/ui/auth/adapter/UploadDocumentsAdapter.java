package com.roadstar.driverr.app.module.ui.auth.adapter;

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

public class UploadDocumentsAdapter extends RecyclerView.Adapter<UploadDocumentsAdapter.MyViewHolder> {

    Activity activity;
    ArrayList<DocumentsListResp> documentsLists;
    imagePickerClick imagePickerClick;

    public UploadDocumentsAdapter(Activity mActivity, ArrayList<DocumentsListResp> array) {
        this.documentsLists = array;
        this.activity = mActivity;
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

        if (documentsList.getFileName() == null) {
            holder.tvNameOfDocument.setText(documentsList.getName());
        } else {
            holder.tvNameOfDocument.setText(documentsList.getFileName());
        }

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
            imageView = itemView.findViewById(R.id.image);
            tvNameOfDocument = itemView.findViewById(R.id.tvDocumentName);

            tvNameOfDocument.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imagePickerClick.selectImage(getAdapterPosition());
                }
            });
        }
    }

    public void setImageClickListner(final imagePickerClick imageClickListner) {
        imagePickerClick = imageClickListner;
    }


    public interface imagePickerClick {
        void selectImage(int position);
    }

    public void updateImage() {
        UploadDocumentsAdapter.this.notifyDataSetChanged();
    }

}
