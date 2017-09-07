package learn.common.io.aio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-8-17
 * Time: 上午10:23
 * To change this template use File | Settings | File Templates.
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
        }
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();

    }

    private static class AsyncTimeServerHandler implements Runnable {
        private final int port;
        CountDownLatch latch;
        AsynchronousServerSocketChannel asynchronousServerSocketChannel;

        public AsyncTimeServerHandler(int port) {
            this.port = port;
        }

        public void run() {
            try {
                this.asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
                asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
                System.out.println("The time server is start in port : " + port);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            latch = new CountDownLatch(1);
            doAccept();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        private void doAccept() {
            asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
        }

        private class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {
            public void completed(AsynchronousSocketChannel channel, AsyncTimeServerHandler attachment) {
                attachment.asynchronousServerSocketChannel.accept(attachment, this);
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                channel.read(buffer, new ReadWriteCompletionAttachment(channel, buffer), new ReadCompletionHandler());
            }

            public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
                exc.printStackTrace();
                attachment.latch.countDown();
            }
        }


        private class ReadCompletionHandler implements CompletionHandler<Integer, ReadWriteCompletionAttachment> {

            public void completed(Integer result, ReadWriteCompletionAttachment attachment) {
                ByteBuffer buffer = attachment.getBuffer();
                buffer.flip();
                byte[] body = new byte[buffer.remaining()];
                buffer.get(body);
                String req = null;
                try {
                    req = new String(body, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                System.out.println("The time server receive order : " + req);
                String currentTime = "QUERY TIME ORDER\r\n".contentEquals(req) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                doWrite(attachment.getChannel(), currentTime + "\r\n");
            }

            private void doWrite(AsynchronousSocketChannel channel, String currentTime) {
                if (currentTime != null && currentTime.trim().length() > 0) {
                    byte[] bytes = currentTime.getBytes();
                    ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
                    writeBuffer.put(bytes);
                    writeBuffer.flip();
                    channel.write(writeBuffer, new ReadWriteCompletionAttachment(channel, writeBuffer), new WriteCompletionHandler());
                }
            }

            public void failed(Throwable exc, ReadWriteCompletionAttachment attachment) {
                try {
                    attachment.getChannel().close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        private class WriteCompletionHandler implements CompletionHandler<Integer, ReadWriteCompletionAttachment> {
            public void completed(Integer result, ReadWriteCompletionAttachment attachment) {
                if (attachment.getBuffer().hasRemaining()) {
                    attachment.getChannel().write(attachment.getBuffer(), attachment, this);
                }
            }

            public void failed(Throwable exc, ReadWriteCompletionAttachment attachment) {
                try {
                    attachment.getChannel().close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }
}

