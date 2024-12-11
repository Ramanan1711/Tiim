package com.tiim.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tiim.model.DestructionSerialId;
import com.tiim.model.ToolDestructionNote;
import com.tiim.model.ToolSerialNumber;

@Repository
public class ToolDestructionDao {

	@Autowired
	DataSource datasource;

	@Autowired
	TransactionHistoryDao historyDao;

	public List<ToolDestructionNote> getSerialNumbers(DestructionSerialId destructionSerialId) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ToolDestructionNote> toolDestructionNotes = new ArrayList<ToolDestructionNote>();
		int[] serialIds = destructionSerialId.getSerialId();
		try {
			con = datasource.getConnection();
			if (serialIds != null) {
				for (int i = 0; i < serialIds.length; i++) {
					System.out.println("serialIds[i]: "+serialIds[i]);
					pstmt = con.prepareStatement(
							"select serialId, lotnumber, serialNumber, module, rejectQty, acceptQty from tool_serial_number where serialId = ? ");
					pstmt.setInt(1, serialIds[i]);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						ToolDestructionNote toolDestructionNote = new ToolDestructionNote();
						toolDestructionNote.setSerialId(rs.getInt("serialId"));
						toolDestructionNote.setLotNumber(rs.getString("lotnumber"));
						long millis = System.currentTimeMillis();
						toolDestructionNote.setCreationDate(new Date(millis));
						toolDestructionNote.setDestructionBy("system");
						toolDestructionNotes.add(toolDestructionNote);
					}
				}
			}

		} catch (Exception ex) {
			System.out.println("Exception when delete the serial number : " + ex.getMessage());
			ex.printStackTrace();
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
						"Exception when closing the connection in delete the serial number : " + ex.getMessage());
			}
		}

		return toolDestructionNotes;

	}

	public void addDestructionNote(DestructionSerialId destructionSerialId) {

		List<ToolDestructionNote> toolDestructionNotes = getSerialNumbers(destructionSerialId);
		System.out.println("toolDestructionNotes: "+toolDestructionNotes.toString());
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			for (ToolDestructionNote destructionNote : toolDestructionNotes) {
				System.out.println("destructionNote: "+destructionNote.getSerialId());
				pstmt = con.prepareStatement(
						"insert into tool_desctruction_note_detail(serialId, lotnumber, destruction_by, created_at) values(?, ?, ?, ?)");
				pstmt.setInt(1, destructionNote.getSerialId());
				pstmt.setString(2, destructionNote.getLotNumber());
				pstmt.setString(3, destructionNote.getDestructionBy());
				long millis = System.currentTimeMillis();
				pstmt.setDate(4, new Date(millis));

				pstmt.executeUpdate();
			}

		} catch (Exception ex) {
			System.out.println("Exception when add the serial number : " + ex.getMessage());
			ex.printStackTrace();
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
						.println("Exception when closing the connection in add the serial number : " + ex.getMessage());
			}
		}
	}
	
	public void approveDestructionNote(DestructionSerialId destructionSerialId) {

		List<ToolDestructionNote> toolDestructionNotes = getSerialNumbers(destructionSerialId);
		System.out.println("toolDestructionNotes: "+toolDestructionNotes.toString());
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			for (ToolDestructionNote destructionNote : toolDestructionNotes) {
				System.out.println("destructionNote: "+destructionNote.getSerialId());
				pstmt = con.prepareStatement(
						"insert into tool_desctruction_note_detail(serialId, lotnumber, destruction_by, created_at) values(?, ?, ?, ?)");
				pstmt.setInt(1, destructionNote.getSerialId());
				pstmt.setString(2, destructionNote.getLotNumber());
				pstmt.setString(3, destructionNote.getDestructionBy());
				long millis = System.currentTimeMillis();
				pstmt.setDate(4, new Date(millis));

				pstmt.executeUpdate();
			}

		} catch (Exception ex) {
			System.out.println("Exception when add the serial number : " + ex.getMessage());
			ex.printStackTrace();
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
						.println("Exception when closing the connection in add the serial number : " + ex.getMessage());
			}
		}
	}

	public List<ToolDestructionNote> getToolDestructionNote() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ToolDestructionNote> toolDestuctionNotes = new ArrayList<ToolDestructionNote>();
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"SELECT t.id, t.lotnumber, s.serialNumber, s.rejectqty, s.module, t.destruction_by, t.created_At, t.serialId FROM "
					+ " tool_desctruction_note_detail t join tool_serial_number s on t.serialid = s.serialid");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ToolDestructionNote toolDestructionNote = new ToolDestructionNote();
				toolDestructionNote.setId(rs.getInt("id"));
				toolDestructionNote.setDestructionBy(rs.getString("destruction_by"));
				toolDestructionNote.setSerialId(rs.getInt("serialId"));
				toolDestructionNote.setLotNumber(rs.getString("lotnumber"));
				toolDestructionNote.setCreationDate(rs.getDate("created_at"));
				toolDestructionNote.setModule(rs.getString("module"));
				toolDestructionNote.setSerialNumber(rs.getString("serialNumber"));
				toolDestructionNote.setRejectedQty(rs.getInt("rejectqty"));
				toolDestuctionNotes.add(toolDestructionNote);
			}

		} catch (Exception ex) {
			System.out.println("Exception when add the serial number : " + ex.getMessage());
			ex.printStackTrace();
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
						.println("Exception when closing the connection in add the serial number : " + ex.getMessage());
			}
		}
		return toolDestuctionNotes;
	}

	public List<ToolSerialNumber> getSerialNumberForDestruction() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ToolSerialNumber> lstSerialNumber = new ArrayList<ToolSerialNumber>();
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"select serialId, lotnumber, serialNumber, module, rejectQty, acceptQty from tool_serial_number"
							+ " where serialId not in (select serialId from tool_desctruction_note_detail)");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ToolSerialNumber serialNumber = new ToolSerialNumber();
				serialNumber.setSerialId(rs.getLong("serialId"));
				serialNumber.setLotNumber(rs.getString("lotnumber"));
				serialNumber.setSerialNumber(rs.getString("serialNumber"));
				serialNumber.setModule(rs.getString("module"));
				serialNumber.setRejectQty(rs.getInt("rejectQty"));
				serialNumber.setAcceptQty(rs.getInt("acceptQty"));
				lstSerialNumber.add(serialNumber);
			}

		} catch (Exception ex) {
			System.out.println("Exception when delete the serial number : " + ex.getMessage());
			ex.printStackTrace();
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
						"Exception when closing the connection in delete the serial number : " + ex.getMessage());
			}
		}

		return lstSerialNumber;
	}

}
