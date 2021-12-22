package models.databases;

import models.databases.exceptions.DatabaseLoadException;
import models.databases.exceptions.DatabaseSaveException;
import java.io.*;
import java.util.ArrayList;

public abstract class Database<T> implements Serializable {
    protected ArrayList<T> data;
    protected String path;
    public static final String SAVE_ERROR = "The database could not be saved";
    public static final String LOAD_ERROR = "The database could not be loaded";

    public boolean fileExists() {
        File file = new File(path);
        return !(file.isDirectory()) && file.exists();
    }

    public void save() throws DatabaseSaveException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new DatabaseSaveException(e);
        }
    }

    public void load() throws DatabaseLoadException {
        try {
            File folder = new File("database");
            File file = new File(path);
            if (!folder.mkdirs() && !file.createNewFile()){
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
                    data = (ArrayList<T>) ois.readObject();
                }
            }
        } catch (EOFException ignored) {
        } catch (ClassNotFoundException | IOException e) {
            throw new DatabaseLoadException(e);
        }
    }

    public void add(T object){
        data.add(object);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    public ArrayList<T> getData(){
        return data;
    }
}
