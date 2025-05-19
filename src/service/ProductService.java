/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ProductDAO;
import entity.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RGB
 */
public class ProductService implements ProductDAO {

    public ProductService() {
    }

    public List<Product> getAllData() {
        return getAllProducts();
    }
    
    public boolean addData(Product p){
        return addProduct(p);
    }
    
    public boolean updateData(Product p){
        return  updateProduct(p);
    }
    
    public boolean deleteData(int id){
        return deleteProduct(id);
    }
}
