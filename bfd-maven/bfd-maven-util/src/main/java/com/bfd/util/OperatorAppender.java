/**
 * @Copyright www.baifendian.com 2015
 */

package com.bfd.util;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/**
 * @description : 自定义appender
 * @author : sunhaobo
 * @version : 2015年6月12日 下午3:47:49
 */

public class OperatorAppender extends DailyRollingFileAppender {

    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        return this.getThreshold().equals(priority);
    }

}
