package com.example.mealmate.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

public class SMSHelper {

    public static void sendGroceryListSMS(Activity activity, ArrayList<String> items, String phoneNumber) {
        StringBuilder smsBody = new StringBuilder();
        smsBody.append("🛒 Grocery List:\n");

        for (String item : items) {
            smsBody.append("- ").append(item).append("\n");
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("sms_body", smsBody.toString());
        activity.startActivity(intent);
    }
}
