FROM isxcode/nginx-and-jdk:0.0.1

COPY ./star-dist/target/spark-star-1.2.0-bin/spark-star-1.2.0 /opt/spark-star
COPY ./star-website/build /usr/share/nginx/html

ENV STAR_HOME=/opt/spark-star
ENV HADOOP_HOME=/opt/hadoop
ENV HADOOP_CONF_DIR=/opt/hadoop/etc/hadoop
ENV YARN_CONF_DIR=/opt/hadoop/etc/hadoop
ENV SPARK_HOME=/opt/spark

EXPOSE 30156
EXPOSE 80

COPY ./default.conf /etc/nginx/http.d/

RUN echo 'nohup java -jar /opt/spark-star/lib/star-server.jar >> /opt/spark-star/log/star-server.log 2>&1 & \
          nginx -g "daemon off;"' >> /start.sh

CMD [ "sh","/start.sh" ]

# docker build -t isxcode/spark-star:0.0.1 -f ./Dockerfile  .
# docker run --restart=always --name spark-star -p 8889:80 -p 30156:30156 -v /opt/homebrew/Cellar/hadoop/3.3.4/libexec:/opt/hadoop -v /opt/homebrew/opt/apache-spark/libexec:/opt/spark --add-host ispong-mac.local:198.18.1.108 -d isxcode/spark-star:0.0.1

