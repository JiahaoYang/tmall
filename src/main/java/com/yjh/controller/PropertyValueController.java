package com.yjh.controller;

import com.yjh.pojo.Product;
import com.yjh.pojo.PropertyValue;
import com.yjh.service.ProductService;
import com.yjh.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyValueController {
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private ProductService productService;

    @RequestMapping("admin_propertyValue_edit")
    public String edit(int pid, Model model) {
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValue> values = propertyValueService.list(product.getId());

        model.addAttribute("product", product);
        model.addAttribute("ptValues", values);
        return "admin/editPropertyValue";
    }

    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue propertyValue) {
        propertyValueService.update(propertyValue);
        return "success";
    }

}
