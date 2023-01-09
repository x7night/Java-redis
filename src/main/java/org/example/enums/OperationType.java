package org.example.enums;

/**
 * 命令类型
 */
public enum OperationType {
    /**
     * 读命令, 不修改数据库
     */
    R,
    /**
     *写命令, 可能会修改数据库
     */
    W,
    /**
     * 管理命令
     */
    A,
    ;
}
