package com.wxy.controller;

import com.wxy.domain.User;
import com.wxy.service.HrmService;
import com.wxy.util.common.HrmConstants;
import com.wxy.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    /**
     * 自动注入UserService
     */
    @Autowired
    @Qualifier("hrmService")
    private HrmService hrmService;

    /**
     * 处理登录请求
     * @param loginname 登录名
     * @param password 密码
     * @return 跳转的视图
     */
    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("loginname") String loginname, @RequestParam("password") String password, HttpSession session, ModelAndView mv){
        User user = hrmService.login(loginname, password);
        if (user != null){
            session.setAttribute(HrmConstants.USER_SESSION, user);
            mv.setViewName("redirect:/main");
        }else {
            mv.addObject("message", "登录名或密码错误！");
            mv.setViewName("redirect:/loginForm");
        }
        return mv;
    }

    /**
     * 处理查询请求
     * @param pageIndex 请求的是第几页
     * @param user 模糊查询参数
     * @param model
     */
    @RequestMapping("/user/selectUser")
    public String selectUser(Integer pageIndex, @ModelAttribute User user, Model model){
        PageModel pageModel = new PageModel();
        if (pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        /**查询用户信息*/
        List<User> users = hrmService.findUser(user, pageModel);
        model.addAttribute("users", users);
        model.addAttribute("pageModel", pageModel);
        return "user/user";
    }

    /**
     * 处理删除用户请求
     * @param ids 需要删除的id
     * @param mv
     */
    @RequestMapping("/user/removeUser")
    public ModelAndView removeUser(String ids, ModelAndView mv){
        //分解id字符串
        String[] idArray = ids.split(",");
        for (String id : idArray){
            hrmService.removeUserById(Integer.parseInt(id));
        }
        mv.setViewName("redirect:/user/selectUser");
        return mv;
    }

    /**
     * 处理修改用户请求
     * @param flag 1表示跳转到修改页面，2表示执行修改操作
     * @param user 修改的对象
     * @param mv
     */
    @RequestMapping("/user/updateUser")
    public ModelAndView updateUser(String flag, @ModelAttribute User user, ModelAndView mv){
        if (flag.equals("1")){
            User target = hrmService.findUserById(user.getId());
            mv.addObject("user", target);
            mv.setViewName("user/showUpdateUser");
        }else {
            hrmService.modifyUser(user);
            mv.setViewName("redirect:/user/selectUser");
        }
        return mv;
    }

    /**
     * 处理添加请求
     * @param flag 1表示跳转到添加页面，2表示执行添加操作
     * @param user 添加的对象
     * @param mv
     */
    @RequestMapping("/user/addUser")
    public ModelAndView addUser(String flag, @ModelAttribute User user, ModelAndView mv){
        if (flag.equals("1")){
            mv.setViewName("user/showAddUser");
        }else {
            hrmService.addUser(user);
            mv.setViewName("redirect:/user/selectUser");
        }
        return mv;
    }
}
