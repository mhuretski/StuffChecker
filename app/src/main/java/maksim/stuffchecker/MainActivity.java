package maksim.stuffchecker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickRequest(View view) {
        logResponse();
    }

    public void onClickResponse(View view) {
        clearResponse();
        sendNotification();
    }

    private void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "My Notifications",
                    NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                /*.setAutoCancel(true)*/
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setTicker("Hearty365")
//                     .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(setActivityInNotification())
                .setContentTitle("setContentTitle")
                .setContentText("setContentText Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .setContentInfo("setContentInfo");

        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }

    private PendingIntent setActivityInNotification() {
        return PendingIntent.getActivity(
                this,
                0,
                new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void clearResponse() {
        ((TextView) findViewById(R.id.textField)).setText("Cleared");
    }

    private void logResponse() {
        /*Instantiate the RequestQueue.*/
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.google.com";

        /*Request a string response from the provided URL.*/
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                (response) -> ((TextView) findViewById(R.id.textField)).setText(response.substring(0, 50)),
                error -> System.out.println("That didn't work!"));

        /*Add the request to the RequestQueue.*/
        queue.add(stringRequest);
    }

}
