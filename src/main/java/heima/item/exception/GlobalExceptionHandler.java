package heima.item.exception;

import heima.item.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e){
        log.error("程序出错了！",e);
        return Result.error("出错了，联系管理员");
    }
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e){
        log.error("程序出错了！",e);
        String msg=e.getMessage();
        int i=msg.indexOf("Duplicate entry");
        String errMsg=msg.substring(i);
        String arr[]=errMsg.split(" ");
        return Result.error(arr[2]+"已存在");
    }
    @ExceptionHandler(DeleteClassException.class)
    public Result handleDeleteClassException(DeleteClassException e){
        log.error("程序出错了！",e);
        return Result.error(e.getMessage());
    }
}
