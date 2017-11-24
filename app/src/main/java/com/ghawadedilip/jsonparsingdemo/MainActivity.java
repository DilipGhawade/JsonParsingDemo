package com.ghawadedilip.jsonparsingdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
  Button button;
  TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    button =  findViewById(R.id.btn_parse);
    textView = findViewById(R.id.tv_parse);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        new jsonParse().execute("https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoItem.txt");
      }


    });

}

public class jsonParse extends AsyncTask<String,String,String>
{

  @Override
  protected String doInBackground(String... params) {

    HttpURLConnection connection = null;
    BufferedReader reader;
    try {
      URL url = new URL(params[0]);
      connection = (HttpURLConnection)url.openConnection();

      connection.connect();

      InputStream stream = connection.getInputStream();
      reader = new BufferedReader(new InputStreamReader(stream));
      StringBuffer buffer = new StringBuffer();
      String line = "";
      while ((line = reader.readLine()) != null) {
        buffer.append(line);
      }
      String json = buffer.toString();
      JSONObject jsonObject = new JSONObject(json);
      JSONArray jsonArray = jsonObject.getJSONArray("movies");

      JSONObject jsonObject1 = jsonArray.getJSONObject(0);

      String name = jsonObject1.getString("movie");
      String year = jsonObject1.getString("year");
      return name + " " + year;


    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;

  }

  @Override
  protected void onPostExecute(String s) {

    textView.setText(s);
  }
}
}
