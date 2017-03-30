package ren.ashin.mina.demo.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.util.NoopFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * @ClassName: MyServer
 * @Description: TODO
 * @author renzx
 * @date Mar 28, 2017
 */
public class MyServer {
    private static Logger logger = Logger.getLogger(MyServer.class); // 日志
    private static final int port = 8899; // 端口

    public static void main(String[] args) {
        // 创建一个非阻塞的server端的socket
        IoAcceptor acceptor = new NioSocketAcceptor();
        // 设置过滤器,选用Mina自带的过滤器一行一行读取代码
         acceptor.getFilterChain().addLast("myChain", new ProtocolCodecFilter(new
         TextLineCodecFactory(Charset.forName("UTF-8"))));
//        acceptor.getFilterChain().addLast("myChain", new NoopFilter());
        // 设置读取数据的缓冲区大小
        acceptor.getSessionConfig().setReadBufferSize(2048);
        // 读写通道10秒内无操作进入空闲状态
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        // 绑定逻辑处理器
        acceptor.setHandler(new ServerHandler());
        // 绑定端口,启动服务器
        try {
            acceptor.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("服务已启动，端口是:" + port);
    }
}
