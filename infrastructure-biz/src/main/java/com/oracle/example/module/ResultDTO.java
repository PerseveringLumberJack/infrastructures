package com.oracle.example.module;


import com.oracle.example.enums.ErrorEnum;
import com.oracle.example.enums.ResultDTOTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@ApiModel(value = "短信发送响应参数")
@Data
@Slf4j
public class ResultDTO<P> {

    @ApiModelProperty("状态码")
    private String code;

    @ApiModelProperty("消息")
    private String message;

    @ApiModelProperty("类型")
    private String type = ResultDTOTypeEnum.COMMON.code();

    @ApiModelProperty("数据")
    private P data;

    public ResultDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultDTO(String code, String message, P data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 判断某个对象是否成功
     *
     * @param resultDTO
     * @return
     */
    public static boolean isSuccess(ResultDTO<?> resultDTO) {
        return resultDTO.getCode().equals(ErrorEnum.SUCCESS.code());
    }

    /**
     * 返回成功状态，以及数据
     */
    public static <P> ResultDTO<P> success(P data) {
        return new ResultDTO<>(ErrorEnum.SUCCESS.code(),
                ErrorEnum.SUCCESS.message(),
                data);
    }

    /**
     * 返回成功状态，以及数据
     */
    public static <T> ResultDTO<T> success() {
        return success(null);
    }

    /**
     * 返回成功状态，数据为空
     */
    public static <T> ResultDTO<T> success(Class<T> tClass) {
        try {
            return success(tClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(ErrorEnum.UNKNOWN_ERROR.message());
        }
        return success();
    }

    /**
     * 返回错误状态， 包含错误状态码和错误消息
     */
    public static ResultDTO<?> error(String code, String msg) {
        return new ResultDTO(code, msg);
    }

    /**
     * 返回错误状态， 包含错误状态码和错误消息
     */
    public static <T> ResultDTO<T> error(String code, String msg, T obj) {
        return new ResultDTO<T>(code, msg, obj);
    }

    /**
     * 返回错误状态,
     */
    public static <T> ResultDTO<T> error(String code, String msg, Class<T> tClass) {
        return new ResultDTO<T>(code, msg);
    }


}
