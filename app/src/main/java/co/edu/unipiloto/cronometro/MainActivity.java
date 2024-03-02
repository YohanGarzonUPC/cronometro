package co.edu.unipiloto.cronometro;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private TextView lapTimesTextView;
    private long startTime;
    private long[] lapTimes = new long[5];
    private int lapCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        lapTimesTextView = findViewById(R.id.lapTimesTextView);
    }

    public void startChronometer(View view) {
        startTime = SystemClock.elapsedRealtime();
        chronometer.setBase(startTime);
        chronometer.start();

        findViewById(R.id.startButton).setVisibility(View.GONE);
        findViewById(R.id.stopButton).setVisibility(View.VISIBLE);
        findViewById(R.id.lapButton).setVisibility(View.VISIBLE);
    }

    public void stopChronometer(View view) {
        chronometer.stop();
        lapCounter = 0;
        lapTimes = new long[5];
        displayLapTimes();
        findViewById(R.id.startButton).setVisibility(View.VISIBLE);
        findViewById(R.id.stopButton).setVisibility(View.GONE);
        findViewById(R.id.lapButton).setVisibility(View.GONE);
    }

    public void recordLap(View view) {
        if (lapCounter < 5) {
            lapTimes[lapCounter] = SystemClock.elapsedRealtime() - startTime;
            lapCounter++;
            displayLapTimes();
        }
    }

    private void displayLapTimes() {
        StringBuilder laps = new StringBuilder();
        for (int i = 0; i < lapCounter; i++) {
            laps.append("Vuelta ").append(i + 1).append(": ")
                    .append(formatTime(lapTimes[i])).append("\n");
        }
        lapTimesTextView.setText(laps.toString());
    }

    private String formatTime(long time) {
        int hours = (int) (time / 3600000);
        int minutes = (int) (time - hours * 3600000) / 60000;
        int seconds = (int) (time - hours * 3600000 - minutes * 60000) / 1000;
        int milliseconds = (int) (time - hours * 3600000 - minutes * 60000 - seconds * 1000);
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }
}
