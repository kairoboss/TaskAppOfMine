package com.example.taskappofmine.ui.onboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.viewpager.widget.PagerAdapter;

import com.example.taskappofmine.R;
import com.example.taskappofmine.ui.utils.Prefs;

public class BoardAdapter extends PagerAdapter {

    private String[] titles = new String[]{"Fast", "Free", "Powerful"};
    private String[] descriptions = new String[]{"No slow", "don't pay", "Strong"};
    private Integer[] images = new Integer[]{R.drawable.onboard_page1, R.drawable.onboard_page2, R.drawable.onboard_page3};
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.page_board, container, false);
        TextView textTitle = view.findViewById(R.id.page_title);
        ImageView imageView = view.findViewById(R.id.page_image_view);
        TextView description  = view.findViewById(R.id.page_description);
        Button btnGetStarted = view.findViewById(R.id.get_started_button);
        description.setText(descriptions[position]);
        textTitle.setText(titles[position]);
        imageView.setImageResource(images[position]);
        container.addView(view);
        if (position == 2){
            btnGetStarted.setVisibility(View.VISIBLE);
        }else {
            btnGetStarted.setVisibility(View.GONE);
        }
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs prefs = new Prefs(view.getContext());
                prefs.setShown();
                NavController navController = Navigation.findNavController(view);
                navController.navigateUp();
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
