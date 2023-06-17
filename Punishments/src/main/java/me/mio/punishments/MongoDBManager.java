package me.mio.punishments;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MongoDBManager {
    private final String databaseName = "punishments";
    private final String collectionName = "punishmentLogs";
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDBManager() {
        String connectionString = "mongodb://localhost:27017";
        MongoClientURI uri = new MongoClientURI(connectionString);
        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase(databaseName);
        collection = database.getCollection(collectionName);
    }

    public boolean isBlacklisted(UUID targetUUID) {
        Document query = new Document("uuid", targetUUID.toString());
        return collection.find(query).first() != null;
    }

    public void addToBlacklist(UUID targetUUID) {
        Document document = new Document("uuid", targetUUID.toString());
        collection.insertOne(document);
    }

    public void removeFromBlacklist(UUID targetUUID) {
        Document query = new Document("uuid", targetUUID.toString());
        collection.deleteOne(query);
    }

    public void logPunishment(String punishmentLog) {
        Document document = new Document("log", punishmentLog);
        collection.insertOne(document);
    }

    public List<String> getPunishmentLogs() {
        List<String> punishmentLogs = new ArrayList<>();
        for (Document document : collection.find()) {
            String log = document.getString("log");
            if (log != null) {
                punishmentLogs.add(log);
            }
        }
        return punishmentLogs;
    }

    public void disconnect() {
        mongoClient.close();
    }
}
