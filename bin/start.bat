@echo on

C:\ProgramData\Oracle\Java\javapath\java.exe -Dfile.encoding=UTF-8 -classpath .;.\lib\* -XX:PermSize=256m -XX:MaxPermSize=512m -Xms1024m -Xmx1024m com.combat.core.Engine
pause