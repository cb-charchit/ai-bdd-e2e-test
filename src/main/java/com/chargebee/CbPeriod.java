package com.chargebee;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Period;
import java.util.Date;

public class CbPeriod {
    public final Period value;

    public CbPeriod(String periodString) {
        this.value = Period.parse(periodString);
    }

    public Timestamp toTimestamp() {
        return new Timestamp(Date.from(Instant.now().plus(value)).getTime());
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("MM/dd/yyyy").format(toTimestamp());
    }
}
