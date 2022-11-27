package com.example.dices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int numberOfDices;
    private int numberOfRemainingThrows;
    private Dice[] dices;
    private View clickedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDefaultValues();
        findViews();
        //createDicesArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
        clickedView = v;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        numberOfDices = Integer.parseInt(item.getTitle().toString());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getNumericShortcut()){
            case '0':
                lock();
                return true;
            case '1':
                unlock();
                return true;
            case '2':
                lockForever();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void findViews() {
        TextView textView = findViewById(R.id.textViewSum);
        CheckBox checkBox = findViewById(R.id.checkBoxSum);

        findViewById(R.id.buttonThrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOfRemainingThrows > 0) {
                    generateDices();
                    if (checkBox.isChecked())
                        textView.setText(String.format("Points: %d", sumPoints()));
                    numberOfRemainingThrows--;
                } else {
                    Log.i("end", "In the end of the program should be sth else than this message");
                }
            }
        });
    }

    private void setDefaultValues() {
        numberOfDices = 1;
        numberOfRemainingThrows = 3;
        dices = new Dice[6];
        clickedView = null;
        fillDicesArray();
    }

    private void fillDicesArray() {
        dices[0] = new Dice(R.drawable.dice_empty, findViewById(R.id.imageViewDice), 0);
        dices[1] = new Dice(R.drawable.dice_empty, findViewById(R.id.imageViewDice2), 0);
        dices[2] = new Dice(R.drawable.dice_empty, findViewById(R.id.imageViewDice3), 0);
        dices[3] = new Dice(R.drawable.dice_empty, findViewById(R.id.imageViewDice4), 0);
        dices[4] = new Dice(R.drawable.dice_empty, findViewById(R.id.imageViewDice5), 0);
        dices[5] = new Dice(R.drawable.dice_empty, findViewById(R.id.imageViewDice6), 0);

        for (int i = 0; i < 6; i++) {
            registerForContextMenu(dices[i].getImageView());
        }
    }

    private void generateDices(){
        for (int i = 0; i < numberOfDices; i++){
            if (!dices[i].isLocked() && !dices[i].isLockedForever()) {
                dices[i].setDiceValue(new Random().nextInt(6) + 1);
                dices[i].getImageView().setImageResource(dices[i].getResourceId());
            }
        }
    }

    private int sumPoints() {
        int sum = 0;

        for (int i = 0; i < numberOfDices; i++) {
            sum += dices[i].getDiceValue();
        }
        return sum;
    }

    private void lock() {
        for (int i = 0; i < numberOfDices; i++) {
            if (clickedView.getId() == dices[i].getImageView().getId()){
                dices[i].setLocked(true);
                Log.i("lockInfo", String.format("Dice %d locked", i + 1));
            }
        }
    }

    private void unlock() {
        for (int i = 0; i < numberOfDices; i++) {
            if (clickedView.getId() == dices[i].getImageView().getId()){
                dices[i].setLocked(false);
                Log.i("lockInfo", String.format("Dice %d unlocked", i + 1));
            }
        }
    }

    private void lockForever() {
        for (int i = 0; i < numberOfDices; i++) {
            if (clickedView.getId() == dices[i].getImageView().getId()){
                dices[i].setLockedForever(true);
                Log.i("lockInfo", String.format("Dice %d locked forever", i + 1));
            }
        }
    }
}