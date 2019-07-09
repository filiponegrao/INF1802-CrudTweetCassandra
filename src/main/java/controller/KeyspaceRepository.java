package controller;

import com.datastax.driver.core.Session;

public class KeyspaceRepository {

    private static KeyspaceRepository data = new KeyspaceRepository();

    private Session session;

    public static KeyspaceRepository sharedInstance() {
        return data;
    }

    public void configureSession(Session session) {
        this.session = session;
    }

    private KeyspaceRepository() {
    }

    public void createKeyspace(String name, String replicationStrategy, int replicasCount) {

        StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
                .append(name)
                .append(" WITH replication = {")
                .append("'class':'")
                .append(replicationStrategy)
                .append("','replication_factor':")
                .append(replicasCount).append("};");

        final String query = sb.toString();

        session.execute(query);
    }

    public void useKeyspace(String keyspace)  {

        session.execute("USE " + keyspace);
    }

    public void dropKeyspace(String keyspaceName) {

        StringBuilder builder = new StringBuilder("DROP KEYSPACE ").append(keyspaceName);
        String query = builder.toString();
        System.out.println("Excluindo keyspace " + keyspaceName);
        session.execute(query);
    }
}
