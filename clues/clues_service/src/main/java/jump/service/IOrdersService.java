package jump.service;

import jump.domain.Orders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IOrdersService {


    List<Orders> findAll(int page, int size) throws Exception;

    Orders findById(int ordersId) throws Exception;

    void save(Orders orders) throws Exception;

    List<String> selectOrderNumber() throws Exception;

    List<Orders> findAllByUsername(Integer page, Integer size, String username) throws Exception;

    void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
