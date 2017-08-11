package com.tdt.billionaire;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CallDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity activity;
    public Context context;
    public ImageButton call1, call2, call3, call4;
    ImageView call_full;
    TextView tv1, tv2, tv3, tv4;


    public CallDialog(Activity activity) {
            super(activity);
            this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            setContentView(R.layout.dialog_call);
            call1 = (ImageButton) findViewById(R.id.call1);
            call1.setOnClickListener(this);
            call2 = (ImageButton) findViewById(R.id.call2);
            call2.setOnClickListener(this);
            call3 = (ImageButton) findViewById(R.id.call3);
            call3.setOnClickListener(this);
            call4 = (ImageButton) findViewById(R.id.call4);
            call4.setOnClickListener(this);
            call_full = (ImageView) findViewById(R.id.call_full);
            call_full.setOnClickListener(this);
            tv1 = (TextView) findViewById(R.id.tv1);
            tv2 = (TextView) findViewById(R.id.tv2);
            tv3 = (TextView) findViewById(R.id.tv3);
            tv4 = (TextView) findViewById(R.id.tv4);
            ((ReadyActivity)activity).help1.setBackgroundResource(R.drawable.callx);
            ((ReadyActivity)activity).help1.setEnabled(false);

    }

    @Override
    public void onClick(View view) {
        String ans = ((ReadyActivity) activity).rightAnswer;
        switch (view.getId()) {
            case R.id.call1:
                call_full.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.VISIBLE);
                call_full.setBackgroundResource(R.drawable.call_1);
                tv1.setText("Tôi nghĩ " + ans + " là câu trả lời đúng");
                break;
            case R.id.call2:
                call_full.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                call_full.setBackgroundResource(R.drawable.call_2);
                tv2.setText("Tôi nghĩ " + ans + " là câu trả lời đúng");
                break;
            case R.id.call3:
                call_full.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                call_full.setBackgroundResource(R.drawable.call_3);
                tv3.setText("Tôi nghĩ " + ans + " là câu trả lời đúng");
                break;
            case R.id.call4:
                call_full.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.VISIBLE);
                call_full.setBackgroundResource(R.drawable.call_4);
                tv4.setText("Tôi nghĩ " + ans + " là câu trả lời đúng");
                break;
            case R.id.call_full:
                dismiss();
                cancel();
                break;
            default:
                cancel();
                dismiss();
                break;
        }
    }
}
