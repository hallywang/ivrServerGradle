/*
 * Created on 2004-3-4
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.hally.test;

import com.hisunsray.intersp.TCPSpServer;
import com.hisunsray.vspp.data.ClientVO;
import com.hisunsray.vspp.data.PacketHeadVO;
import com.hisunsray.vspp.data.PacketInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Yuanxb
 *         <p/>
 *         To change the template for this generated type comment go to Window - Preferences - Java - Code Generation -
 *         Code and Comments
 */
public class TestInterSp {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(TestInterSp.class);

        try {

            SimpleDateFormat formattxt = new SimpleDateFormat("yyyyMMdd");
            Calendar cr = Calendar.getInstance();
            cr.setTime(new Date());

            cr.add(Calendar.DATE, -1);
            String sz_time = formattxt.format(cr.getTime());
            logger.info("yesterday is {}", sz_time);
            logger.info("test server is begin");
            int nPort = 8080;
            String nIP = "127.0.0.1";
            int nKey = 1;

            logger.info("NOW Begin CommInit");
            TCPSpServer tcpServer = new TCPSpServer();
            int ret = tcpServer.vsppCommInit(nPort, nKey);

            logger.info("comminit ret is {}", ret);


            while (true) {
                PacketHeadVO paHeadVO = new PacketHeadVO();
                logger.info("begin to recv packets");
                PacketInfoVO paInfoVO = tcpServer.vsppReceiveMessage();
                logger.info("get message ok");
                ClientVO cliVO = paInfoVO.getClientVO();
                String pBody = paInfoVO.getPacketBody();
                String ip = cliVO.getHostIP();
                int port = cliVO.getNPort();
                paHeadVO = paInfoVO.getPaHeadVO();

                logger.debug("port is "+cliVO.getNPort());
                logger.debug("body is " + pBody);
                logger.debug("被叫is " + paHeadVO.getCalledNUMBER());
                logger.debug("主叫is" + paHeadVO.getCallingNUMBER());


                logger.debug("opcode is " + paHeadVO.getOpCode());
                paHeadVO.setSubCommand("02");
                /*Random rand = new Random();
                pBody = String.valueOf(Math.abs(rand.nextInt(10000)));*/
                paInfoVO.setPacketBody("中文呢测试^^hally^^test^^test33");
                paInfoVO.setPaHeadVO(paHeadVO);
                tcpServer.vsppSendMessage(cliVO, paInfoVO);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
