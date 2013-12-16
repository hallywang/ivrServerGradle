package com.hally.server;

import com.emag.config.MyConfigurer;
import com.hally.service.vspp.IVsppService;
import com.hisunsray.intersp.TCPSpServer;
import com.hisunsray.vspp.data.ClientVO;
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
                PacketInfoVO clientResquestInfo = tcpServer.vsppReceiveMessage(); //获取到客户端信息

                PacketHeadVO clientResquestHeader = clientResquestInfo.getPaHeadVO();
                logger.info("get message ok");

                ClientVO clientVO = clientResquestInfo.getClientVO();
                String ip = clientVO.getHostIP();
                int port = clientVO.getNPort();


                //serverId 就是 serviceId,应该是高阳api的笔误
                String serviceId = clientResquestHeader.getServerID();
                String operateId = clientResquestHeader.getOperateID();
                String pBody = clientResquestInfo.getPacketBody();
                String pHeader = clientResquestHeader.getPacketHeadString(pBody);
                String errorNo= clientResquestHeader.getErrno();

                logger.info("==in==host ip is:{},port is:{}", ip, port);
                logger.info("==in==serviceId:{},operateid:{},header is:{},errorNo is:{},body is:{}",
                        serviceId, operateId, pHeader, errorNo,pBody);

                IVsppService vsppService = (IVsppService) vsppServiceFactory.get(clientResquestHeader.getOperateID());

                PacketInfoVO responseInfo = new PacketInfoVO(); //返回信息
                try {
                    responseInfo = vsppService.response(clientResquestInfo); //处理输入信息
                } catch (Exception e) {
                    logger.error("本次请求有误{}", e);
                }
                tcpServer.vsppSendMessage(responseInfo.getClientVO(), responseInfo);

                String responseBody = responseInfo.getPacketBody();
                PacketHeadVO responseHead = responseInfo.getPaHeadVO();
                String responseHeadString = responseHead.getPacketHeadString(responseBody);
                logger.info("==out==header is:{},errorNo is:{},body is:{}",
                        responseHeadString, responseHead.getErrno(),responseBody);
            }
        } catch (Exception e) {
            logger.error("致命错误：{}", e);
        }

    }


}
