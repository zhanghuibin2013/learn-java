package learn.common.io.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-8-17
 * Time: 下午1:16
 * To change this template use File | Settings | File Templates.
 */
public class TimeClient {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
        }
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
    }

    private static class AsyncTimeClientHandler implements Runnable {
        private final String host;
        private final int port;
        private AsynchronousSocketChannel client;
        private CountDownLatch latch;

        public AsyncTimeClientHandler(String host, int port) {
            this.host = host;
            this.port = port;
            try {
                this.client = AsynchronousSocketChannel.open();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        @Override
        public void run() {
            this.latch = new CountDownLatch(1);
            this.client.connect(new InetSocketAddress(host, port), this, new ConnectCompletionHandler());
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        private class ConnectCompletionHandler implements CompletionHandler<Void, AsyncTimeClientHandler> {
            @Override
            public void completed(Void result, AsyncTimeClientHandler attachment) {
                byte[] bytes = "QUERY TIME ORDER\r\n".getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
                writeBuffer.put(bytes);
                writeBuffer.flip();
                client.write(writeBuffer, writeBuffer, new WriteCompletionHandler());
            }

            @Override
            public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                latch.countDown();
            }
        }

        private class WriteCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (attachment.hasRemaining()) {
                    client.write(attachment, attachment, this);
                } else {
                    System.out.println("Send order 2 server succeed.");
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    client.read(readBuffer, readBuffer, new ReadCompletionHandler());
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                latch.countDown();
            }
        }

        private class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                byte[] bytes = new byte[attachment.remaining()];
                attachment.get(bytes);
                String body = null;
                try {
                    body = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                System.out.println("Now is : " + body);
                latch.countDown();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                latch.countDown();
            }
        }
    }
}
