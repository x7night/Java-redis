package org.example.command;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import org.example.annotation.Command;
import org.example.operations.PingOperation;
import org.example.operations.SelectOperation;
import org.example.operations.UnsupportedOperation;
import org.example.operations.client.ClientOperation;
import org.example.operations.config.ConfigOperation;
import org.example.operations.expire.ExpireOperation;
import org.example.operations.string.StringGetOperation;
import org.example.operations.string.StringSetOperation;
import org.example.operations.list.ListGetOperation;
import org.example.operations.list.ListSetOperation;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CommandFinder {
    private static final UnsupportedOperation UNSUPPORTED = new UnsupportedOperation();
    private static final SelectOperation SELECT = new SelectOperation();
    private static final StringSetOperation STRING_SET = new StringSetOperation();
    private static final StringGetOperation STRING_GET = new StringGetOperation();
    private static final ListGetOperation LIST_GET = new ListGetOperation();
    private static final ListSetOperation LIST_SET = new ListSetOperation();
    private static final ClientOperation CLIENT = new ClientOperation();
    private static final ConfigOperation CONFIG = new ConfigOperation();
    private static final PingOperation PING= new PingOperation();

    private static final ExpireOperation EXPIRE = new ExpireOperation();

    private static final HashMap<String, RedisCommand> commandMap = new HashMap<>();

    public static void init() {
        String dirStr = "org.example.operations";
        Set<Class<?>> clazzSet = ClassUtil.scanPackage(dirStr);
        for (Class<?> clazz : clazzSet) {
            Command annotation = clazz.getAnnotation(Command.class);
            if (annotation != null) {
                RedisCommand commandInstance;
                try {
                    commandInstance = (RedisCommand)clazz.getDeclaredConstructor().newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                commandMap.put(annotation.value(), commandInstance);
            }
        }

    }

    public static RedisCommand find(String operationName){
        return commandMap.getOrDefault(operationName, commandMap.get("unsupported"));
    }

//    public static RedisCommand find(String operationName){
//        switch (operationName.toLowerCase()){
//            case "select":
//                return SELECT;
//            case "set":
//                return STRING_SET;
//            case "get":
//                return STRING_GET;
//            case "lset":
//                return LIST_SET;
//            case "lget":
//                return LIST_GET;
//            case "client":
//                return CLIENT;
//            case "config":
//                return CONFIG;
//            case "ping":
//                return PING;
//            case "expire":
//                return EXPIRE;
//            default:
//                return UNSUPPORTED;
//        }
//    }

}
