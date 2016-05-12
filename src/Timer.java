//Author Adam Feldscher
//Purpose: Multipurpose Timer class

public class Timer {

    private long startTime;
    private long acumTime;
    private boolean isRunning;

    public void start() { //starts the timer
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
        }
    }

    public long get() { //gets the current time in millis
        return acumTime + (isRunning ? System.currentTimeMillis() - startTime : 0);
    }

    public double getSec() { //gets the time in seconds
        return get() / 1000.0;
    }

    public boolean isRunning() { //checks if the timer is running
        return isRunning;
    }

    public void stop() { //stops the timer but stores the time on the clock
        if (isRunning) {
            acumTime += System.currentTimeMillis() - startTime;
            isRunning = false;
        }
    }

    public void reset() { //resets the timer but does not stop it.
        acumTime = 0;
        startTime = System.currentTimeMillis();
    }
}