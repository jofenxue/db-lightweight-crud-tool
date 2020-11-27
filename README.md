# system-dbpatch-tool
轻量级的数据库crud操作工具。配置文件dbsqls.xml中配置数据库配置文件路径、用户相关信息及数据操作语句。工具会根据配置的语句依次进行执行，所有操作在一个事务内进行，失败则回滚。

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