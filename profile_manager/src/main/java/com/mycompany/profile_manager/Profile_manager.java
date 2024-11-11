/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.profile_manager;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import java.util.Scanner;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author Lakshetaa
 */
public class Profile_manager {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("profile_manager");
            MongoCollection<Document> collection = database.getCollection("profiles");
            int choice=1;
            String firstName;
            String lastName;
            String userName;
            String bio;
            String profilePicture;
            while(choice!=0){
                System.out.println("Enter 1 to Create a new Profile\nEnter 2 to Update a Profile\nEnter 0 to Exit: ");
                choice = sc.nextInt();
                switch(choice){
                    case 1 -> {
                        firstName=sc.nextLine();
                        lastName=sc.nextLine();
                        userName=sc.nextLine();
                        bio=sc.nextLine();
                        profilePicture=sc.nextLine();
                        Document profile = new Document("firstName",firstName)
                                .append("lastName", lastName)
                                .append("userName", userName)
                                .append("bio", bio)
                                .append("profilePicture", profilePicture);
                        collection.insertOne(profile);
                        System.out.println("Profile Created successfully!");
                    }
                    case 2 -> {
                        System.out.println("Enter User Name: ");
                        userName=sc.nextLine();
                        Document query = new Document().append("userName",  userName);
                        System.out.println("Enter First Name: ");
                        firstName=sc.nextLine();
                        System.out.println("Enter Last Name: ");
                        lastName=sc.nextLine();
                        System.out.println("Enter Bio: ");
                        bio=sc.nextLine();
                        System.out.println("Enter Profile Picture: ");
                        profilePicture=sc.nextLine();
                        Bson updates = Updates.combine(
                                Updates.set("fistName", firstName),
                                Updates.set("lastName", lastName),
                                Updates.set("bio", bio),
                                Updates.set("profilePicture", profilePicture));
                        try {
                            UpdateResult result = collection.updateOne(query, updates);
                            if(result.getModifiedCount()>0){
                                System.out.println("Update Successfull.");                            
                            }
                        } catch (MongoException me) {
                            System.err.println("Unable to update due to an error: " + me);
                        }
                    }

                        
                        
                }
                
            }
        }
    }
}          