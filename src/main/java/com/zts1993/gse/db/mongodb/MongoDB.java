/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.db.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zts1993.gse.util.ConfigurationUtil;
import org.bson.Document;

/**
 * Created by TianShuo on 2015/4/12.
 */
public class MongoDB {

    private static MongoClient mongoClient;

    private MongoDB() {
    }

    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            synchronized (MongoDB.class) {
                if (mongoClient == null) {
                    mongoClient = new MongoClient(
                            ConfigurationUtil.getValue("MongoServerIp", "127.0.0.1"),
                            Integer.parseInt(ConfigurationUtil.getValue("MongoServerPort", "27017"))
                    );
                }
            }
        }
        return mongoClient;
    }


    public static MongoDatabase getMongoDatabase(String dbName) {
        return getMongoClient().getDatabase(dbName);
    }

    public static MongoCollection<Document> getMongoCollection(String dbName, String collectionName) {
        return getMongoDatabase(dbName).getCollection(collectionName);
    }

}
