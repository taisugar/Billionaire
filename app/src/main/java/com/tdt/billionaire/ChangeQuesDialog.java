package com.tdt.billionaire;;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ChangeQuesDialog extends Dialog implements View.OnClickListener {
    public Activity activity;
    ImageButton btnYes, btnNo;
    Typeface custom_font;
    public ChangeQuesDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_changeques);
        custom_font = Typeface.createFromAsset(getContext().getAssets(), "Sitka_Bold.ttf");
        btnYes = (ImageButton) findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);
        btnNo = (ImageButton) findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYes:
                ((ReadyActivity)activity).data_LoadtoView();
                ((ReadyActivity)activity).help4.setBackgroundResource(R.drawable.refresh_quesitonx);
                ((ReadyActivity)activity).help4.setEnabled(false);
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
