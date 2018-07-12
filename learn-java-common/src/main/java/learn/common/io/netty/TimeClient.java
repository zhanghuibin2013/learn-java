package learn.common.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-8-17
 * Time: 下午1:16
 * To change this template use File | Settings | File Templates.
 */
public class TimeClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
        }
        new TimeClient().connect("127.0.0.1", port);
    }

    private void connect(String host, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new TimeClientChannel());
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    private class TimeClientChannel extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new TimeClientHandler());
        }
    }

    private class TimeClientHandler extends ChannelHandlerAdapter {
        private byte[] req;

        private TimeClientHandler() {
            req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            for (int i = 0; i < 100; i++) {
                ByteBuf firstMessage = Unpooled.buffer(req.length);
                firstMessage.writeBytes(req);
                ctx.writeAndFlush(firstMessage);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//            ByteBuf buf = (ByteBuf) msg;
//            byte[] req = new byte[buf.readableBytes()];
//            buf.readBytes(req);
            String body = (String) msg;
            System.out.println("Now is : " + body);
//            ctx.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
