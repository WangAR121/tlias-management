package heima.item.aspect;

import heima.item.pojo.OperateLog;
import heima.item.service.LogService;
import heima.item.utils.CurrentHolder;
import heima.item.utils.MaxLenUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 操作日志切面：只拦截「加了 {@link heima.item.anno.Log} 注解」的方法，
 * 记录 操作人 / 操作时间 / 类名 / 方法名 / 方法参数 / 返回值 / 耗时，并异步落库到 operate_log。
 *
 * <p>记录哪些接口由注解决定：需要记录的操作在 controller 方法上打 @Log，
 * 没打注解的方法（各种查询接口）不会被记录。</p>
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogService logService;

    /**
     * 切入点：@annotation(...) 只匹配「方法上带 @Log 注解」的方法，
     * 所以查询类接口不加 @Log 就不会进日志表。
     */
    @Around("@annotation(heima.item.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //1. 记录开始时间
        long begin = System.currentTimeMillis();

        //2. 执行原始的业务方法（这里必须把返回值接住，否则接口会返回 null）
        Object result = null;
        String returnValue = null;
        try {
            result = joinPoint.proceed();
            returnValue = result == null ? null : result.toString();
        } catch (Throwable e) {
            //操作失败也要记录日志，把异常信息写进返回值字段后继续往外抛，交给全局异常处理器
            returnValue = e.toString();
            throw e;
        } finally {
            //3. 记录结束时间
            long end = System.currentTimeMillis();

            //4. 组装操作日志
            OperateLog operateLog = new OperateLog();
            operateLog.setOperateEmpId(CurrentHolder.getCurrentId());
            operateLog.setOperateTime(LocalDateTime.now());
            operateLog.setClassName(joinPoint.getTarget().getClass().getName());
            operateLog.setMethodName(joinPoint.getSignature().getName());
            operateLog.setMethodParams(MaxLenUtils.truncate(Arrays.toString(joinPoint.getArgs())));
            operateLog.setReturnValue(MaxLenUtils.truncate(returnValue));
            operateLog.setCostTime(end - begin);

            //5. 异步保存日志（失败也不影响业务）
            try {
                logService.save(operateLog);
            } catch (Exception ex) {
                log.error("保存操作日志失败：{}", ex.getMessage());
            }
        }

        return result;
    }
}