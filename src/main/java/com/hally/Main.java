package com.hally;

import com.hally.server.VsppServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-11-21 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */

public class Main {




    private  static ApplicationContext getApplicationContext() {
        return new ClassPathXmlApplicationContext("spring-context.xml");
    }

    public static void main(String[] args) {

        ApplicationContext context = getApplicationContext();

        VsppServer vsspServer = (VsppServer) context.getBean("vsppServer");

        vsspServer.startServer();


       /* IUserLogsService userLogsService = (IUserLogsService) context.getBean("userLogsService");

        IvrUserLogs ivrUserLogs = new IvrUserLogs();
        ivrUserLogs.setMsisdn("1111");
        ivrUserLogs.setCallNumber("11");
        ivrUserLogs.setCallTime(new Date());
        ivrUserLogs.setEndTime(new Date());
        ivrUserLogs.setCallSecond(110L);
        ivrUserLogs.setCreateTime(new Date());

        userLogsService.save(ivrUserLogs);*/


    }

}
