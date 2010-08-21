package com.jayway.android.robotium.remotesolo;

  import java.util.logging.Level;
  import java.util.logging.Logger;
  
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelDownstreamHandler;
  import org.jboss.netty.channel.ChannelEvent;
  import org.jboss.netty.channel.ChannelHandlerContext;
  import org.jboss.netty.channel.ChannelStateEvent;
  import org.jboss.netty.channel.ExceptionEvent;
  import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
  

  public class ClientHandler extends SimpleChannelHandler {
  
      private static final Logger logger = Logger.getLogger(
              ClientHandler.class.getName());
  
      @Override
      public void handleUpstream(
              ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
          if (e instanceof ChannelStateEvent) {
              logger.info(e.toString());
          }

          super.handleUpstream(ctx, e);
      }
      
      @Override
      public void handleDownstream(
              ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
          if (e instanceof ChannelStateEvent) {
              logger.info(e.toString());
          }

          super.handleDownstream(ctx, e);
      }


      @Override
      public void messageReceived(
              ChannelHandlerContext ctx, MessageEvent e) {
          // Print out the line received from the server.
          System.out.println(e.getMessage());
      }
  
      @Override
      public void exceptionCaught(
              ChannelHandlerContext ctx, ExceptionEvent e) {
          logger.log(
                  Level.WARNING,
                  "Unexpected exception from downstream.",
                  e.getCause());
          e.getChannel().close();
      }
  }