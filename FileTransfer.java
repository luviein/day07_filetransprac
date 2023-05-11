import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileTransfer {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(3000);
        System.out.println("Starting server at port 3000\n");
        Socket socket = server.accept();
        
        try(InputStream is = socket.getInputStream()){
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            //receiving file name and file size from client
            String fileName = dis.readUTF();
            long fileSize = dis.readLong();

            System.out.printf("File name received: %s, file size received: %d\n", fileName, fileSize);



        }
    

    }

}