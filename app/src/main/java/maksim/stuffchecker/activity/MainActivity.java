package maksim.stuffchecker.activity;

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
import maksim.stuffchecker.R;
import maksim.stuffchecker.entity.ObjForValidation;
import org.apache.commons.text.StringEscapeUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickRequest(View view) {
        ObjForValidation objForValidation1 = new ObjForValidation(
                1,
                "google",
                "http://www.google.com",
                new String[]{
                        "{display:inline-block;margin:0 2px}</style><div",
                        ">Google offered in:  <a href=",
                        ">русский</a>"
                }
        );
        logResponse(objForValidation1);
        ObjForValidation objForValidation2 = new ObjForValidation(
                2,
                "google",
                "http://www.google.com",
                new String[]{
                        "{display:inline-block;margin:0 2px}</style><div",
                        ">Google offered in:  <a href=",
                        ">усский</a>"
                }
        );
        logResponse(objForValidation2);
        ObjForValidation objForValidation3 = new ObjForValidation(
                3,
                "google",
                "http://www.google.com",
                new String[]{
                        "{isplay:inline-block;margin:0 2px}</style><div",
                        ">Google offered in:  <a href=",
                        ">усский</a>"
                }
        );
        logResponse(objForValidation3);
    }

    public void onClickResponse(View view) {
        clearResponse();
        sendNotification();
    }

    private void sendNotification(ObjForValidation objForValidation) {
        String name = objForValidation.getName();
        Context context = getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, name);
        notificationBuilder
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(setActivityInNotification())
                .setContentTitle(context.getString(R.string.notif_title))
                .setContentText(String.format(context.getString(R.string.notif_text), name));
        notificationManager.notify(objForValidation.getId(), notificationBuilder.build());
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

    private void logResponse(ObjForValidation objForValidation) {
        /*Instantiate the RequestQueue.*/
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = objForValidation.getRequestGET();

        /*Request a string response from the provided URL.*/
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                (response) -> {
                    objForValidation.setTime(new java.util.Date().getTime());
                    TextView textView = findViewById(R.id.textField);
                    String unescapedResponse = StringEscapeUtils.unescapeHtml4(response);
                    if (isOk(unescapedResponse, objForValidation.getValidatedPartOfResponse())) {
                        objForValidation.setIsOk(true);
                    } else {
                        objForValidation.setIsOk(false);
                        sendNotification(objForValidation);
                    }
                    System.out.println(objForValidation.getId() + " " + objForValidation.getIsOk());
                },
                error -> System.out.println("That didn't work!"));

        /*Add the request to the RequestQueue.*/
        queue.add(stringRequest);
    }

    private boolean isOk(String request, String[] compareWith) {
        boolean result = true;
        String source = request;
        for (String part : compareWith) {
            if (source.contains(part)) {
                int position = source.indexOf(part);
                source = source.substring(position);
            } else {
                result = false;
                break;
            }
        }
        return result;
    }

}
