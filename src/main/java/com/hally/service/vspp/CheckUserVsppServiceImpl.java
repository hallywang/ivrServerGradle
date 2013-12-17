package com.hally.service.vspp;

import com.emag.config.MyConfigurer;
import com.hally.cache.EhcacheService;
import com.hally.cache.ObjectEhCache;
import com.hally.service.IBlackUserService;
import com.hisunsray.vspp.data.PacketHeadVO;
import com.hisunsray.vspp.data.PacketInfoVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private EhcacheService ehcacheService;

    @Override
    public PacketInfoVO response(PacketInfoVO packetInfoVO) {

        PacketHeadVO paHeadVO = packetInfoVO.getPaHeadVO();

        String pBody = packetInfoVO.getPacketBody();

        paHeadVO.setSubCommand("02"); //接口规范，必须设置为02

        packetInfoVO.setPacketBody(responseBody(pBody));
        packetInfoVO.setPaHeadVO(paHeadVO);

        return packetInfoVO;
    }

    private String responseBody(String body) {

        String split = (String) MyConfigurer.getContextProperty("split");


        String[] fileds = StringUtils.split(body, split);

        String userMobile = fileds[0];
        String callNumber = fileds[1];
        String startTime = fileds[2];
        String area = fileds[3];

        StringBuilder responseBody = new StringBuilder();

        // 0－允许接入：合法用户 1－允许接入：限定拨打时长 其他：限制接入
        String flag = "0";

        //提示语 0，1时，置主菜单序号，默认“00” 其他时，置异常提示音：spid_tip_xx.vox
        String blockTip = "00";

        Integer limitSecond = 0; //限定时长
        String sms = "";//挂机短信，“sms”表示需要发送挂机短信 其他表示不发送挂短
        String smsTemplateId = "";  //挂机短信-模板号
        String smsContentId = ""; //挂机短信-内容ID

        ObjectEhCache cache = ehcacheService.getCache("blackUser");


        List list = (List) cache.get(userMobile);

        if (list == null) {
            list = ivrBlackUserService.getByMobile(userMobile);
            cache.put(userMobile, list);
        }

        if (list != null && list.size() > 0) {
            flag = "9";
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
