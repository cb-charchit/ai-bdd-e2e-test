package com.cbee.models;

public class TrialPeriod {
    public final int duration;
    public final TimePeriod timePeriod;

    public TrialPeriod(int duration, TimePeriod timePeriod) {
        this.duration = duration;
        this.timePeriod = timePeriod;
    }
}
