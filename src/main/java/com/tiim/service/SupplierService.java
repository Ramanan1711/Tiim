package com.tiim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiim.dao.SupplierDao;
import com.tiim.model.Supplier;

@Service
public class SupplierService {
	
	@Autowired
	SupplierDao supplierDao;
	
	public String addSupplier(Supplier supplier, int userId)
	{
		return supplierDao.addSupplier(supplier, userId);
	}
	
	public String updateSupplier(Supplier supplier, int userId)
	{
		return supplierDao.updateSupplier(supplier, userId);
	}
	
	public String deleteSupplier(int supplierId, int userId)
	{
		return supplierDao.deleteSupplier(supplierId, userId);
	}
	
	public List<Supplier> getSupplierDetails(String searchSupplier)
	{
		return supplierDao.getSupplierDetails(searchSupplier);
	}
	
	public Supplier getSupplier(int supplierId)
	{
		return supplierDao.getSupplierDetail(supplierId);
	}

	public String changeSupplierStatus(int supplierId)
	{
		return supplierDao.changeSupplierStatus(supplierId);
	}
}
