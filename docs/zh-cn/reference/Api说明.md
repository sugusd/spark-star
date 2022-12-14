### - 执行SparkSql

> `http://localhost:8080/execute?sql=select * from ispong_db.users` 

```java
public StarResponse execute(@RequestParam String sql) {

    return starTemplate.build().sql(sql).execute();
}
```

```json
{
  "code": "200",
  "msg": "提交成功",
  "data": {
    "applicationId": "application_1671005804173_0001"
  }
}
```

### -查询任务状态

> `http://localhost:8080/getStatus?applicationId=application_1671005804173_0001`

```java
public StarResponse getStatus(@RequestParam String applicationId) {

    return starTemplate.build().applicationId(applicationId).getStatus();
}
```

```json
{
  "code": "200",
  "msg": "获取任务状态成功",
  "data": {
    "appFinalStatus": "SUCCEEDED",
    "appState": "FINISHED"
  }
}
```

### - 查看运行日志

> `http://localhost:8080/getLog?applicationId=application_1671005804173_0001`

```java
public StarResponse getLog(@RequestParam String applicationId) {

    return starTemplate.build().applicationId(applicationId).getLog();
}
```

```json
{
  "code": "200",
  "msg": "获取作业日志成功",
  "data": {
    "logList": [
      "2022-12-14 16:17:02,226 INFO util.SignalUtils: Registering signal handler for TERM",
      "2022-12-14 16:17:02,228 INFO util.SignalUtils: Registering signal handler for HUP",
      "2022-12-14 16:17:02,228 INFO util.SignalUtils: Registering signal handler for INT",
      "2022-12-14 16:17:02,594 INFO spark.SecurityManager: Changing view acls to: ispong",
      "2022-12-14 16:17:02,595 INFO spark.SecurityManager: Changing modify acls to: ispong",
      "2022-12-14 16:17:02,595 INFO spark.SecurityManager: Changing view acls groups to: ",
      "2022-12-14 16:17:02,596 INFO spark.SecurityManager: Changing modify acls groups to: ",
      "2022-12-14 16:17:02,596 INFO spark.SecurityManager: SecurityManager: authentication disabled; ui acls disabled; users  with view permissions: Set(ispong); groups with view permissions: Set(); users  with modify permissions: Set(ispong); groups with modify permissions: Set()",
      "2022-12-14 16:17:02,695 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable",
      "2022-12-14 16:17:02,850 INFO yarn.ApplicationMaster: ApplicationAttemptId: appattempt_1671005804173_0001_000001",
      "2022-12-14 16:17:02,879 INFO yarn.ApplicationMaster: Starting the user application in a separate Thread",
      "2022-12-14 16:17:02,887 INFO yarn.ApplicationMaster: Waiting for spark context initialization...",
      "2022-12-14 16:17:03,236 INFO conf.HiveConf: Found configuration file null",
      "2022-12-14 16:17:03,448 INFO spark.SparkContext: Running Spark version 3.1.1",
      "2022-12-14 16:17:03,489 INFO resource.ResourceUtils: ==============================================================",
      "2022-12-14 16:17:03,489 INFO resource.ResourceUtils: No custom resources configured for spark.driver.",
      "2022-12-14 16:17:03,489 INFO resource.ResourceUtils: ==============================================================",
      "2022-12-14 16:17:03,490 INFO spark.SparkContext: Submitted application: spark-star",
      "2022-12-14 16:17:03,512 INFO resource.ResourceProfile: Default ResourceProfile created, executor resources: Map(cores -> name: cores, amount: 1, script: , vendor: , memory -> name: memory, amount: 2048, script: , vendor: , offHeap -> name: offHeap, amount: 0, script: , vendor: ), task resources: Map(cpus -> name: cpus, amount: 1.0)",
      "2022-12-14 16:17:03,520 INFO resource.ResourceProfile: Limiting resource is cpus at 1 tasks per executor",
      "2022-12-14 16:17:03,523 INFO resource.ResourceProfileManager: Added ResourceProfile id: 0",
      "2022-12-14 16:17:03,565 INFO spark.SecurityManager: Changing view acls to: ispong",
      "2022-12-14 16:17:03,565 INFO spark.SecurityManager: Changing modify acls to: ispong",
      "2022-12-14 16:17:03,565 INFO spark.SecurityManager: Changing view acls groups to: ",
      "2022-12-14 16:17:03,565 INFO spark.SecurityManager: Changing modify acls groups to: ",
      "2022-12-14 16:17:03,566 INFO spark.SecurityManager: SecurityManager: authentication disabled; ui acls disabled; users  with view permissions: Set(ispong); groups with view permissions: Set(); users  with modify permissions: Set(ispong); groups with modify permissions: Set()",
      "2022-12-14 16:17:03,762 INFO util.Utils: Successfully started service 'sparkDriver' on port 41324.",
      "2022-12-14 16:17:03,786 INFO spark.SparkEnv: Registering MapOutputTracker",
      "2022-12-14 16:17:03,816 INFO spark.SparkEnv: Registering BlockManagerMaster",
      "2022-12-14 16:17:03,840 INFO storage.BlockManagerMasterEndpoint: Using org.apache.spark.storage.DefaultTopologyMapper for getting topology information",
      "2022-12-14 16:17:03,841 INFO storage.BlockManagerMasterEndpoint: BlockManagerMasterEndpoint up",
      "2022-12-14 16:17:03,876 INFO spark.SparkEnv: Registering BlockManagerMasterHeartbeat",
      "2022-12-14 16:17:03,896 INFO storage.DiskBlockManager: Created local directory at /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1671005804173_0001/blockmgr-45c5bb3d-67d4-4dc2-9188-09297df54fcf",
      "2022-12-14 16:17:03,979 INFO memory.MemoryStore: MemoryStore started with capacity 366.3 MiB",
      "2022-12-14 16:17:04,023 INFO spark.SparkEnv: Registering OutputCommitCoordinator",
      "2022-12-14 16:17:04,118 INFO util.log: Logging initialized @2685ms to org.sparkproject.jetty.util.log.Slf4jLog",
      "2022-12-14 16:17:04,185 INFO server.Server: jetty-9.4.36.v20210114; built: 2021-01-14T16:44:28.689Z; git: 238ec6997c7806b055319a6d11f8ae7564adc0de; jvm 1.8.0_352-b08",
      "2022-12-14 16:17:04,207 INFO server.Server: Started @2775ms",
      "2022-12-14 16:17:04,240 INFO server.AbstractConnector: Started ServerConnector@6e7eb224{HTTP/1.1, (http/1.1)}{0.0.0.0:39279}",
      "2022-12-14 16:17:04,240 INFO util.Utils: Successfully started service 'SparkUI' on port 39279.",
      "2022-12-14 16:17:04,242 INFO ui.ServerInfo: Adding filter to /jobs: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,272 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@8ffcc43{/jobs,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,275 INFO ui.ServerInfo: Adding filter to /jobs/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,276 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@5b2f3cab{/jobs/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,276 INFO ui.ServerInfo: Adding filter to /jobs/job: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,277 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@43ec8b80{/jobs/job,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,277 INFO ui.ServerInfo: Adding filter to /jobs/job/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,279 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@26b7b272{/jobs/job/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,279 INFO ui.ServerInfo: Adding filter to /stages: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,280 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@789b2867{/stages,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,281 INFO ui.ServerInfo: Adding filter to /stages/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,282 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@af5a573{/stages/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,282 INFO ui.ServerInfo: Adding filter to /stages/stage: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,283 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@4e3c20e7{/stages/stage,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,283 INFO ui.ServerInfo: Adding filter to /stages/stage/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,285 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@3a1e756e{/stages/stage/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,286 INFO ui.ServerInfo: Adding filter to /stages/pool: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,287 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@93a6800{/stages/pool,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,287 INFO ui.ServerInfo: Adding filter to /stages/pool/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,288 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@1237ee91{/stages/pool/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,288 INFO ui.ServerInfo: Adding filter to /storage: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,289 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@784f8405{/storage,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,290 INFO ui.ServerInfo: Adding filter to /storage/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,291 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@761fb97a{/storage/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,291 INFO ui.ServerInfo: Adding filter to /storage/rdd: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,292 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@2ac98c9a{/storage/rdd,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,292 INFO ui.ServerInfo: Adding filter to /storage/rdd/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,293 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@c43e085{/storage/rdd/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,294 INFO ui.ServerInfo: Adding filter to /environment: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,295 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@1657250d{/environment,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,295 INFO ui.ServerInfo: Adding filter to /environment/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,296 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@15faf54a{/environment/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,297 INFO ui.ServerInfo: Adding filter to /executors: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,298 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@70a57e6a{/executors,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,298 INFO ui.ServerInfo: Adding filter to /executors/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,299 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@1f0db32d{/executors/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,300 INFO ui.ServerInfo: Adding filter to /executors/threadDump: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,300 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@6e01b9b7{/executors/threadDump,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,301 INFO ui.ServerInfo: Adding filter to /executors/threadDump/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,302 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@4d099514{/executors/threadDump/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,302 INFO ui.ServerInfo: Adding filter to /static: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,314 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@5aa800d3{/static,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,315 INFO ui.ServerInfo: Adding filter to /: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,316 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@525b6c11{/,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,316 INFO ui.ServerInfo: Adding filter to /api: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,318 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@2791e734{/api,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,319 INFO ui.ServerInfo: Adding filter to /jobs/job/kill: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,320 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@7c70a5de{/jobs/job/kill,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,320 INFO ui.ServerInfo: Adding filter to /stages/stage/kill: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,321 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@18ee392e{/stages/stage/kill,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:04,324 INFO ui.SparkUI: Bound SparkUI to 0.0.0.0, and started at http://isxcode:39279",
      "2022-12-14 16:17:04,394 INFO cluster.YarnClusterScheduler: Created YarnClusterScheduler",
      "2022-12-14 16:17:04,496 INFO util.Utils: Successfully started service 'org.apache.spark.network.netty.NettyBlockTransferService' on port 42952.",
      "2022-12-14 16:17:04,496 INFO netty.NettyBlockTransferService: Server created on isxcode:42952",
      "2022-12-14 16:17:04,497 INFO storage.BlockManager: Using org.apache.spark.storage.RandomBlockReplicationPolicy for block replication policy",
      "2022-12-14 16:17:04,508 INFO storage.BlockManagerMaster: Registering BlockManager BlockManagerId(driver, isxcode, 42952, None)",
      "2022-12-14 16:17:04,512 INFO storage.BlockManagerMasterEndpoint: Registering block manager isxcode:42952 with 366.3 MiB RAM, BlockManagerId(driver, isxcode, 42952, None)",
      "2022-12-14 16:17:04,515 INFO storage.BlockManagerMaster: Registered BlockManager BlockManagerId(driver, isxcode, 42952, None)",
      "2022-12-14 16:17:04,517 INFO storage.BlockManager: Initialized BlockManager: BlockManagerId(driver, isxcode, 42952, None)",
      "2022-12-14 16:17:04,748 INFO ui.ServerInfo: Adding filter to /metrics/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:04,750 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@37e2faae{/metrics/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:05,340 INFO history.SingleEventLogFileWriter: Logging events to hdfs://isxcode:9000/spark/logs/application_1671005804173_0001_1.inprogress",
      "2022-12-14 16:17:05,718 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8030",
      "2022-12-14 16:17:05,836 INFO yarn.YarnRMClient: Registering the ApplicationMaster",
      "2022-12-14 16:17:05,931 INFO yarn.ApplicationMaster: Preparing Local resources",
      "2022-12-14 16:17:06,027 INFO yarn.ApplicationMaster: ",
      "===============================================================================",
      "Default YARN executor launch context:",
      "  env:",
      "    CLASSPATH -> {{PWD}}<CPS>{{PWD}}/__spark_conf__<CPS>{{PWD}}/__spark_libs__/*<CPS>{{PWD}}/__spark_conf__/__hadoop_conf__",
      "    SPARK_YARN_STAGING_DIR -> hdfs://isxcode:9000/user/ispong/.sparkStaging/application_1671005804173_0001",
      "    SPARK_USER -> ispong",
      "",
      "  command:",
      "    {{JAVA_HOME}}/bin/java \\ ",
      "      -server \\ ",
      "      -Xmx2048m \\ ",
      "      -Djava.io.tmpdir={{PWD}}/tmp \\ ",
      "      '-Dspark.history.ui.port=18080' \\ ",
      "      '-Dspark.driver.port=41324' \\ ",
      "      '-Dspark.ui.port=0' \\ ",
      "      -Dspark.yarn.app.container.log.dir=<LOG_DIR> \\ ",
      "      -XX:OnOutOfMemoryError='kill %p' \\ ",
      "      org.apache.spark.executor.YarnCoarseGrainedExecutorBackend \\ ",
      "      --driver-url \\ ",
      "      spark://CoarseGrainedScheduler@isxcode:41324 \\ ",
      "      --executor-id \\ ",
      "      <executorId> \\ ",
      "      --hostname \\ ",
      "      <hostname> \\ ",
      "      --cores \\ ",
      "      1 \\ ",
      "      --app-id \\ ",
      "      application_1671005804173_0001 \\ ",
      "      --resourceProfileId \\ ",
      "      0 \\ ",
      "      --user-class-path \\ ",
      "      file:$PWD/__app__.jar \\ ",
      "      --user-class-path \\ ",
      "      file:$PWD/fastjson2-2.0.20.jar \\ ",
      "      --user-class-path \\ ",
      "      file:$PWD/star-server.jar \\ ",
      "      --user-class-path \\ ",
      "      file:$PWD/fastjson2-extension-2.0.20.jar \\ ",
      "      --user-class-path \\ ",
      "      file:$PWD/fastjson-2.0.20.jar \\ ",
      "      --user-class-path \\ ",
      "      file:$PWD/star-api-1.2.0.jar \\ ",
      "      --user-class-path \\ ",
      "      file:$PWD/log4j-api-2.12.1.jar \\ ",
      "      1><LOG_DIR>/stdout \\ ",
      "      2><LOG_DIR>/stderr",
      "",
      "  resources:",
      "    star-server.jar -> resource { scheme: \"hdfs\" host: \"isxcode\" port: 9000 file: \"/user/ispong/.sparkStaging/application_1671005804173_0001/star-server.jar\" } size: 120254309 timestamp: 1671005816874 type: FILE visibility: PRIVATE",
      "    log4j-api-2.12.1.jar -> resource { scheme: \"hdfs\" host: \"isxcode\" port: 9000 file: \"/user/ispong/.sparkStaging/application_1671005804173_0001/log4j-api-2.12.1.jar\" } size: 276771 timestamp: 1671005816948 type: FILE visibility: PRIVATE",
      "    fastjson2-2.0.20.jar -> resource { scheme: \"hdfs\" host: \"isxcode\" port: 9000 file: \"/user/ispong/.sparkStaging/application_1671005804173_0001/fastjson2-2.0.20.jar\" } size: 1669522 timestamp: 1671005816199 type: FILE visibility: PRIVATE",
      "    fastjson2-extension-2.0.20.jar -> resource { scheme: \"hdfs\" host: \"isxcode\" port: 9000 file: \"/user/ispong/.sparkStaging/application_1671005804173_0001/fastjson2-extension-2.0.20.jar\" } size: 90096 timestamp: 1671005816891 type: FILE visibility: PRIVATE",
      "    __app__.jar -> resource { scheme: \"hdfs\" host: \"isxcode\" port: 9000 file: \"/user/ispong/.sparkStaging/application_1671005804173_0001/star-sql-plugin.jar\" } size: 4954 timestamp: 1671005816168 type: FILE visibility: PRIVATE",
      "    __spark_conf__ -> resource { scheme: \"hdfs\" host: \"isxcode\" port: 9000 file: \"/user/ispong/.sparkStaging/application_1671005804173_0001/__spark_conf__.zip\" } size: 258689 timestamp: 1671005817113 type: ARCHIVE visibility: PRIVATE",
      "    star-api-1.2.0.jar -> resource { scheme: \"hdfs\" host: \"isxcode\" port: 9000 file: \"/user/ispong/.sparkStaging/application_1671005804173_0001/star-api-1.2.0.jar\" } size: 26230 timestamp: 1671005816931 type: FILE visibility: PRIVATE",
      "    fastjson-2.0.20.jar -> resource { scheme: \"hdfs\" host: \"isxcode\" port: 9000 file: \"/user/ispong/.sparkStaging/application_1671005804173_0001/fastjson-2.0.20.jar\" } size: 184539 timestamp: 1671005816910 type: FILE visibility: PRIVATE",
      "    __spark_libs__ -> resource { scheme: \"hdfs\" host: \"isxcode\" port: 9000 file: \"/user/ispong/.sparkStaging/application_1671005804173_0001/__spark_libs__797374420015421838.zip\" } size: 228498058 timestamp: 1671005816089 type: ARCHIVE visibility: PRIVATE",
      "",
      "===============================================================================",
      "2022-12-14 16:17:06,042 INFO yarn.YarnAllocator: Resource profile 0 doesn't exist, adding it",
      "2022-12-14 16:17:06,065 INFO conf.Configuration: resource-types.xml not found",
      "2022-12-14 16:17:06,065 INFO resource.ResourceUtils: Unable to find 'resource-types.xml'.",
      "2022-12-14 16:17:06,071 INFO cluster.YarnSchedulerBackend$YarnSchedulerEndpoint: ApplicationMaster registered as NettyRpcEndpointRef(spark://YarnAM@isxcode:41324)",
      "2022-12-14 16:17:06,081 INFO yarn.YarnAllocator: Will request 2 executor container(s) for  ResourceProfile Id: 0, each with 1 core(s) and 2432 MB memory. with custom resources: <memory:2432, vCores:1>",
      "2022-12-14 16:17:06,104 INFO yarn.YarnAllocator: Submitted 2 unlocalized container requests.",
      "2022-12-14 16:17:06,152 INFO yarn.ApplicationMaster: Started progress reporter thread with (heartbeat : 3000, initial allocation : 200) intervals",
      "2022-12-14 16:17:06,378 INFO yarn.YarnAllocator: Launching container container_1671005804173_0001_01_000002 on host isxcode for executor with ID 1 for ResourceProfile Id 0",
      "2022-12-14 16:17:06,381 INFO yarn.YarnAllocator: Received 1 containers from YARN, launching executors on 1 of them.",
      "2022-12-14 16:17:07,608 INFO yarn.YarnAllocator: Launching container container_1671005804173_0001_01_000003 on host isxcode for executor with ID 2 for ResourceProfile Id 0",
      "2022-12-14 16:17:07,610 INFO yarn.YarnAllocator: Received 1 containers from YARN, launching executors on 1 of them.",
      "2022-12-14 16:17:09,253 INFO cluster.YarnSchedulerBackend$YarnDriverEndpoint: Registered executor NettyRpcEndpointRef(spark-client://Executor) (172.25.28.235:47998) with ID 1,  ResourceProfileId 0",
      "2022-12-14 16:17:09,446 INFO storage.BlockManagerMasterEndpoint: Registering block manager isxcode:38237 with 912.3 MiB RAM, BlockManagerId(1, isxcode, 38237, None)",
      "2022-12-14 16:17:10,529 INFO cluster.YarnSchedulerBackend$YarnDriverEndpoint: Registered executor NettyRpcEndpointRef(spark-client://Executor) (172.25.28.235:48008) with ID 2,  ResourceProfileId 0",
      "2022-12-14 16:17:10,589 INFO cluster.YarnClusterSchedulerBackend: SchedulerBackend is ready for scheduling beginning after reached minRegisteredResourcesRatio: 0.8",
      "2022-12-14 16:17:10,589 INFO cluster.YarnClusterScheduler: YarnClusterScheduler.postStartHook done",
      "2022-12-14 16:17:10,619 INFO internal.SharedState: Setting hive.metastore.warehouse.dir ('null') to the value of spark.sql.warehouse.dir ('file:/tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1671005804173_0001/container_1671005804173_0001_01_000001/spark-warehouse').",
      "2022-12-14 16:17:10,620 INFO internal.SharedState: Warehouse path is 'file:/tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1671005804173_0001/container_1671005804173_0001_01_000001/spark-warehouse'.",
      "2022-12-14 16:17:10,635 INFO ui.ServerInfo: Adding filter to /SQL: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:10,636 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@76d70d6{/SQL,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:10,636 INFO ui.ServerInfo: Adding filter to /SQL/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:10,637 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@2eab33f9{/SQL/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:10,638 INFO ui.ServerInfo: Adding filter to /SQL/execution: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:10,640 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@4811ac84{/SQL/execution,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:10,640 INFO ui.ServerInfo: Adding filter to /SQL/execution/json: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:10,642 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@5f551f9a{/SQL/execution/json,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:10,643 INFO ui.ServerInfo: Adding filter to /static/sql: org.apache.hadoop.yarn.server.webproxy.amfilter.AmIpFilter",
      "2022-12-14 16:17:10,644 INFO handler.ContextHandler: Started o.s.j.s.ServletContextHandler@6dc39d1a{/static/sql,null,AVAILABLE,@Spark}",
      "2022-12-14 16:17:10,664 INFO storage.BlockManagerMasterEndpoint: Registering block manager isxcode:40379 with 912.3 MiB RAM, BlockManagerId(2, isxcode, 40379, None)",
      "2022-12-14 16:17:11,722 INFO hive.HiveUtils: Initializing HiveMetastoreConnection version 2.3.7 using Spark classes.",
      "2022-12-14 16:17:11,819 INFO conf.HiveConf: Found configuration file null",
      "2022-12-14 16:17:12,138 INFO session.SessionState: Created local directory: /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1671005804173_0001/container_1671005804173_0001_01_000001/tmp/ispong",
      "2022-12-14 16:17:12,156 INFO session.SessionState: Created HDFS directory: /tmp/hive/ispong/e38b9ee9-cce8-428a-917e-83abe228b61c",
      "2022-12-14 16:17:12,163 INFO session.SessionState: Created local directory: /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1671005804173_0001/container_1671005804173_0001_01_000001/tmp/ispong/e38b9ee9-cce8-428a-917e-83abe228b61c",
      "2022-12-14 16:17:12,167 INFO session.SessionState: Created HDFS directory: /tmp/hive/ispong/e38b9ee9-cce8-428a-917e-83abe228b61c/_tmp_space.db",
      "2022-12-14 16:17:12,176 INFO client.HiveClientImpl: Warehouse location for Hive client (version 2.3.7) is file:/tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1671005804173_0001/container_1671005804173_0001_01_000001/spark-warehouse",
      "2022-12-14 16:17:12,654 INFO hive.metastore: Trying to connect to metastore with URI thrift://localhost:9083",
      "2022-12-14 16:17:12,680 INFO hive.metastore: Opened a connection to metastore, current connections: 1",
      "2022-12-14 16:17:12,697 INFO hive.metastore: Connected to metastore.",
      "2022-12-14 16:17:15,301 INFO memory.MemoryStore: Block broadcast_0 stored as values in memory (estimated size 329.1 KiB, free 366.0 MiB)",
      "2022-12-14 16:17:15,360 INFO memory.MemoryStore: Block broadcast_0_piece0 stored as bytes in memory (estimated size 30.9 KiB, free 365.9 MiB)",
      "2022-12-14 16:17:15,362 INFO storage.BlockManagerInfo: Added broadcast_0_piece0 in memory on isxcode:42952 (size: 30.9 KiB, free: 366.3 MiB)",
      "2022-12-14 16:17:15,364 INFO spark.SparkContext: Created broadcast 0 from ",
      "2022-12-14 16:17:15,584 INFO mapred.FileInputFormat: Total input files to process : 1",
      "2022-12-14 16:17:15,604 INFO spark.SparkContext: Starting job: collectAsList at Execute.java:53",
      "2022-12-14 16:17:15,620 INFO scheduler.DAGScheduler: Got job 0 (collectAsList at Execute.java:53) with 1 output partitions",
      "2022-12-14 16:17:15,620 INFO scheduler.DAGScheduler: Final stage: ResultStage 0 (collectAsList at Execute.java:53)",
      "2022-12-14 16:17:15,621 INFO scheduler.DAGScheduler: Parents of final stage: List()",
      "2022-12-14 16:17:15,622 INFO scheduler.DAGScheduler: Missing parents: List()",
      "2022-12-14 16:17:15,627 INFO scheduler.DAGScheduler: Submitting ResultStage 0 (MapPartitionsRDD[4] at collectAsList at Execute.java:53), which has no missing parents",
      "2022-12-14 16:17:15,706 INFO memory.MemoryStore: Block broadcast_1 stored as values in memory (estimated size 9.7 KiB, free 365.9 MiB)",
      "2022-12-14 16:17:15,709 INFO memory.MemoryStore: Block broadcast_1_piece0 stored as bytes in memory (estimated size 5.0 KiB, free 365.9 MiB)",
      "2022-12-14 16:17:15,711 INFO storage.BlockManagerInfo: Added broadcast_1_piece0 in memory on isxcode:42952 (size: 5.0 KiB, free: 366.3 MiB)",
      "2022-12-14 16:17:15,712 INFO spark.SparkContext: Created broadcast 1 from broadcast at DAGScheduler.scala:1383",
      "2022-12-14 16:17:15,726 INFO scheduler.DAGScheduler: Submitting 1 missing tasks from ResultStage 0 (MapPartitionsRDD[4] at collectAsList at Execute.java:53) (first 15 tasks are for partitions Vector(0))",
      "2022-12-14 16:17:15,726 INFO cluster.YarnClusterScheduler: Adding task set 0.0 with 1 tasks resource profile 0",
      "2022-12-14 16:17:15,767 INFO scheduler.TaskSetManager: Starting task 0.0 in stage 0.0 (TID 0) (isxcode, executor 1, partition 0, NODE_LOCAL, 4525 bytes) taskResourceAssignments Map()",
      "2022-12-14 16:17:16,082 INFO storage.BlockManagerInfo: Added broadcast_1_piece0 in memory on isxcode:38237 (size: 5.0 KiB, free: 912.3 MiB)",
      "2022-12-14 16:17:16,704 INFO storage.BlockManagerInfo: Added broadcast_0_piece0 in memory on isxcode:38237 (size: 30.9 KiB, free: 912.3 MiB)",
      "2022-12-14 16:17:18,715 INFO scheduler.TaskSetManager: Finished task 0.0 in stage 0.0 (TID 0) in 2959 ms on isxcode (executor 1) (1/1)",
      "2022-12-14 16:17:18,717 INFO cluster.YarnClusterScheduler: Removed TaskSet 0.0, whose tasks have all completed, from pool ",
      "2022-12-14 16:17:18,726 INFO scheduler.DAGScheduler: ResultStage 0 (collectAsList at Execute.java:53) finished in 3.078 s",
      "2022-12-14 16:17:18,730 INFO scheduler.DAGScheduler: Job 0 is finished. Cancelling potential speculative or zombie tasks for this job",
      "2022-12-14 16:17:18,730 INFO cluster.YarnClusterScheduler: Killing all running tasks in stage 0: Stage finished",
      "2022-12-14 16:17:18,733 INFO scheduler.DAGScheduler: Job 0 finished: collectAsList at Execute.java:53, took 3.128000 s",
      "2022-12-14 16:17:18,748 INFO spark.SparkContext: Starting job: collectAsList at Execute.java:53",
      "2022-12-14 16:17:18,749 INFO scheduler.DAGScheduler: Got job 1 (collectAsList at Execute.java:53) with 1 output partitions",
      "2022-12-14 16:17:18,750 INFO scheduler.DAGScheduler: Final stage: ResultStage 1 (collectAsList at Execute.java:53)",
      "2022-12-14 16:17:18,750 INFO scheduler.DAGScheduler: Parents of final stage: List()",
      "2022-12-14 16:17:18,750 INFO scheduler.DAGScheduler: Missing parents: List()",
      "2022-12-14 16:17:18,751 INFO scheduler.DAGScheduler: Submitting ResultStage 1 (MapPartitionsRDD[4] at collectAsList at Execute.java:53), which has no missing parents",
      "2022-12-14 16:17:18,757 INFO memory.MemoryStore: Block broadcast_2 stored as values in memory (estimated size 9.7 KiB, free 365.9 MiB)",
      "2022-12-14 16:17:18,758 INFO memory.MemoryStore: Block broadcast_2_piece0 stored as bytes in memory (estimated size 5.0 KiB, free 365.9 MiB)",
      "2022-12-14 16:17:18,759 INFO storage.BlockManagerInfo: Added broadcast_2_piece0 in memory on isxcode:42952 (size: 5.0 KiB, free: 366.3 MiB)",
      "2022-12-14 16:17:18,759 INFO spark.SparkContext: Created broadcast 2 from broadcast at DAGScheduler.scala:1383",
      "2022-12-14 16:17:18,760 INFO scheduler.DAGScheduler: Submitting 1 missing tasks from ResultStage 1 (MapPartitionsRDD[4] at collectAsList at Execute.java:53) (first 15 tasks are for partitions Vector(1))",
      "2022-12-14 16:17:18,760 INFO cluster.YarnClusterScheduler: Adding task set 1.0 with 1 tasks resource profile 0",
      "2022-12-14 16:17:18,762 INFO scheduler.TaskSetManager: Starting task 0.0 in stage 1.0 (TID 1) (isxcode, executor 1, partition 1, NODE_LOCAL, 4525 bytes) taskResourceAssignments Map()",
      "2022-12-14 16:17:18,793 INFO storage.BlockManagerInfo: Added broadcast_2_piece0 in memory on isxcode:38237 (size: 5.0 KiB, free: 912.3 MiB)",
      "2022-12-14 16:17:18,821 INFO scheduler.TaskSetManager: Finished task 0.0 in stage 1.0 (TID 1) in 59 ms on isxcode (executor 1) (1/1)",
      "2022-12-14 16:17:18,821 INFO cluster.YarnClusterScheduler: Removed TaskSet 1.0, whose tasks have all completed, from pool ",
      "2022-12-14 16:17:18,822 INFO scheduler.DAGScheduler: ResultStage 1 (collectAsList at Execute.java:53) finished in 0.069 s",
      "2022-12-14 16:17:18,823 INFO scheduler.DAGScheduler: Job 1 is finished. Cancelling potential speculative or zombie tasks for this job",
      "2022-12-14 16:17:18,823 INFO cluster.YarnClusterScheduler: Killing all running tasks in stage 1: Stage finished",
      "2022-12-14 16:17:18,825 INFO scheduler.DAGScheduler: Job 1 finished: collectAsList at Execute.java:53, took 0.076747 s",
      "2022-12-14 16:17:19,157 INFO codegen.CodeGenerator: Code generated in 223.809524 ms",
      "2022-12-14 16:17:19,298 INFO server.AbstractConnector: Stopped Spark@6e7eb224{HTTP/1.1, (http/1.1)}{0.0.0.0:0}",
      "2022-12-14 16:17:19,304 INFO ui.SparkUI: Stopped Spark web UI at http://isxcode:39279",
      "2022-12-14 16:17:19,310 INFO cluster.YarnClusterSchedulerBackend: Shutting down all executors",
      "2022-12-14 16:17:19,311 INFO cluster.YarnSchedulerBackend$YarnDriverEndpoint: Asking each executor to shut down",
      "2022-12-14 16:17:19,648 INFO yarn.YarnAllocator: Completed container container_1671005804173_0001_01_000003 on host: isxcode (state: COMPLETE, exit status: 0)",
      "2022-12-14 16:17:19,648 INFO yarn.YarnAllocator: Executor for container container_1671005804173_0001_01_000003 exited because of a YARN event (e.g., preemption) and not because of an error in the running job.",
      "2022-12-14 16:17:19,649 INFO yarn.YarnAllocator: Completed container container_1671005804173_0001_01_000002 on host: isxcode (state: COMPLETE, exit status: 0)",
      "2022-12-14 16:17:19,650 INFO yarn.YarnAllocator: Executor for container container_1671005804173_0001_01_000002 exited because of a YARN event (e.g., preemption) and not because of an error in the running job.",
      "2022-12-14 16:17:19,754 INFO spark.MapOutputTrackerMasterEndpoint: MapOutputTrackerMasterEndpoint stopped!",
      "2022-12-14 16:17:19,763 INFO memory.MemoryStore: MemoryStore cleared",
      "2022-12-14 16:17:19,763 INFO storage.BlockManager: BlockManager stopped",
      "2022-12-14 16:17:19,770 INFO storage.BlockManagerMaster: BlockManagerMaster stopped",
      "2022-12-14 16:17:19,772 INFO scheduler.OutputCommitCoordinator$OutputCommitCoordinatorEndpoint: OutputCommitCoordinator stopped!",
      "2022-12-14 16:17:19,778 INFO spark.SparkContext: Successfully stopped SparkContext",
      "2022-12-14 16:17:19,779 INFO yarn.ApplicationMaster: Final app status: SUCCEEDED, exitCode: 0",
      "2022-12-14 16:17:19,783 INFO yarn.ApplicationMaster: Unregistering ApplicationMaster with SUCCEEDED",
      "2022-12-14 16:17:19,796 INFO impl.AMRMClientImpl: Waiting for application to be successfully unregistered.",
      "2022-12-14 16:17:19,898 INFO yarn.ApplicationMaster: Deleting staging directory hdfs://isxcode:9000/user/ispong/.sparkStaging/application_1671005804173_0001",
      "2022-12-14 16:17:19,904 INFO util.ShutdownHookManager: Shutdown hook called",
      "2022-12-14 16:17:19,904 INFO util.ShutdownHookManager: Deleting directory /tmp/hadoop-ispong/nm-local-dir/usercache/ispong/appcache/application_1671005804173_0001/spark-b8513288-b20c-4e17-be9a-ebe8eb032925"
    ]
  }
}
```

