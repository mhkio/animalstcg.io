package content;

import java.io.File;

class FileRenamer {

    public static void main(String[] args) {
        String filepath = "carddb/images/bas/";
        File dir = new File(filepath);
        File[] files = dir.listFiles();

        String filename;

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {

                    filename = "bas" + files[i].toString().substring(filepath.length(), filepath.length() + 3) 
                        + ".jpg";

                    File newFile = new File(filepath + filename);
                    System.out.println("Renaming " + files[i].toString() + " to " + newFile.toString());
                    files[i].renameTo(newFile);
                }
            }

        } else {
            System.out.println(filepath + " does not contain any files.");
        }
    }
}