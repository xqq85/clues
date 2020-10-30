package jump.service.impl;

import com.alibaba.fastjson.JSONObject;
import jump.domain.Orders;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

public class KafkaListenerServiceImpl implements MessageListener<String, String> {
    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        String order = record.value();
        if(!order.equals("")){
            Orders orders = JSONObject.parseObject(order, Orders.class);
            System.out.println(orders);
            System.out.println("你好111111111111111111111111111");
        }
    }
}
