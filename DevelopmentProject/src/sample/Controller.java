package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

import java.io.Console;
import java.io.File;
import java.util.Date;

public class Controller {

    @FXML
    TableView<Table> tableView;
    @FXML
    TableColumn<Table,String> tableColumnName;
    @FXML
    TableColumn<Table,String> tableColumnDataChange;
    @FXML
    TableColumn<Table,String> tableColumnType;
    @FXML
    TableColumn<Table,String> tableColumnSize;

    @FXML TreeView treeView;
    @FXML AnchorPane anchorPane;
    @FXML AnchorPane anchorPane3;
    String pathFileStandart=new String("D:\\OfficeOfCollections");
    public  class InfoFile
    {
        String name;
        String data;
        String type;
        String size;
        public  InfoFile(){}
        public  InfoFile(String Name,String Data,String Type,String Size)
        {
            name=Name;
            data=Data;
            type=Type;
            size=Size;
        }
        public String getName(){
            return name;
        }
        public String getData(){
            return data;
        }
        public String getType(){
            return type;
        }
        public String getSize(){
            return size;
        }
        public void setName(String Name){
            name=Name;
        }
        public void setData(String Data){
            data=Data;
        }
        public void setType(String Type){
            type=Type;
        }
        public void setSize(String Size){
            size=Size;
        }
    }
    class Directory{
       // String pathFile=new String("D:\\OfficeOfCollections");
        File[] getDirrecoryFile(String path){
            File directory1=new File(path);
            if(!directory1.isFile()&&!directory1.isDirectory()) {
                System.out.println("Not File");
                return null;
            }
            File[] listFile=directory1.listFiles();
            return listFile;

        }
        public InfoFile getInfoFile(File f){
            InfoFile infoFile=new InfoFile();
            infoFile.setName(f.getName());

            if(f.isFile()) {
                Long l;
                l = f.length();
                int count=0;
                if(l>1024) {
                    l = l / 1024;
                    count++;
                }
                String[] s={" bytes"," kbytes"," mbystes"," gbystes"};
                infoFile.setSize(String.valueOf(l)+s[count]);
                infoFile.setType("File");
            }
            else{
                infoFile.setType("Folder");
            }
            Long lastModified=f.lastModified();
            Date date=new Date(lastModified);
            infoFile.setData(String.valueOf(date));
            return infoFile;

        }
        void getDirrectoryFolder(String path){
        }
        ObservableList<InfoFile> picker(String path)
        {
            ObservableList<InfoFile> picker=FXCollections.observableArrayList();
            InfoFile infoFile=new InfoFile();
            File[] massivFiles=getDirrecoryFile(path);
            int i=0;
            infoFile=new InfoFile();
            while(massivFiles.length>i){
              infoFile=  getInfoFile(massivFiles[i]);
                picker.add(infoFile);
                i++;
            }
            return picker;
        };

    }


    Directory directory=new Directory();
    InfoFile infoFile=new InfoFile();
    ListView l=new ListView();




    @FXML void initialize()
    {

        ObservableList<Table> data=FXCollections.observableArrayList();
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Table, String>("Name"));
        tableColumnDataChange.setCellValueFactory(new PropertyValueFactory<Table, String>("DataChange"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<Table, String>("Type"));
        tableColumnSize.setCellValueFactory(new PropertyValueFactory<Table, String>("Size"));
        int j=0;
        while(directory.picker(pathFileStandart).size()>j)
        {
            infoFile=directory.picker(pathFileStandart).get(j);
            data.add(new Table(infoFile.getName(),infoFile.getData(),infoFile.getType(),infoFile.getSize()));
            j++;
        }



        tableView.setItems(data);



        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2){
                    System.out.println("Double clicked");
                   Table t2=   tableView.getSelectionModel().getSelectedItem();
                  // if(t2!=null)
                  //  System.out.println(t2.getName());
                    tableView.getSelectionModel().clearSelection();
                }
            }
        });
   }
}



////////////////////////////////////In Class directory after return listFile
 /*   int i=0;
           ObservableList data = FXCollections.observableArrayList();
            while(listFile.length>i) {
                data.add(listFile[i].getName());
                System.out.println(listFile[i].getName());
                System.out.println( listFile[i].length());
                                i++;
            }
           // return null;
           */
