package com.chezhibao.bigdata.authorization;

import com.chezhibao.bigdata.common.utils.CookieUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/15.
 */
public class RequestHelper {
    /**
     * 得到request对象
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes==null){
            return null;
        }
        return requestAttributes.getRequest();
    }
    /**
     * 得到response对象
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes==null){
            return null;
        }
        return requestAttributes.getResponse();
    }

    /**
     * 获取cookie中的userId
     * @return
     */
    public static Integer getUserId(){
        Integer userId=null;
        HttpServletRequest request = getRequest();
        if(request!=null){
            String loginUserId = CookieUtils.getCookieValue(request, "LOGIN_USER_ID");
            if (!StringUtils.isEmpty(loginUserId)){
                userId = Integer.parseInt(loginUserId);
            }
        }
        Assert.notNull(userId,"用户ID不能为空！请先登陆！");
        return userId;
    }
    /**
     * 获取cookie中的userId
     * @return
     */
    public static Integer getUserId(HttpServletRequest request){
        Integer userId=null;
        if(request!=null){
            String loginUserId = CookieUtils.getCookieValue(request, "LOGIN_USER_ID");
            if (!StringUtils.isEmpty(loginUserId)){
                userId = Integer.parseInt(loginUserId);
            }
        }
        Assert.notNull(userId,"用户ID不能为空！请先登陆！");
        return userId;
    }
    /**
     * 获取cookie中的userId 并校验
     * @return
     */
    public static Integer getAndVerifyUserId(HttpServletRequest request){
        Integer userId=getUserId(request);
        Assert.notNull(userId,"用户ID不能为空！请先登陆！");
        return userId;
    }
    /**
     * 获取cookie中的userId 并校验
     * @return
     */
    public static Integer getAndVerifyUserId(){
        Integer userId=getUserId();
        Assert.notNull(userId,"用户ID不能为空！请先登陆！");
        return userId;
    }
}