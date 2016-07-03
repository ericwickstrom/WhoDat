package com.example.beardsmcgee.whodat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String url = "http://www.posh24.com/celebrities";
    private ArrayList<Person> arrayList;

    private ImageView imageView;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    //correct answer name
    private String correctAnswer;
    //url for current picture
    private String pictureUrl;

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
                checkAnswer(view);
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(view);
            }
        });

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(view);
            }
        });

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(view);
            }
        });
    }

    private class FetchHTMLTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements imgs = doc.select("img");
                for (Element img : imgs) {
                    //Website lists 100 persons prior to other images.
                    //Thus arrayList will be full when size is 100.
                    if (arrayList.size() < 100) {
                        arrayList.add(new Person(img.attr("alt"), img.attr("src")));
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            nextQuestion();
        }
    }

    private void checkAnswer(View view) {
        Button button = (Button) view;

        //todo: should put these strings in the correct file
        String correctToast = "Correct!";
        String incorrectToast = "Wrong! That was " + correctAnswer;

        if (correctAnswer.equals(button.getText())) {
            Toast.makeText(getApplicationContext(), correctToast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), incorrectToast, Toast.LENGTH_SHORT).show();
        }
        nextQuestion();
    }

    private void nextQuestion() {
        Random random = new Random();
        int current = random.nextInt(arrayList.size() - 1);
        correctAnswer = (String) arrayList.get(current).getName();
        pictureUrl = (String) arrayList.get(current).getImageUrl();
        displayPicture();
        displayNames();
    }

    private void displayPicture() {
        Picasso.with(getApplicationContext()).load(pictureUrl).resize(800, 800).into(imageView);
    }

    private void displayNames() {
        Random random = new Random();
        int upperBound = arrayList.size() - 1;
        int correctButton = random.nextInt(3);
        switch (correctButton) {
            //cases 0 to 3, since Random() generates from lower bound of 0.
            case (0):
                button1.setText(correctAnswer);
                button2.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button3.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button4.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                break;

            case (1):
                button2.setText(correctAnswer);
                button1.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button3.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button4.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                break;

            case (2):
                button3.setText(correctAnswer);
                button1.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button2.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button4.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                break;

            case (3):
                button4.setText(correctAnswer);
                button1.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button2.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                button3.setText((String) arrayList.get(random.nextInt(upperBound)).getName());
                break;
        }
    }
}
