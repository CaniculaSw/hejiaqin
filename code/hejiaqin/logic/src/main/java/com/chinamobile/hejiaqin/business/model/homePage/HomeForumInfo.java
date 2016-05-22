package com.chinamobile.hejiaqin.business.model.homePage;

/**
 * desc: 推荐讲堂
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/5/19.
 */
public class HomeForumInfo {
    //讲座ID
    private String id;
    //讲座名称
    private String title;
    //作者信息
    private String authorIntro;
    //图片
    private String cover;
    //1视频；2图片动画。
    private String mediatype;
    //查看次数
    private long viewcount;
    //1初级、2中级、3高级
    private String grade;
    //运动范围：1全身运动、2上身、3下身
    private String range;
    //有无器械：1有、0无
    private String apparatus;
    //视频时长，单位秒
    private long totaltime;
    //作者
    private String author;
    //点赞次数
    private long thumbsUp;
    //创建时间
    private String createtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorIntro() {
        return authorIntro;
    }

    public void setAuthorIntro(String authorIntro) {
        this.authorIntro = authorIntro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public long getViewcount() {
        return viewcount;
    }

    public void setViewcount(long viewcount) {
        this.viewcount = viewcount;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getApparatus() {
        return apparatus;
    }

    public void setApparatus(String apparatus) {
        this.apparatus = apparatus;
    }

    public long getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(long totaltime) {
        this.totaltime = totaltime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(long thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
