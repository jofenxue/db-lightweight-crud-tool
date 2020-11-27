# system-dbpatch-tool
轻量级的数据库crud操作工具。配置文件dbsqls.xml中配置数据库配置文件路径、用户相关信息及数据操作语句。工具会根据配置的语句依次进行执行，所有操作在一个事务内进行，失败则回滚。

此项目目前仅支持单线程操作，并发情况下会出现问题; 若需要在高并发情况下适用需要增加数据库连接池、对象动态分配与销毁等改造。

## useage
1. 在dbsqls.xml文件中编辑需要执行的sql语句
2. linux平台执行./start.sh
3. windows平台执行start.bat
4. 运行日志可以在./log/run.log中查看

## xml格式
```xml
<dbsqls>
    <dbconfig>
        <configpath>F:\apache-tomcat-7.0.40-jf\wtpwebapps\platform_framework\WEB-INF\classes\dev\platform.properties</configpath>
        <username>database.connection.username</username>
        <password>database.connection.password</password>
        <dburl>database.connection.url</dburl>
    </dbconfig>
    <sql order="1">
        SELECT DISTINCT FNC_PRD_INFO.* from dual
    </sql>
    <sql order="2">
        CREATE TABLE "VATAX_ADDED_INFO"
        (	"SEQNO" VARCHAR2(36 BYTE),
        "UPDATEDATE" DATE,
        "ORISEQNO" VARCHAR2(36 BYTE)
        )
    </sql>
    <sql order="3">
    3
    </sql>
</dbsqls>
```