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
import java.net.Socket;

public class ResponseHandler extends Thread {
    
    private Socket sc;    
    private BufferedWriter bw;
    private String fileName;
    private File dir;
    
    public ResponseHandler(Socket sc) {
        if (sc == null) {
            throw new NullPointerException("Socket can not be null");
        }
        this.sc = sc;
    }    
    
    @Override
    public void run() {

        try (
            InputStream is = this.sc.getInputStream();
            Reader r = new InputStreamReader(is, "UTF-8");                    
            BufferedReader br = new BufferedReader(r);
            OutputStream os = this.sc.getOutputStream();
            Writer w = new OutputStreamWriter(os, "UTF-8");
            BufferedWriter bw = new BufferedWriter(w);                    
            
        ) {
            this.bw = bw;
            String line = br.readLine();

            if (line != null && !"".equals(line)) {

                String[] parts = line.split(" ");

                if (parts.length >= 3) {

                    this.fileName = parts[1];
                    System.out.println(this.fileName);   

                    if (this.fileName.equals("/quit")) {
                        // needs refactoring
    //                    quitMessage(bw);
    //                    run = false;
                    } else {
                        this.dir = new File("src/" + this.fileName);   

                        if (this.dir.exists()) {
                            if (this.dir.isDirectory()) {

                                this.listDirectory();

                            } else {
                                
                                this.displayFile();
                                
                            }
                        } else {
                            bw.write("HTTP/1.1 404 File not found");
                            bw.newLine();
                        }
                    }

                    bw.flush();
                }
            }
        } catch (Exception ex) {System.out.println(ex.getMessage());}
    }
    
    private void listDirectory() throws IOException {
        System.out.println(this.dir.getAbsolutePath());
        this.bw.write("HTTP/1.1 200 OK");
        this.bw.newLine();
        this.bw.write("Content-Type: text/html");
        this.bw.newLine();
        this.bw.newLine();
        this.bw.write("<html>");
        this.bw.write("<body>");
        File[] files = this.dir.listFiles();
        System.out.println(files.length);
        this.bw.write("<div>" + this.dir.getAbsolutePath() + "</div>");
        this.bw.newLine();
        this.bw.newLine();
        this.bw.write("<div>" + files.length + ":</div>");
        this.bw.newLine();

        this.bw.write("<a href=\"..\">..</a>");
        this.bw.write("<br>");

        for (File file : files) {

            if (file.isDirectory()) {
                this.bw.write("<div><a href=\"" + file.getName() + "/\">" + file.getName() + " (folder)</a></div>");
                this.bw.newLine();
                System.out.println(file.getAbsolutePath());
            } else {
                System.out.println(file.getName() + " (size in bytes: " + file.length()+")");
                this.bw.write("<div><a href=\"" + file.getName() + "\">" + file.getName() + " (size in bytes: " + file.length() + ")</a></div>");
                this.bw.newLine();
            }

        }
        this.bw.write("<div></div>");
        this.bw.newLine();
        // needs refactoring
//        bw.write("<div><a href=\"/quit\">[QUIT]</a></div>");
//        bw.newLine();
//        bw.newLine();
        this.bw.write("</body>");
        this.bw.write("</html>");
    }
    
    private void displayFile() throws IOException {
        this.bw.write("HTTP/1.1 200 OK");
        this.bw.newLine();
        if (this.fileName.endsWith(".html")) {
            this.bw.write("Content-Type: text/html");
            this.bw.newLine();
        } else if (this.fileName.endsWith(".js")) {
            this.bw.write("Content-Type: text/javascript; charset=UTF-8");
            this.bw.newLine();
        } else if (this.fileName.endsWith(".css")) {
            this.bw.write("Content-Type: text/css");
            this.bw.newLine();
        } else if (this.fileName.endsWith(".txt")) {
            this.bw.write("Content-Type: text/plain");
            this.bw.newLine();
        } 
        // extra: try images, (base64 encoding)
        /*else if (this.fileName.endsWith(".png")) {
            this.bw.write("Content-Type: image/png");
            this.bw.newLine();
        }*/

        this.bw.newLine();
        try (
            FileInputStream fis = new FileInputStream(this.dir);
            Reader fr = new InputStreamReader(fis, "UTF-8");
            BufferedReader fbr = new BufferedReader(fr);
        ) {
            String fileLine;
            while ((fileLine = fbr.readLine()) != null) {
                this.bw.write(fileLine);
                this.bw.newLine();
            }
        }
    }
    
    // unused - refactor
    private void quitMessage() throws IOException {
        this.bw.write("HTTP/1.1 200 OK");
        this.bw.newLine();
        this.bw.write("Content-Type: text/html");
        this.bw.newLine();
        this.bw.newLine();
        this.bw.write("<html>");
        this.bw.write("<body>");
        this.bw.write("<h1>Server is shutting down. Bye.</h1>");
        this.bw.write("</body>");
        this.bw.write("</html>");
    }
    
}
