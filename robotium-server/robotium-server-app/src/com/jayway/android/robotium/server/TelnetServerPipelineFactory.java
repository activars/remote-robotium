package com.jayway.android.robotium.server;

import static org.jboss.netty.channel.Channels.*;
  
import org.jboss.netty.channel.ChannelPipeline;
  import org.jboss.netty.channel.ChannelPipelineFactory;
  import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
  import org.jboss.netty.handler.codec.frame.Delimiters;
  import org.jboss.netty.handler.codec.string.StringDecoder;
  import org.jboss.netty.handler.codec.string.StringEncoder;
  
  /**
28   * Creates a newly configured {@link ChannelPipeline} for a new channel.
29   *
30   * @author <a href="http://www.jboss.org/netty/">The Netty Project</a>
31   * @author <a href="http://gleamynode.net/">Trustin Lee</a>
32   *
33   * @version $Rev: 2080 $, $Date: 2010-01-26 18:04:19 +0900 (Tue, 26 Jan 2010) $
34   *
35   */
  public class TelnetServerPipelineFactory implements
          ChannelPipelineFactory {
  
      public ChannelPipeline getPipeline() throws Exception {
          // Create a default pipeline implementation.
          ChannelPipeline pipeline = pipeline();
  
          // Add the text line codec combination first,
          pipeline.addLast("framer", new DelimiterBasedFrameDecoder(
                  8192, Delimiters.lineDelimiter()));
          pipeline.addLast("decoder", new StringDecoder());
          pipeline.addLast("encoder", new StringEncoder());
          // and then business logic.
          pipeline.addLast("handler", new TelnetServerHandler());
  
          return pipeline;
      }
  }
