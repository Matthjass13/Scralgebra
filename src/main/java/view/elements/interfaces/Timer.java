package view.elements.interfaces;

import javax.swing.*;

public class Timer extends JPanel {

    private int timeRemaining;
    private final Label timerLabel;
    private final javax.swing.Timer timer;
    public boolean isRunning = true;

    public Timer(int totalTime) {

        timeRemaining=totalTime;
        timerLabel = new Label("Time : " + timeRemaining, "Large", "Black");
        setOpaque(false);
        this.add(timerLabel);

        timer = new javax.swing.Timer(1000, e -> {
            if (timeRemaining > 0) {
                timeRemaining--;
                timerLabel.setText("Time : " + timeRemaining);
            } else
                stopTimer();
        });

        timer.start();

    }

    public void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            isRunning = false;
        }
    }
    public void resumeTimer() {
        if (timer != null && !timer.isRunning()) {
            timer.start();
            isRunning = true;
        }
    }
    public void toggleTimer() {
        if (isRunning)
            stopTimer();
        else
            resumeTimer();
    }
    public void addIncrement() {
        int increment = 5;
        timeRemaining += increment;
        timerLabel.setText("Time : " + timeRemaining);
    }
    public javax.swing.Timer getTimer() {
        return timer;
    }
    public int getTimeRemaining() {
        return timeRemaining;
    }


}
