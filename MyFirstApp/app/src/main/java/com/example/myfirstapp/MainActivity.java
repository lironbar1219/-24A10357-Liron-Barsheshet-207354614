package com.example.myfirstapp;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.Logic.GameManager;
import com.example.myfirstapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MaterialButton main_BTN_right;
    private MaterialButton main_BTN_left;
    private GridLayout main_grid;
    private ImageView spaceship;

    private ShapeableImageView[] main_IMG_hearts;

    private ShapeableImageView[][] mainMatrix;

    private Vibrator vibrator;

    private static final long DELAY = 400;
    private static final long DELAY_FALLING = 200;


    private GameManager gameManager;
    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, DELAY);
            dropObject();
        }
    };

    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, DELAY_FALLING);
            changeObjectsPos();
        }
    };


    private void dropObject() {
        gameManager.dropObject();
    }

    public void toastAndVibrate(String text) {
        toast(text);
        vibrate();
    }
    private void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(500);
        }
    }
    private void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }


    private int numRows = 7;
    private int numColumns = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mainMatrix = new ShapeableImageView[numRows][numColumns];
        findViews();
        gameManager = new GameManager(main_IMG_hearts.length, numRows, numColumns);

        main_BTN_left.setOnClickListener(view -> leftClicked());
        main_BTN_right.setOnClickListener(view -> rightClicked());
        handler.postDelayed(runnable, 0);
        handler.postDelayed(runnable2, DELAY_FALLING);
    }

    private void leftClicked() {
        gameManager.moveLeft();
        refreshUI();
    }

    private void rightClicked() {
        gameManager.moveRight();
        refreshUI();
    }

    private void refreshUI() {
        changeSpaceShipPos();
    }

    private void changeObjectsPos() {
        gameManager.moveDownAllObjects();
        int posI;
        int posJ;
        for(int i = 0; i < gameManager.getFallingObjects().size(); i++) {
            posI = gameManager.getFallingObjects().get(i).getposI();
            posJ = gameManager.getFallingObjects().get(i).getposJ();
            if(posI < numRows) {
                if (posI > 0) {
                    mainMatrix[posI - 1][posJ].setVisibility(View.INVISIBLE);
                }
                if (posI == numRows - 1) {
                    if (mainMatrix[posI][posJ].getVisibility() == View.VISIBLE) {
                        // Collusion:
                        collusionHappened(gameManager, "BOOOOM");
                    }
                } else {
                    mainMatrix[posI][posJ].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void collusionHappened(GameManager gameManager, String text) {
        gameManager.setLife(gameManager.getLife() - 1);
        main_BTN_left.setEnabled(false);
        main_BTN_right.setEnabled(false);
        toastAndVibrate(text);
        downLife();

        handler.removeCallbacks(runnable);
        handler.removeCallbacks(runnable2);

        new Handler().postDelayed(() -> {
            main_BTN_left.setEnabled(true);
            main_BTN_right.setEnabled(true);
            handler.postDelayed(runnable, DELAY);
            handler.postDelayed(runnable2, DELAY_FALLING);
        }, 2000);
    }

    private void downLife() {
        if(gameManager.getLife() < main_IMG_hearts.length) {
            main_IMG_hearts[gameManager.getLife()].setVisibility(View.INVISIBLE);
        } else {
            for(int i = 0; i < main_IMG_hearts.length; i++) {
                main_IMG_hearts[i].setVisibility(View.VISIBLE);
            }
        }
    }

    private void changeSpaceShipPos() {
        mainMatrix[6][0].setVisibility(View.INVISIBLE);
        mainMatrix[6][1].setVisibility(View.INVISIBLE);
        mainMatrix[6][2].setVisibility(View.INVISIBLE);
        mainMatrix[6][gameManager.getPosSpaceShip()].setVisibility(View.VISIBLE);
    }

    private void findViews() {
        main_IMG_hearts = new ShapeableImageView[] {
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_BTN_left = findViewById(R.id.main_BTN_left);

        mainMatrix[0][0] = findViewById(R.id.main_IMG_pos00);
        mainMatrix[0][1] = findViewById(R.id.main_IMG_pos01);
        mainMatrix[0][2] = findViewById(R.id.main_IMG_pos02);
        mainMatrix[1][0] = findViewById(R.id.main_IMG_pos10);
        mainMatrix[1][1] = findViewById(R.id.main_IMG_pos11);
        mainMatrix[1][2] = findViewById(R.id.main_IMG_pos12);
        mainMatrix[2][0] = findViewById(R.id.main_IMG_pos20);
        mainMatrix[2][1] = findViewById(R.id.main_IMG_pos21);
        mainMatrix[2][2] = findViewById(R.id.main_IMG_pos22);
        mainMatrix[3][0] = findViewById(R.id.main_IMG_pos30);
        mainMatrix[3][1] = findViewById(R.id.main_IMG_pos31);
        mainMatrix[3][2] = findViewById(R.id.main_IMG_pos32);
        mainMatrix[4][0] = findViewById(R.id.main_IMG_pos40);
        mainMatrix[4][1] = findViewById(R.id.main_IMG_pos41);
        mainMatrix[4][2] = findViewById(R.id.main_IMG_pos42);
        mainMatrix[5][0] = findViewById(R.id.main_IMG_pos50);
        mainMatrix[5][1] = findViewById(R.id.main_IMG_pos51);
        mainMatrix[5][2] = findViewById(R.id.main_IMG_pos52);
        mainMatrix[6][0] = findViewById(R.id.main_IMG_pos60);
        mainMatrix[6][1] = findViewById(R.id.main_IMG_pos61);
        mainMatrix[6][2] = findViewById(R.id.main_IMG_pos62);

    }









}
