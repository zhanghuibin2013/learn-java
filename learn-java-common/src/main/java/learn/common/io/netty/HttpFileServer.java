package learn.common.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-8-17
 * Time: 上午10:23
 * To change this template use File | Settings | File Templates.
 */
public class HttpFileServer {
    private static final String DEFAULT_PATH = "D:\\";

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8010;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
        }
        new HttpFileServer().run(port, DEFAULT_PATH);
    }

    private void run(int port, String basePath) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler(basePath));
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        private final String basePath;

        public ChildChannelHandler(String basePath) {
            this.basePath = basePath;
        }

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
            socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
            socketChannel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
            socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
            socketChannel.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(basePath));
        }
    }

    private class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
        private final Pattern INSECURE_URI = Pattern.compile("");
        private final String basePath;

        public HttpFileServerHandler(String basePath) {
            this.basePath = basePath;
        }


        @Override
        protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
            if (!fullHttpRequest.getDecoderResult().isSuccess()) {
                sendError(ctx, HttpResponseStatus.BAD_REQUEST);
                return;
            }
            if (fullHttpRequest.getMethod() != HttpMethod.GET) {
                sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
                return;
            }
            String uri = fullHttpRequest.getUri();
            String path = sanitizeUri(uri);
            if (path == null) {
                sendError(ctx, HttpResponseStatus.FORBIDDEN);
                return;
            }
            File file = new File(path);
            if (file.isHidden() || !file.exists()) {
                sendError(ctx, HttpResponseStatus.NOT_FOUND);
                return;
            }
            if (!file.isFile()) {
                sendError(ctx, HttpResponseStatus.FORBIDDEN);
                return;
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            long fileLength = randomAccessFile.length();
            HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            setContentLength(response, fileLength);
            setContentType(response, file);
            if (isKeepAlive(fullHttpRequest)) {
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response);
            ChannelFuture sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 8192), ctx.newProgressivePromise());
            sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
                @Override
                public void operationProgressed(ChannelProgressiveFuture channelProgressiveFuture, long l, long l2) throws Exception {
                    System.out.println(l);
                }

                @Override
                public void operationComplete(ChannelProgressiveFuture channelProgressiveFuture) throws Exception {
                    System.out.println(channelProgressiveFuture);
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
            ctx.writeAndFlush(response)
                    .addListener(ChannelFutureListener.CLOSE);
        }

        private boolean isKeepAlive(FullHttpRequest request) {
            return request.headers().contains(HttpHeaders.Names.CONNECTION)
                    && request.headers().get(HttpHeaders.Names.CONNECTION).contains(HttpHeaders.Values.KEEP_ALIVE);
        }

        private void setContentType(HttpResponse response, File file) {
            String name = file.getName();
            int index = name.lastIndexOf('.');
            name = name.substring(index + 1);
            response.headers().add(HttpHeaders.Names.CONTENT_TYPE, "file/" + name);
        }

        private void setContentLength(HttpResponse response, long fileLength) {
            response.headers().add(HttpHeaders.Names.CONTENT_LENGTH, fileLength);
        }

        private String sanitizeUri(String uri) throws UnsupportedEncodingException {
            try {
                uri = URLDecoder.decode(uri, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            }
            uri = uri.replace('/', File.separatorChar);
            if (uri.contains(File.separator + ".")
                    || uri.contains("." + File.separator)
                    || uri.startsWith(".")
                    || uri.endsWith(".")
                    || INSECURE_URI.matcher(uri).matches()) {
                return null;
            }
            return basePath + File.separator + uri;
        }

        private void sendError(ChannelHandlerContext channelHandlerContext, HttpResponseStatus status) {
            DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
            channelHandlerContext.writeAndFlush(response)
                    .addListener(ChannelFutureListener.CLOSE);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
