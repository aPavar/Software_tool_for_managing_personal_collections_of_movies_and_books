package sample;

import com.sun.xml.internal.bind.v2.runtime.output.ForkXmlOutput;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;

class  Sinhronizer{
    Controller controller;
   public   Sinhronizer(){}
    void setController(Controller c){controller=c;}
    Controller getController(){return controller;}


}
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
//        AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));

//////////////////////   Get class Controller
       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        AnchorPane root = loader.load();
        Controller controller= loader.getController();
        Sinhronizer sinhronizer=new Sinhronizer();
        sinhronizer.setController(controller);

////////////////////////End code get class Controller
        primaryStage.setTitle("DevolopmentProject");
        primaryStage.setScene(new Scene(root));
      //  primaryStage.getScene().
      //  String pathFile=new String("D:\\OfficeOfCollections");

     //   listView.setEditable(true);
     //   listView.setItems(data);
     //   listView.setCellFactory(ComboBoxListCell.forListView(data));
//        controller.SetDataListView(data);



        primaryStage.show();

    }


    public static void main(String[] args) throws InterruptedException {
        launch(args);
        Sinhronizer s=new Sinhronizer();
        s.getController().writer.join();
        if(s.getController().writer!=null)
        s.getController().writer.interrupt();

    }
}
