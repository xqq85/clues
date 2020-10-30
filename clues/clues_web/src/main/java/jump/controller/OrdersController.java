package jump.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import jump.domain.Clients;
import jump.domain.Clues;
import jump.domain.Orders;
import jump.service.IClientsService;
import jump.service.IKafkaService;
import jump.service.IOrdersService;
import jump.service.IProductService;
import jump.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    @Autowired
    private IClientsService clientsService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IKafkaService kafkaService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "4") Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        for (GrantedAuthority authority:user.getAuthorities()) {
            System.out.println(authority.getAuthority());
            if(authority.getAuthority().equals("ROLE_ADMIN")){
                List<Orders> ordersList = ordersService.findAll(page, size);
                //PageInfo就是一个分页Bean
                PageInfo pageInfo = new PageInfo(ordersList);
                mv.addObject("pageInfo", pageInfo);
                mv.setViewName("orders-page-list");
                return mv;
            }
        }
        System.out.println("你好");
        List<Orders> ordersList = ordersService.findAllByUsername(page, size,user.getUsername());
        System.out.println(ordersList);
        //PageInfo就是一个分页Bean
        PageInfo pageInfo = new PageInfo(ordersList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("orders-page-list");
        return mv;
    }

    //查找订单
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id", required = true) int ordersId) throws Exception {
        ModelAndView mv = new ModelAndView();
        Orders orders = ordersService.findById(ordersId);
        mv.addObject("orders",orders);
        mv.setViewName("orders-show");
        return mv;
    }

    //订单添加
    @RequestMapping("/save.do")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String save(@ModelAttribute Orders orders, RedirectAttributes attr) throws Exception {
        //System.out.println(orders);
        //查找客户
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Clients clients = clientsService.selectClientsByNumberAndTrackNumber(orders.getcNumber(),user.getUsername());
        //System.out.println(clients);
        if(clients == null || clients.getcNumber() == null){
            //跳转到无此客户页面
            attr.addFlashAttribute("errorReason","客户不存在");
            return "redirect:loss.do";
        }else {
            //生成订单号
            String randomString = null;
            List<String> list = ordersService.selectOrderNumber();
            while(true){
                randomString = RandomString.randomString("OH",7);
                if(!list.contains(randomString)){
                    break;
                }
            }
            orders.setOrderNumber(randomString);
            orders.setClientName(clients.getClientName());
            orders.setOrderTime(new Date());
            orders.setTrackNumber(user.getUsername());
            if(kafkaService.productMessage("订单", JSON.toJSONString(orders))){
                ordersService.save(orders);
                //跳转到订单成功页面
                attr.addFlashAttribute("errorReason","订单成功！");
                return "redirect:loss.do";
            }else {
                //跳转到订单失败页面
                attr.addFlashAttribute("errorReason","订单失败！");
                return "redirect:loss.do";
            }
        }
    }

    @RequestMapping(value="/download_excel.do")

    //获取url链接上的参数
    public @ResponseBody String download_excel(HttpServletRequest request, HttpServletResponse response) throws Exception{

        try{
            ordersService.exportExcel(request,response);
            return "导出信息成功！";
        } catch(Exception e){
            e.printStackTrace();
            return "导出信息失败！";
        }
    }


    //跳转到错误页面
    @RequestMapping("/loss.do")
    public ModelAndView loss() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("loss");
        return mv;
    }
}
