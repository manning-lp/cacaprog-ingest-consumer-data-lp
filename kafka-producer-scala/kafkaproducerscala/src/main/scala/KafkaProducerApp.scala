import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, ProducerConfig}
import java.util.Properties

object KafkaProducerApp {
  def main(args: Array[String]): Unit = {
    val props = new Properties()

    // Configure the Kafka producer
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092") // Replace with your Kafka server
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

    // Create the Kafka producer
    val producer = new KafkaProducer[String, String](props)

    // Create a producer record with a dummy event
    val record = new ProducerRecord[String, String]("testea-topic", "key", "Hello, Kafka!")

    // Send the record
    producer.send(record)

    // Close the producer
    producer.close()

    println("Message sent to Kafka")
  }
}

