package com.cxsj.baipiao.request;

import com.cxsj.baipiao.bizShare.BaseRequest;
import lombok.Data;

@Data
public class UpdateUserRequest extends BaseRequest {

    private String pic;

    private String nick;

    private String sex;
}
