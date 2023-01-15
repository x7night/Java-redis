# Java-redis
使用java语言仿照redis编写的内存key-value数据库, 用于学习redis的基本原理

目前支持使用redis-cli进行连接以及下列命令:
- ping
- select 
- set 
- get 
- lset
- lget
- hset
- hgetall
- sadd
- smembers
- zadd
- zrange(only support: "zrange myzset 0 1" or "zrange myzset 0 1 byscore")
- expire
- del

