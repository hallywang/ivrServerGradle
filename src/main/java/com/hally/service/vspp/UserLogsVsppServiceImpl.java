package com.hally.service.vspp;

import com.emag.config.MyConfigurer;
import com.emag.util.TimeUtil;
import com.hally.pojo.IvrUserLogs;
import com.hally.service.IUserLogsService;
import com.hisunsray.vspp.data.ClientVO;
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
        PacketHeadVO paHeadVO = packetInfoVO.getPaHeadVO();
        ClientVO cliVO = packetInfoVO.getClientVO();

        String ip = cliVO.getHostIP();
        int port = cliVO.getNPort();

        String serviceId = paHeadVO.getServerID(); //todo ? serviceid
        String operateId = paHeadVO.getOperateID();
        String pBody = packetInfoVO.getPacketBody();


        logger.info("开始记录用户日志：");
        logger.info("port is {}", port);
        logger.info("host ip is {}", ip);
        logger.info("body is {}", pBody);
        logger.info("被叫is {}", paHeadVO.getCalledNUMBER());
        logger.info("主叫is {}", paHeadVO.getCallingNUMBER());
        logger.info("opcode is {}", paHeadVO.getOpCode());
        logger.info("getPacketHeadString:{}", paHeadVO.getPacketHeadString(pBody));
        paHeadVO.setSubCommand("02");

        String errNo = saveLogs(packetInfoVO);
        paHeadVO.setErrno(errNo);
        /*errorno = 00000，成功
                errorno = 00002，失败*/

        packetInfoVO.setPacketBody("");
        packetInfoVO.setPaHeadVO(paHeadVO);

        logger.info("记录用户日志结束");


        return packetInfoVO;
    }

    /**
     * errorno = 00000，成功 errorno = 00002，失败
     *
     * @param packetInfoVO
     * @return
     */
    private String saveLogs(PacketInfoVO packetInfoVO) {
        String split = (String) MyConfigurer.getContextProperty("split");
        String body = packetInfoVO.getPacketBody();
        String[] fileds = StringUtils.split(body, split);

        String userMobile = fileds[0];
        String callNumber = fileds[1];
        String startTime = fileds[2];
        String endTime = fileds[3];
        Date sTime;
        Date eTime;
        long callSecond = 0;
        try {
            sTime = TimeUtil.parseDateByString(startTime, "yyyy-MM-dd HH:mm:ss");
            eTime = TimeUtil.parseDateByString(endTime, "yyyy-MM-dd HH:mm:ss");
            callSecond = TimeUtil.getSecondDis(sTime, eTime);
        } catch (ParseException e) {
            logger.error("时间格式错误,startTime:{},endTime:{},ex:{}", startTime, endTime, e);
            eTime = new Date();
            sTime = eTime;
        }

        IvrUserLogs ivrUserLogs = new IvrUserLogs();
        ivrUserLogs.setMsisdn(userMobile);
        ivrUserLogs.setCallNumber(callNumber);
        ivrUserLogs.setCallTime(sTime);
        ivrUserLogs.setEndTime(eTime);
        ivrUserLogs.setCallSecond(callSecond);
        ivrUserLogs.setCreateTime(new Date());

        userLogsService.save(ivrUserLogs);


        return "00000"; //todo  需要修改
    }
}
