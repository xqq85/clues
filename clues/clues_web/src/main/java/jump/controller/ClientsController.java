package jump.controller;

import com.github.pagehelper.PageInfo;
import jump.domain.Clients;
import jump.domain.Clues;
import jump.domain.PublicSea;
import jump.service.IClientsService;
import jump.service.IPublicSeaCluesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/clients")
@PreAuthorize("hasRole('ROLE_USER')")
public class ClientsController {

    @Autowired
    private IClientsService clientrsService;

    @Autowired
    private IPublicSeaCluesService publicSeaCluesService;

    //查找所有客户
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "4") Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Clients> clientsList = clientrsService.findAll(page, size);
        //PageInfo就是一个分页Bean
        PageInfo pageInfo = new PageInfo(clientsList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("clients-list");
        return mv;
    }

    //根据Id查找客户
    @RequestMapping("/selectClientsById.do")
    public ModelAndView selectClientsById(@RequestParam(name = "id", required = true) int clientsId) throws Exception {
        ModelAndView mv = new ModelAndView();
        //根据客户改变客户
        Clients clients = clientrsService.selectClientsById(clientsId);
        mv.addObject("clients",clients);
        mv.setViewName("clients-change");
        return mv;
    }

    //更改客户
    @RequestMapping("/changeClients.do")
    public String changeClients(Clients clients) throws Exception {
        //改变线索
        Clients client = clientrsService.selectClientsById(clients.getId());
        client.setMobile(clients.getMobile());
        client.setClientName(clients.getClientName());
        client.setAddress(clients.getAddress());
        client.setClientDesc(clients.getClientDesc());
        client.setEmail(clients.getEmail());
        clientrsService.updateById(client);
        return "redirect:findAll.do";
    }

    //失败描述
    @RequestMapping("/failueDesc.do")
    public ModelAndView failueDesc(@RequestParam(name = "cNumber", required = true) String cNumber) throws Exception {
        ModelAndView mv = new ModelAndView();
        //根据客户Id搜索客户
        Clients clients = clientrsService.selectClientsByNumber(cNumber);
        if(clients.getTransform() == 0){
            mv.addObject("cNumber",cNumber);
            mv.setViewName("publicSea-change");
            return mv;
        }else {
            mv.addObject("errorReason","已跟丢，无需再次丢弃到公海");
            mv.setViewName("loss");
            return mv;
        }
    }

    //改变客户到公海
    @RequestMapping("/transformClients.do")
    public String transformClientsById(@Valid @ModelAttribute("publicSea") PublicSea publicSea, BindingResult result, Model model) throws Exception {

        System.out.println(publicSea);
        if(result.getErrorCount() > 2){
            FieldError failueError = result.getFieldError("failueDesc");
//            System.out.println("错误消息提示：" + failueError.getDefaultMessage());
//            System.out.println("错误的字段是？" + failueError.getField());
//            System.out.println(failueError);
//            System.out.println("------------------------");
            model.addAttribute("failueError",failueError.getDefaultMessage());
            model.addAttribute("failueDesc",publicSea.getFailueDesc());
            model.addAttribute("cNumber",publicSea.getcNumber());
            return "publicSea-change";
        }else {
            //根据客户Id搜索客户
            Clients clients = clientrsService.selectClientsByNumber(publicSea.getcNumber());

            //复制clients到publicSea
            BeanUtils.copyProperties(clients, publicSea);
            System.out.println(publicSea);
            //根据客户Id更新客户
            clients.setTransform(1);
            clientrsService.updateById(clients);
            publicSea.setEntryTime(new Date());
            publicSeaCluesService.insert(publicSea);
            return "redirect:findAll.do";
        }
    }
}