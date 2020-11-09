package com.example.taskappofmine.ui.home.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskappofmine.App;
import com.example.taskappofmine.R;
import com.example.taskappofmine.models.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class FormFragment extends Fragment {
    private EditText editText;
    private Task task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        Random random = new Random();
        String title = editText.getText().toString().trim();
        task = new Task(title, System.currentTimeMillis());
        task.setId(random.nextLong());
        App.getDataBase().taskDao().insert(task);
        Bundle b = new Bundle( );
        b.putSerializable("text", task);
        getParentFragmentManager().setFragmentResult("form", b);
        saveToFireStore(task);

    }

    private void saveToFireStore(Task task) {
        FirebaseFirestore.getInstance().collection("tasks").document(Long.toString(task.getId())).set(task)
                .addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task task) {
                if (task.isSuccessful()){
                    Toast.makeText(requireActivity(), "Успешно", Toast.LENGTH_SHORT).show();
                    close();
                }else {
                    Toast.makeText(requireActivity(), "Ошибка", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void close(){
        NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        navController.navigateUp();
    }
}