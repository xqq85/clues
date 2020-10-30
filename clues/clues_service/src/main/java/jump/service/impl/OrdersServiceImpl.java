package jump.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jump.dao.IOrdersDao;
import jump.domain.Orders;
import jump.service.IClientsService;
import jump.service.IOrdersService;
import jump.service.IProductService;
import jump.utils.PoiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private IOrdersDao ordersDao;

    @Override
    public List<Orders> findAll(int page, int size) throws Exception {

        //参数pageNum 是页码值   参数pageSize 代表是每页显示条数
        PageHelper.startPage(page, size);
        return ordersDao.findAll();
    }

    @Override
    public Orders findById(int ordersId) throws Exception{
        return ordersDao.findById(ordersId);
    }

    @Override
    public void save(Orders orders) throws Exception {
        ordersDao.insert(orders);
    }

    @Override
    public List<String> selectOrderNumber() throws Exception {
        return ordersDao.selectOrderNumber();
    }

    @Override
    public List<Orders> findAllByUsername(Integer page, Integer size, String username) throws Exception {
        //参数pageNum 是页码值   参数pageSize 代表是每页显示条数
        PageHelper.startPage(page, size);
        return ordersDao.findAllByUsername(username);
    }

    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] headers = new String[]{"id","orderNumber","productNumber","productName","cNumber","clientName","orderTime","payType","orderStatus","orderDesc","trackNumber"};
        List<Orders> ordersList = null;
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (GrantedAuthority authority:user.getAuthorities()) {
            if(authority.getAuthority().equals("ROLE_ADMIN")){
                ordersList = ordersDao.findAll();
            }
        }
        if(ordersList == null){
            ordersList = ordersDao.findAllByUsername(user.getUsername());
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Orders order:ordersList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",String.valueOf(order.getId()));
            map.put("orderNumber",order.getOrderNumber());
            map.put("productNumber",order.getProductNumber());
            map.put("productName",order.getProductName());
            map.put("cNumber",order.getcNumber());
            map.put("clientName",order.getClientName());
            map.put("orderTime",order.getOrderTimeStr());
            map.put("payType",order.getPayTypeStr());
            map.put("orderStatus",order.getOrderStatusStr());
            map.put("orderDesc",order.getOrderDesc());
            map.put("trackNumber",order.getTrackNumber());
            list.add(map);
            System.out.println(list);
        }
        PoiUtil.exportFile("订单数据导出", headers, headers, null, null, list, request, response);
    }
}
