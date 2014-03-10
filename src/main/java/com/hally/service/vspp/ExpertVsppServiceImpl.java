package com.hally.service.vspp;

import com.common.config.MyConfigurer;
import com.common.util.TimeUtil;
import com.hally.cache.EhcacheService;
import com.hally.common.Constants;
import com.hally.pojo.IvrConfigData;
import com.hally.pojo.IvrUserLogs;
import com.hally.service.IConfigDataService;
import com.hally.service.IUserLogsService;
import com.hisunsray.vspp.data.PacketHeadVO;
import com.hisunsray.vspp.data.PacketInfoVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 14-3-7 | hallywang | created <p/> </p>
 *
 * @author hallywang
 * @version 1.0.0
 */
@Service("configDataVsppService")
public class ExpertVsppServiceImpl implements IVsppService {
    private static Logger logger = LoggerFactory.getLogger(ExpertVsppServiceImpl.class);

    @Resource
    private EhcacheService ehcacheService;

    @Resource
    private IConfigDataService configDataService;

    @Resource
    private IUserLogsService userLogsService;

    private static String RESP_STATUS_OK = "0";
    private static String RESP_STATUS_ERROR = "1";

    @Override
    public PacketInfoVO response(PacketInfoVO packetInfoVO) {
        PacketHeadVO paHeadVO = packetInfoVO.getPaHeadVO();

        String pBody = packetInfoVO.getPacketBody();
        String operateId = paHeadVO.getOperateID();
        String serviceId = paHeadVO.getServerID();


        paHeadVO.setSubCommand("02"); //接口规范，必须设置为02


        String split = (String) MyConfigurer.getContextProperty("split");

        String errNo = Constants.ERROR_NO_ERROR;
        paHeadVO.setErrno(errNo);

        while (true) {

            String body = packetInfoVO.getPacketBody();

            if (body == null || "".equals(body)) {
                logger.error("request body is null");
                break;
            }

            String[] fileds = StringUtils.split(body, split);

            if (fileds == null || fileds.length < 4) {
                logger.error("request body is error:{}", body);
                break;
            }

            String userMobile = fileds[0];
            String callNumber = fileds[1];
            String startTime = fileds[2];
            String touchButton = fileds[3];

            Date sTime;
            long callSecond = -1;
            try {
                sTime = TimeUtil.parseDateByString(startTime, "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                logger.error("记录用户行为失败,startTime时间格式错误,startTime:{},ex:{}", startTime, e);
                break;
            }
            try {
                IvrUserLogs ivrUserLogs = new IvrUserLogs();
                ivrUserLogs.setMsisdn(userMobile);
                ivrUserLogs.setServiceId(serviceId);
                ivrUserLogs.setCallNumber(callNumber);
                ivrUserLogs.setCallTime(sTime);
                ivrUserLogs.setEndTime(new Date());
                ivrUserLogs.setCallSecond(callSecond);
                ivrUserLogs.setCreateTime(new Date());
                ivrUserLogs.setOperateId(operateId);
                ivrUserLogs.setTouchButton(touchButton);

                userLogsService.save(ivrUserLogs);
            } catch (Exception e) {
                logger.error("记录用户行为失败,入库错误: {}", e);
            }

            List list = configDataService.listValid(operateId, serviceId);

            StringBuilder respBody = new StringBuilder();

            if (list.size() <= 0) {
                respBody.append(RESP_STATUS_ERROR);
                respBody.append(split);
                respBody.append("");
                errNo = Constants.ERROR_NO_ERROR;
            } else {
                IvrConfigData ivrConfigData = (IvrConfigData) list.get(0);
                respBody.append(RESP_STATUS_OK);
                respBody.append(split);
                respBody.append(ivrConfigData.getContent());
                errNo = Constants.ERROR_NO_OK;
            }
            paHeadVO.setErrno(errNo);
            packetInfoVO.setPacketBody(respBody.toString());
            packetInfoVO.setPaHeadVO(paHeadVO);

            break;
        }

        return packetInfoVO;
    }
}
