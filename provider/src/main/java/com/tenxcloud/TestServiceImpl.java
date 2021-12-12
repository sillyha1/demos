package com.tenxcloud;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Slf4j
@DubboService(version = "${demo.service.version}", group = "${demo.service.group}")
public class TestServiceImpl implements TestService {
    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public String test(String msg) {
        RpcContext rpcContext = RpcContext.getContext();
        System.out.println(rpcContext.getAttachments());
        log.info(msg);
        if ("500".equals(msg) || "503".equals(msg) || "400".equals(msg)) {
            throw new RuntimeException("mock exception :" + msg);
        }
        return String.format("MESH_SVC_NAME:[%s], MESH_SVC_VERSION:[%s], Provider [%s] : Hello, %s, time: %s ", System.getenv("MESH_SVC_NAME"), System.getenv("MESH_SVC_VERSION"), applicationName, msg, new Date());
    }

    @Override
    public String slowCall(String msg, Long interval) {
        RpcContext rpcContext = RpcContext.getContext();
        System.out.println(rpcContext.getAttachments());
        log.info(msg);
        if ("500".equals(msg) || "503".equals(msg) || "400".equals(msg)) {
            throw new RuntimeException("mock exception :" + msg);
        }
        if (interval > 0) {
            try {
                log.info("执行等待中。。。。！");
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                throw new RuntimeException("test time interval mock exception :" + msg + " 间隔：" + interval);
            }
        }
        return String.format("MESH_SVC_NAME:[%s], MESH_SVC_VERSION:[%s], Provider [%s] : Hello, %s, time: %s ",
                System.getenv("MESH_SVC_NAME"),
                System.getenv("MESH_SVC_VERSION"),
                applicationName, msg, new Date());
    }

}