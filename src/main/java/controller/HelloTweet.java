package controller;

import com.datastax.driver.core.*;
import model.Tweet;

import java.util.List;

public class HelloTweet {

    public static String path = "localhost";
    public static String keyspaceName = "tweet_library";

    public static void main(String[] args) {

        Cluster cluster = null;

        try {

            // Abre conexao com o Cassandra
            cluster = Cluster.builder().addContactPoint(path).build();
            Session session = cluster.connect();

            KeyspaceRepository keyspaceRepo = KeyspaceRepository.sharedInstance();
            keyspaceRepo.configureSession(session);

            TweetRepository tweetRepo = TweetRepository.sharedInstance();
            tweetRepo.configureSession(session);

            // Cria o keyspace
            keyspaceRepo.createKeyspace(keyspaceName, "SimpleStrategy", 1);
            keyspaceRepo.useKeyspace(keyspaceName);

            System.out.println("Keyspace " + keyspaceName + " conectado!");

            // Cria a tabela de tweets
            tweetRepo.createTableTweets();

            // Cria a tabela de tweets by language
            tweetRepo.createTableTweetsLanguage();

            // Insere 5 tweets
            for (int i = 0; i< 5; i++) {

                LocalDate date = LocalDate.fromYearMonthDay(2019,9,23+i);
                Tweet tweet = new Tweet(
                        i+1,
                        "teste",
                        date,
                        i % 2 == 0,
                        -32.0001 + i,
                        -32.0001 + i,
                        i % 3 == 0,
                        "user_" + i
                );

                tweetRepo.insertTweet(tweet);
            }

            String[] languages = {"pt_br","en"};
            // Insere 5 tweets language
            for (int i = 0; i< 5; i++) {

                LocalDate date = LocalDate.fromYearMonthDay(2019,8,26+i);
                Tweet tweet = new Tweet(i, "Maravilha! Um tweet Brasileiro!", date, "user_" + (i+1), languages[i%2]);
                tweetRepo.insertTweetsLanguage(tweet);
            }

            // Imprime os tweets
            List<Tweet> tweets = tweetRepo.getAllTweets();
            for (Tweet tweet: tweets) {
                System.out.println(tweet.toString());
            }

            // Imprime os tweets language
            tweetRepo.getAllTweetsLanguage();

            // Seleciona um tweet language
            tweetRepo.getLastByLanguage(languages[0]);

            // Exclui um tweet
            tweetRepo.deleteById(1);

            // Exclui os tweets de uma language ("en")
            tweetRepo.deleteTweetByLanguage(languages[1]);

            // Imprime os tweets
            tweets = tweetRepo.getAllTweets();
            for (Tweet tweet: tweets) {
                System.out.println(tweet.toString());
            }

            // Imprime os tweets language
            tweetRepo.getAllTweetsLanguage();

            // Exclui tabela de tweet
            tweetRepo.dropTable();

            // Exclui tabela de tweet languages
            tweetRepo.dropTableLanguage();

            // Exclui keyspace
            keyspaceRepo.dropKeyspace(keyspaceName);

        } finally {
            if (cluster != null)
                cluster.close();
        }
    }
}
