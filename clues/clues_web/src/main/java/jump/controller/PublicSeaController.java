package jump.controller;

import com.github.pagehelper.PageInfo;
import jump.dao.ICluesDao;
import jump.domain.Clients;
import jump.domain.Clues;
import jump.domain.PublicSea;
import jump.service.*;
import jump.utils.common.CommonResult;
import jump.utils.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/publicSea")
public class PublicSeaController {

    @Autowired
    private IPublicSeaCluesService publicSeaCluesService;

    @Autowired
    private IClientsService clientsService;

    @Autowired
    private ICluesDao cluesDao;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private IUserService userService;

    //公海中查找所有线索
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "4") Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<PublicSea> publicSeas = publicSeaCluesService.findAll(page, size);
        //PageInfo就是一个分页Bean
        PageInfo pageInfo = new PageInfo(publicSeas);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("publicSea-list");
        return mv;
    }

    //添加线索
    @RequestMapping("/save.do")
    public String addCluesToClues(@Valid @ModelAttribute(value="publicSea") PublicSea publicSea, BindingResult result) throws Exception{
        if(result.hasErrors()){
            return "publicSea-add";
        }else {
            publicSeaCluesService.addPublicSeaToPublicSea(publicSea);
            return "redirect:findAll.do";
        }
    }

    //导入线索
    @RequestMapping(value = "/importExcel.do", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult importExcel(@RequestParam("file") MultipartFile multipartfile) throws Exception {
        File file = FileUtil.convertMultipartFileToFile(multipartfile);
        if (file==null){
            return CommonResult.failed("导入失败！");
        }
        int i = fileUploadService.importExcelPublicSea(file);
        if (i > 0) {
            CommonResult result = CommonResult.success();
            result.setData(i);
            System.out.println("输出！");
            return result;
        } else {
            return CommonResult.failed("导入失败！");
        }
    }

    //根据Id查找公海线索
    @RequestMapping("/selectPublicSeaById.do")
    public ModelAndView selectClientsById(@RequestParam(name = "id", required = true) int publicSeaId) throws Exception {
        ModelAndView mv = new ModelAndView();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //根据线索id改变线索
        PublicSea publicSea = publicSeaCluesService.selectPublicSeaById(publicSeaId);
        if(user.getUsername().equals(publicSea.getEntryNumber())){
            //根据客户改变客户
            mv.addObject("publicSea",publicSea);
            mv.setViewName("publicSea-change1");
            return mv;
        }else {
            mv.addObject("errorReason","您无权更改此条线索！");
            mv.setViewName("loss");
            return mv;
        }
    }

    //更改客户
    @RequestMapping("/changePublicSea.do")
    public String changePublicSea(PublicSea publicSea) throws Exception {
        //改变线索
        PublicSea publicSea1 = publicSeaCluesService.selectPublicSeaById(publicSea.getId());
        publicSea1.setMobile(publicSea.getMobile());
        publicSea1.setEmail(publicSea.getEmail());
        publicSea1.setClientName(publicSea.getClientName());
        publicSea1.setAddress(publicSea.getAddress());
        publicSea1.setClientDesc(publicSea.getClientDesc());
        publicSeaCluesService.updateById(publicSea1);
        return "redirect:findAll.do";
    }

    //查找跟踪人员号
    @RequestMapping("/findTrack.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findTrack(@RequestParam(name = "id", required = true) int publicSeaId) throws Exception {
        ModelAndView mv = new ModelAndView();

        //查看该线索状态
        PublicSea publicSea1 = publicSeaCluesService.selectPublicSeaById(publicSeaId);

        if(publicSea1.getTransform() == 0){
            List<String> list = userService.findTrackNumber();
            System.out.println(list);
            mv.addObject("id",publicSeaId);
            mv.addObject("lists",list);
            mv.setViewName("publicSea-transform");
            return mv;
        }else {
            mv.addObject("errorReason","已转化，无需再次转化");
            mv.setViewName("loss");
            return mv;
        }
    }

    //根据Id转化公海线索
    @RequestMapping("/transformClientsById.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String transformClientsById(@RequestParam(name = "id", required = true) int publicSeaId, @RequestParam(name = "track", required = true) String track, RedirectAttributes attr) throws Exception {
        ModelAndView mv = new ModelAndView();

        //将公海线索状态改为转化
        PublicSea publicSea1 = publicSeaCluesService.selectPublicSeaById(publicSeaId);

        if(publicSea1.getTransform() == 0){
            List<String> trackNumberList = publicSeaCluesService.selectTrackNumberByclientName(publicSea1.getClientName());
            if(trackNumberList.contains(track)){
                attr.addFlashAttribute("errorReason","该人员已跟丢，无法再次转化，您可以寻找其他人员");
                return "redirect:/orders/loss.do";
            }else {
                Clues clues = new Clues();
                BeanUtils.copyProperties(publicSea1,clues);
                clues.setTrackNumber("000");
                clues.setCluesName(publicSea1.getClientName());
                clues.setCluesDesc(publicSea1.getClientDesc());
                clues.setEntryNumber(track);
                System.out.println(clues);
                publicSea1.setTransform(1);
                publicSeaCluesService.updateById(publicSea1);

                //在客户表中添加相关人员的记录
                cluesDao.save(clues);
                return "redirect:findAll.do";
            }
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
//
//    //跳转到无法转化页面
//    @RequestMapping("/cantTransform.do")
//    public ModelAndView cantTransform() throws Exception {
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("cantTransform");
//        return mv;
//    }
}
