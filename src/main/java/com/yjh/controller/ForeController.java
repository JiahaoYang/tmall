package com.yjh.controller;

import com.github.pagehelper.PageHelper;
import com.yjh.comparator.*;
import com.yjh.pojo.*;
import com.yjh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("")
public class ForeController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("foreHome")
    public String home(Model model) {
        List<Category> categories = categoryService.list();
        productService.fill(categories);
        productService.fillByRow(categories);
        model.addAttribute("cs", categories);
        return "fore/home";
    }

    @RequestMapping("foreRegister")
    public String register(Model model, User user) {
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);

        boolean exist = userService.isExist(name);
        if (exist) {
            model.addAttribute("msg", "用户名被占用");
            model.addAttribute("user", null);
            return "fore/register";
        }
        userService.add(user);
        return "redirect:registerSuccessPage";
    }

    @RequestMapping("foreLogin")
    public String login(String name, String password, HttpSession session, Model model) {
        name = HtmlUtils.htmlEscape(name);
        password = HtmlUtils.htmlEscape(password);
        User user = userService.get(name, password);
        if (user != null) {
            session.setAttribute("user", user);
            int carttotalCount = orderItemService.cartTotalCount(user.getId());
            session.setAttribute("cartTotalItemNumber", carttotalCount);
            return "redirect:foreHome";
        }
        model.addAttribute("msg", "用户名或密码错误");
        return "fore/login";
    }

    @RequestMapping("foreLogout")
    public String logout(HttpSession session) {
        if (session.getAttribute("user") != null)
            session.setAttribute("user", null);
        return "redirect:foreHome";
    }

    @RequestMapping("foreProduct")
    public String product(int pid, Model model) {
        Product product = productService.get(pid);
        List<ProductImage> singleImages = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> detailImages = productImageService.list(pid, ProductImageService.type_detail);
        product.setProductSingleImages(singleImages);
        product.setProductDetailImages(detailImages);

        List<PropertyValue> propertyValues = propertyValueService.list(pid);
        List<Review> reviews = reviewService.list(pid);
        productService.setSaleAndReview(product);

        model.addAttribute("p", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("pvs", propertyValues);

        return "fore/product";
    }

    @RequestMapping("foreCheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null)
            return "success";
        return "fail";
    }

    @RequestMapping("foreLoginAjax")
    @ResponseBody
    public String loginAjax(String name, String password, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        password = HtmlUtils.htmlEscape(password);
        User user = userService.get(name, password);
        if (user != null) {
            session.setAttribute("user", user);
            int carttotalCount = orderItemService.cartTotalCount(user.getId());
            session.setAttribute("cartTotalItemNumber", carttotalCount);
            return "success";
        }
        return "fail";
    }

    @RequestMapping("foreCategory")
    public String category(Integer cid, String sort, Model model) {
        Category category = categoryService.get(cid);
        List<Product> products = productService.list(cid);
        productService.setSaleAndReview(products);
        if (sort != null) {
            switch (sort) {
                case "all":
                    Collections.sort(products, new ProductAllComparator());
                    break;
                case "review":
                    Collections.sort(products, new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(products, new ProductDateComparator());
                    break;
                case "price":
                    Collections.sort(products, new ProductPriceComparator());
                    break;
                case "saleCount":
                    Collections.sort(products, new ProductSaleCountComparator());
                    break;
            }
        }
        category.setProducts(products);
        model.addAttribute("c", category);
        return "fore/category";
    }

    @RequestMapping("foreSearch")
    public String search(String keyword, Model model) {
        PageHelper.offsetPage(0, 20);
        List<Product> products = productService.search(keyword);
        model.addAttribute("ps", products);
        return "fore/searchResult";
    }

    /**
     * 立即购买:查找购物车中的订单项(orderItem存在但未对应任何order)
     * 查找该商品是否已被添加进购物车(uid和pid都相同)
     */
    @RequestMapping("foreBuyone")
    public String buyone(HttpSession session, int pid, int num) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        boolean found = false;
        int id = 0;
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getPid() == pid) {
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                id = orderItem.getId();
                found = true;
                break;
            }
        }
        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPid(pid);
            orderItem.setUid(user.getId());
            orderItem.setNumber(num);
            orderItemService.add(orderItem);
            id = orderItem.getId();
        }
        return "redirect:foreBuy?oiid=" + id;
    }

    /**
     * 结算页面
     *
     * @param oiid 购物车页面跳转过来会有多个订单项
     */
    @RequestMapping("foreBuy")
    public String buy(String[] oiid, HttpSession session, Model model) {
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for (String oid : oiid) {
            OrderItem orderItem = orderItemService.get(Integer.parseInt(oid));
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            orderItems.add(orderItem);
        }
        session.setAttribute("ois", orderItems);    //购物车, 添加数据到HttpSession作用域
        model.addAttribute("total", total); //添加数据到HttpServletRequest作用域

        return "fore/buy";
    }

    @RequestMapping("foreAddCart")
    @ResponseBody
    public String addCart(HttpSession session, int pid, int num) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        boolean found = false;
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getPid() == pid) {
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                found = true;
                break;
            }
        }
        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPid(pid);
            orderItem.setUid(user.getId());
            orderItem.setNumber(num);
            orderItemService.add(orderItem);
        }
        return "success";
    }

    @RequestMapping("foreCart")
    public String cart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        model.addAttribute("ois", orderItems);
        return "fore/cart";
    }

    @RequestMapping("foreChangeOrderItem")
    @ResponseBody
    public String updateOrderItem(int pid, Integer num, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            OrderItem orderItem = orderItemService.get(user, pid);
            if (orderItem != null) {
                orderItem.setNumber(num);
                orderItemService.update(orderItem);
                return "success";
            }
        }
        return "fail";
    }

    @RequestMapping("foreDeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(int oiid) {
        if (orderItemService.delete(oiid) < 1)
            return "fail";
        return "success";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("foreCreateOrder")
    public String createOrder(Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("ois");
        String orderCode = new Random().nextInt(1000) + "-" + LocalDate.now();
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        order.setStatus(OrderService.waitPay);
        float totalPrice = orderService.add(order, orderItems);
        return "redirect:foreAlipay?oid=" + order.getId() + "&total=" + totalPrice;
    }

    @RequestMapping("forePayed")
    public String payed(int oid, Model model) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("o", order);
        return "fore/payed";
    }

    @RequestMapping("foreBought")
    public String bought(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.list(user.getId(), OrderService.delete);
        orderItemService.fill(orders);
        model.addAttribute("os", orders);
        return "fore/bought";
    }

    @RequestMapping("foreConfirmPay")
    public String confirmPay(int oid, Model model) {
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        model.addAttribute("o", order);
        return "fore/confirmPay";
    }

    @RequestMapping("foreOrderConfirmed")
    public String orderConfirmed(int oid) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.waitReview);
        o.setConfirmDate(new Date());
        orderService.update(o);
        return "fore/orderConfirmed";
    }

    @RequestMapping("foreDeleteOrder")
    @ResponseBody
    public String deleteOrder(int oid){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.delete);
        orderService.update(o);
        return "success";
    }

    @RequestMapping("foreReview")
    public String review(int oid, Model model) {
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        Product product = order.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(product.getId());
        productService.setSaleAndReview(product);
        model.addAttribute("p", product);
        model.addAttribute("o", order);
        model.addAttribute("reviews", reviews);
        return "fore/review";
    }

    @RequestMapping("foreDoReview")
    public String doReview(int pid, int oid, HttpSession session, String content) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.finish);
        orderService.update(order);

        Product product = productService.get(pid);
        content = HtmlUtils.htmlEscape(content);
        User user = (User) session.getAttribute("user");

        Review review = new Review();
        review.setContent(content);
        review.setCreateDate(new Date());
        review.setPid(pid);
        review.setUid(user.getId());
        reviewService.add(review);

        return "redirect:foreReview?oid=" + oid + "&showonly=true";
    }
}
