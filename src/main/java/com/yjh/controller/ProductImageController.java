package com.yjh.controller;

import com.yjh.pojo.Product;
import com.yjh.pojo.ProductImage;
import com.yjh.service.ProductImageService;
import com.yjh.service.ProductService;
import com.yjh.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductService productService;

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage pi, HttpSession session, UploadedImageFile uploadedImageFile) {
        productImageService.add(pi);
        String filename = pi.getId() + ".jpg";
        String imageFolder;
        String imageFolderSmall = null;
        String imageFolderMiddle = null;
        if (ProductImageService.type_single.equals(pi.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolderSmall = session.getServletContext().getRealPath("img/productSingleSmall");
            imageFolderMiddle = session.getServletContext().getRealPath("img/productSingleMiddle");
        } else {
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
        }
        File file = new File(imageFolder, filename);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            uploadedImageFile.getImage().transferTo(file);
            BufferedImage image = ImageUtil.change2jpg(file);
            ImageIO.write(image, "jpg", file);

            if (ProductImageService.type_single.equals(pi.getType())) {
                File fSmall = new File(imageFolderSmall, filename);
                File fMiddle = new File(imageFolderMiddle, filename);

                ImageUtil.resizeImage(file, 56, 56, fSmall);
                ImageUtil.resizeImage(file, 217, 190, fMiddle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid=" + pi.getPid();
    }

    @RequestMapping("admin_productImage_delete")
    public String delete(int id, HttpSession session) {
        ProductImage pi = productImageService.get(id);
        String filename = id + ".jpg";
        String imageFolder;
        String imageFolderSmall = null;
        String imageFolderMiddle = null;

        if (ProductImageService.type_single.equals(pi.getType())) {
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolderSmall = session.getServletContext().getRealPath("img/productSingleSmall");
            imageFolderMiddle = session.getServletContext().getRealPath("img/productSingleMiddle");
            File imageFile = new File(imageFolder, filename);
            File f_small = new File(imageFolderSmall, filename);
            File f_middle = new File(imageFolderMiddle, filename);
            imageFile.delete();
            f_small.delete();
            f_middle.delete();
        } else {
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
            File imageFile = new File(imageFolder, filename);
            imageFile.delete();
        }
        productImageService.delete(id);
        return "redirect:admin_productImage_list?pid=" + pi.getPid();
    }

    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model) {
        Product product = productService.get(pid);
        List<ProductImage> piSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> piDetail= productImageService.list(pid, ProductImageService.type_detail);

        model.addAttribute("product", product);
        model.addAttribute("piSingle", piSingle);
        model.addAttribute("piDetail", piDetail);

        return "admin/listProductImage";
    }
}
