import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.ArrayList;

import static java.sql.DriverManager.println;


public class firstTweets {

    public static void main(String[] args) throws TwitterException {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("Enter Key")
                .setOAuthConsumerSecret("Enter Key")
                .setOAuthAccessToken("Enter Key")     
                .setOAuthAccessTokenSecret("Enter Key");

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter4j.Twitter twitter = tf.getInstance();

//        List alltweets = new ArrayList<>();
//        String screen_name = "@Ibra_official";
//        Paging paging = new Paging(1, 100);
//        List<Status> statuses = twitter.getUserTimeline(screen_name,paging);
//
//        alltweets.addAll(statuses);
//        int oldest = alltweets.get(-1).;
        Query query = new Query("#peace");
        int numberOfTweets = 512;
        long lastID = Long.MAX_VALUE;
        ArrayList<Status> tweets = new ArrayList<Status>();
        while (tweets.size () < numberOfTweets) {
            if (numberOfTweets - tweets.size() > 100)
                query.setCount(100);
            else
                query.setCount(numberOfTweets - tweets.size());
            try {
                QueryResult result = twitter.search(query);
                tweets.addAll(result.getTweets());
                System.out.println("Gathered " + tweets.size() + " tweets");
                for (Status t: tweets)
                    if(t.getId() < lastID) lastID = t.getId();

            }
            catch (TwitterException te) {
                System.out.println("Couldn't connect: " + te);
            }
            query.setMaxId(lastID-1);
        }
        System.out.println("Done");
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("tweetData.txt"), "utf-8"));
            for(int i=0;i<tweets.size();i++){
                writer.write(String.valueOf(tweets.get(i)));
            }

            //writer.write("Something");
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }

    }
}
