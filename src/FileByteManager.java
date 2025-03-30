import java.io.*;
import java.util.ArrayList;

public class FileByteManager {

    public static final int bytesInEmptyFile = 4;

    private final String fileName;
    private final boolean appendByteFile;

    public FileByteManager(String fileName, boolean appendByteFile) throws IOException {

        this.fileName = fileName;
        this.appendByteFile = appendByteFile;
        File file = new File(fileName);
        if (!file.exists()) {
            this.clearByteFile();
        }
    }

    public ArrayList<Order> readArrayListFromFile() throws IOException, ClassNotFoundException {

        ArrayList<Order> tempOrderTypeArrayList = new ArrayList<Order>();

        File file = new File(fileName);
        if (file.length() > bytesInEmptyFile) {

            FileInputStream fileStreamIn = new FileInputStream(fileName);
            ObjectInputStream fileObjectStreamIn = new ObjectInputStream(fileStreamIn);

            tempOrderTypeArrayList = (ArrayList<Order>) fileObjectStreamIn.readObject();

            fileStreamIn.close();
            fileObjectStreamIn.close();
        }
        return tempOrderTypeArrayList;
    }

    public void writeArrayListToFile(ArrayList<Order> tempOrderTypeArrayList) throws IOException {

        if (appendByteFile) {

            FileOutputStream fileStreamOut = new FileOutputStream(fileName, true);

            ObjectOutputStream fileObjectStreamOutAppend = fileStreamOut.getChannel().position() == 0 ?
                    new ObjectOutputStream(fileStreamOut) :
                    new AppendObjectOutputStream(fileStreamOut);

            fileObjectStreamOutAppend.writeObject(tempOrderTypeArrayList);

            fileObjectStreamOutAppend.close();

            fileStreamOut.close();

        } else {
            FileOutputStream fileStreamOut = new FileOutputStream(fileName);
            ObjectOutputStream fileObjectStreamOut = new ObjectOutputStream(fileStreamOut);

            fileObjectStreamOut.writeObject(tempOrderTypeArrayList);

            fileObjectStreamOut.close();

            fileStreamOut.close();
        }
    }

    public void clearByteFile() throws IOException {

        FileOutputStream fileStreamOut = new FileOutputStream(fileName);
        ObjectOutputStream fileObjectStreamOut = new ObjectOutputStream(fileStreamOut);

        fileStreamOut.close();
        fileObjectStreamOut.close();
    }
}