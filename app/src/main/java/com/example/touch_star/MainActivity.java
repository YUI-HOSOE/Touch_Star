package com.example.touch_star;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int currentImageViewIndex = 0;
    private int count;
    boolean starting = false;
    private int roop = 0;
    private Handler handler;
    private Runnable runnable;


    ImageView[] stars = new ImageView[6];
    TextView txtstart;
    TextView txtScore, txtResult;
    int index = 0;
    private Timer timer;
    int[] id = {R.id.star1, R.id.star2, R.id.star3, R.id.star4, R.id.star5, R.id.star6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.txtstart).setOnClickListener(this);
        txtScore = findViewById(R.id.txtScore);
        txtResult = findViewById(R.id.txtResult);


        for (int i = 0; i < id.length; i++) {
            stars[i] = findViewById(id[i]);
            stars[i].setOnClickListener(this);
            stars[i].setVisibility(View.INVISIBLE);
        }

        List<ImageView> listStars = new ArrayList<>();
        for (int i = 0; i < stars.length; i++) {
            listStars.add(stars[i]);
        }


        for (int i = 0; i < listStars.size(); i++) {
            ViewGroup.LayoutParams layoutParams = listStars.get(i).getLayoutParams();
            layoutParams.width = 10; // 変更したい幅を指定
            layoutParams.height = 10; // 変更したい高さを指定
            listStars.get(i).setLayoutParams(layoutParams);
        }
    }

    @Override
    public void onClick(View v) {
        //startを押すとアニメーションがスタート
        if (v.getId() == R.id.txtstart) {
            System.out.println("なんでやねん");
            roop = 0;
            count = 0;
            starting = true;
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    animateImage();
                }
            };
            handler.postDelayed(runnable, 1000);
        } else if (starting) {
            ((ImageView) v).setVisibility(View.INVISIBLE);
            System.out.println("nanndemo");
            count++;

        }
        txtScore.setText(count + "0点");


//    Timer time =new Timer();
//    time.scheduleAtFixedRate(new TimerTask() {
//
//        @Override
//        public void run() {
//            if (index < id.length) {
//                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.size_up);
//                stars[index].startAnimation(animation);
//                index++;
//            } else {
//                timer.cancel();
//            }
//        }
//
//    },0,2000);


    }

    private void animateImage() {
        if (currentImageViewIndex > 5) {
            currentImageViewIndex = 0;
        }

        ImageView imageView = null;
        switch (currentImageViewIndex) {
            case 0:
                imageView = findViewById(R.id.star1);
                break;
            case 1:
                imageView = findViewById(R.id.star2);
                break;
            case 2:
                imageView = findViewById(R.id.star3);
                break;
            case 3:
                imageView = findViewById(R.id.star4);
                break;
            case 4:
                imageView = findViewById(R.id.star5);
                break;
            case 5:
                imageView = findViewById(R.id.star6);
                roop++;
                break;
        }
//ImageViewのビジビリティ

        imageView.setVisibility(View.VISIBLE);


        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 20f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 20f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(1000);
        animatorSet.start();

        currentImageViewIndex++;
        if (roop < 2) {
            handler.postDelayed(runnable, 300);
        } else {
            ending();
        }
        //終わった後、数秒後、画像を空に。スコアも0点で表示


    }

    public void ending() {


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < stars.length; i++) {
                    stars[i].setVisibility(View.INVISIBLE);
                }
                //スコアに応じたテキストなど
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.size_up);
                String text = "";
                if (count == 12) {
                    text = "Perfect!!";
                } else if (count > 8) {
                    text = "great!";

                } else if (count > 4) {
                    text = "good";
                }else{
                    text="がんばれ";
                }
                txtResult.setText(text);
                txtResult.startAnimation(animation);
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        txtResult.setText("");
                    }
                },3000);
            }
        }, 1000);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}