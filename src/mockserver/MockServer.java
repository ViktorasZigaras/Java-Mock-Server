package mockserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServer {

    public static boolean work = true;
    
    public static void main(String[] args) throws IOException {
        
        try (ServerSocket sc = new ServerSocket(9000);) {
            while (work) {
//                try (Socket socket = sc.accept();) {
//                    ResponseHandler rh = new ResponseHandler(socket);
//                    rh.start();
//                }
                Socket socket = sc.accept();
                ResponseHandler rh = new ResponseHandler(socket);
                rh.start();
            }
        }
    }
    
}

// TODO

// extra: kill button

// http://localhost:9000/web
// http://localhost:9000/quit
