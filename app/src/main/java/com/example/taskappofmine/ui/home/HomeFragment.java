package com.example.taskappofmine.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskappofmine.App;
import com.example.taskappofmine.MainActivity;
import com.example.taskappofmine.R;
import com.example.taskappofmine.interfaces.OnItemClickListener;
import com.example.taskappofmine.models.Task;
import com.example.taskappofmine.ui.utils.Prefs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TaskAdapter taskAdapter;
    private Task task;
    private boolean b = false;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskAdapter = new TaskAdapter();
        loadData();
        setHasOptionsMenu(true);

        taskAdapter.setOnItemClickListener(new OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(int pos) {

                Toast.makeText(requireContext(), taskAdapter.getItem(pos).getTitle() + " ", Toast.LENGTH_SHORT).show();
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
                                taskAdapter.removeItem(pos);
                                App.getDataBase().taskDao().delete(taskAdapter.getItem(pos));
                            }
                        });
                builder.show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForm();
            }
        });
        initResultListener();
        initList(view);
    }

    private void loadData() {
        List<Task> tasks = App.getDataBase().taskDao().getAll();
        taskAdapter.addList(tasks);
    }

    private void initList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_home);

        recyclerView.setAdapter(taskAdapter);
    }

    private void initResultListener() {
        getParentFragmentManager().setFragmentResultListener("form", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                task = (Task) result.getSerializable("text");
                taskAdapter.addItem(task);
            }
        });
    }

    private void openForm() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_navigation_home_to_formFragment);
    }
    public static String[] generateRandomWords(int numberOfWords)
    {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();
        for(int i = 0; i < numberOfWords; i++)
        {
            char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
            for(int j = 0; j < word.length; j++)
            {
                word[j] = (char)('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }
        return randomStrings;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort_time) {
            if (b == true) {
                List<Task> taskList = App.getDataBase().taskDao().reverseOrderByDate();
                taskAdapter.addList(taskList);
            } else {
                List<Task> taskList = App.getDataBase().taskDao().orderByDate();
                taskAdapter.addList(taskList);
                b = true;
            }
        }
        if (item.getItemId() == R.id.sort_alphabet) {
            if (b == true) {
                List<Task> taskList = App.getDataBase().taskDao().reverseOrderByAlphabet();
                taskAdapter.addList(taskList);
            } else {
                List<Task> taskList = App.getDataBase().taskDao().orderByAlphabet();
                taskAdapter.addList(taskList);
                b = true;
            }
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
    }
}