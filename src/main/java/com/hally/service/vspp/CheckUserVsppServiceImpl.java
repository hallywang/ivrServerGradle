package com.hally.service.vspp;

import com.emag.config.MyConfigurer;
import com.hally.service.IBlackUserService;
import com.hisunsray.vspp.data.ClientVO;
import com.hisunsray.vspp.data.PacketHeadVO;
import com.hisunsray.vspp.data.PacketInfoVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-11 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
@Service("checkUserVsppService")
public class CheckUserVsppServiceImpl implements IVsppService {
    private static Logger logger = LoggerFactory.getLogger(CheckUserVsppServiceImpl.class);

    @Resource
    private IBlackUserService ivrBlackUserService;

    @Override
    public PacketInfoVO response(PacketInfoVO packetInfoVO) {

        PacketHeadVO paHeadVO = packetInfoVO.getPaHeadVO();
        ClientVO cliVO = packetInfoVO.getClientVO();

        String ip = cliVO.getHostIP();
        int port = cliVO.getNPort();

        String serviceId = paHeadVO.getServerID(); //todo ? serviceid
        String operateId = paHeadVO.getOperateID();
        String pBody = packetInfoVO.getPacketBody();

        logger.info("port is {}", port);
        logger.info("host ip is {}", ip);
        logger.info("body is {}", pBody);
        logger.info("����is {}", paHeadVO.getCalledNUMBER());
        logger.info("����is {}", paHeadVO.getCallingNUMBER());
        logger.info("opcode is {}", paHeadVO.getOpCode());
        paHeadVO.setSubCommand("02");
        packetInfoVO.setPacketBody(responseBody(pBody));
        packetInfoVO.setPaHeadVO(paHeadVO);

        return packetInfoVO;
    }

    private String responseBody(String body) {

        String split = (String) MyConfigurer.getContextProperty("split");


        String[] fileds = StringUtils.split(body,split);

        String userMobile = fileds[0];
        String callNumber = fileds[1];
        String startTime = fileds[2];
        String area = fileds[3];

        StringBuilder responseBody = new StringBuilder();
        String flag = "0"; // 0��������룺�Ϸ��û� 1��������룺�޶�����ʱ�� ���������ƽ���

        String blockTip = "blocktip"; //��ʾ��
        Integer limitSecond = 0; //�޶�ʱ��
        String sms = "";//�һ����ţ���sms����ʾ��Ҫ���͹һ����� ������ʾ�����͹Ҷ�
        String smsTemplateId = "";  //�һ�����-ģ���
        String smsContentId = ""; //�һ�����-����ID
        logger.info("userMobile:{}", userMobile);

        List list = ivrBlackUserService.getByMobile(userMobile);

        if (list != null && list.size() > 0) {
            flag = "9";
            logger.info("list size ===== {}", list.size());
        }

        responseBody.append(flag).append(split);      //�ֶ�1
        responseBody.append(blockTip).append(split);   //�ֶ�2
        responseBody.append(limitSecond).append(split); //�ֶ�3
        responseBody.append(sms).append(split); //�ֶ�4
        responseBody.append(smsTemplateId).append(split); //�ֶ�5
        responseBody.append(smsContentId).append(split); //�ֶ�6

        //ƽ̨Ԥ��
        responseBody.append("").append(split);  //�ֶ�7
        responseBody.append("").append(split); //�ֶ�8
        responseBody.append("").append(split);  //�ֶ�9
        responseBody.append("").append(split);  //�ֶ�10

        //sp�Զ���
        responseBody.append("").append(split); //�ֶ�11
        responseBody.append("").append(split); //�ֶ�12
        responseBody.append("").append(split);  //�ֶ�13
        responseBody.append("").append(split);  //�ֶ�14
        responseBody.append(new Date());              //�ֶ�15 todo


        return responseBody.toString();

    }
}
