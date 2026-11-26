# Maven环境打包
mvn package -f common/common/pom.xml
# 将jar文件添加为本地依赖项
mvn install:install-file -Dfile=common/common/target/common-0.0.1.jar -DgroupId=com.jpmsoft.jpm -DartifactId=common -Dversion=0.0.1 -Dpackaging=jar