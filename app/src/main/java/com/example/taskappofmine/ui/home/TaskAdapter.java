package com.example.taskappofmine.ui.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskappofmine.R;
import com.example.taskappofmine.interfaces.OnItemClickListener;
import com.example.taskappofmine.models.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> list = new ArrayList<>();
    TextView textTitle;
    TextView textDate;
    Task task;
    private OnItemClickListener onItemClickListener;

    public void addItem(Task task) {
        list.add(0, task);
        notifyItemInserted(0);
    }


    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    public Task getItem(int pos) {
        return list.get(pos);
    }

    public int getItemPosition(int pos) {
        return pos;
    }

    public void removeItem(int pos) {
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    public void addList(List<Task> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.list_text);
            textDate = itemView.findViewById(R.id.list_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void bind(Task task) {
            textTitle.setText(task.getTitle());
            Date date = new Date(task.getCreatedAt());
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:ss");
            textDate.setText(df.format(date));
        }
    }
}
