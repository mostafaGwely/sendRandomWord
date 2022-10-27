package com.example.demo;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
@EnableScheduling
public class ClassConfig {
    private static OkHttpClient client = new OkHttpClient();
    private static String getRandomUrl = "https://mostafa-memorize.herokuapp.com/word";
    private String postUrl = "https://mostafa-memorize.herokuapp.com/word";
    private static String wirepush= "https://wirepusher.com/send?id=xd3Tmps3D";

    private static Request request = new Request.Builder().url(getRandomUrl).build();
    static FCMService z = new FCMService();
    static PushNotificationService y = new PushNotificationService(z);
    static PushNotificationController x = new PushNotificationController(y);
    static PushNotificationRequest h = new PushNotificationRequest();
    private static boolean firstTime = true;
    @Scheduled(fixedDelay = 1000 * 60 * 60 )
    public void sendMessage() throws InterruptedException {
//        System.out.println("hello");
        h.setToken("ccgDv7d7S3yMJ69H9kfLal:APA91bH51PQoV3cEablSTt5a_RzqcsGhGBMaBdrToQ2wKxs76TB1kRRVbUIMgeulrTco6D7c5WcowiPLbJlwxA7V76Y-ikAiF1jDA_kkkFMkYuKCwpsOdwroMGVPgMIkpBUZnjrtKz_8");
        firstTime = true;
        getRandomWord();
        Thread.sleep(1000 * 10);
        firstTime = false;
        getRandomWord();


    }


    public static void getRandomWord(){
//        Request r = new Request.Builder()
//                .url(wirepush+"&title=title&message=adfadfadf")
//                .build();
//         client.newCall(r).enqueue(new Callback() {
//             @Override
//             public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                 System.out.println(e.getMessage());
//             }
//
//             @Override
//             public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                 System.out.println(response.body().string());
//             }
//         });

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                h.setTitle("Error: request faild");
                h.setMessage(e.getMessage());
                if (!firstTime)
                    x.sendTokenNotification(h);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                final String MyRes = response.body().string();

                JSONObject MyResJson = null;
                try {
                    MyResJson = new JSONObject(MyRes);
                    String ss = MyResJson.getString("p").trim() + " : " + MyResJson.getString("word").trim() + "\n\n -" + MyResJson.getString("description") ;
                    String title = MyResJson.getString("p").trim() + " : " + MyResJson.getString("word").trim();
                    String content = MyResJson.getString("description");

                    h.setTitle(title);
                    h.setMessage(content);
                    if (!firstTime)
                        x.sendTokenNotification(h);

                } catch (JSONException e) {
                    h.setTitle("Error: onResponse");
                    h.setMessage(e.getMessage());
                    if (!firstTime)
                        x.sendTokenNotification(h);
                }



            }
        });


    }
}
