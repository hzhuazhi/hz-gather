package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.common.utils.DateUtil;
import com.hz.gather.master.core.common.utils.RandomUtil;
import com.hz.gather.master.core.mapper.NoticeMapper;
import com.hz.gather.master.core.model.entity.SysNoticeInfo;
import com.hz.gather.master.core.service.DateService;
import com.hz.gather.master.util.PublicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/2/18 16:00
 * @Version 1.0
 */
@Service
public class DateServiceImpl <T> extends BaseServiceImpl<T> implements DateService<T> {
    @Autowired
    private NoticeMapper sysNoticeInfoMapper;

    @Override
    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public int randomNotice(Integer  count) {
        Map<String,Object>   map = new HashMap<>();
        map.put("0",0D);
        map.put("1",200D);
        map.put("2",500D);
        map.put("3",800D);
        map.put("4",1000D);
        map.put("5",2000D);
        for(int i=0;i<count;i++){
            String  begenDate=DateUtil.getDateHour(new Date());
            String  endDate =DateUtil.getPlusTime(new Date());
            Date date = DateUtil.randomDate(begenDate+":00:00",endDate);
            String  key = RandomUtil.getRandom(1,6);
            Double  money = Double.parseDouble(map.get(key)+"");

            String  nickname = "**"+RandomUtil.getRandom(4,10);
            SysNoticeInfo sysNoticeInfo =PublicMethod.insertNoticeModelDate(nickname,(Integer.parseInt(key)+1),money,date);
            sysNoticeInfoMapper.insertSelective(sysNoticeInfo);
        }
        return 0;
    }
}
