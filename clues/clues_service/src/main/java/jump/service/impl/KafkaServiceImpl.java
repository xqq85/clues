package jump.service.impl;

import jump.domain.Orders;
import jump.service.IKafkaService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class KafkaServiceImpl implements IKafkaService {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public boolean productMessage(String name,String orders) {
        boolean success;
        try{
            kafkaTemplate.sendDefault(name,orders);
            success = true;
        }catch (Exception e){
            e.printStackTrace();
            success = false;
        }
        return success;
    }
}
