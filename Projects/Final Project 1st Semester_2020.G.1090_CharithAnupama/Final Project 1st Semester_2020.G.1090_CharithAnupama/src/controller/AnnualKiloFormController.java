package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import view.tm.AnnualIncomeTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class AnnualKiloFormController {
    public TableColumn colYear;
    public TableColumn colKilo;
    public Label txtYear;
    public TableView<AnnualIncomeTM> tblYear;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();

        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colKilo.setCellValueFactory(new PropertyValueFactory<>("kg"));
        ObservableList<AnnualIncomeTM> income=new CustomerController().getAnnualIncome(txtYear.getText());
        tblYear.setItems(income);
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy");
        txtYear.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();

        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
}
