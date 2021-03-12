package cntnt;

import java.io.*;

public class FileIO {

    static void writeToFile(String filename, String data){

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            writer.write(data);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static String loadProperties(){

        String line = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(Main.propertiesLocation));

            line = reader.readLine();


        } catch (FileNotFoundException e) {
            System.out.println("Properties not found");

        }catch (IOException e) {
            e.printStackTrace();
        }


        return line;
    }


}
