package cntnt;

import java.io.File;
import java.util.Objects;

public class FileBrowser {

    static String displayContent(String stringToMatch) {

        File curDir = new File(Main.folderWithShortcuts);

        StringBuilder contents = new StringBuilder();

        for (File file : Objects.requireNonNull(curDir.listFiles())) {

            if (!file.isDirectory() && file.getName().substring(0, file.getName().indexOf(".")).toLowerCase().contains(stringToMatch.toLowerCase())) {
                contents.append(file.getName()).append("\n");
            }

        }

        return contents.toString();
    }


}
