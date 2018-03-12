package com.yjh.interceptor;

import com.yjh.pojo.OrderItem;
import com.yjh.pojo.User;
import com.yjh.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.*;
import java.util.List;

@Component
public class OtherInterceptor implements HandlerInterceptor{
    @Autowired
    private OrderItemService orderItemService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int totalNumber = 0;
        if (user != null) {
            List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
                for (OrderItem orderItem : orderItems)
                    totalNumber += orderItem.getNumber();
        }
        session.setAttribute("cartTotalItemNumber", totalNumber);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
