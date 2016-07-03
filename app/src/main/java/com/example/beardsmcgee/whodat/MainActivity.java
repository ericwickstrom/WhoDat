package com.example.beardsmcgee.whodat;

import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private String url = "http://www.posh24.com/celebrities";
    private ArrayList<Person> arrayList;

    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();
        FetchHTMLTask task = new FetchHTMLTask();
        task.execute();

        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);

        final Iterator it = arrayList.iterator();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = (Person) it.next();
                String url = person.getImageUrl();
                String name = person.getName();

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
    }
}