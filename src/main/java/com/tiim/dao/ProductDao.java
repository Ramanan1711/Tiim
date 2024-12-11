package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.tiim.model.Machine;
import com.tiim.model.Product;
import com.tiim.util.TiimUtil;

@Repository
public class ProductDao {

	@Autowired
	DataSource datasource;
	@Autowired
	TransactionHistoryDao historyDao;
	
	@Autowired
	MessageSource messageSource;
	
	public String addProduct(Product product, int userId)
	{

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		TransactionHistory history = new TransactionHistory();
		try
		{
			if(isProductExists(product.getProductName(), product.getDrawingNo()))
			{
				con = datasource.getConnection();
				pstmt = con.prepareStatement("insert into mst_product(productname, drawingno, strength, mocpunches,mocdies,shape,dimensions,breaklineupper,"
						+ " breaklinelower,lasermarking,hardcromeplating,toolinglife,serviceintervalqty,embosingUpper,embosingLower,isActive,"
						+ " machinetype, typeoftool, dustcapgroove, inspectionreport, dqdocument, moccertificate, ductcapmoccertificate, uom,expiryperiod, minAcceptedQty, uploadedpath,"
						+ " punchSetNo, compForce)"
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1, TiimUtil.ValidateNull(product.getProductName()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(product.getDrawingNo()).trim());
				pstmt.setString(3, TiimUtil.ValidateNull(product.getStrength()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(product.getMocPunches()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(product.getMocDies()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(product.getShape()).trim());
				pstmt.setString(7, TiimUtil.ValidateNull(product.getDimensions()).trim());
				pstmt.setString(8, TiimUtil.ValidateNull(product.getBreaklineUpper()).trim());
				pstmt.setString(9, TiimUtil.ValidateNull(product.getBreaklineLower()).trim());
				pstmt.setString(10, TiimUtil.ValidateNull(product.getLaserMaking()).trim());
				pstmt.setString(11, TiimUtil.ValidateNull(product.getHardChromePlating()).trim());
				pstmt.setString(12, "");
				pstmt.setString(13, TiimUtil.ValidateNull(product.getServiceIntervalQty()).trim());
				pstmt.setString(14, TiimUtil.ValidateNull(product.getEmbosingUpper()).trim());
				pstmt.setString(15, TiimUtil.ValidateNull(product.getEmbosingLower()).trim());
				pstmt.setInt(16, 1);
				pstmt.setString(17, TiimUtil.ValidateNull(product.getMachineType()).trim());
				pstmt.setString(18, TiimUtil.ValidateNull(product.getTypeOfTool()).trim());
				pstmt.setString(19, TiimUtil.ValidateNull(product.getDustCapGroove()).trim());
				pstmt.setString(20, TiimUtil.ValidateNull(product.getInspectionReport()).trim());
				pstmt.setString(21, TiimUtil.ValidateNull(product.getDqDocument()).trim());
				pstmt.setString(22, TiimUtil.ValidateNull(product.getMocCertificate()).trim());
				pstmt.setString(23, TiimUtil.ValidateNull(product.getDuctCapMOCCertificate()).trim());
				pstmt.setString(24, TiimUtil.ValidateNull(product.getUom()).trim());
				pstmt.setInt(25, product.getExpiryPeriod());
				pstmt.setInt(26, product.getAcceptedQty());
				pstmt.setString(27, product.getUploadedPath());
				pstmt.setString(28, product.getPunchSetNo());
				pstmt.setInt(29, product.getCompForce());
				pstmt.executeUpdate();
				msg = "Saved Successfully";
				
				history.setPageName(messageSource.getMessage("product.master", null,null));
				history.setDescription(messageSource.getMessage("product.insert", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			}
			else
			{
				msg = "Already Exists";
			}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when adding the machine master detail in mst_machine table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in machine master detail in mst_machine table : "+ex.getMessage());
			}
		}
		return msg;
	
	}
	
	public String updateProduct(Product product, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			if(isProductExistsUpdate(product.getProductId(), product.getProductName()))
			{
				con = datasource.getConnection(); 
				pstmt = con.prepareStatement("Update mst_product set productname = ?, drawingno = ?, strength = ?, mocpunches = ?,mocdies = ?,shape = ?,dimensions = ?,breaklineupper = ?,"
						+ " breaklinelower = ?,lasermarking = ?,hardcromeplating = ?,toolinglife = ?,serviceintervalqty = ?,embosingUpper = ?,embosingLower = ?, isActive = ?, "
						+ "  machinetype = ?, typeoftool = ?, dustcapgroove = ?, inspectionreport = ?, dqdocument = ?, moccertificate = ?, ductcapmoccertificate = ?, uom = ?, "
						+ " expiryperiod = ?, minAcceptedQty = ?, uploadedpath = ?, punchSetNo = ?, compForce = ? "
						+ " where productid = ?");
				pstmt.setString(1, TiimUtil.ValidateNull(product.getProductName()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(product.getDrawingNo()).trim());
				pstmt.setString(3, TiimUtil.ValidateNull(product.getStrength()).trim());
				pstmt.setString(4, TiimUtil.ValidateNull(product.getMocPunches()).trim());
				pstmt.setString(5, TiimUtil.ValidateNull(product.getMocDies()).trim());
				pstmt.setString(6, TiimUtil.ValidateNull(product.getShape()).trim());
				pstmt.setString(7, TiimUtil.ValidateNull(product.getDimensions()).trim());
				pstmt.setString(8, TiimUtil.ValidateNull(product.getBreaklineUpper()).trim());
				pstmt.setString(9, TiimUtil.ValidateNull(product.getBreaklineLower()).trim());
				pstmt.setString(10, TiimUtil.ValidateNull(product.getLaserMaking()).trim());
				pstmt.setString(11, TiimUtil.ValidateNull(product.getHardChromePlating()).trim());
				pstmt.setString(12, TiimUtil.ValidateNull(product.getToolingLife()).trim());
				pstmt.setString(13, TiimUtil.ValidateNull(product.getServiceIntervalQty()).trim());
				pstmt.setString(14, TiimUtil.ValidateNull(product.getEmbosingUpper()).trim());
				pstmt.setString(15, TiimUtil.ValidateNull(product.getEmbosingLower()).trim());
				pstmt.setInt(16, 1);
				pstmt.setString(17, TiimUtil.ValidateNull(product.getMachineType()).trim());
				pstmt.setString(18, TiimUtil.ValidateNull(product.getTypeOfTool()).trim());
				pstmt.setString(19, TiimUtil.ValidateNull(product.getDustCapGroove()).trim());
				pstmt.setString(20, TiimUtil.ValidateNull(product.getInspectionReport()).trim());
				pstmt.setString(21, TiimUtil.ValidateNull(product.getDqDocument()).trim());
				pstmt.setString(22, TiimUtil.ValidateNull(product.getMocCertificate()).trim());
				pstmt.setString(23, TiimUtil.ValidateNull(product.getDuctCapMOCCertificate()).trim());
				pstmt.setString(24, TiimUtil.ValidateNull(product.getUom()).trim());
				pstmt.setInt(25, product.getExpiryPeriod());
				pstmt.setInt(26, product.getAcceptedQty());
				pstmt.setString(27, product.getUploadedPath());
				pstmt.setString(28, TiimUtil.ValidateNull(product.getPunchSetNo()).trim());
				pstmt.setInt(29, product.getCompForce());
				pstmt.setInt(30, product.getProductId());
				
				pstmt.executeUpdate();
				msg = "Updated Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("product.master", null,null));
				history.setDescription(messageSource.getMessage("product.update", null,null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			}
			else
			{
				msg = "Already Exists";
			}
		}
		catch(Exception ex)
		{
			System.out.println("Exception when updating the Product detail in mst_product table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in Product detail in mst_product table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public String deleteProduct(int productId, int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from mst_product where productid = ?");
			pstmt.setInt(1, productId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("product.master", null,null));
			history.setDescription(messageSource.getMessage("product.delete", null,null));
			history.setUserId(userId);
			historyDao.addHistory(history);
		}catch(Exception ex)
		{
			System.out.println("Exception when delete the Product detail in mst_product table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in delete the Product detail in mst_product table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	
	public List<Product> getProductDetails(String searchProduct)
	{
		List<Product> lstProduct = new ArrayList<Product>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Product product;
		try
		{
			con = datasource.getConnection();
			if(searchProduct != null && !"".equals(searchProduct))
			{
			    pstmt = con.prepareStatement("Select productid, p.productname, p.drawingno, p.strength, p.mocpunches, p.mocdies,p.shape,p.dimensions,p.breaklineupper, "
			    		+ "  p.breaklinelower,p.lasermarking,p.hardcromeplating,p.toolinglife,p.serviceintervalqty,p.embosingUpper,p.embosingLower,p.isActive,"
			    		+ "  p.machinetype, p.typeoftool, p.dustcapgroove, p.inspectionreport, p.dqdocument, p.moccertificate, p.ductcapmoccertificate, p.uom,p.expiryperiod,"
			    		+ " p.minAcceptedQty, p.uploadedpath,"
			    		+ "  p.punchSetNo, p.compForce, intentId from mst_product p"
			    		+ " left join tooling_intent_note t on p.productname = t.productname"
			    		+ "  Where p.productname like '%"+searchProduct+"%' order by p.productname");
			}
			else
			{
				pstmt = con.prepareStatement("Select productid, p.productname, p.drawingno, p.strength, p.mocpunches, p.mocdies,p.shape,p.dimensions,p.breaklineupper, "
			    		+ "  p.breaklinelower,p.lasermarking,p.hardcromeplating,p.toolinglife,p.serviceintervalqty,p.embosingUpper,p.embosingLower,p.isActive,"
			    		+ "  p.machinetype, p.typeoftool, p.dustcapgroove, p.inspectionreport, p.dqdocument, p.moccertificate, p.ductcapmoccertificate, p.uom,p.expiryperiod,"
			    		+ " p.minAcceptedQty, p.uploadedpath,"
			    		+ "  p.punchSetNo, p.compForce, intentId from mst_product p "
			    		+ " left join tooling_intent_note t on p.productname = t.productname  order by p.productname");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				product = new Product();
				product.setProductId(rs.getInt("productid"));
				product.setProductName(rs.getString("productname"));
				product.setDrawingNo(rs.getString("drawingno"));
				product.setStrength(rs.getString("strength"));
				product.setMocPunches(rs.getString("mocpunches"));
				product.setMocDies(rs.getString("mocdies"));
				product.setShape(rs.getString("shape"));
				product.setDimensions(rs.getString("dimensions"));
				product.setBreaklineUpper(rs.getString("breaklineupper"));
				product.setBreaklineLower(rs.getString("breaklinelower"));
				product.setLaserMaking(rs.getString("lasermarking"));
				product.setHardChromePlating(rs.getString("hardcromeplating"));
				product.setToolingLife(rs.getString("toolinglife"));
				product.setServiceIntervalQty(rs.getString("serviceintervalqty"));
				product.setEmbosingUpper(rs.getString("embosingUpper"));
				product.setEmbosingLower(rs.getString("embosingLower"));
				product.setIsActive(rs.getInt("isActive"));
				product.setMachineType(rs.getString("machinetype"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setDustCapGroove(rs.getString("dustcapgroove"));
				product.setInspectionReport(rs.getString("inspectionreport"));
				product.setDqDocument(rs.getString("dqdocument"));
				product.setMocCertificate(rs.getString("moccertificate"));
				product.setDuctCapMOCCertificate(rs.getString("ductcapmoccertificate"));
				product.setUom(rs.getString("uom"));
				product.setExpiryPeriod(rs.getInt("expiryperiod"));
				product.setAcceptedQty(rs.getInt("minAcceptedQty"));
				product.setUploadedPath(rs.getString("uploadedpath"));
				product.setPunchSetNo(rs.getString("punchSetNo"));
				product.setCompForce(rs.getInt("compForce"));
				product.setIsEdit(1);
				if(rs.getInt("intentId") > 0 )
				{
					product.setIsEdit(0);
				}
				lstProduct.add(product);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getProductDetails in mst_product table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in entire Product details in mst_product table : "+ex.getMessage());
			}
		}
		return lstProduct;	
	}
	
	public List<Product> getProductDetailsByDrawingNo(String searchDrawingNo)
	{
		List<Product> lstProduct = new ArrayList<Product>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Product product;
		try
		{
			con = datasource.getConnection();
			if(searchDrawingNo != null && !"".equals(searchDrawingNo))
			{
			    pstmt = con.prepareStatement("Select productid, productname, drawingno, strength, mocpunches,mocdies,shape,dimensions,breaklineupper, "
			    		+ "  breaklinelower,lasermarking,hardcromeplating,toolinglife,serviceintervalqty,embosingUpper,embosingLower,isActive,"
			    		+ "  machinetype, typeoftool, dustcapgroove, inspectionreport, dqdocument, moccertificate, ductcapmoccertificate, uom, expiryperiod, minAcceptedQty, uploadedpath,  punchSetNo, compForce  "
			    		+ " from mst_product Where drawingno like '%"+searchDrawingNo+"%' order by productname");
			}
			else
			{
				pstmt = con.prepareStatement("Select productid, productname, drawingno, strength, mocpunches,mocdies,shape,dimensions,breaklineupper, "
						+ "  breaklinelower,lasermarking,hardcromeplating,toolinglife,serviceintervalqty,embosingUpper,embosingLower,isActive, "
						+ "  machinetype, typeoftool, dustcapgroove, inspectionreport, dqdocument, moccertificate, ductcapmoccertificate, uom, expiryperiod, minAcceptedQty, uploadedpath,  punchSetNo, compForce  "
						+ " from mst_product order by productname");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				product = new Product();
				product.setProductId(rs.getInt("productid"));
				product.setProductName(rs.getString("productname"));
				product.setDrawingNo(rs.getString("drawingno"));
				product.setStrength(rs.getString("strength"));
				product.setMocPunches(rs.getString("mocpunches"));
				product.setMocDies(rs.getString("mocdies"));
				product.setShape(rs.getString("shape"));
				product.setDimensions(rs.getString("dimensions"));
				product.setBreaklineUpper(rs.getString("breaklineupper"));
				product.setBreaklineLower(rs.getString("breaklinelower"));
				product.setLaserMaking(rs.getString("lasermarking"));
				product.setHardChromePlating(rs.getString("hardcromeplating"));
				product.setToolingLife(rs.getString("toolinglife"));
				product.setServiceIntervalQty(rs.getString("serviceintervalqty"));
				product.setEmbosingUpper(rs.getString("embosingUpper"));
				product.setEmbosingLower(rs.getString("embosingLower"));
				product.setIsActive(rs.getInt("isActive"));
				product.setMachineType(rs.getString("machinetype"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setDustCapGroove(rs.getString("dustcapgroove"));
				product.setInspectionReport(rs.getString("inspectionreport"));
				product.setDqDocument(rs.getString("dqdocument"));
				product.setMocCertificate(rs.getString("moccertificate"));
				product.setDuctCapMOCCertificate(rs.getString("ductcapmoccertificate"));
				product.setUom(rs.getString("uom"));
				product.setExpiryPeriod(rs.getInt("expiryperiod"));
				product.setAcceptedQty(rs.getInt("minAcceptedQty"));
				product.setUploadedPath(rs.getString("uploadedpath"));
				product.setPunchSetNo(rs.getString("punchSetNo"));
				product.setCompForce(rs.getInt("compForce"));
				lstProduct.add(product);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getProductDetails in mst_product table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in entire Product details in mst_product table : "+ex.getMessage());
			}
		}
		return lstProduct;	
	}
	
	public Product getProductDetail(int productId)
	{
		Product product = new Product();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select productid, productname, drawingno, strength, mocpunches,mocdies,shape,dimensions,breaklineupper, "
			    		+ "  breaklinelower,lasermarking,hardcromeplating,toolinglife,serviceintervalqty,embosingUpper,embosingLower,isActive, "
			    		+ "  machinetype, typeoftool, dustcapgroove, inspectionreport, dqdocument, moccertificate, ductcapmoccertificate, uom, expiryperiod, minAcceptedQty, uploadedpath, punchSetNo, compForce   "
			    		+ " from mst_product Where productid = ?");
			pstmt.setInt(1, productId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				product.setProductId(rs.getInt("productid"));
				product.setProductName(rs.getString("productname"));
				product.setDrawingNo(rs.getString("drawingno"));
				product.setStrength(rs.getString("strength"));
				product.setMocPunches(rs.getString("mocpunches"));
				product.setMocDies(rs.getString("mocdies"));
				product.setShape(rs.getString("shape"));
				product.setDimensions(rs.getString("dimensions"));
				product.setBreaklineUpper(rs.getString("breaklineupper"));
				product.setBreaklineLower(rs.getString("breaklinelower"));
				product.setLaserMaking(rs.getString("lasermarking"));
				product.setHardChromePlating(rs.getString("hardcromeplating"));
				product.setToolingLife(rs.getString("toolinglife"));
				product.setServiceIntervalQty(rs.getString("serviceintervalqty"));
				product.setEmbosingUpper(rs.getString("embosingUpper"));
				product.setEmbosingLower(rs.getString("embosingLower"));
				//System.out.println(rs.getString("embosingUpper")+", "+product.getEmbosingUpper());
				product.setIsActive(rs.getInt("isActive"));
				//product.setMachineType(rs.getString("machinetype"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setDustCapGroove(rs.getString("dustcapgroove"));
				product.setInspectionReport(rs.getString("inspectionreport"));
				product.setDqDocument(rs.getString("dqdocument"));
				product.setMocCertificate(rs.getString("moccertificate"));
				product.setDuctCapMOCCertificate(rs.getString("ductcapmoccertificate"));
				product.setIsActive(rs.getInt("isActive"));
				product.setUom(rs.getString("uom"));
				product.setExpiryPeriod(rs.getInt("expiryperiod"));
				product.setAcceptedQty(rs.getInt("minAcceptedQty"));
				product.setUploadedPath(rs.getString("uploadedpath"));
				product.setPunchSetNo(rs.getString("punchSetNo"));
				product.setCompForce(rs.getInt("compForce"));
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the particular machine details in mst_machine table by using machineid : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in  particular machine details in mst_machine table by using machineid : "+ex.getMessage());
			}
		}
		return product;	
	}
	
	private boolean isProductExists(String productName, String drawingNumber)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_product WHERE productname = ? and drawingno=?");
			 pstmt.setString(1, TiimUtil.ValidateNull(productName).trim());
			 pstmt.setString(2, TiimUtil.ValidateNull(drawingNumber).trim());
			 rs = pstmt.executeQuery();
			 if(rs.next())
			 {
				 count = rs.getInt("Is_Exists");
			 }
			 
			 if(count > 0)
			 {
				 isExists = false;
			 }
		}
		catch(Exception e)
		{
			System.out.println("Exception while checking the product name exists in mst_product table when adding : "+e.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in product master detail in mst_product table when adding : "+ex.getMessage());
			}
		}
		return isExists;
	}
	
	private boolean isProductExistsUpdate(int productId, String productName)
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try
		{
			 con = datasource.getConnection();
			 pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_product WHERE  productid <> ? and  productname = ?");
			 pstmt.setInt(1, productId);
			 pstmt.setString(2, TiimUtil.ValidateNull(productName).trim());
			 rs = pstmt.executeQuery();
			 if(rs.next())
			 {
				 count = rs.getInt("Is_Exists");
			 }
			 
			 if(count > 0)
			 {
				 isExists = false;
			 }
		}
		catch(Exception e)
		{
			System.out.println("Exception while checking the productname exists in mst_product table when updating : "+e.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in productname master detail in mst_product table when updating : "+ex.getMessage());
			}
		}
		return isExists;
	}
	
	public String changeProductStatus(int machineId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try
		{
			   con = datasource.getConnection();
				pstmt = con.prepareStatement("Select isActive from mst_product where productid = ?");
				pstmt.setInt(1, machineId);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					isActive = rs.getInt("isActive");
					if(isActive == 1)
					{
						isActive=0;
						msg = "Made InActive Successfully";
					}
					else
					{
						isActive=1;
						msg = "Made Active Successfully";	
					}
				}
				
				pstmt = con.prepareStatement("Update mst_product set isActive = ?  where productid = ?");
				pstmt.setInt(1, isActive);
				pstmt.setInt(2, machineId);
				pstmt.executeUpdate();								
		}
		catch(Exception ex)
		{
			System.out.println("Exception when changing the status of Product in mst_product table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in changing the status of Product in mst_product table : "+ex.getMessage());
			}
		}
		
		return msg;
	}
	
	public List<Product> getAutoProductDetails(String searchProduct)
	{
		List<Product> lstProduct = new ArrayList<Product>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Product product;
		try
		{
			con = datasource.getConnection();
		    pstmt = con.prepareStatement("Select productid, productname, drawingno, strength, mocpunches,mocdies,shape,dimensions,breaklineupper, "
	    		+ "  breaklinelower,lasermarking,hardcromeplating,toolinglife,serviceintervalqty,embosingUpper,embosingLower,isActive,"
	    		+ "  machinetype, typeoftool, dustcapgroove, inspectionreport, dqdocument, moccertificate, ductcapmoccertificate, uom, expiryperiod, minAcceptedQty, uploadedpath,punchSetNo, compForce "
	    		+ " from mst_product Where productname like '"+searchProduct+"%' order by productname");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				product = new Product();
				product.setProductId(rs.getInt("productid"));
				product.setProductName(rs.getString("productname"));
				product.setDrawingNo(rs.getString("drawingno"));
				product.setStrength(rs.getString("strength"));
				product.setMocPunches(rs.getString("mocpunches"));
				product.setMocDies(rs.getString("mocdies"));
				product.setShape(rs.getString("shape"));
				product.setDimensions(rs.getString("dimensions"));
				product.setBreaklineUpper(rs.getString("breaklineupper"));
				product.setBreaklineLower(rs.getString("breaklinelower"));
				product.setLaserMaking(rs.getString("lasermarking"));
				product.setHardChromePlating(rs.getString("hardcromeplating"));
				product.setToolingLife(rs.getString("toolinglife"));
				product.setServiceIntervalQty(rs.getString("serviceintervalqty"));
				product.setEmbosingUpper(rs.getString("embosingUpper"));
				product.setEmbosingLower(rs.getString("embosingLower"));
				product.setIsActive(rs.getInt("isActive"));
				product.setMachineType(rs.getString("machinetype"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setDustCapGroove(rs.getString("dustcapgroove"));
				product.setInspectionReport(rs.getString("inspectionreport"));
				product.setDqDocument(rs.getString("dqdocument"));
				product.setMocCertificate(rs.getString("moccertificate"));
				product.setDuctCapMOCCertificate(rs.getString("ductcapmoccertificate"));
				product.setUom(rs.getString("uom"));
				product.setExpiryPeriod(rs.getInt("expiryperiod"));
				product.setAcceptedQty(rs.getInt("minAcceptedQty"));
				product.setUploadedPath(rs.getString("uploadedpath"));
				product.setPunchSetNo(rs.getString("punchSetNo"));
				product.setCompForce(rs.getInt("compForce"));
				lstProduct.add(product);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getAutoProductDetails in mst_product table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in entire getAutoProductDetails in mst_product table : "+ex.getMessage());
			}
		}
		return lstProduct;	
	}
	
	public List<Product> getAutoDrawingNo(String drawingNo)
	{
		List<Product> lstProduct = new ArrayList<Product>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Product product;
		try
		{
			con = datasource.getConnection();
		    pstmt = con.prepareStatement("Select productid, productname, drawingno, strength, mocpunches,mocdies,shape,dimensions,breaklineupper, "
	    		+ "  breaklinelower,lasermarking,hardcromeplating,toolinglife,serviceintervalqty,embosingUpper,embosingLower,isActive,"
	    		+ "  machinetype, typeoftool, dustcapgroove, inspectionreport, dqdocument, moccertificate, ductcapmoccertificate, uom, expiryperiod, minAcceptedQty, uploadedpath,punchSetNo, compForce"
	    		+ " from mst_product Where drawingno like '"+drawingNo+"%' order by productname");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				product = new Product();
				product.setProductId(rs.getInt("productid"));
				product.setProductName(rs.getString("productname"));
				product.setDrawingNo(rs.getString("drawingno"));
				product.setStrength(rs.getString("strength"));
				product.setMocPunches(rs.getString("mocpunches"));
				product.setMocDies(rs.getString("mocdies"));
				product.setShape(rs.getString("shape"));
				product.setDimensions(rs.getString("dimensions"));
				product.setBreaklineUpper(rs.getString("breaklineupper"));
				product.setBreaklineLower(rs.getString("breaklinelower"));
				product.setLaserMaking(rs.getString("lasermarking"));
				product.setHardChromePlating(rs.getString("hardcromeplating"));
				product.setToolingLife(rs.getString("toolinglife"));
				product.setServiceIntervalQty(rs.getString("serviceintervalqty"));
				product.setEmbosingUpper(rs.getString("embosingUpper"));
				product.setEmbosingLower(rs.getString("embosingLower"));
				product.setIsActive(rs.getInt("isActive"));
				product.setMachineType(rs.getString("machinetype"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setDustCapGroove(rs.getString("dustcapgroove"));
				product.setInspectionReport(rs.getString("inspectionreport"));
				product.setDqDocument(rs.getString("dqdocument"));
				product.setMocCertificate(rs.getString("moccertificate"));
				product.setDuctCapMOCCertificate(rs.getString("ductcapmoccertificate"));
				product.setUom(rs.getString("uom"));
				product.setExpiryPeriod(rs.getInt("expiryperiod"));
				product.setAcceptedQty(rs.getInt("minAcceptedQty"));
				product.setUploadedPath(rs.getString("uploadedpath"));
				product.setPunchSetNo(rs.getString("punchSetNo"));
				product.setCompForce(rs.getInt("compForce"));
				lstProduct.add(product);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getAutoProductDetails in mst_product table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in entire getAutoProductDetails in mst_product table : "+ex.getMessage());
			}
		}
		return lstProduct;	
	}
	
	public List<Product> getProductDetailsByDrawing(String drawingNo)
	{
		System.out.println("next: "+drawingNo);
		List<Product> lstProduct = new ArrayList<Product>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Product product;
		try
		{
			con = datasource.getConnection();
			if(drawingNo == null || "".equals(drawingNo))
			{
		     pstmt = con.prepareStatement("Select productid, productname, drawingno, strength, mocpunches,mocdies,shape,dimensions,breaklineupper, "
	    		+ "  breaklinelower,lasermarking,hardcromeplating,toolinglife,serviceintervalqty,embosingUpper,embosingLower,isActive,"
	    		+ "  machinetype, typeoftool, dustcapgroove, inspectionreport, dqdocument, moccertificate, ductcapmoccertificate, uom, expiryperiod, minAcceptedQty, uploadedpath,punchSetNo, compForce "
	    		+ " from mst_product order by productname");
			}else
			{
				System.out.println("else part");
				pstmt = con.prepareStatement("Select productid, productname, drawingno, strength, mocpunches,mocdies,shape,dimensions,breaklineupper, "
			    		+ "  breaklinelower,lasermarking,hardcromeplating,toolinglife,serviceintervalqty,embosingUpper,embosingLower,isActive,"
			    		+ "  machinetype, typeoftool, dustcapgroove, inspectionreport, dqdocument, moccertificate, ductcapmoccertificate, uom, expiryperiod, minAcceptedQty, uploadedpath,punchSetNo, compForce "
			    		+ " from mst_product where drawingno = ? order by productname");
				pstmt.setString(1, drawingNo);
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getString("drawingno"));
				product = new Product();
				product.setProductId(rs.getInt("productid"));
				product.setProductName(rs.getString("productname"));
				product.setDrawingNo(rs.getString("drawingno"));
				product.setStrength(rs.getString("strength"));
				product.setMocPunches(rs.getString("mocpunches"));
				product.setMocDies(rs.getString("mocdies"));
				product.setShape(rs.getString("shape"));
				product.setDimensions(rs.getString("dimensions"));
				product.setBreaklineUpper(rs.getString("breaklineupper"));
				product.setBreaklineLower(rs.getString("breaklinelower"));
				product.setLaserMaking(rs.getString("lasermarking"));
				product.setHardChromePlating(rs.getString("hardcromeplating"));
				product.setToolingLife(rs.getString("toolinglife"));
				product.setServiceIntervalQty(rs.getString("serviceintervalqty"));
				product.setEmbosingUpper(rs.getString("embosingUpper"));
				product.setEmbosingLower(rs.getString("embosingLower"));
				product.setIsActive(rs.getInt("isActive"));
				product.setMachineType(rs.getString("machinetype"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setDustCapGroove(rs.getString("dustcapgroove"));
				product.setInspectionReport(rs.getString("inspectionreport"));
				product.setDqDocument(rs.getString("dqdocument"));
				product.setMocCertificate(rs.getString("moccertificate"));
				product.setDuctCapMOCCertificate(rs.getString("ductcapmoccertificate"));
				product.setUom(rs.getString("uom"));
				product.setExpiryPeriod(rs.getInt("expiryperiod"));
				product.setAcceptedQty(rs.getInt("minAcceptedQty"));
				product.setUploadedPath(rs.getString("uploadedpath"));
				product.setPunchSetNo(rs.getString("punchSetNo"));
				product.setCompForce(rs.getInt("compForce"));
				
				lstProduct.add(product);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getAutoProductDetails in mst_product table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in entire getAutoProductDetails in mst_product table : "+ex.getMessage());
			}
		}
		return lstProduct;	
	}
	public List<Product> getProductDetailsByLot(String toolinglotnumber)
	{
		List<Product> lstProduct = new ArrayList<Product>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		Product product;
		try
		{
			con = datasource.getConnection();
			if(toolinglotnumber == null || "".equals(toolinglotnumber))
			{
		     pstmt = con.prepareStatement("select t1.toolinglotnumber,t1.drawingno,t1.productname,t1.machinetype,t1.typeoftool,punchSetNo,compForce,dustcapgroove"
	    		+ "  from stock t1 left join tooling_receipt_product t2 on t1.toolinglotnumber=t2.toolinglotnumber "
	    		+ " where t1.toolinglotnumber not in (select distinct lotNumber from mst_clearance_no where isMoved=0) group by t1.toolinglotnumber");
			}else
			{
				 
				pstmt = con.prepareStatement("select t1.toolinglotnumber,t1.drawingno,t1.productname,t1.machinetype,t1.typeoftool,punchSetNo,compForce,dustcapgroove "
			    		+ "from stock t1 left join tooling_receipt_product t2 on t1.toolinglotnumber=t2.toolinglotnumber where t1.toolinglotnumber = ? "
			    		+ " and t1.toolinglotnumber not in (select distinct lotNumber from mst_clearance_no where isMoved=0)group by t1.toolinglotnumber ");
				pstmt.setString(1, toolinglotnumber);
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				product = new Product();
			//	product.setProductId(rs.getInt("productid"));
				product.setProductName(rs.getString("productname"));
				product.setToolinglotnumber(rs.getString("toolinglotnumber"));
				product.setDrawingNo(rs.getString("drawingno"));
			/*	product.setStrength(rs.getString("strength"));
				product.setMocPunches(rs.getString("mocpunches"));
				product.setMocDies(rs.getString("mocdies"));
				product.setShape(rs.getString("shape"));
				product.setDimensions(rs.getString("dimensions"));
				product.setBreaklineUpper(rs.getString("breaklineupper"));
				product.setBreaklineLower(rs.getString("breaklinelower"));
				product.setLaserMaking(rs.getString("lasermarking"));
				product.setHardChromePlating(rs.getString("hardcromeplating"));
				product.setToolingLife(rs.getString("toolinglife"));
				product.setServiceIntervalQty(rs.getString("serviceintervalqty"));
				product.setEmbosingUpper(rs.getString("embosingUpper"));
				product.setEmbosingLower(rs.getString("embosingLower"));
				product.setIsActive(rs.getInt("isActive"));*/
				product.setMachineType(rs.getString("machinetype"));
				product.setTypeOfTool(rs.getString("typeoftool"));
				product.setPunchSetNo(rs.getString("punchSetNo"));
				product.setCompForce(rs.getInt("compForce"));
			/*	product.setDustCapGroove(rs.getString("dustcapgroove"));
				product.setInspectionReport(rs.getString("inspectionreport"));
				product.setDqDocument(rs.getString("dqdocument"));
				product.setMocCertificate(rs.getString("moccertificate"));
				product.setDuctCapMOCCertificate(rs.getString("ductcapmoccertificate"));
				product.setUom(rs.getString("uom"));
				product.setExpiryPeriod(rs.getInt("expiryperiod"));
				product.setAcceptedQty(rs.getInt("minAcceptedQty"));
				product.setUploadedPath(rs.getString("uploadedpath"));*/
				product.setDustCapGroove(rs.getString("dustcapgroove"));
				lstProduct.add(product);
			}
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the entire getAutoProductDetails in mst_product table by lot : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in entire getAutoProductDetails in mst_product table : "+ex.getMessage());
			}
		}
		return lstProduct;	
	}
	public String getProductUploadedPath(String productName, String drawingNo, String machineType, String typeOfTool)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String uploadedPath = "";
		try
		{
			   con = datasource.getConnection();
				pstmt = con.prepareStatement("select uploadedpath from mst_product where productname = ? and drawingno = ? and machinetype = ? and typeoftool = ?");
				pstmt.setString(1, productName);
				pstmt.setString(2, drawingNo);
				pstmt.setString(3, machineType);
				pstmt.setString(4, typeOfTool);
				rs = pstmt.executeQuery();
				if(rs.next())
				{
					uploadedPath = rs.getString("uploadedpath");
				}
											
		}
		catch(Exception ex)
		{
			System.out.println("Exception when getProductUploadedPath in mst_product table : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}if(pstmt != null)
				{
					pstmt.close();
				}if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in getProductUploadedPath in mst_product table : "+ex.getMessage());
			}
		}
		
		return uploadedPath;
	}

	public Product getProductIntervalQnty(String productname, String drawingNo)
	{
		Product product = new Product();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;  
		try
		{
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select toolinglife,serviceintervalqty from mst_product where productname = ? and drawingno = ?");
			pstmt.setString(1, productname);
			pstmt.setString(2, drawingNo);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
			
				product.setToolingLife(rs.getString("toolinglife"));
				product.setServiceIntervalQty(rs.getString("serviceintervalqty"));
				
			}
		}catch(Exception ex)
		{
			System.out.println("Exception when getting the particular getProductIntervalQnty in mst_machine table by using machineid : "+ex.getMessage());
		}
		finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
				if(con != null)
				{
					con.close();
				}
			}catch(Exception ex)
			{
				System.out.println("Exception when closing the connection in  particular getProductIntervalQnty in mst_machine table by using machineid : "+ex.getMessage());
			}
		}
		return product;	
	}
}
