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
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
//        AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));

//////////////////////   Get class Controller
       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        AnchorPane root = loader.load();
        Controller controller= loader.getController();

////////////////////////End code get class Controller
        primaryStage.setTitle("DevolopmentProject");
        primaryStage.setScene(new Scene(root));
        String pathFile=new String("D:\\OfficeOfCollections");
        File directory1=new File(pathFile);
        if(!directory1.isFile()&&
                !directory1.isDirectory())
            System.out.println("Not File");
        File[] listFile=directory1.listFiles();
            int j=0;
            while(controller.directory.picker(pathFile).size()>j)
            {
                controller.infoFile=controller.directory.picker(pathFile).get(j);
                System.out.print(controller.infoFile.getName());
                j++;
            }
     //   listView.setEditable(true);
     //   listView.setItems(data);
     //   listView.setCellFactory(ComboBoxListCell.forListView(data));
//        controller.SetDataListView(data);



        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
