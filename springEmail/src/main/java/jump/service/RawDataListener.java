package jump.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jump.domain.Orders;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RawDataListener {

    @KafkaListener(topics = {"${kafka.consumer.topic}"})
    public void listen(ConsumerRecord<String,String> record) throws IOException {
        String order = record.value();
        if(!order.equals("")){
            Orders orders = JSONObject.parseObject(order, Orders.class);
            System.out.println(orders);
            System.out.println("你好1111111111111111111");
        }
    }

}
