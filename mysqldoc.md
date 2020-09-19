mysql
   事务：ACID 原子性，一致性，持久性，隔离性
   事务并发可能遇见的问题：　 　1、脏读：事务A读取了事务B更新的数据，然后B回滚操作，那么A读取到的数据是脏数据
                        　　2、不可重复读：事务 A 多次读取同一数据，事务 B 在事务A多次读取的过程中，对数据作了更新并提交，导致事务A多次读取同一数据时，结果 不一致。
                        　　3、幻读：系统管理员A将数据库中所有学生的成绩从具体分数改为ABCDE等级，但是系统管理员B就在这个时候插入了一条具体分数的记录，当系统管理员A改结束后发现还有一条记录没有改过来，就好像发生了幻觉一样，这就叫幻读。
                            　　小结：不可重复读的和幻读很容易混淆，不可重复读侧重于修改，幻读侧重于新增或删除。解决不可重复读的问题只需锁住满足条件的行，解决幻读需要锁表
                                 mysql事务隔离级别
                              事务隔离级别 	                  脏读 	不可重复读 	幻读
                              读未提交（read-uncommitted） 	    是 	    是 	    是
                              读已提交（read-committed） 	        否   	是   	是
                              可重复读（repeatable-read） 	    否 	    否    	是
                              可串行化（serializable） 	        否   	否   	否
   创建索引的目的：
      1创建唯一索引，保证数据库表中每条数据的唯一性
      2 加快检索数据的速度，这也是创建索引最重要的目的
      3 帮助服务器避免排序和临时表
      4 将随机io变为顺序io
      5 加快表与表之间的连接
     索引的缺点
       1 当对表中的数据进行增删改操作的时候索引也要进行动态的维护。
       2 索引需要占据物理空间
       3 创建维护索引需要时间
      检测是否使用了索引  https://blog.csdn.net/vtopqx/article/details/86504206
              explain sql语句   显示key实际使用的索引
      注意事项
        1 在经常需要搜索的列上，可以加快搜索的速度
        2 在经常使用where语句的列上创建索引可以加快搜索的速度
        3 在经常需要排序的列上创建索引，索引已经排序，这样查询可以利用索引的排序
        4 特大型表开销太大
        5  where子句中不要使用函数，这样会无法使用索引
        6 删除长期不用的索引（MySQL 5.7 可以通过查询 sys 库的 chema_unused_indexes 视图来查询哪些索引从未被使用）
    
  大表优化
      当MySQL单表数据救赎过大时，数据库的curl性能会明显下降，常见优化如下
      1 务必禁止不带任何限制数据范围条件的查询语句，比如查订单历史，时间设置为一个月内
      2 读写分离 主库写，从库读
      3 垂直分区 简单来说垂直拆分就是数据表列的拆分，按照业务逻辑，功能的不同可以进行拆分
      4 水平拆分 水平拆分就是数据表行的拆分（将数据拆分到多个表中，表明不相同），
             1）按照时间来分（user2019，user2020）
             2) hash取模分，如果id是interger类型也可以，对n个表取模
             3） 建立一个单独的表保存id与实际表的对应关系
   主从分离 mysql安装地址 /usr/local/etc/mysql
        1 master将操作记录到binary log（二进制日志文件当中）写入日志文件完成后，master通知引擎提交日志
        2 salve将binary log 拷贝到relay log（中介日志），salve开启一个线程，这个线程在master打开一个连接，读取binary log，写入relay log
        3 salve重做relay log
        总结：主服务器把操作记录到binary log——>从服务器将binary log中的数据同步到relay log（中介日志中）——>从服务器读取中介日志执行同步数据
       安装mysql wget https://mirrors.huaweicloud.com/mysql/Downloads/MySQL-8.0/mysql-8.0.19-linux-glibc2.12-x86_64.tar.xz
                 解压 tar xvf mysql-8.0.19-linux-glibc2.12-x86_64.tar.xz
                 创建用户及用户组 groupadd mysql
                 # 用户 （用户名/密码）useradd -g mysql mysql
                授权chown -R mysql.mysql /usr/local/mysql8.0/ #  或  chown -R mysql .   或   chgrp -R mysql .
                初始化mysql-server ./bin/mysqld --user=mysql --basedir=/usr/local/etc/mysql/mysql1 --datadir=/usr/local/etc/mysql/mysql1/data/ --initialize ; # 亲测

       