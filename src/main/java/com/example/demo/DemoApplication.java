package com.example.demo;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;

@SpringBootApplication
public class DemoApplication {
	private static OkHttpClient client = new OkHttpClient();
	private static String getRandomUrl = "https://mostafa-memorize.herokuapp.com/word";
	private String postUrl = "https://mostafa-memorize.herokuapp.com/word";
	private static Request request = new Request.Builder().url(getRandomUrl).build();
	static FCMService z = new FCMService();
	static PushNotificationService y = new PushNotificationService(z);
	static PushNotificationController x = new PushNotificationController(y);
	static PushNotificationRequest h = new PushNotificationRequest();
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DemoApplication.class, args);
		h.setToken("c8H3GzknSRmm0MfISfIIkg:APA91bH88_5maiHVGWQLurW5Pqc7iogiFqdgaohmwt0Vz5_Mvo0oRvmfHlIs0YtA-0IEIOClWOYg69PMWxDHAPTS13Dy7Z-I-b-bojM1v6bLZ8yXXluSWcPp39ffzt2XPT5lu9jhSjuu");


		//getRandomWord();

		while (true){

			getRandomWord();
			Thread.sleep(1000 * 60);
		}


	}

	public static void getRandomWord(){

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				h.setTitle("Error: request faild");
				h.setMessage(e.getMessage());
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
					x.sendTokenNotification(h);

				} catch (JSONException e) {
					h.setTitle("Error: onResponse");
					h.setMessage(e.getMessage());
					x.sendTokenNotification(h);
				}



			}
		});


	}

}
