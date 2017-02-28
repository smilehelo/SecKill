package org.seckill.entity;

import java.util.Date;

public class Seckill {

	private long seckillId;
	
	private String commodityName;
	
	private int commodityNumber;
	
	private Date startTime;
	
	private Date endTime;
	
	private Date createTime;

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public int getCommodityNumber() {
		return commodityNumber;
	}

	public void setCommodityNumber(int commodityNumber) {
		this.commodityNumber = commodityNumber;
	}



	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Seckill [seckillId=" + seckillId + ", commodityName="
				+ commodityName + ", commodityNumber=" + commodityNumber
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", createTime=" + createTime + "]";
	}

	
	
}
