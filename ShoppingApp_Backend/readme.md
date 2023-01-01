### Kafka:

### Start Zookeeper
##### .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

### Start Kafka
#### .\bin\windows\kafka-server-start.bat .\config\server.properties

### Consume
#### .\bin\windows\kafka-console-consumer.bat --topic shopping --from-beginning --bootstrap-server localhost:9092
