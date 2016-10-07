package com.springapp.mvc.Controller;

import com.springapp.mvc.Dao.InsuranceDao;
import com.springapp.mvc.Model.Insurance;
import com.springapp.mvc.Service.ServiceImpl.InsuranceService;
import com.springapp.mvc.Util.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/25.
 */
@Controller
@RequestMapping("/insurance")
public class InsuranceController {

    @Resource
    private InsuranceService insuranceService;
    @Resource
    private Insurance insurance;

    @RequestMapping("")
    public ModelAndView display(){
        ArrayList<Insurance> arr = insuranceService.display();
        ModelAndView view = new ModelAndView("insurance");
        view.addObject("list", arr);

        Map<String, Integer> map = insuranceService.addUpByOwner(arr);
        view.addObject("addUpByOwner", map);
        return view;
    }

    @RequestMapping("/add")
    public String add(){
        return "insuranceAdd";
    }

    @RequestMapping("/save")
    public String save(HttpServletRequest request){
        insurance.setName(request.getParameter("name"));
        insurance.setAge(new Integer(request.getParameter("age")));
        insurance.setAmount(new Integer(request.getParameter("amount")));
        insurance.setBelongTo(request.getParameter("belongTo"));
        insurance.setCoverage(new Integer(request.getParameter("coverage")));
        insurance.setOwner(request.getParameter("owner"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            insurance.setStart(sdf.parse(request.getParameter("start")));
        }catch (Exception e){
            e.printStackTrace();
        }
        insurance.setType(request.getParameter("type"));
        insurance.setYears(new Integer(request.getParameter("years")));
        insuranceService.save(insurance);
        return "redirect:/insurance";
    }
}
