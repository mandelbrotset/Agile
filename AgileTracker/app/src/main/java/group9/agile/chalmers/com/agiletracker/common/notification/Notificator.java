package group9.agile.chalmers.com.agiletracker.common.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import group9.agile.chalmers.com.agiletracker.MainActivity;
import group9.agile.chalmers.com.agiletracker.R;

/**
 * Created by isak & Sarah on 4/28/15.
 */
public class Notificator {
    private static int notification_id;
    private static int led_on_ms;
    private static int led_off_ms;
    static  {
     notification_id = 0;
        led_on_ms = 1000;
        led_off_ms = 1000;
    }

    public static void displayNotification(String title, String text, int notificationColor, int ledColor, Activity targetActivity) {
        NotificationCompat.Builder nb = new NotificationCompat.Builder(targetActivity.getApplicationContext());
        nb.setContentTitle(title);
        nb.setContentText(text);
        nb.setColor(notificationColor);
        nb.setLights(ledColor, led_on_ms, led_off_ms);
        nb.setSmallIcon(R.mipmap.notification); //using a test image. We need to create a real one.

        Intent intent = new Intent(targetActivity, targetActivity.getClass());
        PendingIntent pIntent = PendingIntent.getActivity(targetActivity, 0, intent, 0);
        nb.setContentIntent(pIntent);

        Notification n = nb.build();
        ((NotificationManager)targetActivity.getSystemService(Context.NOTIFICATION_SERVICE)).notify(notification_id++, n);
    }
}
