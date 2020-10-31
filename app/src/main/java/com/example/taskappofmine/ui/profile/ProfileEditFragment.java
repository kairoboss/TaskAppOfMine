package com.example.taskappofmine.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskappofmine.R;
import com.example.taskappofmine.ui.utils.Prefs;

import java.util.Objects;


public class ProfileEditFragment extends Fragment {
    private Button btnSave;
    private EditText editText;
    Prefs prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_edit, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        prefs = new Prefs(requireContext());

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSave = view.findViewById(R.id.btn_save_edit);
        editText = view.findViewById(R.id.profile_editText);
        String text = prefs.getString();
        editText.setText(text);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        prefs.putString(editText.getText().toString());
        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        navController.navigateUp();
    }
}