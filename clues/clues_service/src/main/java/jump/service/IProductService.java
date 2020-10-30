package jump.service;


import jump.domain.Product;

import java.util.List;

public interface IProductService {

    public List<Product> findAll(int page, int size) throws Exception;

    void save(Product product) throws Exception;

    Product selectById(int productId) throws Exception;

    void updateById(Product product) throws Exception;

    Product findById(int id) throws Exception;
}
