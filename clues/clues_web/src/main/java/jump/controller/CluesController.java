package jump.controller;


import com.github.pagehelper.PageInfo;
import jump.domain.Clients;
import jump.domain.Clues;
import jump.domain.SendEMailVo;
import jump.service.IClientsService;
import jump.service.ICluesService;
import jump.service.IFileUploadService;
import jump.service.RedisService;
import jump.utils.common.CommonResult;
import jump.utils.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/clues")
public class CluesController {

    @Autowired
    private ICluesService cluesService;

    @Autowired
    private IClientsService clientsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private IFileUploadService fileUploadService;

    //导入线索
    @RequestMapping(value = "/importExcel.do", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public @ResponseBody CommonResult importExcel(@RequestParam("file") MultipartFile multipartfile) throws Exception {
        File file = FileUtil.convertMultipartFileToFile(multipartfile);
        if (file==null){
            return CommonResult.failed("导入失败！");
        }
        int i = fileUploadService.importExcelClues(file);
        if (i > 0) {
            CommonResult result = CommonResult.success();
            result.setData(i);
            System.out.println("输出！");
            return result;
        } else {
            return CommonResult.failed("导入失败！");
        }
    }

    //查询所有线索
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "4") Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        for (GrantedAuthority authority:user.getAuthorities()) {
            System.out.println(authority.getAuthority());
            if(authority.getAuthority().equals("ROLE_ADMIN")){
                List<Clues> cluesList = cluesService.findAll(page, size);
                //PageInfo就是一个分页Bean
                PageInfo pageInfo = new PageInfo(cluesList);
                mv.addObject("pageInfo", pageInfo);
                mv.setViewName("clues-list");
                return mv;
            }
        }
        List<Clues> cluesList = cluesService.findAllByUsername(page, size,user.getUsername());
        //PageInfo就是一个分页Bean
        PageInfo pageInfo = new PageInfo(cluesList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("clues-list");
        return mv;
    }

    //添加线索
    @RequestMapping("/save.do")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String addCluesToClues(@Valid @ModelAttribute(value="clues")Clues clues, BindingResult result) throws Exception{
        if(result.hasErrors()){
            return "clues-add";
        }else {
            cluesService.addClueToClues(clues);
            return "redirect:findAll.do";
        }
    }

    //根据Id查找线索
    @RequestMapping("/selectCluesById.do")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView selectCluesById(@RequestParam(name = "id", required = true) int cluesId) throws Exception {
        ModelAndView mv = new ModelAndView();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //根据线索id改变线索
        Clues clues = cluesService.selectCluesById(cluesId);
        if(user.getUsername().equals(clues.getEntryNumber())){
            System.out.println(clues);
            mv.addObject("clues",clues);
            mv.setViewName("clues-change");
            return mv;
        }else {
            mv.addObject("errorReason","您无权更改此条线索！");
            mv.setViewName("loss");
            return mv;
        }
    }

    //更改线索
    @RequestMapping("/changeClues.do")
    public String changeClues(@Valid @ModelAttribute(value="clues")Clues clues, BindingResult result) throws Exception {
        //改变线索
        if(result.hasErrors() && result.getErrorCount() > 1){
            return "clues-change";
        }else {
            System.out.println(clues);
            cluesService.changeById(clues);
            return "redirect:findAll.do";
        }
    }

    //根据线索id转化线索
    @RequestMapping("/transformCluesById.do")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String transformCluesById(@RequestParam(name = "id", required = true) int cluesId, RedirectAttributes attr) throws Exception {
        //根据Id查找线索
        Clues clues = cluesService.selectCluesById(cluesId);
        //检查clues中是否转化是否未0
        if(clues.getTransform() == 0){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!user.getUsername().equals(clues.getEntryNumber())){
                attr.addFlashAttribute("errorReason","您无权转化");
                return "redirect:/orders/loss.do";
            }
            clues.setTrackNumber(user.getUsername());
            //复制clues到clients
            Clients client = new Clients();
            BeanUtils.copyProperties(clues, client);
            client.setClientName(clues.getCluesName());
            client.setClientDesc(clues.getCluesDesc());
            System.out.println(client);
            clues.setTransform(1);
            cluesService.updateCluesTransform(clues);
            clientsService.insert(client);
            return "redirect:findAll.do";
        }else {
            attr.addFlashAttribute("errorReason","已转化，无需再次转化");
            return "redirect:/orders/loss.do";
        }
    }

//    //跳转到转化失败页面
//    @RequestMapping("/transformFailue.do")
//    public ModelAndView transformFailue() throws Exception {
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("transformFailue");
//        return mv;
//    }

    //将邮箱消息发送到Redis
    @RequestMapping("/pushEMail.do")
    public ModelAndView pushEMail(SendEMailVo sendEMailVo) throws Exception {
        ModelAndView mv = new ModelAndView();

        List<String> list = sendEMailVo.getEmail();

        list.set(0,list.get(0).substring(1,list.get(0).length()));
        list.set(list.size()-1,list.get(list.size()-1).substring(0,list.get(list.size()-1).length()-1));
        System.out.println(list);
        System.out.println(sendEMailVo.getEmail());
        System.out.println(sendEMailVo.getSubject());
        System.out.println(sendEMailVo.getContent());
        try {
            redisService.pushList(sendEMailVo);
        }catch (Exception e){
            e.printStackTrace();
            mv.addObject("errorReason","邮箱消息发送失败！");
            mv.setViewName("loss");
            return mv;
        }
        mv.addObject("errorReason","邮箱消息发送成功！");
        mv.setViewName("loss");
        return mv;
    }

    //编写邮箱内容
    @RequestMapping("/eMailContext.do")
    public ModelAndView eMailContext(@RequestParam(name = "email", required = true) List<String> email) throws Exception {
        ModelAndView mv = new ModelAndView();

        System.out.println(email.get(0));
        SendEMailVo sendSmsVo = new SendEMailVo();
        sendSmsVo.setEmail(email);
        mv.addObject("sendSmsVo",sendSmsVo);
        mv.setViewName("email-context");
        return mv;
    }
}