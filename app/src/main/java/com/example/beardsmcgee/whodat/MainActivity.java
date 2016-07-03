package com.example.beardsmcgee.whodat;

import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String url = "http://www.posh24.com/celebrities";
    private ArrayList<Person> arrayList;

    private ImageView imageView;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    //current position in array
    private int i = 0;
    private String correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();
        FetchHTMLTask task = new FetchHTMLTask();
        task.execute();

        imageView = (ImageView) findViewById(R.id.imageView);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private class FetchHTMLTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements imgs = doc.select("img");
                for(Element img : imgs){
                    //Website lists 100 persons prior to other images.
                    //Thus arrayList will be full when size is 100.
                    if(arrayList.size() < 100){
                        arrayList.add(new Person(img.attr("alt"), img.attr("src")));
                    }

                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            displayPicture();
            displayNames();
        }
    }

    private void displayPicture(){
        Person person = (Person) arrayList.get(i);
        String url = person.getImageUrl();
        correctAnswer = person.getName();
        Picasso.with(getApplicationContext()).load(url).resize(800, 800).into(imageView);
        i++;
        if(i >= arrayList.size()){
            i = 0;
        }
    }

    private void displayNames(){
        Random random = new Random();
        int upperBound = arrayList.size()-1;
        int correctButton = random.nextInt(3);
        String wrongAnswer;
        switch (correctButton){
            //cases 0 to 3, since Random() generates from lower bound of 0.
            case(0):
                button1.setText(correctAnswer);
                button2.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button3.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button4.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                break;

            case(1):
                button2.setText(correctAnswer);
                button1.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button3.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button4.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                break;

            case(2):
                button3.setText(correctAnswer);
                button1.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button2.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button4.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                break;

            case(3):
                button4.setText(correctAnswer);
                button1.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button2.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button3.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                break;
        }
    }
}
