package com.example.greenpassapp.model;

import android.content.Context;

// import com.example.greenpassapp.model.File0;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * I tried to convert this to Java
 * @see File0 for the actual class
 */
public final class File {

    public static java.io.File getFile(String filename, Context context) {
        return File0.INSTANCE.getFile(filename, context);
    }

    public static boolean createFile(String filename, Context context) throws IOException {
        return File0.INSTANCE.createFile(filename, context);
        /*
        java.io.File file = this.getFile(filename, context);
        return file.createNewFile();
         */
    }

    public static String readFile(String filename, Context context) throws IOException {
        return File0.INSTANCE.readFile(filename, context);
        /*
        InputStream inputStream = context.openFileInput(filename);
        Reader var6 = new InputStreamReader(inputStream);
        BufferedReader $this$useLines$iv = var6 instanceof BufferedReader ? (BufferedReader)var6 : new BufferedReader(var6, 8192);

        BufferedReader reader = $this$useLines$iv instanceof BufferedReader ? $this$useLines$iv : new BufferedReader($this$useLines$iv, 8192);
        StringBuilder ans = new StringBuilder();
        String s = "hi! placeholder text here";
        while (!s.equals("")) {
            s = reader.readLine();
            ans.append(s);
        }
        return ans.toString();
         */
    }

    public static void writeToFile(String filename, Context context, String data) throws FileNotFoundException {
        File0.INSTANCE.writeFile(filename, context, data);
        /*
        FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);

        try {
            byte[] bytes = data.getBytes();
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

    }

    public static void appendToFile(String filename, Context context, String data) throws FileNotFoundException {
        File0.INSTANCE.appendToFile(filename, context, data);
        /*
        FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_APPEND);

        try {
            byte[] bytes = data.getBytes(Charsets.UTF_8);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
    }

    public static void appendLineToFile(String filename, Context context, String data) throws FileNotFoundException {
        File0.INSTANCE.appendLineToFile(filename, context, data);
//        this.appendToFile(filename, context, data + "\n");
    }

    /**
     * Why did I do this?
     * @param newLine The new line to write to the file
     * @param find The key to overwrite in the file
     * @param delimiter The delimiter the file uses, example: ","
     */
    public static void overwriteLine(String filename, Context context, String newLine, String find, String delimiter) throws IOException {
        File0.INSTANCE.overwriteLine(filename, context, newLine, find, delimiter);
        /*
        String string = this.readFile(filename, context);
        StringBuilder newString = new StringBuilder();
        String[] data = string.split("\\n");
        boolean found = false;

        for (String s : data) {
            if (s.equals("")) continue;
            if (s.split(delimiter)[0].equals(find)) {
                newString.append(newLine.equals("") ? "" : newLine + "\n");
                found = true;
            } else {
                newString.append(s).append('\n');
            }
        }

        if (!found) {
            this.appendLineToFile(filename, context, newLine);
        } else {
            this.writeFile(filename, context, newString.toString());
        }
         */
    }

    /**
     * overwrite the line with an empty string
     */
    public static void deleteLine(String filename, Context context, String find, String delimiter) throws IOException {
        File0.INSTANCE.deleteLine(filename, context, find, delimiter);
//        this.overwriteLine(filename, context, "", find, delimiter);
    }
}
