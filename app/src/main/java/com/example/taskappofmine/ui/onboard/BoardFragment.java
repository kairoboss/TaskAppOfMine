package com.example.taskappofmine.ui.onboard;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.taskappofmine.R;
import com.example.taskappofmine.ui.utils.Prefs;
import com.example.taskappofmine.ui.onboard.BoardAdapter;
import com.google.android.material.tabs.TabLayout;
public class BoardFragment extends Fragment {

    private Button skip;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager(view);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
        skip = view.findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
    }

    private void initViewPager(View view) {
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        BoardAdapter boardAdapter = new BoardAdapter();
        viewPager.setAdapter(boardAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager,true);
    }
    private void close() {
        Prefs prefs = new Prefs(requireContext());
        prefs.setShown();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}