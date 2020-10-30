package jump.controller;

import com.github.pagehelper.PageInfo;
import jump.domain.Role;
import jump.domain.UserInfo;
import jump.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    //给用户添加角色
    @RequestMapping("/addRoleToUser.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addRoleToUser(@RequestParam(name = "userId", required = true) int userId, @RequestParam(name = "ids", required = true) int[] roleIds) throws Exception {
        userService.addRoleToUser(userId, roleIds);
        return "redirect:findAll.do";
    }

    //查询用户以及用户可以添加的角色
    @RequestMapping("/findUserByIdAndAllRole.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id", required = true) int userid) throws Exception {
        ModelAndView mv = new ModelAndView();
        //1.根据用户id查询用户
        UserInfo userInfo = userService.findById(userid);
        //2.根据用户id查询可以添加的角色
        List<Role> otherRoles = userService.findOtherRoles(userid);
        mv.addObject("user", userInfo);
        mv.addObject("roleList", otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }

    //查询指定id的用户
    @RequestMapping("/findById.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findById(int id) throws Exception {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userService.findById(id);
        mv.addObject("user", userInfo);
        mv.setViewName("user-show1");
        return mv;
    }

    //查询指定id的用户
    @RequestMapping("/findById1.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findById1(int id) throws Exception {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userService.findById(id);
        mv.addObject("user", userInfo);
        mv.setViewName("user-change");
        return mv;
    }

    //用户添加
    @RequestMapping("/save.do")
    //@PreAuthorize("authentication.principal.username == 'NH1905001'")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(@Valid @ModelAttribute(value="userInfo") UserInfo userInfo, BindingResult result) throws Exception {
        if(result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {

                System.out.println("错误消息提示：" + fieldError.getDefaultMessage());
                System.out.println("错误的字段是？" + fieldError.getField());
                System.out.println(fieldError);
                System.out.println("------------------------");
            }
            return "user-add";
        }else {
            userService.save(userInfo);
            return "redirect:findAll.do";
        }
    }

    //查找所有的用户
    @RequestMapping("/findAll.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "4") Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<UserInfo> userList = userService.findAll(page, size);
        //PageInfo就是一个分页Bean
        PageInfo pageInfo = new PageInfo(userList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("user-list");
        return mv;
    }

    //保存用户
    @RequestMapping("/changeUser.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String changeUser(UserInfo userInfo) throws Exception {

        userService.updateById(userInfo);
        return "redirect:findAll.do";
    }

    //删除用户
    @RequestMapping("/deleteUser.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@RequestParam(name = "ids", required = true) int[] ids) throws Exception {
        if(ids!=null && ids.length!=0){
            userService.deleteUsersByIds(ids);
        }
        return "redirect:findAll.do";
    }
}
