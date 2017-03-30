package ren.ashin.mina.demo.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.util.NoopFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @ClassName: MyClient
 * @Description: TODO
 * @author renzx
 * @date Mar 28, 2017
 */
public class MyClient {
    private static Logger logger = Logger.getLogger(MyClient.class);
    private static final String host = "127.0.0.1";
    private static final int port = 8898;

    public static void main(String[] args) {
        // 创建连接
        SocketConnector connector = new NioSocketConnector();
        // 设置过滤器,选用Mina自带的过滤器一行一行读取代码
        connector.getFilterChain().addLast("myChain",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        // 设置连接超时时间
        connector.setConnectTimeoutMillis(30 * 1000);
        // 绑定逻辑处理器
        connector.setHandler(new ClientHandler());
        // 连接到服务器
        ConnectFuture future = connector.connect(new InetSocketAddress(host, port));
        // 等待连接创建完成
        future.awaitUninterruptibly();
        // 获得session
        IoSession session = future.getSession();
        // 发送消息
        session.write("AA \r\nclose");

        logger.info("客户端正在连接服务器，" + host + ":" + port);

        // 等待连接断开
        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }
}
