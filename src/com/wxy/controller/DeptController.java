package com.wxy.controller;

import com.wxy.domain.Dept;
import com.wxy.service.HrmService;
import com.wxy.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DeptController {

    @Autowired
    @Qualifier("hrmService")
    private HrmService hrmService;

    @RequestMapping("/dept/selectDept")
    public String selectDept(Integer pageIndex, @ModelAttribute Dept dept, Model model){
        PageModel pageModel = new PageModel();
        if (pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        /**查询*/
        List<Dept> depts = hrmService.findDept(dept, pageModel);
        model.addAttribute("depts", depts);
        model.addAttribute("pageModel", pageModel);
        return "dept/dept";
    }

    @RequestMapping("/dept/removeDept")
    public ModelAndView removeDept(String ids, ModelAndView mv){
        String[] idArray = ids.split(",");
        for (String id : idArray){
            hrmService.removeDeptById(Integer.parseInt(id));
        }
        mv.setViewName("redirect:/dept/selectDept");
        return mv;
    }

    @RequestMapping("/dept/addDept")
    public ModelAndView addDept(String flag, @ModelAttribute Dept dept, ModelAndView mv){
        if (flag.equals("1")){
            mv.setViewName("dept/showAddDept");
        }else {
            hrmService.addDept(dept);
            mv.setViewName("redirect:/dept/selectDept");
        }
        return mv;
    }

    @RequestMapping("/dept/updateDept")
    public ModelAndView updateDept(String flag, @ModelAttribute Dept dept, ModelAndView mv){
        if (flag.equals("1")){
            Dept target = hrmService.findDeptById(dept.getId());
            mv.addObject("dept", target);
            mv.setViewName("dept/showUpdateDept");
        }else {
            hrmService.modifyDept(dept);
            mv.setViewName("redirect:/dept/selectDept");
        }
        return mv;
    }
}
