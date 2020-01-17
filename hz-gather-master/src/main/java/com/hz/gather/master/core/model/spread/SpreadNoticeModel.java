package com.hz.gather.master.core.model.spread;

import com.hz.gather.master.core.protocol.page.BasePage;

import java.io.Serializable;

/**
 * @Description 系统通知，系统公告，传播、扩散的通知的实体属性Bean
 * @Author yoko
 * @Date 2020/1/16 21:05
 * @Version 1.0
 */
public class SpreadNoticeModel extends BasePage implements Serializable {
    private static final long   serialVersionUID = 1203923301144L;

    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 公告名称
     */
    private String noticeName;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 简述：简单描述
     */
    private String sketch;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告的图标/图片
     */
    private String iconAds;

    /**
     * 内容的页面地址
     */
    private String pageAds;

    /**
     * 公告的开始有效时间
     */
    private String startTime;

    /**
     * 公告的结束有效时间
     */
    private String endTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 是否有效：0有效，1无效/删除
     */
    private Integer yn;

    /**
     * 服务器当前时间
     */
    private String nowTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIconAds() {
        return iconAds;
    }

    public void setIconAds(String iconAds) {
        this.iconAds = iconAds;
    }

    public String getPageAds() {
        return pageAds;
    }

    public void setPageAds(String pageAds) {
        this.pageAds = pageAds;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getSketch() {
        return sketch;
    }

    public void setSketch(String sketch) {
        this.sketch = sketch;
    }
}
