package learn.common.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author zhanghuibin
 */
public class ReadCompletionHandler implements
        CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        if (this.channel == null) {
            this.channel = channel;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
            String req = new String(body, "UTF-8");
            System.out.println("The time server receive order : " + req);
            String response;
            if ("\r\n".equalsIgnoreCase(req)) {
                response = Thread.currentThread().toString();
            } else if ("QUERY TIME ORDER\r\n".equalsIgnoreCase(req)) {
                response = new java.util.Date(System.currentTimeMillis()).toString() + "\r\n";
            } else {
                response = "BAD ORDER";
            }
            doWrite(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(String content) {
        if (content == null || content.trim().length() <= 0) {
            doNextRead();
            return;
        }
        byte[] bytes = (content).getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        CompletionHandler<Integer, ByteBuffer> handler = new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                // 如果没有发送完成，继续发送
                if (buffer.hasRemaining()) {
                    channel.write(buffer, buffer, this);
                } else {
                    doNextRead();
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (IOException e) {
                    // ingnore on close
                }
            }
        };
        channel.write(writeBuffer, writeBuffer, handler);
    }

    private void doNextRead() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        channel.read(buffer, buffer, this);
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
