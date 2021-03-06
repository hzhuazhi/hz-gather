package com.hz.gather.master.core.common.utils.constant;


/**
 * @author df
 * @Description:缓存key
 * @create 2017-09-27 18:36
 **/
public class CachedKeyUtils {
    /**
     * 主服务生成缓存Key
     *
     * @param objects
     * @return
     */
    public static String getCacheKey(Object... objects) {
        int size = objects.length;
        StringBuffer key = new StringBuffer(SystemKeysEnum.KEY_GATHER.getName());
        int i = 0;
        for (Object object : objects) {
            if (object != null) {
                key.append(object.toString());
                if (i < size - 1) {
                    key.append("-");
                }
            }
            i++;
        }
        return key.toString();
    }

    /**
     * 支付模块生成缓存Key
     *
     * @param objects
     * @return
     */
    public static String getPfCacheKey(Object... objects) {
        int size = objects.length;
        StringBuffer key = new StringBuffer(SystemKeysEnum.KEY_PF.getName());
        int i = 0;
        for (Object object : objects) {
            if (object != null) {
                key.append(object.toString());
                if (i < size - 1) {
                    key.append("-");
                }
            }
            i++;
        }
        return key.toString();
    }

}
