package com.cxsj.baipiao.request;

import com.cxsj.baipiao.bizShare.BaseRequest;
import com.cxsj.baipiao.domain.Address;
import lombok.Data;

@Data
public class UpdateAddressRequest extends BaseRequest {

    private Address address;
}
