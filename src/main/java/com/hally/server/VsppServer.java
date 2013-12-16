package com.hally.server;

import com.emag.config.MyConfigurer;
import com.hally.service.vspp.IVsppService;
import com.hisunsray.intersp.TCPSpServer;
import com.hisunsray.vspp.data.PacketHeadVO;
import com.hisunsray.vspp.data.PacketInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-11 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
@Component
public class VsppServer {

    private static Logger logger = LoggerFactory.getLogger(VsppServer.class);

    @Resource
    private Map vsppServiceFactory;


    public void startServer() {
        logger.info("NOW Begin CommInit");
        TCPSpServer tcpServer = new TCPSpServer();
        int nPort = Integer.valueOf(MyConfigurer.getContextProperty("nPort").toString());
        int nKey = Integer.valueOf(MyConfigurer.getContextProperty("nKey").toString());
        try {

            int ret = tcpServer.vsppCommInit(nPort, nKey);
            logger.info("comminit ret is {}", ret);
            while (true) {

                logger.info("begin to recv packets");
                PacketInfoVO paInfoVO = tcpServer.vsppReceiveMessage();

                PacketHeadVO paHeadVO = paInfoVO.getPaHeadVO();
                logger.info("get message ok");

                logger.info("operateid:{}", paHeadVO.getOperateID());
                IVsppService vsppService = (IVsppService) vsppServiceFactory.get(paHeadVO.getOperateID());

                try {
                    paInfoVO = vsppService.response(paInfoVO);
                } catch (Exception e) {
                    logger.error("本次请求有误{}", e);

                }


                tcpServer.vsppSendMessage(paInfoVO.getClientVO(), paInfoVO);
            }
        } catch (Exception e) {
            logger.error("致命错误：{}", e);
        }

    }


}
