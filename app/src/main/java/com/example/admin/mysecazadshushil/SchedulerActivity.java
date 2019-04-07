package com.example.admin.mysecazadshushil;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mysecazadshushil.Adapters.ScheduleRecyclerViewAdapter;
import com.example.admin.mysecazadshushil.Models.Schedule_model;
import com.example.admin.mysecazadshushil.MyDBHelper.ScheduleDbHelper;


import org.w3c.dom.Text;

import java.util.List;

public class SchedulerActivity extends AppCompatActivity {

    FloatingActionButton fab;
    List<Schedule_model> schedules;
    ScheduleDbHelper scheduleDbHelper;
    TextView day, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        getSupportActionBar().setTitle("Schedule");
        scheduleDbHelper = new ScheduleDbHelper(this);
        schedules = scheduleDbHelper.getAll();
        day=(TextView)findViewById(R.id.dayTextView);
        time=(TextView)findViewById(R.id.timeTextView);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "New", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                startActivity(new Intent(SchedulerActivity.this, NewScheduleActivity.class));
            }
        });

        initRecylcerView();
    }

    private void initRecylcerView() {
        RecyclerView recyclerView = findViewById(R.id.scheduleListView);
        final ScheduleRecyclerViewAdapter recyclerViewAdapter = new ScheduleRecyclerViewAdapter(this, schedules);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Animation animationUtils=AnimationUtils.loadAnimation(this, R.anim.itemrecyclerviewanimation);
        recyclerView.startAnimation(animationUtils);
        /*AnimatorSet animatorSet=new AnimatorSet();
        ObjectAnimator animatortranslate=ObjectAnimator.ofFloat(recyclerView, "translationY", true?400: -400,0);
        animatortranslate.setDuration(900);
        animatorSet.play(animatortranslate);
        animatorSet.start();*/

        SwipeHelper swipeHelper = new SwipeHelper(this, recyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Edit",
                        0,
                        Color.parseColor("#C7C7CB"),

                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Intent intent = new Intent(getApplicationContext(), ScheduleUpdateActivity.class);
                                intent.putExtra("id", schedules.get(pos).getId());
                                intent.putExtra("day", schedules.get(pos).getDay());
                                intent.putExtra("item", schedules.get(pos).getItem());
                                intent.putExtra("subject", schedules.get(pos).getSubject());
                                intent.putExtra("time", schedules.get(pos).getTime());
                                startActivity(intent);
                            }
                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(final int pos) {
                                final AlertDialog.Builder dialog = new AlertDialog.Builder(SchedulerActivity.this);
                                dialog.setMessage("Are you sure?");
                                dialog.setTitle("Note delete");
                                dialog.setIcon(android.R.drawable.ic_dialog_alert);
                                dialog.setCancelable(false);

                                dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (scheduleDbHelper.deleteById(schedules.get(pos).getId())) {
                                            Toast.makeText(getApplicationContext(), "Schedule Deleted", Toast.LENGTH_SHORT).show();
                                            schedules.remove(pos);
                                            recyclerViewAdapter.notifyItemRemoved(pos);
                                        }
                                    }
                                });

                                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                                dialog.show();
                            }
                        }
                ));
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
