package com.zhiliao.hotel.controller.invoice.params;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("发票对象Invoice")
public class InvoiceParam {

    /**
     * 发票类型:1:增值税普通发票;2增值税专用发票
     */
    @ApiModelProperty(value = "发票类型:1:增值税普通发票;2增值税专用发票", required = true)
    private Byte invoicetype;

    /**
     * 发票类型:(主体)1: 个人;2单位
     */
    @ApiModelProperty(value = "发票类型:(主体)1: 个人;2单位", required = true)
    private Byte mainbodytype;


    /**
     * 个人真实姓/或单位抬头
     */
    @ApiModelProperty(value = "个人真实姓/或单位抬头", required = true)
    private String title;


    /**
     *
     单位的纳税人识别号:15/18或20位
     */
    @ApiModelProperty(value = "单位的纳税人识别号:15/18或20位", required = true)
    private String identifier;


    /**
     *
     个人或单位电话号码
     */
    @ApiModelProperty(value = "个人或单位电话号码", required = true)
    private String tel;


    /**
     * 0电子发票；1纸质发票
     */
    @ApiModelProperty(value = "0电子发票；1纸质发票", required = true)
    private Byte electronicpapertype;


    /**
     * 开户行
     */
    @ApiModelProperty(value = "开户行", required = true)
    private String bank;

    /**
     * 开户行账号
     */
    @ApiModelProperty(value = "开户行账号", required = true)
    private String bankaccountnumber;


    /**
     * 单位电话
     */
    @ApiModelProperty(value = "单位电话", required = true)
    private String companytel;


    /**
     * 单位地址
     */
    @ApiModelProperty(value = "单位地址", required = true)
    private String companyaddress;


    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息", required = false)
    private String remark;

    @Override
    public String toString() {
        return "InvoiceParam{" +
                "invoicetype=" + invoicetype +
                ", mainbodytype=" + mainbodytype +
                ", title='" + title + '\'' +
                ", identifier='" + identifier + '\'' +
                ", tel='" + tel + '\'' +
                ", electronicpapertype=" + electronicpapertype +
                ", bank='" + bank + '\'' +
                ", bankaccountnumber='" + bankaccountnumber + '\'' +
                ", companytel='" + companytel + '\'' +
                ", companyaddress='" + companyaddress + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Byte getInvoicetype() {
        return invoicetype;
    }

    public void setInvoicetype(Byte invoicetype) {
        this.invoicetype = invoicetype;
    }

    public Byte getMainbodytype() {
        return mainbodytype;
    }

    public void setMainbodytype(Byte mainbodytype) {
        this.mainbodytype = mainbodytype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Byte getElectronicpapertype() {
        return electronicpapertype;
    }

    public void setElectronicpapertype(Byte electronicpapertype) {
        this.electronicpapertype = electronicpapertype;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankaccountnumber() {
        return bankaccountnumber;
    }

    public void setBankaccountnumber(String bankaccountnumber) {
        this.bankaccountnumber = bankaccountnumber;
    }

    public String getCompanytel() {
        return companytel;
    }

    public void setCompanytel(String companytel) {
        this.companytel = companytel;
    }

    public String getCompanyaddress() {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress) {
        this.companyaddress = companyaddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
