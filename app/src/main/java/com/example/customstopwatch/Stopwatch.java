package com.example.customstopwatch;


import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Stopwatch {


    private List<Sector> sectors;
    private long startTime = -1L;
    private long elapsed;
    private boolean running = false;

    private static Sector nextSector;

    public Stopwatch() {
        
        sectors = new ArrayList<>(8);
        nextSector = new Sector(0, 0,0,false,false);
    }

    public void Start () {
        if(!running) {
            startTime = System.nanoTime();
            nextSector.startTime = startTime;
            nextSector.running = true;
            running = true;
        }
    }

//    public void Pause () {
//        if(running) {
//            elapsed = System.nanoTime() - startTime;
//            running = false;
//        }
//    }

    public void Reset () {
        startTime = -1L;
        running = false;
        elapsed = 0;
        sectors.clear();
    }

    public long GetElapsed() {
        return System.nanoTime() - startTime;
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public void Lap () {
        nextSector.enabled = true;
        nextSector.sectorTime = System.nanoTime() - nextSector.startTime;
        nextSector.totalTime = GetElapsed();

        sectors.add(new Sector(nextSector));

        Log.d("Sector", "Sector lapped: " + nextSector.toString());
    }

    public void Toggle() {
        if(running)
            Reset();
        else
            Start();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public class Sector {

        long startTime;
        long sectorTime;
        long totalTime;
        boolean running;
        boolean enabled;

        Sector (Sector copy) {
            this.startTime = copy.startTime;
            this.sectorTime = copy.sectorTime;
            this.totalTime = copy.totalTime;
            this.running = copy.running;
            this.enabled = copy.enabled;
        }

        Sector(long startTime, long sectorTime, long totalTime, boolean running, boolean enabled) {
            this.startTime = startTime;
            this.sectorTime = sectorTime;
            this.totalTime = totalTime;
            this.running = running;
            this.enabled = enabled;
        }

        @NonNull
        @Override
        public String toString() {
            @SuppressLint("DefaultLocale")
            String s = String.format("%02d:%02d:%02d", TimeUnit.NANOSECONDS.toHours(sectorTime),
                    TimeUnit.NANOSECONDS.toMinutes(sectorTime) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.NANOSECONDS.toSeconds(sectorTime) % TimeUnit.MINUTES.toSeconds(1) );
            return s;
        }
    }
}
