package com.example.dices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int numberOfDices = 1;
    private Dice[] dices = new Dice[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDicesArray();

        TextView textView = findViewById(R.id.textViewSum);
        CheckBox checkBox = findViewById(R.id.checkBoxSum);

        findViewById(R.id.buttonThrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateDices();

                if (checkBox.isChecked())
                    textView.setText(String.format("Points: %d", sumPoints()));
            }
        });
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        numberOfDices = Integer.parseInt(item.getTitle().toString());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getNumericShortcut()){
            case '0':
                lock(info.targetView);
                return true;
            case '1':
                unlock(info.targetView);
                return true;
            case '2':
                lockForever(info.targetView);
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }

    private void generateDices(){
        for (int i = 0; i < numberOfDices; i++){
            if (!dices[i].isLocked()) {
                dices[i].setDiceValue(new Random().nextInt(6) + 1);
                dices[i].getImageView().setImageResource(dices[i].getResourceId());
            }
        }
    }

    private void createDicesArray() {

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

    private int sumPoints() {
        int sum = 0;

        for (int i = 0; i < numberOfDices; i++) {
            sum += dices[i].getDiceValue();
        }
        return sum;
    }

    private void lock(View view) {
        for (int i = 0; i < numberOfDices; i++) {
            if (view.getId() == dices[i].getImageView().getId()){
                dices[i].setLocked(true);
            }
        }
    }

    private void unlock(View view) {

    }

    private void lockForever(View view) {

    }
}