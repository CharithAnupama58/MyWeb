package controller;

import bo.BOFactory;
import bo.custom.ItemBO;
import bo.custom.PurchaseOrderBO;
import bo.custom.impl.ItemBOImpl;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import dto.ItemDTO;

import java.sql.SQLException;

public class MostMovableItemFormController {
    public Label lblItemCode;
    private final ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.ITEM);

    public void loadLabelOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO itemDTO =itemBO.getMostMovableItem();
        lblItemCode.setText(itemDTO.getItemCode());
    }
}
