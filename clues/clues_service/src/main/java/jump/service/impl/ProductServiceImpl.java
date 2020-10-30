package jump.service.impl;

import com.github.pagehelper.PageHelper;
import jump.dao.IProductDao;
import jump.domain.Product;
import jump.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public Product selectById(int productId) throws Exception {
        return productDao.selectById(productId);
    }

    @Override
    public void updateById(Product product) throws Exception {
        productDao.updateById(product);
    }

    @Override
    public Product findById(int id) throws Exception {
        return productDao.selectById(id);
    }

    @Override
    public List<Product> findAll(int page, int size) throws Exception{
        PageHelper.startPage(page, size);
        return productDao.findAll();
    }
}
