package com.hally.test;

/**
 * function description.
 * <p/>
 * <p><h2>Change History</h2>
 * <p/>
 * 13-12-13 ; hally ; created
 * <p/>
 * </p>
 *
 * @author hally
 * @version 1.0.0
 */
/*
 * Created on 2004-3-17
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

import com.hisunsray.intersp.TCPSpClient;
import com.hisunsray.vspp.data.ClientVO;
import com.hisunsray.vspp.data.PacketHeadVO;
import com.hisunsray.vspp.data.PacketInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 *         <p/>
 *         To change the template for this generated type comment go to Window - Preferences - Java - Code Generation -
 *         Code and Comments
 */
public class TestClient0601 {
    private static Logger logger = LoggerFactory.getLogger(TestClient0601.class);

    private static int connectServer(TCPSpClient spClient, ClientVO cliVO, PacketInfoVO paVO) throws Exception {
        int result = 0;
        String nIP = "127.0.0.1";
        int nPort = 9090;
        while (true) {

            String spID = "1";
            int key = 1;
            logger.debug("begin bind");
            result = spClient.vsppCommInit(nIP, nPort, key, spID);
            if (!(result == 0)) {
                logger.debug("bind fail");
                Thread.sleep(10);
            } else {
                logger.debug("bind success");
                break;
            }
            logger.debug("out bind");
        }
        // PacketInfoVO paVO = new PacketInfoVO();
        String bodyStr = "13675180163;125901234;2013-12-12 13:10:10;2013-12-12 14:10:10";
            /*for(int i=0;i<14000;i++)
                bodyStr=bodyStr+"9";*/
        paVO.setPacketBody(bodyStr);

        PacketHeadVO paHeadVO = new PacketHeadVO();
        paHeadVO.setCategory("01");
        paHeadVO.setSubCommand("01");
        paHeadVO.setOpCode("0101");
        paHeadVO.setSeqNo("123123");
        paHeadVO.setSpID("1");
        paHeadVO.setServerID("05432");

        paHeadVO.setOperateID("0601");//////
        paHeadVO.setCallingNUMBER("13675180163");
        paHeadVO.setCalledNUMBER("12590443");

        paHeadVO.setResult("0001");
        paHeadVO.setCryType("00000");
        paHeadVO.setKeyNO("");
        paVO.setPaHeadVO(paHeadVO);
        //ClientVO cliVO = new ClientVO();
        cliVO.setHostIP(nIP);
        cliVO.setNPort(nPort);

        result = spClient.vsppSendMessage(cliVO, paVO);


        return result;
    }

    public static void main(String[] args) {

        int result = 0;
        TCPSpClient spClient = new TCPSpClient();
        ClientVO cliVO = new ClientVO();
        PacketInfoVO paVO = new PacketInfoVO();
        try {
            result = connectServer(spClient, cliVO, paVO);
        } catch (Exception e) {
            e.printStackTrace();
        }


        logger.debug("result is " + result);

        while (true) {
            logger.debug("begin receive work");
            PacketInfoVO paInfoVO = null;
            try {
                paInfoVO = spClient.vsppReceiveMessage();

                logger.info("result 结果 is {}", paInfoVO.getPacketBody());

                // PacketInfoVO paVO = new PacketInfoVO();
                String bodyStr = "13675180163;11111;2013-12-12 13:10:10;2013-12-12 14:10:10";
            /*for(int i=0;i<14000;i++)
                bodyStr=bodyStr+"9";*/
                paVO.setPacketBody(bodyStr);


                spClient.vsppSendMessage(cliVO, paVO);


                Thread.sleep(1000L);


            } catch (Exception e) {
                e.printStackTrace();

            }


        }


    }
}
