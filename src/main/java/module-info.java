module tubryansk.lisitsyn.newai {
    requires javafx.controls;
    requires javafx.fxml;


    opens tubryansk.lisitsyn.newai to javafx.fxml;
    exports tubryansk.lisitsyn.newai;
}