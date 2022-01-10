package com.roadstar.driverr.app.module.ui.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.resp.Payment;
import com.roadstar.driverr.app.data.resp.Request;
import com.roadstar.driverr.app.data.resp.RequestsModel;
import com.roadstar.driverr.common.utils.AppUtils;

public class PaymentDialogue {

    Activity activity;
    RequestsModel requestsModel;
    private LinearLayout ll04ContentLayerPayment;
    private TextView invoiceTxt;
    private TextView txt04BasePrice;
    private TextView txt04Distance;
    private TextView txt04Tax;
    private TextView txt04Commision;
    private TextView txt04Total;
    private TextView txt04AmountToPaid;
    private ImageView paymentTypeImg;
    private TextView txt04PaymentMode;
    private AppCompatButton btnConfirmPayment;


    public PaymentDialogue(Activity aa, RequestsModel model) {
        activity = aa;
        requestsModel = model;
        findViews();
    }


    private void findViews() {
        ll04ContentLayerPayment = activity.findViewById(R.id.ll_04_contentLayer_payment);
        invoiceTxt = activity.findViewById(R.id.invoice_txt);
        txt04BasePrice = activity.findViewById(R.id.txt04BasePrice);
        txt04Distance = activity.findViewById(R.id.txt04Distance);
        txt04Tax = activity.findViewById(R.id.txt04Tax);
        txt04Commision = activity.findViewById(R.id.txt04Commision);
        txt04Total = activity.findViewById(R.id.txt04Total);
        txt04AmountToPaid = activity.findViewById(R.id.txt04AmountToPaid);
        paymentTypeImg = activity.findViewById(R.id.paymentTypeImg);
        txt04PaymentMode = activity.findViewById(R.id.txt04PaymentMode);
        btnConfirmPayment = activity.findViewById(R.id.btnConfirmPayment);

        ll04ContentLayerPayment.setVisibility(View.VISIBLE);
        setData();
    }


    void setData() {

        Request request = requestsModel.getRequests().get(0).getRequest();
        Payment payment = request.getPayment();
        txt04BasePrice.setText(payment.getFixed());
        txt04Distance.setText(payment.getDistance());
        txt04Tax.setText(payment.getTax());
        txt04Commision.setText(AppUtils.formatAndRoundOff(AppUtils.convertStringToDouble(payment.getCommision())));
        txt04Total.setText(payment.getTotal());
        txt04AmountToPaid.setText(payment.getPayable());
        txt04PaymentMode.setText(request.getPaymentMode());
        invoiceTxt.setText(activity.getResources().getString(R.string.invoice) + " " + request.getBookingId());

    }


}
