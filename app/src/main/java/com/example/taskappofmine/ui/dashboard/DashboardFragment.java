package com.example.taskappofmine.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskappofmine.App;
import com.example.taskappofmine.R;
import com.example.taskappofmine.interfaces.OnItemClickListener;
import com.example.taskappofmine.models.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private DashTaskAdapter dashTaskAdapter;
    private Task task;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashTaskAdapter = new DashTaskAdapter();
        dashTaskAdapter.setOnItemClickListener(new OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(int pos) {

                Toast.makeText(requireContext(), dashTaskAdapter.getItem(pos).getTitle() + " ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(final int pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Удаление");
                builder.setMessage("Удалить элемент списка?");
                builder.setNegativeButton("Отмена",null);
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dashTaskAdapter.removeItem(pos);
                                App.getDataBase().taskDao().delete(dashTaskAdapter.getItem(pos));
                                deleteFromFireStore(dashTaskAdapter.getItem(pos));
                            }
                        });
                builder.show();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView_dashboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(dashTaskAdapter);
        loadFromFireStore();
    }

    private void initList(View view) {

    }

    private void loadFromFireStore(){
         FirebaseFirestore.getInstance().collection("tasks").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot docs = task.getResult();
                            List<Task> taskList = docs.toObjects(Task.class);
                            dashTaskAdapter.addList(taskList);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void deleteFromFireStore(Task task){
        FirebaseFirestore.getInstance().collection("tasks").document(Long.toString(task.getId())).delete();
    }
}