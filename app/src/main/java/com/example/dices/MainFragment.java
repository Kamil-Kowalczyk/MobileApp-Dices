package com.example.dices;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Random;

public class MainFragment extends Fragment {

    private int numberOfDices;
    private int numberOfRemainingThrows;
    private int sum;
    private Dice[] dices;
    private View clickedView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        setDefaultValues();
        findViews();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
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

    /*@Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback = new OnBackPressedCallback(
                true // default to enabled
        ) {
            @Override
            public void handleOnBackPressed() {

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }*/

    private void findViews() {
        TextView textView = view.findViewById(R.id.textViewMainFragSum);
        CheckBox checkBox = view.findViewById(R.id.checkBoxMainFragSum);

        view.findViewById(R.id.buttonMainFragThrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOfRemainingThrows > 0) {
                    generateDices();

                    int pointsSum = sumPoints();
                    textView.setText(String.format("Points in the throw: %d", pointsSum));

                    if (checkBox.isChecked()) {
                        sum += pointsSum;
                    }
                    numberOfRemainingThrows--;
                } else {
                    createNewFragment();
                }
            }
        });
    }

    private void setDefaultValues() {
        numberOfDices = 1;
        numberOfRemainingThrows = 3;
        sum = 0;
        dices = new Dice[6];
        clickedView = null;
        fillDicesArray();
    }

    private void fillDicesArray() {
        dices[0] = new Dice(getActivity().findViewById(R.id.imageViewDice), 0);
        dices[1] = new Dice(getActivity().findViewById(R.id.imageViewDice2), 0);
        dices[2] = new Dice(getActivity().findViewById(R.id.imageViewDice3), 0);
        dices[3] = new Dice(getActivity().findViewById(R.id.imageViewDice4), 0);
        dices[4] = new Dice(getActivity().findViewById(R.id.imageViewDice5), 0);
        dices[5] = new Dice(getActivity().findViewById(R.id.imageViewDice6), 0);

        for (int i = 0; i < 6; i++) {
            registerForContextMenu(dices[i].getImageView());
            dices[i].setResourceForImageView();
        }
    }

    private void generateDices(){
        for (int i = 0; i < 6; i++){
            if (i < numberOfDices) {
                if (!dices[i].isLocked() && !dices[i].isLockedForever())
                    dices[i].setDiceValue(new Random().nextInt(6) + 1);
            } else {
                dices[i].setDiceValue(0);
                dices[i].setLocked(false);
                dices[i].setLockedForever(false);
            }
            dices[i].setResourceForImageView();

        }
    }

    private int sumPoints() {
        int currentSum = 0;

        for (int i = 0; i < numberOfDices; i++) {
            currentSum += dices[i].getDiceValue();
        }
        return currentSum;
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

    private void createNewFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("sum", sum);

        SumUpFragment fragment = new SumUpFragment();
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }
}