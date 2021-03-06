package com.hally.server;

import com.common.config.MyConfigurer;
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

    public void startServer() throws Exception {
        logger.info("NOW Begin CommInit");
        TCPSpServer tcpServer = new TCPSpServer();
        int nPort = Integer.valueOf(MyConfigurer.getContextProperty("nPort").toString());
        int nKey = Integer.valueOf(MyConfigurer.getContextProperty("nKey").toString());
        try {

            int ret = tcpServer.vsppCommInit(nPort, nKey);
            logger.info("comminit ret is {}", ret);

            if (ret != 0) {
                logger.error("启动服务失败,进程退出,可能是端口{}被占用，ret is:{} ", nPort, ret);
                throw new Exception("启动服务失败,进程退出,可能是端口" + nPort
                        + "被占用，ret is:" + ret);
            }

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
                String errorNo = clientResquestHeader.getErrno();
                String seqNo = clientResquestHeader.getSeqNo();

                logger.info("==in==host ip is:{},port is:{}", ip, port);
                logger.info("==in==seqNo:{},serviceId:{},operateid:{},header is:{},errorNo is:{},body is:{}",
                        seqNo, serviceId, operateId, pHeader, errorNo, pBody);

                //todo  ip 验证

                //根据操作ID获取接口处理的实现类
                IVsppService vsppService = (IVsppService) vsppServiceFactory.get(operateId);

                PacketInfoVO responseInfo = new PacketInfoVO(); //返回信息
                try {
                    responseInfo = vsppService.response(clientResquestInfo); //处理输入信息

                    String responseBody = responseInfo.getPacketBody();
                    PacketHeadVO responseHead = responseInfo.getPaHeadVO();
                    String responseHeadString = responseHead.getPacketHeadString(responseBody);
                    String respSeqNo = responseHead.getSeqNo();
                    logger.info("==out==seqNo:{},header is:{},errorNo is:{},body is:{}",
                            respSeqNo, responseHeadString, responseHead.getErrno(), responseBody);


                } catch (Exception e) {
                    logger.error("本次请求有误 {}", e);
                }

                tcpServer.vsppSendMessage(responseInfo.getClientVO(), responseInfo);

                logger.info("end to recv packets");

            }
        } catch (Exception e) {
            logger.error("致命错误：{}", e);
            throw e;
        }

    }


}
