//package com.advantage.gateway;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PROXY_KEY;
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_URI_KEY;
//
//@Component
//public class AccountSwaggerCustomZuulFilter extends ZuulFilter
//{
//    @Override
//    public String filterType() {
//        return "pre";
//    }
//
//    @Override
//    public int filterOrder() {
//        return PreDecorationFilter.FILTER_ORDER + 1;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() {
//        RequestContext context = RequestContext.getCurrentContext();
//        String originalRequestPath = context.get(REQUEST_URI_KEY).toString();
//        String serviceId = null;
//        try{
//            serviceId = context.get(PROXY_KEY).toString();
//        } catch (Exception e){
//
//        }
//        if(serviceId.equalsIgnoreCase("swagger") && originalRequestPath.contains("/accountrest/")){
//            String modifiedRequestPath = ((String) originalRequestPath).split("/accountrest")[1];
//            context.put(REQUEST_URI_KEY, modifiedRequestPath);
//        }
//        return null;
//    }
//}
