package org.example.operations;

import org.example.compoment.RedisClient;
import org.example.command.RedisCommand;
import org.example.enums.RedisObjectType;
import org.example.enums.OperationType;

public abstract class Operation implements RedisCommand {
    /**
     * 参数数量(包含命令名称)
     */
    int argNum;
    /**
     * 命令类型
     */
    OperationType type;
    /**
     * 操作名称
     */
    String name;
    /**
     * 获取操作数据类型
     * @return 操作数据类型
     */
    public abstract RedisObjectType getDataType();
    /**
     * 获取操作名称
     * @return 操作名
     */
    public String getOperationName(){return name;}

    public abstract boolean beforeExec(RedisClient client);

    public abstract boolean exec(RedisClient client);

    public abstract boolean afterExec(RedisClient client);

    @Override
    public boolean execCommand(RedisClient client) {
        boolean execRes = this.beforeExec(client);
        if (!execRes) {
            return false;
        }
        execRes = this.exec(client);
        if (!execRes) {
            return false;
        }
        execRes = this.afterExec(client);
        if (!execRes) {
            return false;
        }
        return true;
    }
}