### - 中止作业运行

> `http://localhost:8080/stopJob?applicationId=application_1671005804173_0001`

```java
public StarResponse stopJob(@RequestParam String applicationId) {

    return starTemplate.build().applicationId(applicationId).stopJob();
}
```

```json
{
  "code": "200",
  "msg": "停止作业成功",
  "data": {}
}
```

### - 获取返回数据

> `http://localhost:8080/getData?applicationId=application_1671005804173_0001`

```java
public StarResponse getData(@RequestParam String applicationId) {

    return starTemplate.build().applicationId(applicationId).getData();
}
```

```json
{
  "code": "200",
  "msg": "获取数据成功",
  "data": {
    "columnNames": [
      "[username, age, birth]"
    ],
    "dataList": [
      [
        "ispong",
        "18",
        "2020-12-12"
      ]
    ]
  }
}
```

### - 作业动态资源调整

```java
public StarResponse custom(@RequestParam String sql) {

    // 配置spark运行的环境和资源
    Map<String, String> sparkConfig = new HashMap<>();
    sparkConfig.put("spark.executor.memory", "2g");
    sparkConfig.put("spark.driver.memory", "1g");
    sparkConfig.put("hive.metastore.uris", "thrift://localhost:9083");

    // 配置yarn启动作业的参数，可以配置自定义插件
    YarnJobConfig yarnJobConfig = YarnJobConfig.builder()
        .appName("my app")
        .mainClass("com.isxcode.star.Execute")
        .appResourceName("star-sql-plugin")
        .build();

    return starTemplate.build()
        .sql(sql)
        .yarnJobConfig(yarnJobConfig)
        .sparkConfig(sparkConfig).execute();
}
```


