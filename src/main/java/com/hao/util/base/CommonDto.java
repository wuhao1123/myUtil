package com.hao.util.base;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ModelRo.java
 * @Description 功能描述：
 * @author 吴昊 2018年3月6日19:31:15
 */
public class CommonDto {

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码，默认1")
    private Integer       pageNum  = 1;
    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量，默认10，最大50")
    private Integer       pageSize = 10;
    /**
     * 排序参数
     */
    @ApiModelProperty(value = "排序参数，默认id")
    private String        orderParam;
    /**
     * 排序方式：asc/desc
     */
    @ApiModelProperty(value = "排序方式：asc/desc，默认倒序desc")
    private String        orderType;
    /**
     * id
     */
    @ApiModelProperty(value = "查询的id")
    private Integer       id;
    /**
     * ids
     */
    @ApiModelProperty(value = "查询的ids")
    private List<Integer> ids;
    /**
     * 查询关键字
     */
    @ApiModelProperty(value = "查询关键字用来模糊匹配")
    private String        keywords;
    /**
     * 查询开始时间
     */
    @ApiModelProperty(value = "查询开始时间yyyy-MM-dd格式")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date          startTime;
    /**
     * 查询结束时间
     */
    @ApiModelProperty(value = "查询结束时间yyyy-MM-dd格式")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date          endTime;

    /**
     * @return the pageNum
     */
    public Integer getPageNum() {
        return pageNum;
    }

    /**
     * @param pageNum the pageNum to set
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * @return the pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the orderParam
     */
    public String getOrderParam() {
        return orderParam;
    }

    /**
     * @param orderParam the orderParam to set
     */
    public void setOrderParam(String orderParam) {
        this.orderParam = orderParam;
    }

    /**
     * @return the orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param orderType the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the ids
     */
    public List<Integer> getIds() {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    /**
     * @return the keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * @param keywords the keywords to set
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {

        this.startTime = startTime == null ? DateUtil.increaseDate(new Date(), -14) : startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime == null ? new Date() : DateUtil.increaseDate(endTime, 1);
    }

}
