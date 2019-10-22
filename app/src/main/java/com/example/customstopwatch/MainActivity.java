package com.example.customstopwatch;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> fooList = new ArrayList<>(10);
    CustomRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    Stopwatch stopwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stopwatch = new Stopwatch();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopwatch.Toggle();
            }
        });

        Button resetButton = findViewById(R.id.button_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopwatch.Reset();
            }
        });

        Button lapButton = findViewById(R.id.button_lap);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Lap button", "pressed");
                Log.d("Stopwatch", ""+stopwatch.getSectors().size());
                stopwatch.Lap();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager reverseLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        recyclerView.setLayoutManager(reverseLayoutManager);

        adapter = new CustomRecyclerViewAdapter(this, stopwatch.getSectors());
        adapter.setClickListener(new CustomRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("listener", String.format("Removed item %s at position %d", adapter.getItem(position).toString(), position));
                removeItem(position);
            }
        });
        recyclerView.setAdapter(adapter);


        TextView textView = findViewById(R.id.textView);
        textView.setText("00:00");

    }

    void addItem() {
        fooList.add(String.format("Item%d", fooList.size()));
        adapter.notifyDataSetChanged();
    }

    void removeItem(int index) {
        fooList.remove(index);
        recyclerView.removeViewAt(index);
        adapter.notifyItemRemoved(index);
        adapter.notifyItemRangeChanged(index, fooList.size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
