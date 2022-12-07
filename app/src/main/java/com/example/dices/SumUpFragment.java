package com.example.dices;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SumUpFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sum_up, container, false);
        Bundle bundle = getArguments();

        if (bundle != null) {
            TextView textView = view.findViewById(R.id.textViewSumUpFragSum);
            textView.setText(String.valueOf(bundle.getInt("sum")));
        }

        view.findViewById(R.id.buttonSumUpFragPlayAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewFragment();
            }
        });

        return view;
    }

    private void createNewFragment() {
        MainFragment fragment = new MainFragment();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
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
}