package com.tdt.billionaire;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;

/**
 * Created by TE-iA on 04/03/17.
 */

public class ReadyActivity extends Activity implements View.OnClickListener{

    private FrameLayout lyMilestone;
    private LinearLayout lyHelp, lyQuestion;
    private TextView tv, tvQues, tvCTimer;
    private Button btn1, btnA, btnB, btnC, btnD;
    protected ImageButton help1, help2, help3, help4;
    private ImageView mile1, mile2, mile3;
    private MediaPlayer mediaPlayer, mediaPlayer1, mediaBackGround;

    protected int number = 1;
    private int quesPositon, posA, posB, posC;
    private Question question;
    private Random rd;
    private CountDownTimer cTimer;

    private DataAdapter mDbHelper;
    private Boolean selectTrue = false, isStarted = false;
    protected String rightAnswer = "";
    private Animation animation, animation1;
    private Handler handler = new Handler();
    private FinishDialog finishDialog;

    private ArrayList<Integer> passedList;// Danh sách chứa vị trí các câu hỏi (trong mảng dataAdapter) đã được xuất hiện.
    private ArrayList<String> answerDataList;// Danh sách chứa câu trả lời với các lựa chọn A, B, C, D.
    private ArrayList<Button> buttonList;
    private ArrayList<Question> dataAdapter;// Danh sách câu hỏi lấy từ csdl

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            makeInvisible(false);
            if (number == 1)
                animHelp();
            if (number == 1 || number == 5 || number == 10 || number == 15) {
                animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_select);
                switch (number) {
                    case 5:
                        mile1.startAnimation(animation1);
                        break;
                    case 10:
                        mile1.clearAnimation();
                        mile2.startAnimation(animation1);
                        break;
                    case 15:
                        mile1.clearAnimation();
                        mile2.clearAnimation();
                        mile3.startAnimation(animation1);
                        break;
                    default:
                        mile1.clearAnimation();
                        mile2.clearAnimation();
                        mile3.clearAnimation();
                        break;
                }
                animMileStone();
            }

            data_LoadtoView();
            animQuestion();
            playMedia(number);
        }
    };

    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            if (selectTrue) {
                btn1.setBackgroundResource(R.drawable.select_true);
                btn1.startAnimation(animation1);
            }
            else
                btn1.setBackgroundResource(R.drawable.select_wrong);
        }
    };

    /*
     * Set visibility
     */
    public void makeInvisible(boolean isHide) {
        if (isHide) {
            tv.setVisibility(View.INVISIBLE);
            tvQues.setVisibility(View.INVISIBLE);
            tvCTimer.setVisibility(View.INVISIBLE);
            btnA.setVisibility(View.INVISIBLE);
            btnB.setVisibility(View.INVISIBLE);
            btnC.setVisibility(View.INVISIBLE);
            btnD.setVisibility(View.INVISIBLE);
            lyHelp.setVisibility(View.INVISIBLE);
        }
        else {
            tv.setVisibility(View.VISIBLE);
            tvQues.setVisibility(View.VISIBLE);
            tvCTimer.setVisibility(View.VISIBLE);
            btnA.setVisibility(View.VISIBLE);
            btnB.setVisibility(View.VISIBLE);
            btnC.setVisibility(View.VISIBLE);
            btnD.setVisibility(View.VISIBLE);
            lyHelp.setVisibility(View.VISIBLE);
        }
    }

    /*
     * Handler playing audio
     */
    public void playMedia(int number) {
        switch (number) {
            case 1:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no1);
                break;
            case 2:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no2);
                break;
            case 3:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no3);
                break;
            case 4:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no4);
                break;
            case 5:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no5);
                break;
            case 6:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no6);
                break;
            case 7:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no7);
                break;
            case 8:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no8);
                break;
            case 9:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no9);
                break;
            case 10:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no10);
                break;
            case 11:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no11);
                break;
            case 12:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no12);
                break;
            case 13:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no13);
                break;
            case 14:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no14);
                break;
            case 15:
                mediaPlayer1 = mediaPlayer1.create(this, R.raw.ques_no15);
                break;
        }
        mediaPlayer1.start();
        if (number == 1) {
            mediaBackGround = mediaBackGround.create(this, R.raw.phase1);
        }

        if (number == 6) {
            mediaBackGround.reset();
            mediaBackGround = mediaBackGround.create(this, R.raw.phase2);
        }

        if (number == 11) {
            mediaBackGround.reset();
            mediaBackGround = mediaBackGround.create(this, R.raw.phase3);
        }
        mediaBackGround.setLooping(true);
        mediaBackGround.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);

        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_select);

        rd = new Random();
        question = new Question();
        mediaPlayer = new MediaPlayer();
        mediaPlayer1 = new MediaPlayer();
        mediaBackGround = new MediaPlayer();

        passedList = new ArrayList<>();
        answerDataList = new ArrayList<>();
        buttonList = new ArrayList<>();
        dataAdapter = new ArrayList<>();

        btnA = (Button) findViewById(R.id.btnA);
        btnA.setOnClickListener(this);
        btnB = (Button) findViewById(R.id.btnB);
        btnB.setOnClickListener(this);
        btnC = (Button) findViewById(R.id.btnC);
        btnC.setOnClickListener(this);
        btnD = (Button) findViewById(R.id.btnD);
        btnD.setOnClickListener(this);

        help1 = (ImageButton) findViewById(R.id.help1);
        help1.setOnClickListener(this);
        help2 = (ImageButton) findViewById(R.id.help2);
        help2.setOnClickListener(this);
        help3 = (ImageButton) findViewById(R.id.help3);
        help3.setOnClickListener(this);
        help4 = (ImageButton) findViewById(R.id.help4);
        help4.setOnClickListener(this);

        mile1 = (ImageView) findViewById(R.id.mile1);
        mile2 = (ImageView) findViewById(R.id.mile2);
        mile3 = (ImageView) findViewById(R.id.mile3);
        tv = (TextView) findViewById(R.id.tv);
        tvQues = (TextView) findViewById(R.id.tvQues);
        tvCTimer = (TextView) findViewById(R.id.tvCTimer);

        lyMilestone = (FrameLayout) findViewById(R.id.lyMilestone);
        lyMilestone.setVisibility(View.INVISIBLE);

        lyHelp = (LinearLayout) findViewById(R.id.lyHelp);
        lyQuestion = (LinearLayout) findViewById(R.id.lyQuestion);

        buttonList = new ArrayList<Button>();
        mediaPlayer = mediaPlayer.create(this, R.raw.start);
        loadDatabase();
        makeInvisible(true);

        finishDialog = new FinishDialog(ReadyActivity.this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mediaPlayer.start();
        if (number == 1 && isStarted == false) {
            handler.postDelayed(runnable, 3000);
            isStarted = true;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
        mediaBackGround.pause();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.help1:
                CallDialog callDialog = new CallDialog(ReadyActivity.this);
                callDialog.show();
                break;
            case R.id.help2:
                RequestDialog requestDialog = new RequestDialog(ReadyActivity.this);
                requestDialog.show();
                break;
            case R.id.help3:
                DropAnswerDialog dropAnswerDialog = new DropAnswerDialog(ReadyActivity.this);
                dropAnswerDialog.show();
                break;
            case R.id.help4:
                ChangeQuesDialog changeQuesDialog = new ChangeQuesDialog(ReadyActivity.this);
                changeQuesDialog.show();
                break;
            default:
                btn1 = (Button) view;
                btn1.setBackgroundResource(R.drawable.selected);
                if (btn1.getText().toString().equals("") == false) {
                    if (btn1.getText().toString().equals(rightAnswer)) {
                        selectTrue = true;
                        number++;
                        handler.postDelayed(runnable1, 1000);
                        if (number < 16) {
                            handler.postDelayed(runnable, 2000);
                        }
                    }
                    else {
                        selectTrue = false;
                        handler.postDelayed(runnable1, 1000);
                        finishDialog.show();
                    }
                }
                break;
        }
    }

    /*
     * Load data to views
     */
    public void data_LoadtoView() {
        if (number > 1)
            btn1.clearAnimation();

        quesPositon = rd.nextInt(dataAdapter.size());
        while (isPassed(quesPositon)) {
            quesPositon = rd.nextInt(dataAdapter.size());
        }
        question = dataAdapter.get(quesPositon);
        answerDataList = new ArrayList<>(Arrays.asList(question.optA, question.optB, question.optC, question.optD));

        tv.setText("Câu hỏi số " + number + ": ");
        tvQues.setText(question.ques);
        btnA.setText(randomAnswer('A'));
        btnB.setText(randomAnswer('B'));
        btnC.setText(randomAnswer('C'));
        btnD.setText(randomAnswer('D'));
        rightAnswer = question.optR.toString();

        buttonList = new ArrayList<>(Arrays.asList(btnA, btnB, btnC, btnD));
        passedList.add(quesPositon);

        countDownTimer("stop");
        countDownTimer("start");
    }

    /*
     * Checking if the question was passed
     */
    public boolean isPassed(int pos) {
        for (int i = 0; i < passedList.size(); i++) {
            if (pos == passedList.get(i))
                return true;
        }
        return false;
    }

    /*
     * Random answers from database to buttons A,B,C,D
     */
    public String randomAnswer(char pos) {
        int answerData = rd.nextInt(answerDataList.size());
        switch (pos) {
            case 'A':
                posA = answerData;
                btnA.setBackgroundResource(R.drawable.answer_a);
                break;
            case 'B':
                while (answerData == posA)
                    answerData = rd.nextInt(answerDataList.size());
                posB = answerData;
                btnB.setBackgroundResource(R.drawable.answer_b);
                break;
            case 'C':
                while (answerData == posA || answerData == posB)
                    answerData = rd.nextInt(answerDataList.size());
                posC = answerData;
                btnC.setBackgroundResource(R.drawable.answer_c);
                break;
            case 'D':
                while (answerData == posA || answerData == posB || answerData == posC)
                    answerData = rd.nextInt(answerDataList.size());
                btnD.setBackgroundResource(R.drawable.answer_d);
                break;
        }
        return answerDataList.get(answerData);
    }

    /*
     * Load data from database
     */
    public void loadDatabase() {
        try {
            mDbHelper = new DataAdapter(getApplicationContext());
            mDbHelper.createDatabase();
            mDbHelper.open();
            dataAdapter = mDbHelper.getDataAdapter();//Lấy hết các câu hỏi có trong database lưu vào danh sách dataAdapter
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mDbHelper.close();
    }

    /*
     * Count down Timer
     */
    public void countDownTimer(String type) {
        if (type.equals("start")) {
            cTimer = new CountDownTimer(31000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvCTimer.setText("" + millisUntilFinished/1000);
                }
                @Override
                public void onFinish() {
                    finishDialog.show();
                    tvCTimer.setText("0");
                }
            };
            cTimer.start();
        }
        else {
            if (cTimer != null)
                cTimer.cancel();
        }
    }

    /*
     * 50/50
     */
    public void dropHalfAnswer() {
        int pos1 = 0, pos2 = 0;
        while (pos1 == pos2 || (buttonList.get(pos1)).getText().toString().equals(rightAnswer)
                || (buttonList.get(pos2)).getText().toString().equals(rightAnswer)) {
            pos1 = rd.nextInt(buttonList.size());
            pos2 = rd.nextInt(buttonList.size());
        }
        (buttonList.get(pos1)).setText("");
        (buttonList.get(pos2)).setText("");
    }

    /*
     * Animation zone
     */
    public void animHelp() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.down_in);
        lyHelp.startAnimation(animation);
    }

    public void animQuestion() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up_in);
        lyQuestion.startAnimation(animation);
    }

    public void animMileStone() {
        lyMilestone.setVisibility(View.VISIBLE);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in_out);
        lyMilestone.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.out_in);
                lyMilestone.startAnimation(animation);
                lyMilestone.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
