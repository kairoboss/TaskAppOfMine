package com.example.taskappofmine.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taskappofmine.App;
import com.example.taskappofmine.R;
import com.example.taskappofmine.ui.utils.Prefs;

public class ProfileFragment extends Fragment {
    private TextView textView;
    private TextView textPosts;
    private TextView textFollowers;
    private TextView textFollowing;
    private TextView textPhotos;
    Prefs prefs;
    SharedPreferences sPrefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.text_profile);
            prefs = new Prefs(requireContext());
            String savedText = prefs.getString("key");
            textView.setText(savedText);
            textPosts = view.findViewById(R.id.posts);
            textPosts.setText("63 /n posts");
            textFollowers = view.findViewById(R.id.followers);
        textFollowers.setText("63 /n followers");
            textFollowing = view.findViewById(R.id.following);
        textFollowing.setText("63 /n following");
            textPhotos = view.findViewById(R.id.photos);
        textPhotos.setText("63 /n photos");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.actionEdit) {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_navigation_profile_to_profileEditFragment);
        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile, menu);

    }
}