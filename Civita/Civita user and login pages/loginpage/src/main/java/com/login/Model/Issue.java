
//Under model folder Issue.java Firebase integrated... 

package com.login.Model;

import java.time.LocalDate;
import java.util.UUID; // To generate unique IDs

public class Issue {
    private String id; // Unique ID for each issue
    private String description;
    private String date; // Store as String to simplify Firestore mapping

    // No-argument constructor required for Firestore object mapping
    public Issue() {
    }

    public Issue(String description, LocalDate date) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
        this.description = description;
        this.date = (date != null) ? date.toString() : null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Issue{" +
               "id='" + id + '\'' +
               ", description='" + description + '\'' +
               ", date='" + date + '\'' +
               '}';
    }
}