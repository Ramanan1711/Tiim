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

import com.tiim.model.Supplier;
import com.tiim.util.TiimUtil;

@Repository
public class SupplierDao {

	@Autowired
	DataSource datasource;

	@Autowired
	TransactionHistoryDao historyDao;

	@Autowired
	MessageSource messageSource;

	public String addSupplier(Supplier supplier, int userId) {
		String returnValue = new String();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"insert into mst_supplier(suppliercode, suppliername, address, city, phonenumber, email, isActive)"
							+ "values(?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, getNextSupplierCode());
			pstmt.setString(2, TiimUtil.ValidateNull(supplier.getSupplierName()).trim());
			pstmt.setString(3, TiimUtil.ValidateNull(supplier.getAddress()).trim());
			pstmt.setString(4, TiimUtil.ValidateNull(supplier.getCity()).trim());
			pstmt.setString(5, supplier.getPhoneNumber());
			pstmt.setString(6, TiimUtil.ValidateNull(supplier.getEmail()).trim());
			pstmt.setInt(7, 1);
			pstmt.executeUpdate();
			returnValue = "Saved Successfully";

			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("supplier.page", null, null));
			history.setDescription(messageSource.getMessage("supplier.insert", null, null));
			history.setUserId(userId);
			historyDao.addHistory(history);
		} catch (Exception ex) {
			System.out.println(
					"Exception when adding the machine master detail in mst_machine table : " + ex.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connection in machine master detail in mst_machine table : "
								+ ex.getMessage());
			}
		}

		return returnValue;
	}

	private boolean isSupplierExists(String supplierCode) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_supplier WHERE suppliercode = ?");
			pstmt.setString(1, TiimUtil.ValidateNull(supplierCode).trim());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("Is_Exists");
			}

			if (count > 0) {
				isExists = false;
			}
		} catch (Exception e) {
			System.out.println("Exception while checking the suppliercode exists in mst_supplier table when adding : "
					+ e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connection in suppliercode master detail in mst_supplier table when adding : "
								+ ex.getMessage());
			}
		}
		return isExists;
	}

	public String updateSupplier(Supplier supplier, int userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try {
			if (isSupplierExistsUpdate(supplier.getSupplierId(), supplier.getSupplierCode())) {
				con = datasource.getConnection();
				pstmt = con.prepareStatement(
						"Update mst_supplier set suppliername = ?, address = ?, city = ?, phonenumber = ?, email = ?  where supplierid = ?");
				pstmt.setString(1, TiimUtil.ValidateNull(supplier.getSupplierName()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(supplier.getAddress()).trim());
				pstmt.setString(3, TiimUtil.ValidateNull(supplier.getCity()).trim());
				pstmt.setString(4, supplier.getPhoneNumber());
				pstmt.setString(5, TiimUtil.ValidateNull(supplier.getEmail()).trim());
				pstmt.setInt(6, supplier.getSupplierId());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("supplier.page", null, null));
				history.setDescription(messageSource.getMessage("supplier.update", null, null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			} else {
				msg = "Already Exists";
			}
		} catch (Exception ex) {
			System.out
					.println("Exception when updating the Supplier detail in mst_supplier table : " + ex.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("Exception when closing the connection in Supplier detail in mst_supplier table : "
						+ ex.getMessage());
			}
		}

		return msg;
	}

	public String deleteSupplier(int supplierId, int userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from mst_supplier where supplierid = ?");
			pstmt.setInt(1, supplierId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("supplier.page", null, null));
			history.setDescription(messageSource.getMessage("supplier.delete", null, null));
			history.setUserId(userId);
			historyDao.addHistory(history);

		} catch (Exception ex) {
			System.out.println("Exception when delete the Supplier detail in mst_supplier table : " + ex.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connectin in delete the Supplier detail in mst_supplier table : "
								+ ex.getMessage());
			}
		}

		return msg;
	}

	public List<Supplier> getSupplierDetails(String searchSupplier) {
		List<Supplier> lstSupplier = new ArrayList<Supplier>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Supplier supplier;
		try {
			con = datasource.getConnection();
			if (searchSupplier != null && !"".equals(searchSupplier)) {
				pstmt = con.prepareStatement(
						"Select supplierid, suppliercode, suppliername, address, city, phonenumber, email, isActive from mst_supplier Where suppliername like '%"
								+ searchSupplier + "%' order by suppliercode");
			} else {
				pstmt = con.prepareStatement(
						"Select supplierid, suppliercode, suppliername, address, city, phonenumber, email, isActive from mst_supplier order by suppliercode");
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				supplier = new Supplier();
				supplier.setSupplierId(rs.getInt("supplierid"));
				supplier.setSupplierCode(rs.getString("suppliercode"));
				supplier.setSupplierName(rs.getString("suppliername"));
				supplier.setAddress(rs.getString("address"));
				supplier.setCity(rs.getString("city"));
				supplier.setPhoneNumber(rs.getString("phonenumber"));
				supplier.setEmail(rs.getString("email"));
				supplier.setIsActive(rs.getInt("isActive"));

				lstSupplier.add(supplier);
			}

		} catch (Exception ex) {
			System.out.println(
					"Exception when getting the entire getSupplierDetails in mst_supplier table : " + ex.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connection in entire Supplier details in mst_supplier table : "
								+ ex.getMessage());
			}
		}
		return lstSupplier;
	}

	public Supplier getSupplierDetail(int supplierId) {
		Supplier supplier = new Supplier();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"Select supplierid, suppliercode, suppliername, address, city, phonenumber, email, isActive from mst_supplier Where supplierid = ?");
			pstmt.setInt(1, supplierId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				supplier.setSupplierId(rs.getInt("supplierid"));
				supplier.setSupplierCode(rs.getString("suppliercode"));
				supplier.setSupplierName(rs.getString("suppliername"));
				supplier.setAddress(rs.getString("address"));
				supplier.setCity(rs.getString("city"));
				supplier.setPhoneNumber(rs.getString("phonenumber"));
				supplier.setEmail(rs.getString("email"));
				supplier.setIsActive(rs.getInt("isActive"));
				supplier.setSearchSupplier("");
			}
		} catch (Exception ex) {
			System.out.println(
					"Exception when getting the particular supplier details in mst_supplier table by using supplierid : "
							+ ex.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connection in  particular supplier details in mst_supplier table by using supplierid : "
								+ ex.getMessage());
			}
		}
		return supplier;
	}

	private boolean isSupplierExistsUpdate(int supplierId, String suppierCode) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"SELECT COUNT('A') AS Is_Exists FROM mst_supplier WHERE  supplierid <> ? and  suppliercode = ?");
			pstmt.setInt(1, supplierId);
			pstmt.setString(2, TiimUtil.ValidateNull(suppierCode).trim());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("Is_Exists");
			}

			if (count > 0) {
				isExists = false;
			}
		} catch (Exception e) {
			System.out.println("Exception while checking the suppliercode exists in mst_supplier table when updating : "
					+ e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connection in suppliercode master detail in mst_supplier table when updating : "
								+ ex.getMessage());
			}
		}
		return isExists;
	}

	public String changeSupplierStatus(int supplierId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select isActive from mst_supplier where supplierid = ?");
			pstmt.setInt(1, supplierId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				isActive = rs.getInt("isActive");
				if (isActive == 1) {
					isActive = 0;
					msg = "Made InActive Successfully";
				} else {
					isActive = 1;
					msg = "Made Active Successfully";
				}
			}

			pstmt = con.prepareStatement("Update mst_supplier set isActive = ?  where supplierid = ?");
			pstmt.setInt(1, isActive);
			pstmt.setInt(2, supplierId);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println(
					"Exception when changing the status of Supplier in mst_supplier table : " + ex.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connectin in changing the status of Supplier in mst_supplier table : "
								+ ex.getMessage());
			}
		}

		return msg;
	}

	private String getNextSupplierCode() {

		String supplierCode = "Sup001";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT max(supplierid) supplierid FROM mst_supplier");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				supplierCode = "Sup00" + (rs.getInt("supplierid") + 1);
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the getNextSupplierCode : " + ex.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out
						.println("Exception when closing the connection in  getNextSupplierCode : " + ex.getMessage());
			}
		}
		return supplierCode;

	}
}
