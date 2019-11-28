package com.nb.netty.codec.nativeway.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 订购响应消息对象
 */
@Data
@ToString
public class SubscribeResp implements Serializable {

    private static final long serialVersionUID = 2319864755593156593L;

    /**
     * 订购编号
     */
    private int subReqID;
    /**
     * 订购结果， 0 => success
     */
    private int respCode;
    /**
     * 描述信息
     */
    private String desc;
}
