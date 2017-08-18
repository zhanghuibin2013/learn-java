package io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class ReadWriteCompletionAttachment {
    private AsynchronousSocketChannel channel;
    private ByteBuffer buffer;

    public ReadWriteCompletionAttachment(ByteBuffer buffer, AsynchronousSocketChannel channel) {
        this(channel, buffer);
    }


    public AsynchronousSocketChannel getChannel() {
        return channel;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public ReadWriteCompletionAttachment(AsynchronousSocketChannel channel, ByteBuffer buffer) {
        this.channel = channel;
        this.buffer = buffer;
    }
}
