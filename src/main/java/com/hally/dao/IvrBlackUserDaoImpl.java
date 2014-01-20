package com.hally.dao;

import com.hally.dao.base.hibernate4.BaseHibernateDao;
import com.hally.pojo.IvrBlackUser;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * function description. <p/> <p><h2>Change History</h2> <p/> 13-12-6 | hally | created <p/> </p>
 *
 * @author hally
 * @version 1.0.0
 */
@Repository(value = "ivrBlackUserDao")
public class IvrBlackUserDaoImpl extends BaseHibernateDao<IvrBlackUser, Integer>
        implements IvrBlackUserDao {

    public List<IvrBlackUser> loadAllValidUsers() {

        HashMap<String,Object> params = new HashMap<String, Object>();

        params.put("status",1);


        return listByHql("from IvrBlackUser where status=:status",params);


    }
}
