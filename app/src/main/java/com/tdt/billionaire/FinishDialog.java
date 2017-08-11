package com.tdt.billionaire;;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class FinishDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity activity;
    ImageButton btnOk;
    FrameLayout layout;
    TextView tv;
    public FinishDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_finish);
        btnOk = (ImageButton) findViewById(R.id.btnYes);
        btnOk.setOnClickListener(this);
        layout = (FrameLayout) findViewById(R.id.layout);
        layout.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv);
        if (((ReadyActivity)activity).number < 15)
           tv.setText("Bạn bị dừng cuộc chơi tại đây");
    }

    @Override
    public void onClick(View view) {
        activity.finish();
    }
}
