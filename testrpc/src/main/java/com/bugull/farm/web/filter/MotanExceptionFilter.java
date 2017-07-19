package com.bugull.farm.web.filter;

import com.weibo.api.motan.exception.MotanBizException;
import com.weibo.api.motan.exception.MotanErrorMsg;
import com.weibo.api.motan.filter.Filter;
import com.weibo.api.motan.rpc.Caller;
import com.weibo.api.motan.rpc.Provider;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.Response;

/**
 * Created by JasonMao on 2017/7/18.
 */
public class MotanExceptionFilter implements Filter {

    @Override
    public Response filter(Caller<?> caller, Request request) {
        System.out.println("filter 。。。。");
        if (caller instanceof Provider) {
            Response result = null;

            result = caller.call(request);
            MotanBizException motanException = null;
            try {
                if (result == null || result.getException() != null) {
                    // 给调用接口出现异常进行打点
                    Throwable cause = result.getException();
                    if (cause instanceof MotanBizException) {
                        MotanErrorMsg errorMsg = null;
                        if (cause.getCause() instanceof MotanBizException) {
                            MotanBizException ex = (MotanBizException) cause.getCause();
                            errorMsg = ex.getMotanErrorMsg();
                        } else {
                            errorMsg = new MotanErrorMsg(503, 40001, cause.getCause().getMessage());
                        }
                        motanException = new MotanBizException(errorMsg);
                    } else {
                        MotanErrorMsg errorMsg = new MotanErrorMsg(503, 40001, cause.getMessage());
                        motanException = new MotanBizException(errorMsg);
                    }
                }
            } catch (Exception e) {
            }
            if (motanException != null) {
                throw motanException;
            } else {
                return result;
            }

        }
        return null;
    }
}
