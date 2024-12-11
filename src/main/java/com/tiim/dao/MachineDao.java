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
import com.tiim.util.TiimUtil;

@Repository
public class MachineDao {

	@Autowired
	DataSource datasource;
	@Autowired
	TransactionHistoryDao historyDao;

	@Autowired
	MessageSource messageSource;

	public String addMachine(Machine machine, int userId) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try {

			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"insert into mst_machine(machinecode, machinename, description, isActive, machinetype)"
							+ "values(?, ?, ?, ?, ?)");
			// ,Statement.RETURN_GENERATED_KEYS
			pstmt.setString(1, getNextMachineCode());
			pstmt.setString(2, TiimUtil.ValidateNull(machine.getMachineName()).trim());
			pstmt.setString(3, TiimUtil.ValidateNull(machine.getDescription()).trim());
			pstmt.setInt(4, 1);
			pstmt.setString(5, TiimUtil.ValidateNull(machine.getMachineType()).trim());
			pstmt.executeUpdate();
			/*
			 * ResultSet rs = pstmt.getGeneratedKeys(); if(rs.next()) {
			 * System.out.println("generated value: "+rs.getInt(1)); }
			 */
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("machine.page", null, null));
			history.setDescription(messageSource.getMessage("machine.insert", null, null));
			history.setUserId(userId);
			historyDao.addHistory(history);
			msg = "Saved Successfully";

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
		return msg;

	}

	public String updateMachine(Machine machine, int userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try {
			if (isMachineExistsUpdate(machine.getMachineId(), machine.getMachineCode())) {
				con = datasource.getConnection();
				pstmt = con.prepareStatement(
						"Update mst_machine set machinename = ?, description = ?, machinetype = ? where machineid = ?");
				//pstmt.setString(1, TiimUtil.ValidateNull(machine.getMachineCode()).trim());
				pstmt.setString(1, TiimUtil.ValidateNull(machine.getMachineName()).trim());
				pstmt.setString(2, TiimUtil.ValidateNull(machine.getDescription()).trim());
				pstmt.setString(3, TiimUtil.ValidateNull(machine.getMachineType()).trim());
				pstmt.setInt(4, machine.getMachineId());
				pstmt.executeUpdate();
				msg = "Updated Successfully";
				TransactionHistory history = new TransactionHistory();
				history.setPageName(messageSource.getMessage("machine.page", null, null));
				history.setDescription(messageSource.getMessage("machine.update", null, null));
				history.setUserId(userId);
				historyDao.addHistory(history);
			} else {
				msg = "Already Exists";
			}
		} catch (Exception ex) {
			System.out
					.println("Exception when updating the Employee detail in mst_employee table : " + ex.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println("Exception when closing the connection in Employee detail in mst_employee table : "
						+ ex.getMessage());
			}
		}

		return msg;
	}

	public String deleteMachine(int machineId, int userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("delete from mst_machine where machineid = ?");
			pstmt.setInt(1, machineId);
			pstmt.executeUpdate();
			msg = "Deleted Successfully";
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("machine.page", null, null));
			history.setDescription(messageSource.getMessage("machine.delete", null, null));
			history.setUserId(userId);
			historyDao.addHistory(history);
		} catch (Exception ex) {
			System.out.println("Exception when delete the Machine detail in mst_machine table : " + ex.getMessage());
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
						"Exception when closing the connectin in delete the Machine detail in mst_machine table : "
								+ ex.getMessage());
			}
		}

		return msg;
	}

	public List<Machine> getMachineDetails(String searchMachine) {
		List<Machine> lstMachine = new ArrayList<Machine>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Machine machine;
		try {
			con = datasource.getConnection();
			if (searchMachine != null && !"".equals(searchMachine)) {
				pstmt = con.prepareStatement(
						"Select machineid, machinecode, machinename, description, isActive, machinetype from mst_machine Where machinename like '%"
								+ searchMachine + "%' order by machinecode");
			} else {
				pstmt = con.prepareStatement(
						"Select machineid, machinecode, machinename, description, isActive, machinetype from mst_machine order by machinecode");
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				machine = new Machine();
				machine.setMachineId(rs.getInt("machineid"));
				machine.setMachineCode(TiimUtil.ValidateNull(rs.getString("machinecode")).trim());
				machine.setMachineName(TiimUtil.ValidateNull(rs.getString("machinename")).trim());
				machine.setDescription(TiimUtil.ValidateNull(rs.getString("description")).trim());
				machine.setIsActive(rs.getInt("isActive"));
				machine.setMachineType(TiimUtil.ValidateNull(rs.getString("machinetype")).trim());
				lstMachine.add(machine);
			}

		} catch (Exception ex) {
			System.out.println(
					"Exception when getting the entire getMachineDetails in mst_machine table : " + ex.getMessage());
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
						"Exception when closing the connection in entire Machine details in mst_machine table : "
								+ ex.getMessage());
			}
		}
		return lstMachine;
	}

	public Machine getMachineDetail(int machineid) {
		Machine machineDetails = new Machine();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"Select machineid, machinecode, machinename, description, isActive, machinetype from mst_machine Where machineid = ?");
			pstmt.setInt(1, machineid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				machineDetails.setMachineId(rs.getInt("machineid"));
				machineDetails.setMachineCode(TiimUtil.ValidateNull(rs.getString("machinecode")).trim());
				machineDetails.setMachineName(TiimUtil.ValidateNull(rs.getString("machinename")).trim());
				machineDetails.setDescription(TiimUtil.ValidateNull(rs.getString("description")).trim());
				machineDetails.setIsActive(rs.getInt("isActive"));
				machineDetails.setMachineType(TiimUtil.ValidateNull(rs.getString("machinetype")).trim());
				machineDetails.setAction("");
				machineDetails.setSearchMachine("");
			}
		} catch (Exception ex) {
			System.out.println(
					"Exception when getting the particular machine details in mst_machine table by using machineid : "
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
						"Exception when closing the connection in  particular machine details in mst_machine table by using machineid : "
								+ ex.getMessage());
			}
		}
		return machineDetails;
	}

	private boolean isMachineExists(String machineCode) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT COUNT('A') AS Is_Exists FROM mst_machine WHERE machinecode = ?");
			pstmt.setString(1, TiimUtil.ValidateNull(machineCode).trim());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("Is_Exists");
			}

			if (count > 0) {
				isExists = false;
			}
		} catch (Exception e) {
			System.out.println("Exception while checking the machinecode exists in mst_machine table when adding : "
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
						"Exception when closing the connection in machinecode master detail in mst_machine table when adding : "
								+ ex.getMessage());
			}
		}
		return isExists;
	}

	private boolean isMachineExistsUpdate(int machineId, String machineCode) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		int count = 0;
		boolean isExists = true;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"SELECT COUNT('A') AS Is_Exists FROM mst_machine WHERE  machineid <> ? and  machinecode = ?");
			pstmt.setInt(1, machineId);
			pstmt.setString(2, TiimUtil.ValidateNull(machineCode).trim());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("Is_Exists");
			}

			if (count > 0) {
				isExists = false;
			}
		} catch (Exception e) {
			System.out.println("Exception while checking the machinecode exists in mst_machine table when updating : "
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
						"Exception when closing the connection in machinecode master detail in mst_machine table when updating : "
								+ ex.getMessage());
			}
		}
		return isExists;
	}

	public String changeMachineStatus(int machineId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select isActive from mst_machine where machineid = ?");
			pstmt.setInt(1, machineId);
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

			pstmt = con.prepareStatement("Update mst_machine set isActive = ?  where machineid = ?");
			pstmt.setInt(1, isActive);
			pstmt.setInt(2, machineId);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out
					.println("Exception when changing the status of Machine in mst_machine table : " + ex.getMessage());
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
						"Exception when closing the connectin in changing the status of Machine in mst_machine table : "
								+ ex.getMessage());
			}
		}

		return msg;
	}

	public List<String> getMachineType() {
		List<String> lstMachineType = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select machinename from mst_machine where machinename <> ''");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				lstMachineType.add(rs.getString("machinename"));
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the getMachineType : " + ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getMachineType : " + ex.getMessage());
			}
		}
		return lstMachineType;

	}

	private String getNextMachineCode() {

		String machineCode = "Mac001";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select max(machineid) machineid from mst_machine");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				machineCode = "Mac00" + (rs.getInt("machineid") + 1);
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the getMachineType : " + ex.getMessage());
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
						"Exception when closing the connection in  get next machine code : " + ex.getMessage());
			}
		}
		return machineCode;

	}
}
