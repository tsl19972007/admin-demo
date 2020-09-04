package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    //返回码:见ResponseCodeEnum
    private Integer code;
    //返回描述:与ResponseCode对应,可以自定义详细描述
    private String desc;
    //返回数据
    private Object data;

    public static Response ofSuccess() {
        return new Response(ResponseCodeEnum.RESPONSE_OK.getCode(), ResponseCodeEnum.RESPONSE_OK.getDesc(), null);
    }

    public static Response ofSuccess(Object data) {
        return new Response(ResponseCodeEnum.RESPONSE_OK.getCode(), ResponseCodeEnum.RESPONSE_OK.getDesc(), data);
    }

    public static Response ofFailure(ResponseCodeEnum codeEnum) {
        return new Response(codeEnum.getCode(), codeEnum.getDesc(), null);
    }

    public static Response ofFailure(Integer code, String desc) {
        return new Response(code, desc, null);
    }

    public static Response ofFailure(ResponseCodeEnum codeEnum, String desc) {
        return new Response(codeEnum.getCode(), desc, null);
    }
}

