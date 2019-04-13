package com.example.pnam.doctruyen.GetRSS;

import android.os.AsyncTask;

import com.example.pnam.doctruyen.Object.Truyen;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GetRSS extends AsyncTask<String,Void,ArrayList<Truyen>> {
    @Override
    protected ArrayList<Truyen> doInBackground(String... strings) {

        String url = strings[0];
        ArrayList<Truyen> truyens = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("item");
            for (Element element: elements){

                String title = element.select("title").text();
                String itemDescription = element.select("description").text();
                Document docDescription = Jsoup.parse(itemDescription);
                String link = docDescription.select("a").attr("href");
                String urlImg = docDescription.select("img ").attr("src");
                String description = docDescription.text();

                truyens.add(new Truyen(title,description,link,urlImg));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return truyens;
    }

    @Override
    protected void onPostExecute(ArrayList<Truyen> truyens) {
        super.onPostExecute(truyens);
    }
}
