package com.tiim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiim.dao.ProductDao;
import com.tiim.model.Product;

@Service
public class ProductService {

	@Autowired
	ProductDao productDao; 
	
	public String addProduct(Product product, int userId)
	{
		return productDao.addProduct(product, userId);
	}
	
	public String updateProduct(Product product, int userId)
	{
		return productDao.updateProduct(product, userId);
	}
	
	public String deleteProduct(int productId, int userId)
	{
		return productDao.deleteProduct(productId, userId);
	}
	
	public List<Product> getProductDetails(String searchProduct)
	{
		return productDao.getProductDetails(searchProduct);
	}
	
	public List<Product> getProductDetailsByDrawingNo(String searchDrawingNo)
	{
		return productDao.getProductDetailsByDrawingNo(searchDrawingNo);
	}
	
	public Product getProduct(int productId)
	{
		return productDao.getProductDetail(productId);
	}
	
	public String changeProductStatus(int productId)
	{
		return productDao.changeProductStatus(productId);
	}
}
