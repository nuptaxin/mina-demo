package ren.ashin.mina.demo.server;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.google.common.collect.Maps;

/**
 * @ClassName: ServerHandler
 * @Description: TODO
 * @author renzx
 * @date Mar 28, 2017
 */
public class ServerHandler extends IoHandlerAdapter {
    private static Logger logger = Logger.getLogger(ServerHandler.class);
    Map<String, IoSession> ioSessionMap = Maps.newHashMap();

    // 创建连接
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.info("服务器创建session连接!");
    }

    // 打开一个连接
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        logger.info("服务器打开session连接!");
    }

    // 关闭连接
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info("服务器关闭session连接!");
    }

    // 连接空闲
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {

    }

    // 有异常时
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.info("服务器出现异常" + cause);
    }

    // 接收到消息
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String str = message.toString();
        logger.info("服务器收到消息：" + str);
        // String key = StringUtils.substringBefore(str, "-");
        // ioSessionMap.put(key, session);
        // session = ioSessionMap.values().iterator().next();
//        messageSent(session, "test");

        System.out.println("sessionId:"+session.getId());
        if (str.equals("close")) {
            session.write("close");
            session.close(false);
        }else{
            session.write("test");
        }
    }

    // 将要发送消息
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        logger.info("服务器发送消息：" + message.toString());
        // IoBuffer buffer = IoBuffer.allocate(1);
        // buffer.setAutoShrink(true);
        // buffer.put((byte) 1);
    }
}
