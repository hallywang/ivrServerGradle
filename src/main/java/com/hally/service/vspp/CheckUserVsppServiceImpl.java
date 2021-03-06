package com.hally.service.vspp;

import com.common.config.MyConfigurer;
import com.hally.cache.EhcacheService;
import com.hally.cache.ObjectEhCache;
import com.hally.common.Constants;
import com.hally.pojo.IvrBlackUser;
import com.hally.service.IBlackUserService;
import com.hisunsray.vspp.data.PacketHeadVO;
import com.hisunsray.vspp.data.PacketInfoVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    private IBlackUserService blackUserService;
    @Override
    public PacketInfoVO response(PacketInfoVO packetInfoVO) {
        PacketHeadVO paHeadVO = packetInfoVO.getPaHeadVO();
        String pBody = packetInfoVO.getPacketBody();
        paHeadVO.setSubCommand("02"); //接口规范，必须设置为02
        packetInfoVO.setPacketBody(responseBody(pBody, paHeadVO));
        packetInfoVO.setPaHeadVO(paHeadVO);
        return packetInfoVO;
    }

    private String responseBody(String body, PacketHeadVO paHeadVO) {
        String split = (String) MyConfigurer.getContextProperty("split");
        String spId = (String) MyConfigurer.getContextProperty("spId");
        String serviceId = paHeadVO.getServerID(); //packetHeadVO.getServerID(); // 当拨打长号码的时候，header传递有误

        String[] fileds = StringUtils.split(body, split);
        StringBuilder responseBody = new StringBuilder();
        if (fileds == null || fileds.length < 4) {
            responseBody.append("error request body");
            responseBody.append(split);
            responseBody.append(split);
            responseBody.append(split);
            responseBody.append(split);
            responseBody.append(split);
            responseBody.append(split);
            responseBody.append(split);
            responseBody.append(split);
            responseBody.append(split);
            responseBody.append(split);
            responseBody.append(split);
            responseBody.append(split);
            responseBody.append(split);
            responseBody.append(split);
            //拼装正确的包体回复，防止对方客户端报错
            paHeadVO.setErrno(Constants.ERROR_NO_ERROR);
            logger.error("error request body:{}", body);
            return responseBody.toString();
        }

        String userMobile = fileds[0];
        String callNumber = fileds[1];
        String startTime = fileds[2];
        String area = fileds[3];
        // 0－允许接入：合法用户 1－允许接入：限定拨打时长 其他：限制接入
        String flag = "0";
        //提示语 0，1时，置主菜单序号，默认“00” 其他时，置异常提示音：spid_tip_xx.vox
        String blockTip = "00";
        Integer limitSecond = 0; //限定时长
        String sms = "";//挂机短信，“sms”表示需要发送挂机短信 其他表示不发送挂短
        String smsTemplateId = "";  //挂机短信-模板号
        String smsContentId = ""; //挂机短信-内容ID

        //packetHeadVO.getServerID()当拨打长号码的时候，header传递有误
        //1259054311,125905431,1259054312,取4到9位 也就是125905431 作为serviceId
        serviceId=callNumber.substring(4,9);

        if(blackUserService.isBlackUser(userMobile,serviceId)){  //是黑名单
            flag = "9"; //限制接入
            blockTip = spId + MyConfigurer.getContextProperty("blockTip");  //todo 文件名规则
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
        responseBody.append("");              //字段15
        logger.info("checkUserVsppService responseBody:{}", responseBody);
        return responseBody.toString();
    }
}
