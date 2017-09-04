package io.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-7-31
 * Time: 下午3:28
 * To change this template use File | Settings | File Templates.
 */
public class newio {

    @Test
    public void main() throws IOException {
        readNIO();
//        readBIO();
    }

    private static void readBIO() throws IOException {
        File file = new File("data/io.nio-data.txt");
        System.out.println(file.getAbsolutePath());
        FileInputStream fileInputStream=new FileInputStream(file);
        byte[] buf=new byte[1024];
        int bytesRead = fileInputStream.read(buf); //read into buffer.
        while (bytesRead != -1) {
            String s = new String(buf);
            System.out.print(s); // read 1 byte at a time  //
            bytesRead = fileInputStream.read(buf);
        }
        fileInputStream.close();
    }

    private static void readNIO() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("data/io.nio-data.txt", "rw");
        File file = new File("data/io.nio-data.txt");
        System.out.println(file.getAbsolutePath());
        FileInputStream fileInputStream=new FileInputStream(file);
        FileChannel inChannel = fileInputStream.getChannel();
        //create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf); //read into buffer.
        while (bytesRead != -1) {
            buf.flip();  //make buffer ready for read
            while(buf.hasRemaining()){
                System.out.print((char) buf.get()); // read 1 byte at a time
            }

            buf.clear(); //make buffer ready for writing
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
