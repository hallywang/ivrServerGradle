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
        logger.info("被叫is {}", paHeadVO.getCalledNUMBER());
        logger.info("主叫is {}", paHeadVO.getCallingNUMBER());
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
        String flag = "0"; // 0－允许接入：合法用户 1－允许接入：限定拨打时长 其他：限制接入

        String blockTip = "blocktip"; //提示语
        Integer limitSecond = 0; //限定时长
        String sms = "";//挂机短信，“sms”表示需要发送挂机短信 其他表示不发送挂短
        String smsTemplateId = "";  //挂机短信-模板号
        String smsContentId = ""; //挂机短信-内容ID
        logger.info("userMobile:{}", userMobile);

        List list = ivrBlackUserService.getByMobile(userMobile);

        if (list != null && list.size() > 0) {
            flag = "9";
            logger.info("list size ===== {}", list.size());
        }

        responseBody.append(flag).append(split);      //字段1
        responseBody.append(blockTip).append(split);   //字段2
        responseBody.append(limitSecond).append(split); //字段3
        responseBody.append(sms).append(split); //字段4
        responseBody.append(smsTemplateId).append(split); //字段5
        responseBody.append(smsContentId).append(split); //字段6

        //平台预留
        responseBody.append("").append(split);  //字段7
        responseBody.append("").append(split); //字段8
        responseBody.append("").append(split);  //字段9
        responseBody.append("").append(split);  //字段10

        //sp自定义
        responseBody.append("").append(split); //字段11
        responseBody.append("").append(split); //字段12
        responseBody.append("").append(split);  //字段13
        responseBody.append("").append(split);  //字段14
        responseBody.append(new Date());              //字段15 todo


        return responseBody.toString();

    }
}
