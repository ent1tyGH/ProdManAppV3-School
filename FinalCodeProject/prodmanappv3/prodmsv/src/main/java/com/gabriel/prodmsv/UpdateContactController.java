package com.gabriel.prodmsv;

import com.gabriel.prodmsv.model.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
public class UpdateContactController implements Initializable {
    @Setter
    Stage stage;
    @Setter
    Scene parentScene;
    @Setter
    ProdManController controller;

    @FXML
    private TextField tfId;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfEmail;

    public void refresh() throws Exception {
        Contact contact = ProdManController.contact;
        tfId.setText(contact.getId().toString());
        tfName.setText(contact.getName());
        tfPhoneNumber.setText(contact.getPhoneNumber());
        tfEmail.setText(contact.getEmail());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UpdateContactController: initialize");
        tfId = new TextField();
        try {
            refresh();
        } catch (Exception ex) {
            System.out.println("UpdateContactController: " + ex.getMessage());
        }
    }

    public void onSubmit(ActionEvent actionEvent) {
        Contact contact = new Contact();
        contact.setId(Long.parseLong(tfId.getText()));
        contact.setName(tfName.getText());
        contact.setPhoneNumber(tfPhoneNumber.getText());
        contact.setEmail(tfEmail.getText());

        try {
            contact = com.gabriel.prodmsv.service.ContactServiceImpl.getService().update(contact);
            controller.refresh();
            controller.setControlTexts(contact);
            onBack(actionEvent);
        } catch (Exception ex) {
            System.out.println("UpdateContactController:onSubmit Error: " + ex.getMessage());
        }
    }

    public void onBack(ActionEvent actionEvent) {
        System.out.println("UpdateContactController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }
}
