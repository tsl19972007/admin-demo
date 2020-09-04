package com.example.demo.exception;

import com.example.demo.common.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：tsl
 * @date ：Created in 2020/6/9 15:50
 * @description：
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {
    private Integer code;
    private String desc;

    public BusinessException(ResponseCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.desc = codeEnum.getDesc();
    }

    public BusinessException(ResponseCodeEnum codeEnum, String desc) {
        this.code = codeEnum.getCode();
        this.desc = desc;
    }
}
