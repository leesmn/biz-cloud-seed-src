#基础镜像
FROM openjdk:8u212-jre

#设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo Asia/Shanghai > /etc/timezone

#设置java选项
ENV JAVA_OPTS "-Duser.timezone=GMT+08"

#创建jar目录
RUN mkdir -p /app/jar/

#创建配置文件目录
RUN mkdir -p /app/conf/

#创建日志目录
RUN mkdir -p /app/logs/

#启动jar
ENTRYPOINT [ "java", "-jar", "/app/jar/app.jar", "-Dfile.encoding=utf-8" ]

#暴露端口
EXPOSE 41255

#映射日志目录
VOLUME ["/app/logs/"]

#拷贝jar和配置文件
COPY ./target/fm-service.jar /app/jar/app.jar

#RUN chown -R 65534:65534 /app
#USER 65534:65534