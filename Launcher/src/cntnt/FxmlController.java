package cntnt;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;


public class FxmlController {


    public TextField input = new TextField();
    public TextArea result = new TextArea();

    private String usrInput;
    private String[] searchResultInArray;
    private int index = -1;
    private boolean twoFactorInNewPath = false, skip = false, twoFactorInShutdown = false;


    public void initialize() {

        result.setText("type \"help\" to view all commands");

    }

    @FXML
    public void update() {

        if (twoFactorInNewPath || skip || twoFactorInShutdown) {
            skip = false;
            return;
        }

        String searchResult = FileBrowser.displayContent(input.getText());

        if (searchResult.equals("")) {
            result.setText("no elements found in folder " + Main.folderWithShortcuts);
            return;
        }


        searchResultInArray = searchResult.split("\n");

        result.setText(searchResult);

    }


    public void keyListener(KeyEvent event) {

        if (event.getCode() == KeyCode.DOWN) {

            if (index == -1) {
                usrInput = input.getText();
            }

            try {
                index++;
                input.setText(searchResultInArray[index]);
            } catch (ArrayIndexOutOfBoundsException e) {
                index = -1;
                input.setText(usrInput);
            }

            input.positionCaret(input.getText().length());


        } else if (event.getCode() == KeyCode.UP) {

            index--;

            if (index == -1) {
                input.setText(usrInput);
            } else if (index == -2) {

                usrInput = input.getText();

                index = searchResultInArray.length - 1;
                input.setText(searchResultInArray[index]);

            } else {

                try {
                    input.setText(searchResultInArray[index]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Reached Top");
                }

            }

            input.positionCaret(input.getText().length());


        } else if (event.getCode() == KeyCode.ENTER) {

            if (input.getText().length() == 0) {
                return;
            }

            if (input.getText().equals("newpath")) {

                input.setText("");
                result.setText("Enter new path value");
                twoFactorInNewPath = true;

            } else if (twoFactorInNewPath) {

                twoFactorInNewPath = false;

                FileIO.writeToFile(Main.propertiesLocation, input.getText());

                result.setText("New  path set to " + input.getText());

                Main.folderWithShortcuts = input.getText();

                input.setText("");

                update();

            } else if (input.getText().equals("shutdown")) {

                result.setText("Shutting down...");

                input.setText("");
                skip = true;

                runProgram("shutdown -s -t 0");

            } else if(input.getText().equals("shutdownTimer")){

                input.setText("");
                result.setText("Enter seconds to shutdown");
                twoFactorInShutdown = true;

            }else if(twoFactorInShutdown){

                twoFactorInShutdown = false;

                result.setText("shutdown in " + input.getText() + "sec");
                input.setText("");

                runProgram("shutdown -s -t " + input.getText());

            }else if (input.getText().equals("undoShutdown")){

                input.setText("");
                result.setText("shutdown canceled");

                skip = true;

                runProgram("shutdown -a");

            }else if (input.getText().equals("help")) {

                skip = true;
                input.setText("");

                result.setText("Commands:\n \"newpath\" : change or set directory containing shortcuts \n \"shutdown\" : turn off pc immediately\n \"shutdownTimer\" : turn off pc after delay \n \"undoShutdown\" : undo shutdown command");


            } else {


                for (String s : searchResultInArray) {

                    if (input.getText().equals(s)) {
                        executeShortcut(s);

                        return;
                    }

                }

                executeShortcut(searchResultInArray[0]);

            }
        }


    }

    private void executeShortcut(String shortcut) {

        runProgram("cmd /c start \"\" \"" + Main.folderWithShortcuts + "\\" + shortcut + "\"");

        result.setText("Starting " + shortcut + " ...");

        input.setText("");

        skip = true;

    }

    private void runProgram(String command) {

        Runtime run = Runtime.getRuntime();
        try {
            run.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

