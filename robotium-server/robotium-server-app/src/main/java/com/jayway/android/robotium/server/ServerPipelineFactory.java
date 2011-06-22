package com.jayway.android.robotium.server;

import static org.jboss.netty.channel.Channels.*;
  
import org.jboss.netty.channel.ChannelPipeline;
  import org.jboss.netty.channel.ChannelPipelineFactory;
  import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
  import org.jboss.netty.handler.codec.frame.Delimiters;
  import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import android.app.Instrumentation;
  

  public class ServerPipelineFactory implements
          ChannelPipelineFactory {
	  
	  public static String SERVER_HANDLER = "handler";
	  public static String STRING_DECODER = "decoder";
	  public static String STRING_ENCODER = "encoder";
	  public static String FRAMER = "framer";
	  private Instrumentation mInstrumentation;
	  
	  public void setInstrumentation(Instrumentation instrumentation) {
		  mInstrumentation = instrumentation;
	  }
	  
      public ChannelPipeline getPipeline() throws Exception {
          // Create a default pipeline implementation.
          ChannelPipeline pipeline = pipeline();
  
          // Add the text line codec combination first,
          pipeline.addLast(FRAMER, new DelimiterBasedFrameDecoder(
                  8192, Delimiters.lineDelimiter()));
          pipeline.addLast(STRING_DECODER, new StringDecoder());
          pipeline.addLast(STRING_ENCODER, new StringEncoder());
          
          // and then business logic.
          ServerHandler serverHandler = new ServerHandler();
          serverHandler.setInstrumentation(mInstrumentation);
          pipeline.addLast(SERVER_HANDLER, serverHandler);
          
          return pipeline;
      }
  }
