package com.roadstar.driverr.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.roadstar.driverr.R;
/**
 * Created by JunaidAhmed on 5/18/2018.
 */
public class PhotoPickerDialog extends BottomSheetDialog implements View.OnClickListener {

    private Context context;
    private OnPickerItemClickListener onPickerItemClickListener;
    private BottomSheetBehavior<View> mBehavior;

    public PhotoPickerDialog(@NonNull Context context, OnPickerItemClickListener onPickerItemClickListener) {
        super(context);
        this.context = context;
        this.onPickerItemClickListener = onPickerItemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View contentView = View.inflate(getContext(),R.layout.dialog_photo_picker, null);
        setContentView(contentView);
    }

    private void setTransparentBackground() {
        View contentView = View.inflate(getContext(),R.layout.dialog_photo_picker, null);
        setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent())
                .getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) contentView.getParent()).setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = PhotoPickerDialog.this;

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);
        dialog.setCanceledOnTouchOutside(true);
        setTransparentBackground();
        TextView textViewCamera = dialog.findViewById(com.roadstar.driverr.R.id.tv_photo_lib);
        TextView textViewGallery = dialog.findViewById(com.roadstar.driverr.R.id.tv_take_photo);
        TextView textViewCancel = dialog.findViewById(com.roadstar.driverr.R.id.cancel_dialog_tv);
        textViewCamera.setOnClickListener(this);
        textViewGallery.setOnClickListener(this);
        textViewCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        onPickerItemClickListener.onPickerItemClick(view);
    }

    public interface OnPickerItemClickListener {
        void onPickerItemClick(View view);
    }
}
