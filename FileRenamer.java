package content;

import java.io.File;

// Run with 
// javac content/FileRenamer.java && java content.FileRenamer

class FileRenamer {

    public static void main(String[] args) {
        String filepath = "content/carddb/images/oce/";
        File dir = new File(filepath);
        File[] files = dir.listFiles();

        String filename;

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {

                    filename = "oce" + files[i].toString().substring(filepath.length() + 3, filepath.length() + 6) + ".jpg";

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