
package com.hao.commons.time;

import com.google.common.base.MoreObjects;

import java.util.concurrent.TimeUnit;

/**
 * <h3>时间类</h3>
 * <p>
 * 	用于封装时间处理的逻辑 
 * </p>
 * @version $Id: Time.java, v1.0.0 2017年11月14日 下午7:38:39, huangkai
 */
public class Time {
	/** 时间单位 */
	private TimeUnit unit;
	/** 时长 */
	private long duration;

	private Time(long duration, TimeUnit unit) {
		this.duration = duration;
		this.unit = unit;
	}
	
	/** 生成当前时间实例 */
	public static Time current() {
		return milliSeconds(System.currentTimeMillis());
	}

	/** 生成时间实例, 单位为毫秒 */
	public static Time milliSeconds(long milliSecs) {
		return new Time(milliSecs, TimeUnit.MILLISECONDS); 
	}

	/** 生成时间实例, 单位为秒 */
	public static Time seconds(long secs) {
		return new Time(secs, TimeUnit.SECONDS);
	}
	
	/** 生成时间实例, 单位为分 */
	public static Time minutes(long mins) {
		return new Time(mins, TimeUnit.MINUTES);
	}
	
	/** 生成时间实例, 单位为时 */
	public static Time hours(long hours) {
		return new Time(hours, TimeUnit.HOURS);
	}

	/** 生成时间实例, 单位为天 */
	public static Time days(long days) {
		return new Time(days, TimeUnit.DAYS);
	}
	
	/** 
	 * 距离目标时间的时间间隔
	 * 
	 * @param targetTime 目标时间
	 * @return 返回间隔时长
	 */
	public Time until(Time targetTime) {
		return milliSeconds(targetTime.toMillis() - toMillis());
	}

	/** 转成毫秒 */
	public long toMillis() {
		return getUnit().toMillis(getDuration());
	}

	/** 转成秒 */
	public long toSeconds() {
		return getUnit().toSeconds(getDuration());
	}
	
	/** 转成分钟 */
	public long toMinutes() {
		return getUnit().toMinutes(getDuration());
	}

	/** 获取时间单位 */
	public TimeUnit getUnit() {
		return this.unit;
	}

	/** 获取时长 */
	public long getDuration() {
		return duration;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				  .add("duration", this.duration)
				  .add("unit", this.unit)
				  .toString();
	}
}
