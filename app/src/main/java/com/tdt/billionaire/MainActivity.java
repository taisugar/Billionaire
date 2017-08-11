package com.tdt.billionaire;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private boolean soundOff = false;
    private Vibrator vibe = null;
    private ImageView logo;
    private ImageButton ibtn_OK, ibtn_Cancel, ibtn_start, ibtn_Sound;
    private RelativeLayout layout_start;
    private FrameLayout layout_ready_parent, layout_ready_child;
    private Animation animation;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (keyCode == keyEvent.KEYCODE_BACK) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("BẠN RẤT THÔNG MINH?");
            alertDialog.setPositiveButton("Đúng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                            int pid = android.os.Process.myPid();
                            android.os.Process.killProcess(pid);
                        }
                    });

            alertDialog.setNegativeButton("Sai", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            alertDialog.show();
        }
        return super.onKeyDown(keyCode, keyEvent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = mediaPlayer.create(this, R.raw.opening);
        mediaPlayer.setLooping(true);
        ibtn_Sound = (ImageButton) findViewById(R.id.ibt_Sound);
        ibtn_Sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundOff == false) {
                    v.setBackgroundResource(R.drawable.soundx);
                    soundOff = true;
                    mediaPlayer.pause();
                    vibe.vibrate(200);
                } else {
                    v.setBackgroundResource(R.drawable.sound);
                    mediaPlayer.start();
                    soundOff = false;
                }
            }
        });

        logo = (ImageView) findViewById(R.id.logo);
        ibtn_start = (ImageButton) findViewById(R.id.img_start);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ibtn_OK = (ImageButton) findViewById(R.id.ibt_OK);
        buttonEffect(ibtn_OK);
        ibtn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReadyActivity.class);
                startActivity(intent);
                mediaPlayer.pause();
                layout_ready_parent.setVisibility(View.INVISIBLE);
                ibtn_start.setVisibility(View.VISIBLE);
                ibtn_Sound.setVisibility(View.VISIBLE);
            }
        });

        ibtn_Cancel = (ImageButton) findViewById(R.id.ibt_Cancel);
        buttonEffect(ibtn_Cancel);
        ibtn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_ready_parent.setVisibility(View.INVISIBLE);
                ibtn_start.setVisibility(View.VISIBLE);
                ibtn_Sound.setVisibility(View.VISIBLE);
            }
        });

        layout_ready_child = (FrameLayout) findViewById(R.id.layout_ready_child);
        layout_ready_parent = (FrameLayout) findViewById(R.id.layout_ready_parent);
        layout_ready_parent.setVisibility(View.INVISIBLE);

        layout_ready_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_ready_parent.setVisibility(View.INVISIBLE);
                ibtn_start.setVisibility(View.VISIBLE);
                ibtn_Sound.setVisibility(View.VISIBLE);
            }
        });

        layout_start = (RelativeLayout) findViewById(R.id.layout_start);
        layout_start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    layout_ready_parent.setVisibility(View.VISIBLE);
                    ibtn_start.setVisibility(View.INVISIBLE);
                    ibtn_Sound.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
    }

    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0a751b4, PorterDuff.Mode.SCREEN);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

    public void animTap(){
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        ibtn_start.startAnimation(animation);
    }

    public void animLogo() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        logo.startAnimation(animation);
    }

    @Override
    public void onStart() {
        super.onStart();
        animLogo();
        animTap();
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
