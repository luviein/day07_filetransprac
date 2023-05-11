import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

            //initialise byte buffer to receive from client
            byte[] buff = new byte [4*1024];
            int size = 0;
            long totalReceived = 0;
            //create fileoutputstream to receive file
            OutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            //measures own server file received size
            while((size = dis.read(buff)) > 0){
                bos.write(buff, 0, size);
                totalReceived += size;
                //exits out of while loop
                if(totalReceived == size){
                    break;
                }
            }
            System.out.println("Total file size received: " + totalReceived);
            //System.out.println("size" + size + "total received: " + totalReceived );
            OutputStream os1 = socket.getOutputStream();
            BufferedOutputStream bos1 = new BufferedOutputStream(os1);
            DataOutputStream dos1 = new DataOutputStream(bos1);

            dos1.writeUTF("File matches with server");
            dos1.flush();

            bos.close();
         


        }finally{
            socket.close();
        }
    
        server.close();
    }

}