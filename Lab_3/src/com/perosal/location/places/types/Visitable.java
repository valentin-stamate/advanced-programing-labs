package com.perosal.location.places.types;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

public interface Visitable {

    void setOpeningHours(Calendar openStart, Calendar openEnd);
    int getEndingHour();
    int getStartingHour();
    int getVisitingDurationInHours();

    String getOpeningHours();

    default void setDefaultHours(Calendar openStart, Calendar openEnd) {
        openStart.set(Calendar.HOUR, 9);
        openStart.set(Calendar.MINUTE, 30);

        openEnd.set(Calendar.HOUR, 20);
        openEnd.set(Calendar.MINUTE, 0);
    }

    static Duration getVisitingDuration(Calendar openStart, Calendar openEnd) {
        return Duration.ofMillis(openEnd.getTimeInMillis() - openStart.getTimeInMillis());
    }

}
