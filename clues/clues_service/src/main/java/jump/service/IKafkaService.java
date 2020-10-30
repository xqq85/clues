package jump.service;

import jump.domain.Orders;

public interface IKafkaService {
    public boolean productMessage(String name,String orders);
}
