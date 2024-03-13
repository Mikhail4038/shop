package com.keiko.notificationservice.configuration.consumer;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.keiko.notificationservice.entity.SimpleEmail;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class KafkaConsumerConfig {

    private final JsonDeserializer jsonDeserializer;

    //@Autowired
    public KafkaConsumerConfig (JsonDeserializer jsonDeserializer) {
        this.jsonDeserializer = jsonDeserializer;
    }

    //@Bean
    public ConsumerFactory<String, SimpleEmail> consumerFactory () {
        Map<String, Object> configProps = new HashMap<> ();
        configProps.put (ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put (ConsumerConfig.GROUP_ID_CONFIG, "my-group-id");
        configProps.put (ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put (ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<> (configProps);
    }

    //@Bean
    public ConcurrentKafkaListenerContainerFactory<String, SimpleEmail> kafkaListenerContainerFactory () {
        ConcurrentKafkaListenerContainerFactory<String, SimpleEmail> factory
                = new ConcurrentKafkaListenerContainerFactory<> ();
        factory.setBatchListener (false);
        factory.setMessageConverter (new StringJsonMessageConverter ());
        factory.setConsumerFactory (consumerFactory ());
        return factory;
    }
}
