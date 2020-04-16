package com.zhiliao.hotel.model;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "zl_menuvisitlog")
public class ZlMenuvisitlog implements Serializable {
  /**
   * 记录ID
   */
  @Id
  private Integer logId;

  /**
   *模块ID(菜单ID)
   */
  @Column(name = "MenuID")
  private Integer menuId;

  /**
   * 用户IP
   */
  @Column(name = "UserIP")
  private String userIp;

  /**
   * 用户ID
   */
  @Column(name = "UserID")
  private Integer userId;

  /**
   * 创建时间
   */
  @Column(name = "CreateDate")
  private long createDate;

  public Integer getLogId() {
    return logId;
  }

  public void setLogId(Integer logId) {
    this.logId = logId;
  }

  public Integer getMenuId() {
    return menuId;
  }

  public void setMenuId(Integer menuId) {
    this.menuId = menuId;
  }

  public String getUserIp() {
    return userIp;
  }

  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public long getCreateDate() {
    return createDate;
  }

  public void setCreateDate(long createDate) {
    this.createDate = createDate;
  }
}
