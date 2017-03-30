package ren.ashin.mina.demo.client;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * @ClassName: ClientHandler
 * @Description: TODO
 * @author renzx
 * @date Mar 28, 2017
 */
public class ClientHandler implements IoHandler {
    private static Logger logger = Logger.getLogger(ClientHandler.class);

    // 有异常
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.info("程序出现异常" + cause);
    }

    // 接收到消息时
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String str = message.toString();
        logger.info("客户端收到消息：" + str);
        if (str.equals("close")) {
            session.close(true);
        }
    }

    // 将要发送消息
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {

    }

    // 关闭连接
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info("客户端关闭session连接!");
    }

    // 创建连接
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.info("客户端创建session连接!");
    }

    // 连接空闲
    @Override
    public void sessionIdle(IoSession session, IdleStatus arg1) throws Exception {

    }

    // 打开一个连接
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        logger.info("客户端开始session连接!");
    }
}
