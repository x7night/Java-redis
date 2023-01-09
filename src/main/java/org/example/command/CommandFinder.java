package org.example.command;

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

    //    private static final HashMap<String, RedisCommand> commandMap = new HashMap<>();
//    static {
//
//    }

    public static RedisCommand find(String operationName){
        switch (operationName.toLowerCase()){
            case "select":
                return SELECT;
            case "set":
                return STRING_SET;
            case "get":
                return STRING_GET;
            case "lset":
                return LIST_SET;
            case "lget":
                return LIST_GET;
            case "client":
                return CLIENT;
            case "config":
                return CONFIG;
            case "ping":
                return PING;
            case "expire":
                return EXPIRE;
            default:
                return UNSUPPORTED;
        }
    }
}
