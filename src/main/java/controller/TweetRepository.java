package controller;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetRepository {

    private static TweetRepository data = new TweetRepository();

    private static final String tableTweetsName = "tweets";

    private static final String tableTweetsLanguageName = tableTweetsName + "_language";

    private Session session;

    public static TweetRepository sharedInstance() {
        return data;
    }

    public void configureSession(Session session) {
        this.session = session;
    }

    private TweetRepository() {
    }

    public void createTableTweets() {

        System.out.println("createTableTweets - init");

        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(tableTweetsName).append("(")
                .append("id int PRIMARY KEY,")
                .append("text text,")
                .append("created_at date,")
                .append("is_truncated boolean,")
                .append("latitude double,")
                .append("longitude double,")
                .append("is_favorited boolean,")
                .append("username text")
                .append(");");

        final String query = sb.toString();
        System.out.println("Criando tabela " + tableTweetsLanguageName);
        session.execute(query);
    }

    public void insertTweet(Tweet tweet) {

        System.out.println("insertTweet - init");

        StringBuilder builder = new StringBuilder("INSERT INTO ")
                .append(tableTweetsName).append("(id, text, created_at, is_truncated, latitude, longitude, is_favorited, username) ")
                .append("VALUES (")
                .append(tweet.getId()).append(", '")
                .append(tweet.getText()).append("', '")
                .append(tweet.getCreatedAt()).append("', ")
                .append(tweet.isTruncated()).append(", ")
                .append(tweet.getLatitude()).append(", ")
                .append(tweet.getLongitude()).append(", ")
                .append(tweet.isFavorited()).append(", '")
                .append(tweet.getUsername()).append("'")
                .append(");");

        String query = builder.toString();
        System.out.println("Inserindo tweet " + tweet.toString());
        session.execute(query);
    }

    public List<Tweet> getAllTweets() {

        System.out.println("getAllTweets - init");

        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(tableTweetsName);

        final String query = sb.toString();
        ResultSet results = session.execute(query);

        List<Tweet> tweets = new ArrayList<Tweet>();

        for (Row row : results) {
            Tweet twt = new Tweet(
                    (long) row.getInt("id"),
                    row.getString("text"),
                    row.getDate("created_at"),
                    row.getBool("is_truncated"),
                    row.getDouble("latitude"),
                    row.getDouble("longitude"),
                    row.getBool("is_favorited"),
                    row.getString("username")
            );

            tweets.add(twt);
        }

        return tweets;
    }

    public void deleteById(long id) {

        System.out.println("deleteById - init");

        StringBuilder sb = new StringBuilder("DELETE FROM ")
                .append(tableTweetsName)
                .append(" WHERE id = ")
                .append(String.valueOf(id)).append(";");

        final String query = sb.toString();
        System.out.println("Excluindo tweet de id " + id);
        session.execute(query);
    }

    public void dropTable() {

        System.out.println("dropTable - init");

        StringBuilder builder = new StringBuilder("DROP TABLE IF EXISTS ").append(tableTweetsName);

        final String query = builder.toString();
        System.out.println("Excluindo tabela " + tableTweetsName);

        session.execute(query);
    }

    // MARK: Parte 2

    public void createTableTweetsLanguage() {

        System.out.println("createTableTweetsLanguage - init");

        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(tableTweetsLanguageName).append("(")
                .append("id int, ")
                .append("text text, ")
                .append("created_at date, ")
                .append("username text, ")
                .append("language text, ")
                .append("PRIMARY KEY (language, created_at))")
                .append("WITH CLUSTERING ORDER BY (created_at DESC);");

        final String query = builder.toString();
        System.out.println("Criando tabela " + tableTweetsLanguageName);
        session.execute(query);
    }

    public void insertTweetsLanguage(Tweet tweet) {

        System.out.println("insertTweetsLanguage - init");

        StringBuilder builder = new StringBuilder("INSERT INTO ")
                .append(tableTweetsLanguageName).append("(id, text, created_at, username, language) ")
                .append("VALUES (")
                .append(tweet.getId()).append(", '")
                .append(tweet.getText()).append("', '")
                .append(tweet.getCreatedAt()).append("', '")
                .append(tweet.getUsername()).append("', '")
                .append(tweet.getLanguage()).append("');");

        final String query = builder.toString();
        session.execute(query);
        System.out.println("Inserindo tweet language " + tweet.toString());
    }

    public void getAllTweetsLanguage() {

        System.out.println("getAllTweetsLanguage - init");

        StringBuilder builder = new StringBuilder("SELECT * FROM ").append(tableTweetsLanguageName);

        final String query = builder.toString();
        ResultSet results = session.execute(query);

        for (Row row : results) {
            System.out.println("TweetLanguage: "
                    + row.getInt("id") + ", "
                    + row.getString("text") + ", "
                    + row.getDate("created_at") + ", "
                    + row.getString("username") + ", "
                    + row.getString("language"));
        }
    }

    public void getLastByLanguage(String language) {

        System.out.println("getLastByLanguage - init");

        StringBuilder builder = new StringBuilder("SELECT * FROM ")
                .append(tableTweetsLanguageName)
                .append(" WHERE language = '")
                .append(language)
                .append("' ORDER BY created_at DESC LIMIT 1;");

        final String query = builder.toString();
        ResultSet results = session.execute(query);

        for (Row row : results) {
            System.out.println("TweetLanguage: "
                    + row.getInt("id") + ", "
                    + row.getString("text") + ", "
                    + row.getDate("created_at") + ", "
                    + row.getString("username") + ", "
                    + row.getString("language"));
        }
    }

    public void deleteTweetByLanguage(String language) {

        System.out.println("deleteTweetByLanguage - init");

        StringBuilder builder = new StringBuilder("DELETE FROM ")
                .append(tableTweetsLanguageName)
                .append(" WHERE language = '")
                .append(language)
                .append("';");

        final String query = builder.toString();
        session.execute(query);
    }

    public void dropTableLanguage() {

        System.out.println("dropTableLanguage - init");

        StringBuilder builder = new StringBuilder("DROP TABLE IF EXISTS ").append(tableTweetsLanguageName);

        final String query = builder.toString();
        System.out.println("Excluindo tabela " + tableTweetsLanguageName);

        session.execute(query);
    }





}
