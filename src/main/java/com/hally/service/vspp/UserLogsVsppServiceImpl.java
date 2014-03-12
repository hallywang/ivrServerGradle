package com.hally.service.vspp;

import com.common.config.MyConfigurer;
import com.common.util.TimeUtil;
import com.hally.common.Constants;
import com.hally.pojo.IvrUserLogs;
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

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-11 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
@Service("userLogsVsppService")
public class UserLogsVsppServiceImpl implements IVsppService {
    private static Logger logger = LoggerFactory.getLogger(UserLogsVsppServiceImpl.class);
    @Resource
    private IUserLogsService userLogsService;
    @Override
    public PacketInfoVO response(PacketInfoVO packetInfoVO) {
         /*errorno = 00000，成功,errorno = 00002，失败*/
        String errNo = saveLogs(packetInfoVO);
        PacketHeadVO paHeadVO = packetInfoVO.getPaHeadVO();
        paHeadVO.setSubCommand("02");
        paHeadVO.setErrno(errNo);
        packetInfoVO.setPacketBody("");
        packetInfoVO.setPaHeadVO(paHeadVO);

        logger.info("记录用户日志结束");
        return packetInfoVO;
    }

    /**
     * errorno = 00000，成功 errorno = 00002，失败
     *
     * @param packetInfoVO 接收到的报文信息
     * @return errorno error code
     */
    private String saveLogs(PacketInfoVO packetInfoVO) {
        String split = (String) MyConfigurer.getContextProperty("split");
        String errorNo = Constants.ERROR_NO_ERROR;
        PacketHeadVO packetHeadVO = packetInfoVO.getPaHeadVO();
        String serviceId = packetHeadVO.getServerID();
        String operateId = packetHeadVO.getOperateID();

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
            String endTime = fileds[3];
            Date sTime;
            Date eTime;
            long callSecond = 0;
            try {
                sTime = TimeUtil.parseDateByString(startTime, "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                logger.error("记录日志失败,startTime时间格式错误,startTime:{},ex:{}", startTime, e);
                break;
            }
            try {
                eTime = TimeUtil.parseDateByString(endTime, "yyyy-MM-dd HH:mm:ss");
                callSecond = TimeUtil.getSecondDis(sTime, eTime);
            } catch (ParseException e) {
                logger.error("记录日志失败,endTime时间格式错误,endTime:{},ex:{}", endTime, e);
                break;
            }
            try {
                IvrUserLogs ivrUserLogs = new IvrUserLogs();
                ivrUserLogs.setMsisdn(userMobile);
                ivrUserLogs.setServiceId(serviceId);
                ivrUserLogs.setOperateId(operateId);
                ivrUserLogs.setCallNumber(callNumber);
                ivrUserLogs.setCallTime(sTime);
                ivrUserLogs.setEndTime(eTime);
                ivrUserLogs.setCallSecond(callSecond);
                ivrUserLogs.setCreateTime(new Date());

                logger.info("USERLOGS:{},{},{},{},{},{},{},{}"
                        ,userMobile,serviceId,operateId,callNumber,sTime,eTime,callSecond,"") ;

                userLogsService.save(ivrUserLogs);
                errorNo = Constants.ERROR_NO_OK;

            } catch (Exception e) {
                logger.error("记录日志失败,入库错误:{}", e);
            }
            break;
        }
        return errorNo;
    }
}
