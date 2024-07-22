package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.ContactService;
import com.gabriel.prodmsv.model.Contact;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
public class DeleteProductController implements Initializable {
    public TextField tfId;
    public TextField tfName;
    public TextField tfDesc;
    public TextField tfUom;
    @Setter
    Stage stage;
    @Setter
    Scene parentScene;
    @Setter
    ContactService productService;
    @Setter
    ProdManController controller;

    public void refresh(){
        Contact product= ProdManController.product;
        tfId.setText(Integer.toString(product.getId()));
        tfName.setText(product.getName());
        tfDesc.setText(product.getDescription());
        tfUom.setText(product.getUomName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("DeleteProductController: initialize");
        tfId=new TextField("");
        refresh();
    }

    public void onBack(ActionEvent actionEvent) {
        System.out.println("CreateProductController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }


    public void onSubmit(ActionEvent actionEvent) {
        try {
            Contact product = toObject(true);
            ContactService.getService().delete(product.getId());
            controller.refresh();
            controller.clearControlTexts();
            Node node = ((Node) (actionEvent.getSource()));
            Window window = node.getScene().getWindow();
            window.hide();
            stage.setTitle("Manage Product");
            stage.setScene(parentScene);
            stage.show();
        }
        catch (Exception e){
            String message="Error encountered deleting product";
            showErrorDialog(message,e.getMessage());
        }
    }

    protected Contact toObject(boolean isEdit){
        Contact product= new Contact();
        try {
            if(isEdit) {
                product.setId(Integer.parseInt(tfId.getText()));
            }
            product.setName(tfName.getText());
            product.setDescription(tfDesc.getText());
        }catch (Exception e){
            showErrorDialog("Error" ,e.getMessage());
        }
        return product;
    }

    public void showErrorDialog(String message,String addtlMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(addtlMessage)));
        alert.showAndWait();
    }
}
