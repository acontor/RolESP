package org.rolesp.services;

import java.io.*;
import java.util.ArrayList;

public class Save {

    private String nameFile, nameObject;

    public Save() {
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getNameObject() {
        return nameObject;
    }

    public void setNameObject(String nameObject) {
        this.nameObject = nameObject;
    }

    public void save(Object object) {
        File file = new File(nameFile);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            fos.close();
            oos.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public Object loadObject() {
        File file = new File(nameFile);
        Object object = new Object();
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                object = ois.readObject();
                fis.close();
                ois.close();
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(ex);
            }
        } else {
            return null;
        }
        return object;
    }

    public ArrayList<Object> loadArrayList() {
        File file = new File(nameFile);
        ArrayList<Object> object = new ArrayList();
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                object = (ArrayList<Object>) ois.readObject();
                fis.close();
                ois.close();
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(ex);
            }
        } else {
            return null;
        }
        return object;
    }
}
