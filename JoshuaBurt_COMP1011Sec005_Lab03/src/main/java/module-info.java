module com.example.joshuaburt_comp1011sec005_lab03 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.joshuaburt_comp1011sec005_lab03 to javafx.fxml;
    exports com.example.joshuaburt_comp1011sec005_lab03;
}