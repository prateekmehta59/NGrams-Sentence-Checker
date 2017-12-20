package com.company;

import java.net.URLEncoder;
import java.net.URLDecoder;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException {
        // parse the url
        String google = "http://www.google.com/search?q=";
        String search = "I am a boy";
        String charset = "UTF-8";
        String trial = URLEncoder.encode(search, charset);
        int count = 0;
        for (int i = 0; i <= 20; i = i + 10) {
            String page = google + trial + "&ei=aon4Wfr6MqSsjwTr9aqgCg&start=" + i + "&sa=N&biw=1440&bih=803";
            String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!

            Elements links = Jsoup.connect(page).userAgent(userAgent).get().select(".g>.r>a");

            for (Element link : links) {
                String title = link.text();
                String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
                url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

                if (!url.startsWith("http")) {
                    continue; // Ads/news/etc.
                }

                //System.out.println("Title: " + title);
                System.out.println("URL: " + url);
                // extract the text in the url
                Document doc = Jsoup.connect(url).get();
                Elements ps = doc.select("p");
                String filename = "File" + count + ".txt";
                count++;
                BufferedWriter out = new BufferedWriter(new FileWriter(new File(filename)));
                out.write(ps.text());
                out.close();

                // to lowercase letters
                try {
                    FileReader fr = new FileReader(filename);
                    BufferedReader br = new BufferedReader(fr);
                    PrintWriter newOut = new PrintWriter(new FileWriter(new File("lowercase" + filename)));
                    String s = "";
                    while ((s = br.readLine()) != null) {
                        newOut.write(s.toLowerCase() + "\n");
                    }
                    newOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}