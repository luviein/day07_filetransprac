import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {

        // get path from args 0
        Path p = Paths.get(args[0]);

        // converts p to File
        File f = p.toFile();

        // get name and filesize from f
        String fileName = f.getName();
        long fileSize = f.length();

        Socket socket = new Socket("localhost", 3000);
        try{
            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            //send file data
            dos.writeUTF(fileName);
            dos.writeLong(fileSize);
            System.out.printf("Sending file name: %s, file size: %d\n", fileName, fileSize);
            dos.flush();

            //start 4k buffer
            byte[] buffer = new byte[4 * 1024];
            //create size variable for buffer
            int size = 0;
            //Create file & buffered input stream to read size
            InputStream fs = new FileInputStream(f);
            BufferedInputStream bis = new BufferedInputStream(fs);

            //writing file size to server
            while((size = bis.read(buffer)) > 0){
                dos.write(buffer, 0, size);
                dos.flush();

            }
            
            dos.close();
            bis.close();
            bos.close();
            os.close();
            fs.close();

        
        
        }finally{
            socket.close();
        }



        

        



    }
}
