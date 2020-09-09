package com.ndsl.nw.bun133.api.example;

import java.util.List;

public class P2PResponse {
    public String time;
    public int code;
    public EarthquakeIssue issue;
    public boolean cancelled;
    public List<TsunamiArea> areas;
    public Earthquake earthquake;
    public List<EarthquakePoint> points;
}