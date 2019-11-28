package com.nb.netty.codec.nativeway.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author niubin
 * <p>
 * 订购请求消息对象
 */
@Data
@ToString
public class SubscribeReq implements Serializable {

    private static final long serialVersionUID = 5026870089794018043L;
    /**
     * 订购编号
     */
    private int subReqID;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 产品名
     */
    private String productName;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 地址
     */
    private String address;
}
