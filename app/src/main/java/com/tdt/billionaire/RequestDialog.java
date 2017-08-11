package com.tdt.billionaire;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RequestDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity activity;
    ImageView imgRequest;
    Random rd;
    int number;
    ArrayList<Integer> arrayList;
    public RequestDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_request);
        rd = new Random();
        arrayList = new ArrayList<>(Arrays.asList(1,2,3,4));
        number = rd.nextInt(arrayList.size());
        imgRequest = (ImageView) findViewById(R.id.imgRequest);
        imgRequest.setOnClickListener(this);
        ((ReadyActivity)activity).help2.setBackgroundResource(R.drawable.helpx);
        ((ReadyActivity)activity).help2.setEnabled(false);
    }

    @Override
    public void onStart() {
        switch (number) {
            case 1:
                imgRequest.setBackgroundResource(R.drawable.result_1);
                break;
            case 2:
                imgRequest.setBackgroundResource(R.drawable.result_2);
                break;
            case 3:
                imgRequest.setBackgroundResource(R.drawable.result_3);
                break;
            case 4:
                imgRequest.setBackgroundResource(R.drawable.result_4);
                break;
        }
        super.onStart();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgRequest:
                dismiss();
                cancel();
                break;
        }
    }
}
