package com.hm.libb.dsource.net.websocket;

import com.hm.libb.dsource.depict.BitUrl;
import com.hm.libb.dsource.woker.Worker;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.UnsupportedAddressTypeException;

/**
 * Created by huangming on 2017/6/17.
 */
public class BaseWebsocket {

    private static Logger LOG = Logger.getLogger(BaseWebsocket.class);

    public static void runSocket(Worker dp) throws URISyntaxException, SSLException, InterruptedException {
        URI uri = new URI(dp.getBitUrl().getUrl());
        String scheme = uri.getScheme();

        int port = uri.getPort();
        SslContext sslContext;
        if ("wss".equals(scheme)) {
            sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else if ("ws".equals(scheme)) {
            sslContext = null;
        } else {
            throw new UnsupportedAddressTypeException();
        }

        if (port == -1) {
            if ("wss".equals(scheme)) {
                port = 443;
            } else if ("ws".equals(scheme)) {
                port = 80;
            }
        }

        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13,
                null, false, new DefaultHttpHeaders(), 655360);

        DefaultWebsocketHandler handler = new DefaultWebsocketHandler(handshaker, dp);

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group).channel(NioSocketChannel.class).handler(new DefualtChannelInitializer(sslContext, handler, uri.getHost(), port));
        Channel channel = bootstrap.connect(uri.getHost(), port).sync().channel();
        handler.handshakeFuture().sync();

        if (channel.isWritable()) {
            LOG.info(dp.getBitUrl().getUrl() + " is ok!");
            dp.init(channel);
            LOG.info("init complete!");
        } else {
            throw new WebSocketHandshakeException("can't write!");
        }
    }

    private static class DefualtChannelInitializer extends ChannelInitializer<SocketChannel> {
        private ChannelHandler sHandler;
        private SslContext sslContext;
        private int port;
        private String host;

        public DefualtChannelInitializer(SslContext sslContext, ChannelHandler sHandler, String host, int port) {
            this.sslContext = sslContext;
            this.sHandler = sHandler;
            this.host = host;
            this.port = port;
        }

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
            if (sslContext != null) {
                pipeline.addLast(sslContext.newHandler(socketChannel.alloc(), host, port));
            }
            pipeline.addLast(new HttpClientCodec(), new HttpObjectAggregator(8192), sHandler);
        }

    }

}
