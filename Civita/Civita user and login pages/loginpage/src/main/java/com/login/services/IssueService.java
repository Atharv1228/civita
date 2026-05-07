
// under services- IssueService.java Firebase integrated code ...

package com.login.services;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FirestoreException;
import com.google.firebase.database.annotations.Nullable;

import javafx.application.Platform;
import com.login.Model.Issue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class IssueService {

    private static final String COLLECTION_NAME = "issues";
    private Firestore db;

    public IssueService() {
        //FirebaseInitializer.initialize(); // Ensure Firebase is initialized
        db = FirebaseInitialize.getDB();
        System.out.println("IssueService initialized. Firestore instance obtained.");
    }

    public void addIssue(Issue issue) {
        try {
            ApiFuture<WriteResult> result = db.collection(COLLECTION_NAME).document(issue.getId()).set(issue);
            System.out.println("Issue added at: " + result.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error adding issue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Issue> getAllIssues() {
        List<Issue> issues = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                issues.add(document.toObject(Issue.class));
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error getting all issues: " + e.getMessage());
            e.printStackTrace();
        }
        return issues;
    }

    public void deleteIssue(String issueId) {
        ApiFuture<WriteResult> writeResult = db.collection(COLLECTION_NAME).document(issueId).delete();
        try {
            System.out.println("Issue with ID " + issueId + " deleted at: " + writeResult.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error deleting issue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void listenForIssues(Consumer<List<Issue>> onUpdate) {
        db.collection(COLLECTION_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirestoreException e) {
                if (e != null) {
                    System.err.println("Listen failed:" + e);
                    return;
                }

                if (snapshots != null) {
                    List<Issue> updatedIssues = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshots.getDocuments()) {
                        updatedIssues.add(doc.toObject(Issue.class));
                    }
                    // Run on JavaFX Application Thread
                    Platform.runLater(() -> onUpdate.accept(updatedIssues));
                }
            }
        });
    }
}