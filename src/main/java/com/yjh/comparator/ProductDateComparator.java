package com.yjh.comparator;

import com.yjh.pojo.Product;

import java.util.Comparator;

public class ProductDateComparator implements Comparator<Product>{

    @Override
    public int compare(Product o1, Product o2) {
        return o1.getCreateDate().compareTo(o2.getCreateDate());
    }


}
