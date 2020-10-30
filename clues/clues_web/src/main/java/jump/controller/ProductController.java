package jump.controller;

import com.github.pagehelper.PageInfo;
import jump.domain.Orders;
import jump.domain.Product;
import jump.service.IOrdersService;
import jump.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrdersService ordersService;

    //产品添加
    @RequestMapping("/save.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(@Valid @ModelAttribute("product") Product product, BindingResult result) throws Exception {
        if(result.hasErrors()){
            return "product-add";
        }else {
            System.out.println(product);
            productService.save(product);
            return "redirect:findAll.do";
        }
    }

    //查询全部产品
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name="page",required = true,defaultValue = "1")int page,@RequestParam(name="size",required = true,defaultValue = "4")int size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Product> ps = productService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(ps);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("product-list1");
        return mv;
    }
    //根据Id查询产品
    @RequestMapping("/selectById.do")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView selectById(@RequestParam(name = "productId", required = true)int productId) throws Exception {
        ModelAndView mv = new ModelAndView();
        Product product = productService.selectById(productId);
        if(product.getProductStatus() == 0){
            mv.addObject("errorReason", "该订单关闭，无法下单");
            mv.setViewName("loss");
            return mv;
        }else {
            mv.addObject("product", product);
            mv.setViewName("buy-product");
            return mv;
        }
    }

    //根据Id查询产品
    @RequestMapping("/selectById1.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView selectById1(@RequestParam(name = "productId", required = true)int productId) throws Exception {
        ModelAndView mv = new ModelAndView();

        Product product = productService.selectById(productId);
        mv.addObject("product", product);
        mv.setViewName("product-change");
        return mv;
    }

    //编辑产品
    @RequestMapping("/edit.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit(Product product) throws Exception {
        productService.updateById(product);
        return "redirect:findAll.do";
    }

}
