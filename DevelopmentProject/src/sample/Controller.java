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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
    @FXML
    Button buttonBack;
    @FXML
    Button buttonOpen;

    @FXML TreeView treeView;
    @FXML AnchorPane anchorPane;
    @FXML AnchorPane anchorPane3;
   // String pathFileStandart=new String("D:\\OfficeOfCollections");
   //  String pathFileForward=new String("D:\\OfficeOfCollections");
    ObservableList<String> pathFile=FXCollections.observableArrayList();


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
                double l;
                l = f.length();
                int count=0;
                while(l>1024)
                if(l>1024) {
                    l = l / 1024;
                    count++;
                }
                double nd=new BigDecimal(l).setScale(3, RoundingMode.UP).doubleValue();
                String[] s={" bytes"," kbytes"," mbystes"," gbystes"};
                infoFile.setSize(String.valueOf(nd)+s[count]);
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
    class Writer extends Thread{
        Path selectedFile;
        Path saveFile;
        public void setSelectedFile(Path p1 ){
            selectedFile=p1;
        }
        public void setSaveFile(Path p2){
            saveFile=p2;
        }
        public void run(){
            try {
                Files.copy(selectedFile,saveFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static Writer writer;

    Directory directory=new Directory();
    InfoFile infoFile=new InfoFile();
    String getOsType()
    {
        String osType;
        String osName=System.getProperty("os.name");
        String osNameMatch=osName.toLowerCase();

        if(osNameMatch.contains("windows"))
            osType="os_windows";
        else
        {
            if(osNameMatch.contains("linux"))
                osType="os_linux";
            else
                osType=new String("unKnow");
        }
        return osType;
    }



    @FXML void initialize()
    {
       String osType=getOsType();
       if(osType.equals("os_windows")) {
           pathFile.add("D:\\OfficeOfCollections");
       }
        if(osType.equals("os_linux"))
           pathFile.add("/home/notepad/OfficeOfCollection");
        ObservableList<Table> data=FXCollections.observableArrayList();
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Table, String>("Name"));
        tableColumnDataChange.setCellValueFactory(new PropertyValueFactory<Table, String>("DataChange"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<Table, String>("Type"));
        tableColumnSize.setCellValueFactory(new PropertyValueFactory<Table, String>("Size"));
        int j=0;
        while(directory.picker(pathFile.get(pathFile.size()-1)).size()>j)
        {
            infoFile=directory.picker(pathFile.get(pathFile.size()-1)).get(j);
            data.add(new Table(infoFile.getName(),infoFile.getData(),infoFile.getType(),infoFile.getSize()));
            j++;
        }
        tableView.setItems(data);
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2){
                   Table t2=   tableView.getSelectionModel().getSelectedItem();
                   if(t2!=null) {
                       if(osType.equals("os_windows"))
                        pathFile.add(pathFile.get(pathFile.size()-1)+"\\"+t2.getName());
                       else
                       if(osType.equals("os_linux"))
                           pathFile.add(pathFile.get(pathFile.size()-1)+"/"+t2.getName());

                            File f =new File(pathFile.get(pathFile.size()-1));
                       if(!f.isFile()) {
                           ObservableList<Table> data = FXCollections.observableArrayList();
                           tableColumnName.setCellValueFactory(new PropertyValueFactory<Table, String>("Name"));
                           tableColumnDataChange.setCellValueFactory(new PropertyValueFactory<Table, String>("DataChange"));
                           tableColumnType.setCellValueFactory(new PropertyValueFactory<Table, String>("Type"));
                           tableColumnSize.setCellValueFactory(new PropertyValueFactory<Table, String>("Size"));
                           int j = 0;
                           while (directory.picker(pathFile.get(pathFile.size() - 1)).size() > j) {
                               infoFile = directory.picker(pathFile.get(pathFile.size() - 1)).get(j);
                               data.add(new Table(infoFile.getName(), infoFile.getData(), infoFile.getType(), infoFile.getSize()));
                               j++;
                           }

                           tableView.setItems(data);
                       }
                       else
                           pathFile.remove(pathFile.size()-1);
                   }
                    tableView.getSelectionModel().clearSelection();
                }
                //////////end if event//////////
                else
                    if(event.getClickCount()==1){

                        tableView.getSelectionModel().clearSelection();
                   }
            }
        });
        buttonBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(pathFile.size()>1)
                {
                    pathFile.remove(pathFile.size()-1);
                    data.clear();
                  int i=0;
                    while(directory.picker(pathFile.get(pathFile.size()-1)).size()>i)
                    {
                        infoFile=directory.picker(pathFile.get(pathFile.size()-1)).get(i);
                        data.add(new Table(infoFile.getName(),infoFile.getData(),infoFile.getType(),infoFile.getSize()));
                        i++;
                    }
                    tableView.setItems(data);
                }
            }
        });
        buttonOpen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser=new FileChooser();
                fileChooser.setTitle("Open Resourse file");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Books","*.doc","*.docx","*.epub","*.pdf","*.txt"),
                        new FileChooser.ExtensionFilter("Films and media","*avi"),
                        new FileChooser.ExtensionFilter("All","*.*"));
                Stage s=new Stage();
                File selectedFile=fileChooser.showOpenDialog(s);
                File dd=new File(pathFile.get(0));
                fileChooser.setInitialFileName(selectedFile.getName());
                fileChooser.setInitialDirectory(dd);
                File saveFile=fileChooser.showSaveDialog(s);
                writer=new Writer();
                writer.setSelectedFile(selectedFile.toPath());
                writer.setSaveFile(saveFile.toPath());
                writer.start();

            /*    try {
                    Files.copy(selectedFile.toPath(),saveFile.toPath());

                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                // fileChooser.setInitialDirectory(selectedFile);
              //  fileChooser.setInitialDirectory(f);
          /*      File dd=new File(pathFile.get(0));
                fileChooser.setInitialDirectory(dd);
                fileChooser.setInitialFileName(f.getAbsolutePath());fileChooser.
                fileChooser.showSaveDialog(s);
                System.out.println("sgdggd");
            */
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
