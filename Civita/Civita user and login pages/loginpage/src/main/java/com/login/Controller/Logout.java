
package com.login.Controller;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class Logout  {

    public static void show(Window parentWindow) {
    Stage popup = new Stage();
    popup.initOwner(parentWindow);
    popup.initStyle(StageStyle.TRANSPARENT);
    popup.setResizable(false);

    // ❌ Close button
    Button closeBtn = new Button("✖");
    closeBtn.setStyle("""
        -fx-background-color: transparent;
        -fx-text-fill: #e74c3c;
        -fx-font-size: 16px;
        -fx-font-weight: bold;
        -fx-cursor: hand;
    """);
    closeBtn.setOnAction(e -> popup.close());

    // 🚀 Logout message and button
    Label confirmText = new Label("Ready to escape? Let's log you out ...");
    confirmText.setStyle("""
        -fx-font-size: 16px;
        -fx-font-weight: bold;
        -fx-text-fill:black;
    """);

    Button confirmBtn = new Button("🚀 Beam Me Out!");
    confirmBtn.setStyle("""
        -fx-background-color: #c05646ff);
        -fx-text-fill: black;
        -fx-font-weight: bold;
        -fx-background-radius: 12;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 6, 0.3, 2, 3);
        -fx-border-color:black;
        -fx-border-radius: 12;
        -fx-padding: 10 20;
    """);

    confirmBtn.setOnAction(e -> {
        popup.close();
        showLogoutToast(parentWindow);
        navigateToLogin((Stage) parentWindow);
    });

    VBox contentBox = new VBox(20, confirmText, confirmBtn);
    contentBox.setAlignment(Pos.CENTER);
    contentBox.setPadding(new Insets(30, 30, 30, 30));

    StackPane container = new StackPane(contentBox, closeBtn);
    container.setStyle("""
        -fx-background-color: linear-gradient(to bottom right, #847c89ff, #b3f1f1ff);
        -fx-background-radius: 20;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0.1, 1, 2);
    """);
    container.setPadding(new Insets(10));

    StackPane.setAlignment(closeBtn, Pos.TOP_RIGHT);
    StackPane.setMargin(closeBtn, new Insets(10, 10, 0, 0));

    Scene scene = new Scene(container, 380, 200);
    scene.setFill(Color.TRANSPARENT);
    popup.setScene(scene);
    popup.showAndWait();
}
// ✅ Shows a temporary toast message
private static void showLogoutToast(Window owner) {
    Label toastLabel = new Label("✅ Logged out successfully!");
    toastLabel.setStyle("""
        -fx-background-color: rgba(0, 0, 0, 0.75);
        -fx-text-fill: Gold;
        -fx-font-size: 14px;
        -fx-padding: 10px 20px;
        -fx-background-radius: 10;
    """);

    StackPane toastPane = new StackPane(toastLabel);
    toastPane.setPickOnBounds(false);
    toastPane.setMouseTransparent(true);
    toastPane.setAlignment(Pos.BOTTOM_CENTER);

    Scene toastScene = new Scene(toastPane, 350, 80);
    toastScene.setFill(Color.TRANSPARENT);

    Stage toastStage = new Stage();
    toastStage.initStyle(StageStyle.TRANSPARENT);
    toastStage.initOwner(owner);
    toastStage.setScene(toastScene);
    toastStage.setAlwaysOnTop(true);
    toastStage.show();

    // Auto-close after 2.5 seconds
    new Thread(() -> {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
        Platform.runLater(toastStage::close);
    }).start();
}

// ✅ Redirects to login page (replace with your login logic)
private static void navigateToLogin(Stage currentStage) {
    // Replace with actual login screen code
    Label loginLabel = new Label("🔐 Login Page (Stub)");
    StackPane loginRoot = new StackPane(loginLabel);
    loginRoot.setStyle("-fx-background-color: white;");

    Scene loginScene = new Scene(loginRoot, 1600, 800);
    currentStage.setScene(loginScene);
    currentStage.show();
}
}
