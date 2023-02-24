FROM openjdk:8

# 拷贝安装包
COPY ./star-dist/src/main/bin /star-package
COPY ./star-dist/target/spark-star-1.2.0-bin.tar.gz /star-package

# 后端
COPY ./star-backend/target/star-backend-1.2.0.jar /star-backend.jar

VOLUME ["/star-data"]

EXPOSE 8080

RUN echo 'java -jar /star-backend.jar --spring.profiles.active=prod' >> /start.sh

CMD [ "sh","/start.sh" ]

# docker build -t isxcode/spark-star:0.0.1 -f ./Dockerfile  .
# docker run --restart=always --name spark-star -p 30111:8080 -v /Users/ispong/h2:/star-data -d isxcode/spark-star:0.0.1
