package com.hally.service.vspp;

import com.common.config.MyConfigurer;
import com.common.util.TimeUtil;
import com.hally.common.Constants;
import com.hally.pojo.IvrUserLogs;
import com.hally.service.IUserLogsService;
import com.hally.service.MobileService;
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

    @Resource
    private MobileService mobileService;

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
        String serviceId = "";  //packetHeadVO.getServerID(); // 当拨打长号码的时候，header传递有误

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

            String provName = mobileService.getProvName(userMobile);
            String cityName = mobileService.getCityName(userMobile);

            //packetHeadVO.getServerID()当拨打长号码的时候，header传递有误
            //1259054311,125905431,1259054312,取4到9位 也就是125905431 作为serviceId
            serviceId = callNumber.substring(4, 9);
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

                double fee = ((double) callSecond) / 60;  //默认一分钟一元钱
                long fee2 = (long) Math.ceil(fee); //超过一秒也算一分钟，向上取整
                ivrUserLogs.setFee(fee2);

                ivrUserLogs.setCallSecond(callSecond);
                ivrUserLogs.setCreateTime(new Date());
                ivrUserLogs.setProvinceName(provName);
                ivrUserLogs.setCityName(cityName);


                logger.info("USERLOGS:{},{},{},{},{},{},{},{},{},{}"
                        , userMobile, serviceId, operateId, callNumber, sTime, eTime, callSecond, fee2, provName, cityName);

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
