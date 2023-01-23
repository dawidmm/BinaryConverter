module pl.converter.converter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;

    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires org.apache.commons.text;

    opens pl.converter.converter to javafx.fxml;
    exports pl.converter.converter;
}