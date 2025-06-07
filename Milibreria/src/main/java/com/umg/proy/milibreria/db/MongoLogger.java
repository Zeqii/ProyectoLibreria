package com.umg.proy.milibreria.db;

import com.mongodb.client.*;
import org.bson.Document;

public class MongoLogger {

    public static void log(String action, String details) {
        try (MongoClient mongoClient = MongoClients.create(DBConfig.mongoUri)) {
            MongoDatabase db = mongoClient.getDatabase(DBConfig.mongoDatabase);
            MongoCollection<Document> collection = db.getCollection(DBConfig.mongoCollection);

            Document log = new Document()
                    .append("action", action)
                    .append("details", details)
                    .append("timestamp", new java.util.Date());

            collection.insertOne(log);
        } catch (Exception e) {
            System.err.println("MongoLogger error: " + e.getMessage());
        }
    }
}
