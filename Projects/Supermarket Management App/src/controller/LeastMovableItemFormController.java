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

public class LeastMovableItemFormController {
    public Label lblCode;
    private final ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.ITEM);

    public void loadItemCodeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO itemDTO =itemBO.getLeastMovableItem();
        lblCode.setText(itemDTO.getItemCode());
    }
}
