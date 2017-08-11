package com.tdt.billionaire;;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DropAnswerDialog extends Dialog implements View.OnClickListener {
    public Activity activity;
    ImageButton btnYes, btnNo;

    public DropAnswerDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_dropanswer);
        btnYes = (ImageButton) findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);
        btnNo = (ImageButton) findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYes:
                ((ReadyActivity) activity).dropHalfAnswer();
                ((ReadyActivity) activity).help3.setBackgroundResource(R.drawable.fiftyx);
                ((ReadyActivity) activity).help3.setEnabled(false);
                dismiss();
                break;
            case R.id.btnNo:
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
    }
}
