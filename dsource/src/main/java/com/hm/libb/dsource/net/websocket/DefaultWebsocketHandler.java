package com.hm.libb.dsource.net.websocket;

import com.hm.libb.dsource.woker.Worker;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by huangming on 2017/6/17.
 */
public class DefaultWebsocketHandler extends SimpleChannelInboundHandler<Object> {

    private static Logger LOG = Logger.getLogger(DefaultWebsocketHandler.class);

    private static Charset charset = Charset.forName("UTF-8");
    private static CharsetDecoder decoder = charset.newDecoder();

    private WebSocketClientHandshaker handshake;
    private ChannelPromise channelFuture;
    private Worker parser;

    public DefaultWebsocketHandler(WebSocketClientHandshaker handshake, Worker parser) {
        this.handshake = handshake;
        this.parser = parser;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        channelFuture = ctx.newPromise();
    }

    public ChannelFuture handshakeFuture() {
        return channelFuture;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        Channel ch = ctx.channel();
        if (o instanceof FullHttpResponse && !handshake.isHandshakeComplete()) {
            handshake.finishHandshake(ch, (FullHttpResponse) o);
            LOG.info("WebSocket Client Connected!");
            channelFuture.setSuccess();
            return;
        }

        if (o instanceof TextWebSocketFrame) {
            TextWebSocketFrame wf = (TextWebSocketFrame) o;
            onReceive(wf.text());
        } else if (o instanceof PongWebSocketFrame) {
            LOG.warn("Pong");
        } else if (o instanceof CloseWebSocketFrame) {
            LOG.warn("Close");
            ctx.channel().close();
        } else if (o instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame bf = (BinaryWebSocketFrame) o;
            ByteBuffer byteBuffer = bf.content().nioBuffer();
            CharBuffer charBuffer = decoder.decode(byteBuffer);
            onReceive(charBuffer.toString());
        } else {
            LOG.warn("record unregister request!" + o);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        handshake.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        handshake.close(ctx.channel(), new CloseWebSocketFrame());
        handshake.handshake(ctx.channel());
    }

    public void onReceive(String msg) {
        parser.onReceive(msg);
    }
}
