package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by apava on 01.10.2015.
 */
public class Table {
    private final SimpleStringProperty Name;
    private final SimpleStringProperty DataChange;
    private final SimpleStringProperty Type;
    private final SimpleStringProperty Size;

    ////////////////constructor///////////////////////////////////

    public Table(String name,String dataChange, String type,String size)
    {
        this.Name=new SimpleStringProperty(name);
        this.DataChange=new SimpleStringProperty(dataChange);
        this.Type=new SimpleStringProperty(type);
        this.Size=new SimpleStringProperty(size);
    }

    /////////////////////Gettters

    public String getName(){
        return Name.get();
    }

    public String getDataChange(){
        return DataChange.get();
    }

    public String getType(){
        return Type.get();
    }

    public String getSize(){
        return Size.get();
    }

    ///////////////////setters////////////////

    public void setName(String name){
        Name.set(name);
    }

    public void setDataChange(String dataChange){
        DataChange.set(dataChange);
    }

    public void setType(String type){
        Type.set(type);
    }

    public void setSize(String size){
        Size.set(size);
    }


}
