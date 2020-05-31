package mockserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServer {

    public static void main(String[] args) {
            
        try (ServerSocket sc = new ServerSocket(9000);) {
                
            boolean run = true;

            while (run) {

                try (Socket socket = sc.accept();) {

                    InputStream is = socket.getInputStream();
                    Reader r = new InputStreamReader(is, "UTF-8");                    
                    BufferedReader br = new BufferedReader(r);
                    OutputStream os = socket.getOutputStream();
                    Writer w = new OutputStreamWriter(os, "UTF-8");
                    BufferedWriter bw = new BufferedWriter(w);                    
                    String line = br.readLine();

                    if (line != null && !"".equals(line)) {

                        String[] parts = line.split(" ");

                        if (parts.length >= 3) {

                            String fileName = parts[1];
                            System.out.println(fileName);   

                            if (fileName.equals("/quit")) {
                                quitMessage(bw);
                                run = false;
                            } else {
                                File dir = new File("src/" + fileName);   
                                
                                if (dir.exists()) {
                                    if (dir.isDirectory()) {
                                        
                                        System.out.println(dir.getAbsolutePath());
                                        bw.write("HTTP/1.1 200 OK");
                                        bw.newLine();
                                        bw.write("Content-Type: text/html");
                                        bw.newLine();
                                        bw.newLine();
                                        bw.write("<html>");
                                        bw.write("<body>");
                                        File[] files = dir.listFiles();
                                        System.out.println(files.length);
                                        bw.write(dir.getAbsolutePath());
                                        bw.newLine();
                                        bw.write(files.length);
                                        bw.newLine();
                                        for (File file : files) {
                                            // Check if the file is a directory
                                            if (file.isDirectory()) {
                                                // We will not print the directory name, just use it as a new
                                                // starting point to list files from
                                                bw.write(file.getAbsolutePath());
                                                bw.newLine();
                                                System.out.println(file.getAbsolutePath());
                                            } else {
                                                // We can use .length() to get the file size
                                                bw.write(file.getName() + " (size in bytes: " + file.length()+")");
                                                bw.newLine();
                                                System.out.println(file.getName() + " (size in bytes: " + file.length()+")");
                                                bw.write("<div><a href=\"/web/" + file.getName() + "\">" + file.getName() + "</a></div>");
                                                bw.newLine();
                                            }
                                        }
                                        bw.write("</body>");
                                        bw.write("</html>");
                                        
                                    } else {
                                        bw.write("HTTP/1.1 200 OK");
                                        bw.newLine();
                                        if (fileName.endsWith(".html")) {
                                            bw.write("Content-Type: text/html");
                                            bw.newLine();
                                        } else if (fileName.endsWith(".js")) {
                                            bw.write("Content-Type: text/javascript;charset=UTF-8");
                                            bw.newLine();
                                        } else if (fileName.endsWith(".css")) {
                                            bw.write("Content-Type: text/css");
                                            bw.newLine();
                                        } else if (fileName.endsWith(".txt")) {
                                            bw.write("Content-Type: text/plain");
                                            bw.newLine();
                                        }
                                        
                                        bw.newLine();
                                        try (
                                            FileInputStream fis = new FileInputStream(dir);
                                            Reader fr = new InputStreamReader(fis, "UTF-8");
                                            BufferedReader fbr = new BufferedReader(fr);
                                        ) {
                                            String fileLine;
                                            while ((fileLine = fbr.readLine()) != null) {
                                                bw.write(fileLine);
                                                bw.newLine();
                                            }
                                        }
                                    }
                                } else {
                                    bw.write("HTTP/1.1 404 File not found");
                                    bw.newLine();
                                }
                            }
                            
                            bw.flush();
                        }
                    }
                }            
            } 
            
        } catch (IOException ex) {
            System.out.println("Server failed! " + ex.getMessage());
        }
        
    }
    
    public static void quitMessage(BufferedWriter bw) throws IOException {
        bw.write("HTTP/1.1 200 OK");
        bw.newLine();
        bw.write("Content-Type: text/html");
        bw.newLine();
        bw.newLine();
        bw.write("<html>");
        bw.write("<body>");
        bw.write("<h1>Server is shutting down. Bye.</h1>");
        bw.write("</body>");
        bw.write("</html>");
    }
    
}

// http://localhost:9000/quit

// TODO

// 1. make index file, make available

// 4. show their presence in index html

// 5. on click them - display them

// 6. display information like labels and counts

// 8. leave instructions on how to use

// extra: try images

// extra: kill button

// http://localhost:9000/web
// http://localhost:9000/quit
// http://localhost:9000/web/test.txt