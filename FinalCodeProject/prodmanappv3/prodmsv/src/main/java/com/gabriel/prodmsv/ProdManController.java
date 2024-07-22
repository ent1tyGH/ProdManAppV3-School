package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.RemoteContactServiceImpl;
import com.gabriel.prodmsv.model.Contact;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Data;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Data
public class ProdManController implements Initializable {

    @Setter
    private Stage stage;
    @Setter
    private Scene createViewScene;
    @Setter
    private Scene updateViewScene;
    @Setter
    private Scene deleteViewScene;

    @FXML
    private TextField tfId;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfDesc;
    @FXML
    private TextField tfUom;
    @FXML
    private ImageView productImage;
    @FXML
    private VBox prodman;
    @FXML
    private ListView<Contact> lvContacts;
    @FXML
    private Button createButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button closeButton;

    private Image puffy;
    private Image wink;

    private ContactService contactService;

    private UpdateContactController updateContactController;
    private DeleteContactController deleteContactController;
    private CreateContactController createContactController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ProdManController: initialize");
        disableControls();
        try {
            refresh();
            puffy = new Image(getClass().getResourceAsStream("/images/puffy.gif"));
            wink = new Image(getClass().getResourceAsStream("/images/wink.gif"));
            productImage.setImage(puffy);
        } catch (Exception ex) {
            showErrorDialog("Error with image: " + ex.getMessage());
        }
    }

    public void disableControls() {
        tfId.setEditable(false);
        tfName.setEditable(false);
        tfDesc.setEditable(false);
        tfUom.setEditable(false);
    }

    public void setControlTexts(Contact contact) {
        tfName.setText(contact.getName());
        tfDesc.setText(contact.getDescription());
        tfUom.setText(contact.getUomName());
    }

    public void clearControlTexts() {
        tfId.clear();
        tfName.clear();
        tfDesc.clear();
        tfUom.clear();
    }

    @FXML
    public void onMouseClicked(MouseEvent mouseEvent) {
        Contact contact = lvContacts.getSelectionModel().getSelectedItem();
        if (contact == null) {
            return;
        }
        tfId.setText(Integer.toString(contact.getId()));
        setControlTexts(contact);
        System.out.println("Clicked on " + contact);
    }

    @FXML
    public void onCreate(ActionEvent actionEvent) {
        System.out.println("ProdManController: onCreate ");
        Node node = (Node) actionEvent.getSource();
        Scene currentScene = node.getScene();
        Window window = currentScene.getWindow();
        window.hide();
        try {
            if (createViewScene == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("create-contact.fxml"));
                Parent root = fxmlLoader.load();
                createContactController = fxmlLoader.getController();
                createContactController.setStage(stage);
                createContactController.setParentScene(currentScene);
                createContactController.setContactService(contactService);
                createContactController.setProdManController(this);
                createViewScene = new Scene(root, 300, 600);
                stage.setTitle("Create Contact");
                stage.setScene(createViewScene);
                stage.show();
            } else {
                stage.setScene(createViewScene);
                stage.show();
            }
            createContactController.clearControlTexts();
            clearControlTexts();
        } catch (Exception ex) {
            showErrorDialog("Error: " + ex.getMessage());
        }
    }

    @FXML
    public void onUpdate(ActionEvent actionEvent) {
        System.out.println("ProdManController: onUpdate ");
        Node node = (Node) actionEvent.getSource();
        Scene currentScene = node.getScene();
        Window window = currentScene.getWindow();
        window.hide();
        try {
            if (updateViewScene == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("update-contact.fxml"));
                Parent root = fxmlLoader.load();
                updateContactController = fxmlLoader.getController();
                updateContactController.setController(this);
                updateContactController.setStage(stage);
                updateContactController.setParentScene(currentScene);
                updateViewScene = new Scene(root, 300, 600);
            } else {
                updateContactController.refresh();
            }
            stage.setTitle("Update Contact");
            stage.setScene(updateViewScene);
            stage.show();
        } catch (Exception ex) {
            showErrorDialog("Error: " + ex.getMessage());
        }
    }

    @FXML
    public void onDelete(ActionEvent actionEvent) {
        System.out.println("ProdManController: onDelete ");
        Node node = (Node) actionEvent.getSource();
        Scene currentScene = node.getScene();
        Window window = currentScene.getWindow();
        window.hide();
        try {
            if (deleteViewScene == null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("delete-contact.fxml"));
                Parent root = fxmlLoader.load();
                deleteContactController = fxmlLoader.getController();
                deleteContactController.setController(this);
                deleteContactController.setStage(stage);
                deleteContactController.setParentScene(currentScene);
                deleteViewScene = new Scene(root, 300, 600);
            } else {
                deleteContactController.refresh();
            }
            stage.setTitle("Delete Contact");
            stage.setScene(deleteViewScene);
            stage.show();
        } catch (Exception ex) {
            showErrorDialog("Error: " + ex.getMessage());
        }
    }

    @FXML
    public void onClose(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit and lose changes?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void addItem(Contact contact) {
        lvContacts.getItems().add(contact);
    }

    private void refresh() throws Exception {
        contactService = ContactServiceImpl.getService();
        Contact[] contacts = contactService.getContacts();
        lvContacts.getItems().clear();
        lvContacts.getItems().addAll(contacts);
    }
}
