package com.tiim.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.tiim.model.ClearanceClosing;
import com.tiim.model.ProductReport;
import com.tiim.model.Stock;
import com.tiim.model.ToolingInspection;
import com.tiim.model.ToolingIssueNote;
import com.tiim.model.ToolingRequestNote;
import com.tiim.util.TiimUtil;

@Repository
public class ToolingIssueDao {

	@Autowired
	DataSource datasource;

	@Autowired
	StockDao stockDao;

	@Autowired
	ProductReportDao productReport;

	@Autowired
	TransactionHistoryDao historyDao;

	@Autowired
	MessageSource messageSource;

	@Autowired
	ProductDao productDao;

	@Autowired
	ClearanceClosingDao closingDao;

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat sdfDB = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfDBFull = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
	java.util.Date cDate = new java.util.Date();

	public int getIntialValue() {
		int issueId = 0;
		issueId = getMaxOriginalId();

		issueId++;
		/*
		 * Connection con = null; PreparedStatement pstmt = null; ResultSet rs = null;
		 * try { con = datasource.getConnection(); pstmt =
		 * con.prepareStatement("SELECT max(issueId) issueId FROM tooling_issue_note");
		 * rs = pstmt.executeQuery(); if(rs.next()) { issueId = rs.getInt("issueId"); }
		 * issueId++;
		 * 
		 * }catch(Exception ex) {
		 * System.out.println("Exception in ToolingIssueDao  getIntialValue  : "+ex.
		 * getMessage()); } finally { try { if(rs != null) { rs.close(); } if(pstmt !=
		 * null) { pstmt.close(); }if(con != null) { con.close(); } }catch(Exception ex)
		 * { System.out.
		 * println("Exception when closing the connection in ToolingIssueDao getIntialValue : "
		 * +ex.getMessage()); } }
		 */

		return issueId;
	}

	public String addToolingIssueNote(ToolingIssueNote issueNote, int userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try {

			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"insert into tooling_issue_note(issueDate, requestNo, requestDate, requestedBy, issueBy, branchname, originalid, visual_check_by)"
							+ "values(?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			// ,Statement.RETURN_GENERATED_KEYS
			Calendar calendar = Calendar.getInstance();
			java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			pstmt.setDate(1, date);
			pstmt.setInt(2, issueNote.getRequestNo());
			pstmt.setString(3, sdfDB.format(sdf.parse(issueNote.getRequestDate())));
			pstmt.setString(4, TiimUtil.ValidateNull(issueNote.getRequestBy()).trim());
			pstmt.setString(5, TiimUtil.ValidateNull(issueNote.getIssueBy()).trim());
			pstmt.setString(6, TiimUtil.ValidateNull(issueNote.getBranchName()).trim());
			int originalId = getMaxOriginalId();

			originalId++;
			issueNote.setOriginalId(originalId);

			pstmt.setInt(7, originalId);
			pstmt.setString(8, issueNote.getCheckedBy());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				issueNote.setIssueId(rs.getInt(1));
			}

			issueNote.setRevisionNumber(1);
			// updateOriginalId(issueNote);
			msg = addIssueNoteDetails(issueNote);
			msg = "Saved Successfully";
			updateToolingRequestStatus(issueNote, 1);
			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("productionIssue.page", null, null));
			history.setDescription(messageSource.getMessage("productionIssue.insert", null, null));
			history.setUserId(userId);
			historyDao.addHistory(history);

		} catch (Exception ex) {
			System.out.println("Exception when adding the tooling issue note initial value in getInitialValue table : "
					+ ex.getMessage());
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
						"Exception when closing the connection in tooling issue note initial value in getInitialValue : "
								+ ex.getMessage());
			}
		}

		return msg;
	}

	public String updateToolingIssueNote(ToolingIssueNote issueNote, String dbStatus, int userId, String approvalFlag) {
		getRequestVersions(issueNote);
		issueNote = getToolingIssueDetail(issueNote.getIssueId());
		if ("1".equals(approvalFlag) && issueNote != null && issueNote.getApprovalFlag() == 1) {
			storeTransaction1(issueNote, 1);
		} else if ("1".equals(approvalFlag) && issueNote != null) {
			storeTransaction1(issueNote, 1);
		}

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"insert into tooling_issue_note(issueDate, requestNo, requestDate, requestedBy, issueBy, branchname, originalId, versionNumber,visual_check_by)"
							+ "values(?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			// ,Statement.RETURN_GENERATED_KEYS
			Calendar calendar = Calendar.getInstance();
			java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			pstmt.setDate(1, date);
			pstmt.setInt(2, issueNote.getRequestNo());
			pstmt.setString(3, sdfDB.format(sdf.parse(issueNote.getRequestDate())));
			pstmt.setString(4, TiimUtil.ValidateNull(issueNote.getRequestBy()).trim());
			pstmt.setString(5, TiimUtil.ValidateNull(issueNote.getIssueBy()).trim());
			pstmt.setString(6, TiimUtil.ValidateNull(issueNote.getBranchName()).trim());
			pstmt.setInt(7, issueNote.getOriginalId());
			pstmt.setInt(8, issueNote.getRevisionNumber());
			pstmt.setString(9, issueNote.getCheckedBy());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				issueNote.setIssueId(rs.getInt(1));
			}
			msg = addIssueNoteDetails(issueNote);

			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("productionIssue.page", null, null));
			history.setDescription(messageSource.getMessage("productionIssue.update", null, null));
			history.setUserId(userId);
			historyDao.addHistory(history);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling issue note detail in tooling_issue_note table : "
					+ ex.getMessage());
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
						"Exception when closing the connection in tooling issue note detail in tooling_issue_note table : "
								+ ex.getMessage());
			}
		}
		return msg;

	}

	public String updateToolingIssueNoteOld(ToolingIssueNote issueNote, String dbStatus, int userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"update tooling_issue_note set issueDate = ?, requestNo = ?, requestDate = ?, requestedBy = ?, issueBy = ?, visual_check_by = ? "
							+ " where issueId = ?");

			Calendar calendar = Calendar.getInstance();
			java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			pstmt.setDate(1, date);
			pstmt.setInt(2, issueNote.getRequestNo());
			pstmt.setString(3, sdfDB.format(sdf.parse(issueNote.getRequestDate())));
			pstmt.setString(4, TiimUtil.ValidateNull(issueNote.getRequestBy()).trim());
			pstmt.setString(5, TiimUtil.ValidateNull(issueNote.getIssueBy()).trim());
			pstmt.setInt(6, issueNote.getIssueId());
			pstmt.setString(7, issueNote.getCheckedBy());
			pstmt.executeUpdate();

			msg = updateToolingIssueNote(issueNote);

			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("productionIssue.page", null, null));
			history.setDescription(messageSource.getMessage("productionIssue.update", null, null));
			history.setUserId(userId);
			historyDao.addHistory(history);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling issue note detail in tooling_issue_note table : "
					+ ex.getMessage());
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
						"Exception when closing the connection in tooling issue note detail in tooling_issue_note table : "
								+ ex.getMessage());
			}
		}
		return msg;

	}

	public String addIssueNoteDetails(ToolingIssueNote issueNote) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";

		String toolingLotNumber;
		String lastInspectionDate;
		String nextDueQty;
		String issuedQty;
		try {
			con = datasource.getConnection();
			for (int i = 0; i < issueNote.getRequestDetailId().length; i++) {
				pstmt1 = con.prepareStatement("Select requestdetailId, requestId, productName, machintype, drawingno,"
						+ " UOM, batchqty, requestqty, typeoftool from tooling_request_note_detail "
						+ " where requestdetailId = ?");

				pstmt1.setInt(1, issueNote.getRequestDetailId()[i]);
				ResultSet rs = pstmt1.executeQuery();
				while (rs.next()) {
					try {
						pstmt = con.prepareStatement(
								"insert into tooling_issue_note_detail(issueId, typeoftool, productname, machineName, drawingNo, batchQty, "
										+ " requestQty, toolinglotnumber, lastInspectionDate, nextDueQty, issuedQty, UOM, originalId, versionNumber,serialNumber,noofdays,requestdetailId,dustCup )"
										+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);

						pstmt.setInt(1, issueNote.getIssueId());

						pstmt.setString(2, TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
						pstmt.setString(3, TiimUtil.ValidateNull(rs.getString("productname")).trim());
						pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("machintype")).trim());
						pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("drawingNo")).trim());
						pstmt.setLong(6, rs.getLong("batchqty"));
						pstmt.setLong(7, rs.getLong("requestqty"));

						issueNote.setProductName1(rs.getString("productname"));
						issueNote.setTypeOfTooling1(rs.getString("typeoftool"));
						issueNote.setMachineName1(rs.getString("machintype"));
						issueNote.setDrawingNo1(rs.getString("drawingNo"));
						issueNote.setRequestQty1(rs.getLong("requestqty"));

						/*
						 * if (issueNote.getToolingLotNumber()[i] != null &&
						 * !"".equals(issueNote.getToolingLotNumber()[i])) { toolingLotNumber =
						 * TiimUtil.ValidateNull(issueNote.getToolingLotNumber()[i]).trim(); } else {
						 * toolingLotNumber = "0"; }
						 */
						pstmt.setString(8, issueNote.getToolingLotNumber1());

						if ((issueNote.getLastInspectionDate() != null)
								&& (issueNote.getLastInspectionDate().length > 0)
								&& (issueNote.getLastInspectionDate().length == (i + 1))
								&& (issueNote.getLastInspectionDate()[i] != null)
								&& !"".equals(issueNote.getLastInspectionDate()[i])
								&& issueNote.getLastInspectionDate()[i].length() == 10) {
							lastInspectionDate = TiimUtil.ValidateNull(issueNote.getLastInspectionDate()[i]).trim();
							lastInspectionDate = sdfDB.format(sdf.parse(lastInspectionDate));
						} else {
							lastInspectionDate = sdfDB.format(cDate);
						}
						pstmt.setString(9, lastInspectionDate);
						if (issueNote.getNextDueQty() != null && issueNote.getNextDueQty().length > 0
								&& issueNote.getNextDueQty().length == (i + 1) && issueNote.getNextDueQty()[i] != null
								&& !"".equals(issueNote.getNextDueQty()[i])) {
							nextDueQty = TiimUtil.ValidateNull(issueNote.getNextDueQty()[i]).trim();
						} else {
							nextDueQty = "0";
						}
						pstmt.setString(10, nextDueQty);

						if (issueNote.getIssuedQty()[i] != null && !"".equals(issueNote.getIssuedQty()[i])) {
							issuedQty = TiimUtil.ValidateNull(issueNote.getIssuedQty()[i]).trim();
						} else {
							issuedQty = "0";
						}
						pstmt.setString(11, issuedQty);
						pstmt.setString(12, TiimUtil.ValidateNull(rs.getString("UOM")).trim());
						pstmt.setInt(13, issueNote.getOriginalId());
						pstmt.setInt(14, issueNote.getRevisionNumber());
						issueNote.setUOM1(rs.getString("UOM"));
						pstmt.setString(15, issueNote.getSerialNumber());
						pstmt.setInt(16, issueNote.getNoOfDays());
						pstmt.setInt(17, issueNote.getRequestDetailId()[i]);
						pstmt.setString(18, issueNote.getDustCup());
						pstmt.executeUpdate();

						ResultSet rs1 = pstmt.getGeneratedKeys();
						if (rs1.next()) {
							issueNote.setIssueDetailId1(rs1.getInt(1));
						}

					//	storeTransaction(issueNote, i);
					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println(
								"Exception when adding the tooling issue note detail in tooling_issue_note_detail table : "
										+ ex.getMessage());
					} finally {
						try {
							if (pstmt != null) {
								pstmt.close();
							}
						} catch (Exception ex) {
							System.out.println(
									"Exception when closing the connection in tooling issue note detail in tooling_issue_note_detail table : "
											+ ex.getMessage());
						}
					}
				}
			}

			msg = "Saved Successfully";
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out
					.println("Exception when adding the tooling issue note detail in tooling_issue_note_detail table : "
							+ ex.getMessage());
		} finally {
			try {
				if (pstmt1 != null) {
					pstmt1.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connection in tooling issue note detail in tooling_issue_note_detail table : "
								+ ex.getMessage());
			}
		}
		return msg;

	}

	/*
	 * public String updateToolingIssueNote(ToolingIssueNote issueNote) { Connection
	 * con = null; PreparedStatement pstmt = null; PreparedStatement pstmt1 = null;
	 * String msg = "";
	 * 
	 * String toolingLotNumber; String lastInspectionDate; String nextDueQty; String
	 * issuedQty; try { con = datasource.getConnection(); for(int i = 0; i <
	 * issueNote.getRequestDetailId().length; i++) { pstmt1 = con.
	 * prepareStatement("Select requestdetailId, requestId, productName, machintype, drawingno,"
	 * + " UOM, batchqty, requestqty from tooling_request_note_detail " +
	 * " where requestdetailId = ?");
	 * 
	 * pstmt1.setInt(1, issueNote.getRequestDetailId()[i]); ResultSet rs =
	 * pstmt1.executeQuery(); while(rs.next()) { try { pstmt = con.
	 * prepareStatement("update tooling_issue_note_detail set issueId = ?, toolingName = ?, productname = ?, machineName = ?, drawingNo = ?,"
	 * +
	 * " batchQty = ?, requestQty = ?, toolinglotnumber = ?, lastInspectionDate = ?, nextDueQty = ?, issuedQty = ?, UOM = ? "
	 * + " where toolingIssueDetailId = ?");
	 * 
	 * pstmt.setInt(1, issueNote.getIssueId());
	 * 
	 * pstmt.setString(2,
	 * TiimUtil.ValidateNull(rs.getString("productName")).trim());
	 * pstmt.setString(3,
	 * TiimUtil.ValidateNull(rs.getString("productname")).trim());
	 * pstmt.setString(4, TiimUtil.ValidateNull(rs.getString("machintype")).trim());
	 * pstmt.setString(5, TiimUtil.ValidateNull(rs.getString("drawingNo")).trim());
	 * pstmt.setInt(6, rs.getInt("batchqty")); pstmt.setInt(7,
	 * rs.getInt("requestqty"));
	 * 
	 * if(issueNote.getToolingLotNumber()[i] != null &&
	 * !"".equals(issueNote.getToolingLotNumber()[i])) { toolingLotNumber =
	 * TiimUtil.ValidateNull(issueNote.getToolingLotNumber()[i]).trim(); } else {
	 * toolingLotNumber = "0"; } pstmt.setString(8, toolingLotNumber);
	 * 
	 * if(issueNote.getLastInspectionDate()[i] != null &&
	 * !"".equals(issueNote.getLastInspectionDate()[i]) &&
	 * issueNote.getLastInspectionDate()[i].length() == 10) { lastInspectionDate =
	 * TiimUtil.ValidateNull(issueNote.getLastInspectionDate()[i]).trim();
	 * lastInspectionDate = sdfDB.format(sdf.parse(lastInspectionDate)); } else {
	 * lastInspectionDate = ""; } pstmt.setString(9, lastInspectionDate);
	 * 
	 * if(issueNote.getNextDueQty()[i] != null &&
	 * !"".equals(issueNote.getNextDueQty()[i])) { nextDueQty =
	 * TiimUtil.ValidateNull(issueNote.getNextDueQty()[i]).trim(); } else {
	 * nextDueQty = "0"; } pstmt.setString(10, nextDueQty);
	 * 
	 * if(issueNote.getIssuedQty()[i] != null &&
	 * !"".equals(issueNote.getIssuedQty()[i])) { issuedQty =
	 * TiimUtil.ValidateNull(issueNote.getNextDueQty()[i]).trim(); } else {
	 * issuedQty = "0"; } pstmt.setString(11, issuedQty); pstmt.setString(12,
	 * TiimUtil.ValidateNull(rs.getString("UOM")).trim()); pstmt.setInt(13,
	 * issueNote.getIssueDetailId()[i]);
	 * 
	 * pstmt.executeUpdate(); }catch(Exception ex) { System.out.
	 * println("Exception when adding the tooling issue note detail in tooling_issue_note_detail table : "
	 * +ex.getMessage()); } finally { try { if(pstmt != null) { pstmt.close(); }
	 * }catch(Exception ex) { System.out.
	 * println("Exception when closing the connection in tooling issue note detail in tooling_issue_note_detail table : "
	 * +ex.getMessage()); } } } }
	 * 
	 * msg = "Updated Successfully";
	 * 
	 * } catch(Exception ex) { System.out.
	 * println("Exception when updating the tooling issue note detail in tooling_issue_note_detail table : "
	 * +ex.getMessage()); } finally { try { if(pstmt != null) { pstmt.close();
	 * }if(con != null) { con.close(); } }catch(Exception ex) { System.out.
	 * println("Exception when closing the connection in tooling issue note detail in tooling_issue_note_detail table : "
	 * +ex.getMessage()); } } return msg;
	 * 
	 * }
	 */

	public String updateToolingIssueNote(ToolingIssueNote issueNote) {
		//storeTransaction1(getToolingIssueDetail(issueNote.getIssueId()), 1);
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";

		String toolingLotNumber;
		String lastInspectionDate;
		String nextDueQty;
		String issuedQty;
		try {
			con = datasource.getConnection();
			for (int i = 0; i < issueNote.getIssueDetailId().length; i++) {
				try {
					pstmt = con.prepareStatement(
							"update tooling_issue_note_detail set toolinglotnumber = ?, lastInspectionDate = ?, nextDueQty = ?, issuedQty = ? where issueId = ? AND toolingIssueDetailId = ?");

					/*
					 * if (issueNote.getToolingLotNumber()[i] != null &&
					 * !"".equals(issueNote.getToolingLotNumber()[i])) { toolingLotNumber =
					 * TiimUtil.ValidateNull(issueNote.getToolingLotNumber()[i]).trim(); } else {
					 * toolingLotNumber = "0"; }
					 */
					pstmt.setString(1, issueNote.getToolingLotNumber1());

					if (issueNote.getLastInspectionDate()[i] != null && !"".equals(issueNote.getLastInspectionDate()[i])
							&& issueNote.getLastInspectionDate()[i].length() == 10) {
						lastInspectionDate = TiimUtil.ValidateNull(issueNote.getLastInspectionDate()[i]).trim();
						lastInspectionDate = sdfDB.format(sdf.parse(lastInspectionDate));
					} else {
						lastInspectionDate = "";
					}
					pstmt.setString(2, lastInspectionDate);

					if (issueNote.getNextDueQty()[i] != null && !"".equals(issueNote.getNextDueQty()[i])) {
						nextDueQty = TiimUtil.ValidateNull(issueNote.getNextDueQty()[i]).trim();
					} else {
						nextDueQty = "0";
					}
					pstmt.setString(3, nextDueQty);

					if (issueNote.getIssuedQty()[i] != null && !"".equals(issueNote.getIssuedQty()[i])) {
						issuedQty = TiimUtil.ValidateNull(issueNote.getIssuedQty()[i]).trim();
					} else {
						issuedQty = "0";
					}
					pstmt.setString(4, issuedQty);
					pstmt.setInt(5, issueNote.getIssueId());
					pstmt.setInt(6, issueNote.getIssueDetailId()[i]);

					pstmt.executeUpdate();
					System.out.println("before call: " + issueNote.getIssueDetailId()[i] + ", " + i);
					//storeTransaction(issueNote, i);
				} catch (Exception ex) {
					System.out.println(
							"Exception when adding the tooling issue note detail in tooling_issue_note_detail table : "
									+ ex.getMessage());
				} finally {
					try {
						if (pstmt != null) {
							pstmt.close();
						}
					} catch (Exception ex) {
						System.out.println(
								"Exception when closing the connection in tooling issue note detail in tooling_issue_note_detail table : "
										+ ex.getMessage());
					}
				}
			}

			msg = "Updated Successfully";
		} catch (Exception ex) {
			System.out.println(
					"Exception when updating the tooling issue note detail in tooling_issue_note_detail table : "
							+ ex.getMessage());
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
						"Exception when closing the connection in tooling issue note detail in tooling_issue_note_detail table : "
								+ ex.getMessage());
			}
		}
		return msg;

	}

	public ToolingIssueNote editToolingIssueNote(int issueId) {
		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"Select issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, visual_check_by from tooling_issue_note where issueId = ?");
			pstmt.setInt(1, issueId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setIssueDate(sdf.format(sdfDB.parse(rs.getString("issueDate"))));
				issueNote.setRequestNo(rs.getInt("requestNo"));
				issueNote.setRequestDate(sdf.format(sdfDB.parse(rs.getString("requestDate"))));
				issueNote.setRequestBy(rs.getString("requestedBy"));
				issueNote.setIssueBy(rs.getString("issueBy"));
				issueNote.setCheckedBy(rs.getString("visual_check_by"));
			}
		} catch (Exception ex) {
			System.out.println("Exception when editing the tooling_issue_note list : " + ex.getMessage());
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
						"Exception when closing the connection in the edit tooling_issue_note : " + ex.getMessage());
			}
		}
		return issueNote;

	}

	public List<ToolingIssueNote> getToolingIssueNote(String reqBy) {
		List<ToolingIssueNote> lstIssueNote = new ArrayList<ToolingIssueNote>();

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			if (reqBy == null || "".equals(reqBy)) {
				pstmt = con.prepareStatement(
						"select * from (Select issueId,isreport, issueDate, requestNo, requestDate, requestedBy, issueBy, originalid,visual_check_by from tooling_issue_note order by issueId desc)  x group by originalid");
			} else {
				pstmt = con.prepareStatement(
						"select * from (Select issueId,isreport, issueDate, requestNo, requestDate, requestedBy, issueBy, originalid,visual_check_by from tooling_issue_note where requestedBy "
								+ " like '%" + reqBy + "%'  order by issueId desc)  x group by originalid");
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setReportStatus(rs.getInt("isreport"));
				issueNote.setIssueDate(rs.getString("issueDate"));
				issueNote.setRequestNo(rs.getInt("requestNo"));
				issueNote.setRequestDate(rs.getString("requestDate"));
				issueNote.setRequestBy(rs.getString("requestedBy"));
				issueNote.setIssueBy(rs.getString("issueBy"));
				issueNote.setOriginalId(rs.getInt("originalid"));
				issueNote.setCheckedBy(rs.getString("visual_check_by"));
				lstIssueNote.add(issueNote);
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the tooling_issue_note list : " + ex.getMessage());
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
						"Exception when closing the connection in the tooling_issue_note list : " + ex.getMessage());
			}
		}
		return lstIssueNote;
	}

	public List<ToolingIssueNote> getToolingIssueNoteDetail(int issueId) {
		List<ToolingIssueNote> lstIssueNote = new ArrayList<ToolingIssueNote>();

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String inspDate = "";
		String lotNo = "";
		long nextDueQty;
		long issuedQty;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"Select max(toolingIssueDetailId) as toolingIssueDetailId, issueId, typeoftool, productname, machineName, drawingNo, batchQty, originalid, "
							+ " requestQty, toolinglotnumber, lastInspectionDate, nextDueQty, issuedQty, UOM, serialNumber, noofdays, requestdetailId from tooling_issue_note_detail"
							+ " where originalId = ?");
			pstmt.setInt(1, issueId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setIssueDetailId1(rs.getInt("toolingIssueDetailId"));
				issueNote.setTypeOfTooling1(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				issueNote.setProductName1(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				issueNote.setMachineName1(TiimUtil.ValidateNull(rs.getString("machineName")).trim());
				issueNote.setDrawingNo1(TiimUtil.ValidateNull(rs.getString("drawingNo")).trim());
				issueNote.setBatchQty1(rs.getLong("batchQty"));
				issueNote.setRequestQty1(rs.getLong("requestQty"));
				lotNo = TiimUtil.ValidateNull(rs.getString("toolinglotnumber"));
				if ("0".equals(lotNo)) {
					lotNo = "";
				}
				issueNote.setToolingLotNumber1(lotNo);
				inspDate = TiimUtil.ValidateNull(rs.getString("lastInspectionDate"));
				if (!"".equals(inspDate)) {
					inspDate = sdf.format(sdfDB.parse(inspDate));
				} else {
					inspDate = "";
				}
				issueNote.setLastInspectionDate1(inspDate);
				nextDueQty = rs.getLong("nextDueQty");

				issueNote.setNextDueQty1(nextDueQty);
				issuedQty = (rs.getLong("issuedQty"));

				issueNote.setIssuedQty1(issuedQty);
				issueNote.setUOM1(rs.getString("UOM"));
				issueNote.setSerialNumber(rs.getString("serialNumber"));
				issueNote.setNoOfDays(rs.getInt("noofdays"));
				issueNote.setRequestDetailId1(rs.getInt("requestdetailId"));
				lstIssueNote.add(issueNote);
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the tooling_issue_note list : " + ex.getMessage());
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
						"Exception when closing the connection in the tooling_issue_note list : " + ex.getMessage());
			}
		}
		return lstIssueNote;

	}

	public List<ToolingIssueNote> getToolingIssueNoteDetail1(int issueId) {
		List<ToolingIssueNote> lstIssueNote = new ArrayList<ToolingIssueNote>();

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String inspDate = "";
		String lotNo = "";
		long nextDueQty;
		long issuedQty;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"Select  toolingIssueDetailId, issueId, typeoftool, productname, machineName, drawingNo, batchQty, originalid, "
							+ " requestQty, toolinglotnumber, lastInspectionDate, nextDueQty, issuedQty, UOM, serialNumber, noofdays, requestdetailId,dustCup from tooling_issue_note_detail"
							+ " where issueId = ?");
			pstmt.setInt(1, issueId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setIssueDetailId1(rs.getInt("toolingIssueDetailId"));
				issueNote.setTypeOfTooling1(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				issueNote.setProductName1(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				issueNote.setMachineName1(TiimUtil.ValidateNull(rs.getString("machineName")).trim());
				issueNote.setDrawingNo1(TiimUtil.ValidateNull(rs.getString("drawingNo")).trim());
				issueNote.setBatchQty1(rs.getLong("batchQty"));
				issueNote.setRequestQty1(rs.getLong("requestQty"));
				lotNo = TiimUtil.ValidateNull(rs.getString("toolinglotnumber"));
				if ("0".equals(lotNo)) {
					lotNo = "";
				}
				issueNote.setToolingLotNumber1(lotNo);
				inspDate = TiimUtil.ValidateNull(rs.getString("lastInspectionDate"));
				if (!"".equals(inspDate)) {
					inspDate = sdf.format(sdfDB.parse(inspDate));
				} else {
					inspDate = "";
				}
				issueNote.setLastInspectionDate1(inspDate);
				nextDueQty = rs.getLong("nextDueQty");

				issueNote.setNextDueQty1(nextDueQty);
				issuedQty = (rs.getLong("issuedQty"));

				issueNote.setIssuedQty1(issuedQty);
				issueNote.setUOM1(rs.getString("UOM"));
				issueNote.setSerialNumber(rs.getString("serialNumber"));
				issueNote.setNoOfDays(rs.getInt("noofdays"));
				issueNote.setRequestDetailId1(rs.getInt("requestdetailId"));

				ClearanceClosing closing = closingDao.getTotalBatchQnty(rs.getString("toolinglotnumber"));
				issueNote.setBatchNumber(closing.getBatchNumber());
				issueNote.setTotalBatchQty(closing.getBatchQty());
				issueNote.setDustCup(rs.getString("dustCup"));
				lstIssueNote.add(issueNote);
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the tooling_issue_note list : " + ex.getMessage());
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
						"Exception when closing the connection in the tooling_issue_note list : " + ex.getMessage());
			}
		}
		return lstIssueNote;

	}

	public void getMaxToolIssueId(int originalId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"select max(issueid) issueid from tooling_issue_note_detail where originalid = ?");
			pstmt.setInt(1, originalId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int issueId = rs.getInt("issueid");
				//storeTransaction1(getToolingIssueDetail(issueId), 0);
			}

		} catch (Exception ex) {
			System.out.println("Exception when getting the tooling_issue_note list : " + ex.getMessage());
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
						"Exception when closing the connection in the tooling_issue_note list : " + ex.getMessage());
			}
		}
	}

	public ToolingIssueNote getToolingIssueDetail(int issueId) {
		// ToolingIssueNote lstIssueNote = new ToolingIssueNote();

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String inspDate = "";
		String lotNo = "";
		long nextDueQty;
		long issuedQty;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"Select toolingIssueDetailId, issueId, typeoftool, productname, machineName, drawingNo, batchQty,"
							+ " requestQty, toolinglotnumber, lastInspectionDate, nextDueQty, issuedQty, UOM, serialNumber, noofdays,requestdetailId, approvalflag from tooling_issue_note_detail"
							+ " where issueId = ?");
			pstmt.setInt(1, issueId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setIssueDetailId1(rs.getInt("toolingIssueDetailId"));
				issueNote.setTypeOfTooling1(TiimUtil.ValidateNull(rs.getString("typeoftool")).trim());
				issueNote.setProductName1(TiimUtil.ValidateNull(rs.getString("productname")).trim());
				issueNote.setMachineName1(TiimUtil.ValidateNull(rs.getString("machineName")).trim());
				issueNote.setDrawingNo1(TiimUtil.ValidateNull(rs.getString("drawingNo")).trim());
				issueNote.setBatchQty1(rs.getLong("batchQty"));
				issueNote.setRequestQty1(rs.getLong("requestQty"));
				lotNo = TiimUtil.ValidateNull(rs.getString("toolinglotnumber"));
				if ("0".equals(lotNo)) {
					lotNo = "";
				}
				issueNote.setToolingLotNumber1(lotNo);
				inspDate = TiimUtil.ValidateNull(rs.getString("lastInspectionDate"));
				if (!"".equals(inspDate)) {
					inspDate = sdf.format(sdfDB.parse(inspDate));
				} else {
					inspDate = "";
				}
				issueNote.setLastInspectionDate1(inspDate);
				nextDueQty = rs.getLong("nextDueQty");

				issueNote.setNextDueQty1(nextDueQty);
				issuedQty = (rs.getLong("issuedQty"));

				issueNote.setIssuedQty1(issuedQty);
				issueNote.setUOM1(rs.getString("UOM"));
				issueNote.setSerialNumber(rs.getString("serialNumber"));
				issueNote.setNoOfDays(rs.getInt("noofdays"));
				issueNote.setRequestDetailId1(rs.getInt("requestdetailId"));
				issueNote.setApprovalFlag(rs.getInt("approvalflag"));
				// System.out.println("issuedQty: "+issuedQty);
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the tooling_issue_note list : " + ex.getMessage());
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
						"Exception when closing the connection in the tooling_issue_note list : " + ex.getMessage());
			}
		}
		return issueNote;

	}

	public String changeToolingIssueNoteStatus(int issueId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String msg = "";
		int isActive = 0;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("Select isActive from tooling_issue_note where issueId = ?");
			pstmt.setInt(1, issueId);
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

			pstmt = con.prepareStatement("Update tooling_issue_note set isActive = ?  where issueId = ?");
			pstmt.setInt(1, isActive);
			pstmt.setInt(2, issueId);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("Exception when changing the status of request note in tooling_issue_note table : "
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
						"Exception when closing the connection in changing the status of tooling request in tooling_issue_note table : "
								+ ex.getMessage());
			}
		}

		return msg;
	}

	public String deleteToolingIssue(int issueId, int userId, String approvalFlag) {
		//storeTransaction1(getToolingIssueDetail(issueId), 1);
		ToolingIssueNote issueNote = getToolingIssueDetail(issueId);
		/*
		 * if ("1".equals(approvalFlag) && issueNote != null &&
		 * issueNote.getApprovalFlag() == 1) { storeTransaction1(issueNote, 1); } else
		 * if ("1".equals(approvalFlag) && issueNote != null) {
		 * storeTransaction1(issueNote, 1); }
		 */

		Connection con = null;
		PreparedStatement pstmt = null;
		String msg = "";
		try {

			con = datasource.getConnection();
			updateToolingRequestStatus(getIssueNoteByOriginalId(issueId), 0);
			pstmt = con.prepareStatement("delete from tooling_issue_note_detail where originalId = ?");
			pstmt.setInt(1, issueId);
			pstmt.executeUpdate();

			pstmt = con.prepareStatement("delete from tooling_issue_note where originalId = ?");
			pstmt.setInt(1, issueId);
			pstmt.executeUpdate();

			msg = "Deleted Successfully";

			TransactionHistory history = new TransactionHistory();
			history.setPageName(messageSource.getMessage("productionIssue.page", null, null));
			history.setDescription(messageSource.getMessage("productionIssue.delete", null, null));
			history.setUserId(userId);
			historyDao.addHistory(history);

		} catch (Exception ex) {
			System.out.println(
					"Exception when delete the detail in tooling_issue_note and tooling_issue_note_detail table : "
							+ ex.getMessage());
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
						"Exception when closing the connectin in delete the detail in tooling_issue_note and tooling_issue_note_detail table : "
								+ ex.getMessage());
			}
		}

		return msg;
	}

	public ToolingIssueNote getIssueNote(int issueId) {

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"Select issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, originalid, visual_check_by from tooling_issue_note where issueId = ?");
			pstmt.setInt(1, issueId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setOriginalId(rs.getInt("originalid"));
				issueNote.setIssueDate(rs.getString("issueDate"));
				// issueNote.setRequestNo(rs.getInt("requestNo"));
				// issueNote.setRequestDate(rs.getDate("requestDate"));
				// issueNote.setRequestedBy(rs.getString("requestedBy"));
				issueNote.setIssueBy(rs.getString("issueBy"));
				issueNote.setCheckedBy(rs.getString("visual_check_by"));
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the tooling_issue_note list : " + ex.getMessage());
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
						"Exception when closing the connection in the tooling_issue_note list : " + ex.getMessage());
			}
		}
		return issueNote;
	}

	public ToolingIssueNote getIssueNoteByOriginalId(int issueId) {

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"Select issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, originalid, visual_check_by from tooling_issue_note where originalid = ?");
			pstmt.setInt(1, issueId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setOriginalId(rs.getInt("originalid"));
				issueNote.setIssueDate(rs.getString("issueDate"));
				issueNote.setRequestNo(rs.getInt("requestNo"));
				// issueNote.setRequestDate(rs.getDate("requestDate"));
				// issueNote.setRequestedBy(rs.getString("requestedBy"));
				issueNote.setIssueBy(rs.getString("issueBy"));
				issueNote.setCheckedBy(rs.getString("visual_check_by"));
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the tooling_issue_note list : " + ex.getMessage());
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
						"Exception when closing the connection in the tooling_issue_note list : " + ex.getMessage());
			}
		}
		return issueNote;
	}

	public List<ToolingIssueNote> getAutoToolingIssueNote(String issueId, String approvalFlag) {
		List<ToolingIssueNote> lstIssueNote = new ArrayList<ToolingIssueNote>();

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			if ("1".equalsIgnoreCase(approvalFlag)) {
				pstmt = con.prepareStatement(
						"select * from (Select issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, originalid, visual_check_by from tooling_issue_note "
								+ " where issueId  like '" + issueId + "%' "
								// + "and approvalflag = ? "
								+ "and  originalid not in (select issueNo from tooling_production_return_note))  x group by originalid");
				// pstmt.setInt(1, 1);
			} else {
				pstmt = con.prepareStatement(
						"select * from (Select issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, originalid,visual_check_by from tooling_issue_note "
								+ " where issueId  like '" + issueId
								+ "%' and originalid not in (select issueNo from tooling_production_return_note))  x group by originalid");

			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setIssueId(rs.getInt("originalid"));
				issueNote.setIssueDate(rs.getString("issueDate"));
				issueNote.setRequestNo(rs.getInt("requestNo"));
				issueNote.setRequestDate(rs.getString("requestDate"));
				issueNote.setRequestBy(rs.getString("requestedBy"));
				issueNote.setIssueBy(rs.getString("issueBy"));
				issueNote.setCheckedBy(rs.getString("visual_check_by"));
				lstIssueNote.add(issueNote);
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the Autoincrement tooling_issue_note list : " + ex.getMessage());
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
						.println("Exception when closing the connection in the Autoincrement tooling_issue_note list : "
								+ ex.getMessage());
			}
		}
		return lstIssueNote;
	}

	public List<ToolingIssueNote> getPendingToolingIssueNote() {
		List<ToolingIssueNote> lstIssueNote = new ArrayList<ToolingIssueNote>();

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"select * from (Select a.issueId issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, b.productname productname, b.drawingno drawingno, b.machinename machinename, "
							+ " b.typeoftool typeoftool, a.originalId originalId, noofdays, visual_check_by from tooling_issue_note a, tooling_issue_note_detail b "
							+ " where a.approvalflag = 1 and a.issueId = b.issueId and a.originalId not in (select issueNo from tooling_production_return_note) order by a.issueId desc) x group by originalId ");

			rs = pstmt.executeQuery();
			while (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setIssueDate(rs.getString("issueDate"));
				issueNote.setRequestNo(rs.getInt("requestNo"));
				issueNote.setRequestDate(rs.getString("requestDate"));
				issueNote.setRequestBy(rs.getString("requestedBy"));
				issueNote.setIssueBy(rs.getString("issueBy"));
				issueNote.setOriginalId(rs.getInt("originalid"));
				issueNote.setCheckedBy(rs.getString("visual_check_by"));
				issueNote.setUploadPath(productDao.getProductUploadedPath(rs.getString("productname"),
						rs.getString("drawingno"), rs.getString("machinename"), rs.getString("typeoftool")));
				String dt = rs.getString("issueDate"); // Start date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(dt));
				c.add(Calendar.DATE, rs.getInt("noofdays")); // number of days to add
				dt = sdf.format(c.getTime());
				Date d = new Date();
				Date d1 = sdf.parse(sdf.format(d.getTime()));
				Date d2 = sdf.parse(rs.getString("issueDate"));
				if (d1.compareTo(d2) > 0) {
					issueNote.setNoOfDays(1);
				}
				lstIssueNote.add(issueNote);
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the Autoincrement tooling_issue_note list : " + ex.getMessage());
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
						.println("Exception when closing the connection in the Autoincrement tooling_issue_note list : "
								+ ex.getMessage());
			}
		}
		return lstIssueNote;
	}

	private void storeTransaction(ToolingIssueNote issueNote, int index) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		int returnQty = 0;
		try {
			con = datasource.getConnection();
			{
				/*
				 * pstmt1 = con.
				 * prepareStatement("Select requestdetailId, requestId, productName, machintype, drawingno,"
				 * + " UOM, batchqty, requestqty from tooling_request_note_detail " +
				 * " where requestdetailId = ?"); pstmt1.setInt(1,
				 * issueNote.getRequestDetailId()[index]); ResultSet rs = pstmt1.executeQuery();
				 * while(rs.next())
				 */
				{
					try {
						pstmt = con.prepareStatement(
								"insert into transaction(productname, drawingno, toolinglotnumber, UOM, stockqty,machinename, "
										+ " transactiondate, Source )" + "values(?, ?, ?, ?, ?, ?, ?, ? )");
						String lotNo = issueNote.getToolingLotNumber1();
						// ,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(issueNote.getProductName1()));
						pstmt.setString(2, TiimUtil.ValidateNull(issueNote.getDrawingNo1()));
						pstmt.setString(3, (lotNo));
						pstmt.setString(4, TiimUtil.ValidateNull(issueNote.getUOM1()));

						if (issueNote.getIssuedQty()[index] != null && !"".equals(issueNote.getIssuedQty()[index])) {
							returnQty = Integer.parseInt(issueNote.getIssuedQty()[index].trim());
						}

						pstmt.setInt(5, -returnQty);
						pstmt.setString(6, TiimUtil.ValidateNull(issueNote.getMachineName1()));
						pstmt.setString(7, sdf.format(cDate));
						pstmt.setString(8, "ToolingIssue");

						pstmt.executeUpdate();
					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println("Exception when storing the data to transaction in product issue note: "
								+ ex.getMessage());
					} finally {
						try {
							if (pstmt != null) {
								pstmt.close();
							}
						} catch (Exception ex) {
							System.out.println(
									"Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "
											+ ex.getMessage());
						}
					}
				}

			}
			//storeStock(issueNote, index);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(
					"Exception when adding the tooling receipt note detail in tooling_receiving_inspection_details table : "
							+ ex.getMessage());
		} finally {
			try {
				if (pstmt1 != null) {
					pstmt1.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "
								+ ex.getMessage());
			}
		}

	}

	private void storeStock(ToolingIssueNote issueNote, int index) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		int totalStock = 0;
		int returnQty = 0;
		Stock stock = new Stock();
		try {
			con = datasource.getConnection();
			{
				/*
				 * pstmt1 = con.
				 * prepareStatement("Select requestdetailId, requestId, productName, machintype, drawingno,"
				 * + " UOM, typeoftool, requestqty from tooling_request_note_detail " +
				 * " where requestdetailId = ?"); pstmt1.setInt(1,
				 * issueNote.getRequestDetailId()[index]); ResultSet rs = pstmt1.executeQuery();
				 * while(rs.next())
				 */
				{
					try {
						pstmt = con.prepareStatement(
								"SELECT stockQty, stockId FROM stock where toolinglotnumber = ? and typeoftool = ? and productname = ? and drawingno = ? and machinetype = ?");
						// ,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(issueNote.getToolingLotNumber()[index]));
						pstmt.setString(2, TiimUtil.ValidateNull(issueNote.getTypeOfTooling1()));
						pstmt.setString(3, issueNote.getProductName1());
						pstmt.setString(4, TiimUtil.ValidateNull(issueNote.getDrawingNo1()));
						pstmt.setString(5, issueNote.getMachineName1());

						if (issueNote.getIssuedQty()[index] != null && !"".equals(issueNote.getIssuedQty()[index])) {
							returnQty = Integer.parseInt(issueNote.getIssuedQty()[index].trim());
						}

						stock.setToolingLotNumber(issueNote.getToolingLotNumber()[index]);
						stock.setTypeOfTool(TiimUtil.ValidateNull(issueNote.getTypeOfTooling1()).trim());
						stock.setProductName(TiimUtil.ValidateNull(issueNote.getProductName1()).trim());
						stock.setDrawingNo(TiimUtil.ValidateNull(issueNote.getDrawingNo1()).trim());
						stock.setMachineName(issueNote.getMachineName1());
						stock.setUom(issueNote.getUOM1());
						stock.setStockQty(returnQty);
						stock.setBranch(issueNote.getBranchName());
						ResultSet rs1 = pstmt.executeQuery();
						if (rs1.next()) {
							System.out.println("-returnQty: " + returnQty);
							totalStock = rs1.getInt("stockQty") - returnQty;
							stock.setStockId(rs1.getInt("stockId"));
							stock.setStockQty(totalStock);
							stockDao.updateStock(stock);
						} else {
							stockDao.storeStock(stock);
						}

					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println("Exception when storing the data to transaction: " + ex.getMessage());
					} finally {
						try {
							if (pstmt != null) {
								pstmt.close();
							}
						} catch (Exception ex) {
							System.out.println(
									"Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "
											+ ex.getMessage());
						}
					}
				}

				msg = "Saved Successfully";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(
					"Exception when adding the tooling receipt note detail in tooling_receiving_inspection_details table : "
							+ ex.getMessage());
		} finally {
			try {
				if (pstmt1 != null) {
					pstmt1.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "
								+ ex.getMessage());
			}
		}

	}

	private void storeTransaction1(ToolingIssueNote issueNote, int flag) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		int returnQty = 0;
		try {
			con = datasource.getConnection();
			{
				/*
				 * pstmt1 = con.
				 * prepareStatement("Select requestdetailId, requestId, productName, machintype, drawingno,"
				 * + " UOM, batchqty, requestqty from tooling_request_note_detail " +
				 * " where requestdetailId = ?"); pstmt1.setInt(1,
				 * issueNote.getRequestDetailId1()); ResultSet rs = pstmt1.executeQuery();
				 */
				// while(rs.next())
				{
					try {
						pstmt = con.prepareStatement(
								"insert into transaction(productname, drawingno, toolinglotnumber, UOM, stockqty,machinename, "
										+ " transactiondate, Source )" + "values(?, ?, ?, ?, ?, ?, ?, ?)");
						System.out.println(issueNote.getToolingLotNumber1() + ", " + issueNote.getProductName1() + ", "
								+ issueNote.getDrawingNo1());
						String lotNo = issueNote.getToolingLotNumber1();
						// ,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(issueNote.getProductName1()));
						pstmt.setString(2, TiimUtil.ValidateNull(issueNote.getDrawingNo1()));
						pstmt.setString(3, (lotNo));
						pstmt.setString(4, TiimUtil.ValidateNull(issueNote.getUOM1()));

						pstmt.setLong(5, issueNote.getIssuedQty1());
						pstmt.setString(6, TiimUtil.ValidateNull(issueNote.getMachineName1()));
						pstmt.setString(7, sdf.format(cDate));
						pstmt.setString(8, "ToolingIssue");

						pstmt.executeUpdate();
					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println("Exception when storing the data to transaction in product issue note: "
								+ ex.getMessage());
					} finally {
						try {
							if (pstmt != null) {
								pstmt.close();
							}
						} catch (Exception ex) {
							System.out.println(
									"Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "
											+ ex.getMessage());
						}
					}
				}

			}
			//storeStock1(issueNote, flag);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(
					"Exception when adding the tooling receipt note detail in tooling_receiving_inspection_details table : "
							+ ex.getMessage());
		} finally {
			try {
				if (pstmt1 != null) {
					pstmt1.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "
								+ ex.getMessage());
			}
		}

	}

	private void storeStock1(ToolingIssueNote issueNote, int flag) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		String msg = "";
		long totalStock = 0;
		Stock stock = new Stock();
		try {
			con = datasource.getConnection();
			{
				/*
				 * pstmt1 = con.
				 * prepareStatement("Select requestdetailId, requestId, productName, machintype, drawingno,"
				 * + " UOM, typeoftool, requestqty from tooling_request_note_detail " +
				 * " where requestdetailId = ?"); pstmt1.setInt(1,
				 * issueNote.getRequestDetailId1()); ResultSet rs = pstmt1.executeQuery();
				 * while(rs.next())
				 */
				{
					try {
						pstmt = con.prepareStatement(
								"SELECT stockQty, stockId FROM stock where toolinglotnumber = ? and typeoftool = ? and productname = ? and drawingno = ? and machinetype = ?");
						// ,Statement.RETURN_GENERATED_KEYS
						pstmt.setString(1, TiimUtil.ValidateNull(issueNote.getToolingLotNumber1()));
						pstmt.setString(2, TiimUtil.ValidateNull(issueNote.getTypeOfTooling1()));
						pstmt.setString(3, issueNote.getProductName1());
						pstmt.setString(4, issueNote.getDrawingNo1());
						pstmt.setString(5, issueNote.getMachineName1());

						stock.setToolingLotNumber(issueNote.getToolingLotNumber1());
						stock.setTypeOfTool(TiimUtil.ValidateNull(issueNote.getTypeOfTooling1()).trim());
						stock.setProductName(TiimUtil.ValidateNull(issueNote.getProductName1()).trim());
						stock.setDrawingNo(TiimUtil.ValidateNull(issueNote.getDrawingNo1()).trim());
						stock.setMachineName(issueNote.getMachineName1());
						stock.setUom(issueNote.getUOM1());
						stock.setStockQty(issueNote.getIssuedQty1());
						stock.setBranch(issueNote.getBranchName());
						ResultSet rs1 = pstmt.executeQuery();
						if (rs1.next()) {
							if (flag == 1) {
								totalStock = rs1.getInt("stockQty") + issueNote.getIssuedQty1();
							} else {
								totalStock = rs1.getInt("stockQty") - issueNote.getIssuedQty1();
							}

							stock.setStockId(rs1.getInt("stockId"));
							stock.setStockQty(totalStock);
							stockDao.updateStock(stock);
						} else {
							stockDao.storeStock(stock);
						}

					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println("Exception when storing the data to transaction: " + ex.getMessage());
					} finally {
						try {
							if (pstmt != null) {
								pstmt.close();
							}
						} catch (Exception ex) {
							System.out.println(
									"Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "
											+ ex.getMessage());
						}
					}
				}

				msg = "Saved Successfully";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(
					"Exception when adding the tooling receipt note detail in tooling_receiving_inspection_details table : "
							+ ex.getMessage());
		} finally {
			try {
				if (pstmt1 != null) {
					pstmt1.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				System.out.println(
						"Exception when closing the connection in tooling receipt note detail in tooling_receiving_inspection_details table : "
								+ ex.getMessage());
			}
		}

	}

	public List<ToolingIssueNote> getToolingIssueNoteDetail(String approvalFlag) {
		List<ToolingIssueNote> lstIssueNote = new ArrayList<ToolingIssueNote>();

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			if ("1".equalsIgnoreCase(approvalFlag)) {
				pstmt = con.prepareStatement(
						"select * from (Select a.issueId issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, b.productname productname, b.drawingno drawingno, b.machinename machinename, "
								+ " b.typeoftool typeoftool, a.originalId originalId, noofdays, toolinglotnumber from tooling_issue_note a, tooling_issue_note_detail b "
								+ " where a.issueId = b.issueId and isAcknowledge = ?  and  a.originalId not in (select issueNo from tooling_production_return_note) order by a.issueId desc) x group by originalId ");
				// and a.approvalflag = ?
				pstmt.setInt(1, 1);
				// pstmt.setInt(2, 1);
			} else {
				pstmt = con.prepareStatement(
						"select * from (Select a.issueId issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, b.productname productname, b.drawingno drawingno, b.machinename machinename, "
								+ " b.typeoftool typeoftool, a.originalId originalId, noofdays, toolinglotnumber from tooling_issue_note a, tooling_issue_note_detail b "
								+ " where a.issueId = b.issueId and isAcknowledge = ? and  a.originalId not in (select issueNo from tooling_production_return_note) order by a.issueId desc) x group by originalId ");
				pstmt.setInt(1, 1);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setOriginalId(rs.getInt("originalId"));
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setIssueDate(rs.getString("issueDate"));
				issueNote.setRequestNo(rs.getInt("requestNo"));
				issueNote.setRequestDate(rs.getString("requestDate"));
				issueNote.setRequestBy(rs.getString("requestedBy"));
				issueNote.setIssueBy(rs.getString("issueBy"));
				issueNote.setProductName1(rs.getString("productname"));
				issueNote.setMachineName1(rs.getString("machinename"));
				issueNote.setTypeOfTooling1(rs.getString("typeoftool"));
				issueNote.setDrawingNo1(rs.getString("drawingno"));
				issueNote.setToolingLotNumber1(rs.getString("toolinglotnumber"));
				issueNote.setUploadPath(productDao.getProductUploadedPath(rs.getString("productname"),
						rs.getString("drawingno"), rs.getString("machinename"), rs.getString("typeoftool")));

				String dt = rs.getString("issueDate"); // Start date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(dt));
				c.add(Calendar.DATE, rs.getInt("noofdays")); // number of days to add
				dt = sdf.format(c.getTime());
				Date d = new Date();
				Date d1 = sdf.parse(sdf.format(d.getTime()));
				Date d2 = sdf.parse(rs.getString("issueDate"));
				if (d1.compareTo(d2) > 0) {
					issueNote.setNoOfDays(1);
				}
				lstIssueNote.add(issueNote);
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the Autoincrement tooling_issue_note list : " + ex.getMessage());
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
						.println("Exception when closing the connection in the Autoincrement tooling_issue_note list : "
								+ ex.getMessage());
			}
		}
		return lstIssueNote;
	}

	public List<ToolingIssueNote> getToolingIssueNoteDetail() {
		List<ToolingIssueNote> lstIssueNote = new ArrayList<ToolingIssueNote>();

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();

			pstmt = con.prepareStatement(
					"select * from (Select a.issueId issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, b.productname productname, b.drawingno drawingno, b.machinename machinename, "
							+ " b.typeoftool typeoftool, a.originalId originalId, noofdays, toolinglotnumber from tooling_issue_note a, tooling_issue_note_detail b "
							+ " where a.issueId = b.issueId and isAcknowledge = ? and  a.originalId not in (select issueNo from tooling_production_return_note) "
							+ " and  a.issueId not in (select issueid from mst_clearance_no where isClosed=0) order by a.issueId desc) "
							+ " x group by originalId ");
			pstmt.setInt(1, 1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setOriginalId(rs.getInt("originalId"));
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setIssueDate(rs.getString("issueDate"));
				issueNote.setRequestNo(rs.getInt("requestNo"));
				issueNote.setRequestDate(rs.getString("requestDate"));
				issueNote.setRequestBy(rs.getString("requestedBy"));
				issueNote.setIssueBy(rs.getString("issueBy"));
				issueNote.setProductName1(rs.getString("productname"));
				issueNote.setMachineName1(rs.getString("machinename"));
				issueNote.setTypeOfTooling1(rs.getString("typeoftool"));
				issueNote.setDrawingNo1(rs.getString("drawingno"));
				issueNote.setToolingLotNumber1(rs.getString("toolinglotnumber"));
				issueNote.setUploadPath(productDao.getProductUploadedPath(rs.getString("productname"),
						rs.getString("drawingno"), rs.getString("machinename"), rs.getString("typeoftool")));

				String dt = rs.getString("issueDate"); // Start date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(dt));
				c.add(Calendar.DATE, rs.getInt("noofdays")); // number of days to add
				dt = sdf.format(c.getTime());
				Date d = new Date();
				Date d1 = sdf.parse(sdf.format(d.getTime()));
				Date d2 = sdf.parse(rs.getString("issueDate"));
				if (d1.compareTo(d2) > 0) {
					issueNote.setNoOfDays(1);
				}
				lstIssueNote.add(issueNote);
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the Autoincrement tooling_issue_note list : " + ex.getMessage());
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
						.println("Exception when closing the connection in the Autoincrement tooling_issue_note list : "
								+ ex.getMessage());
			}
		}
		return lstIssueNote;
	}

	public List<ToolingIssueNote> getToolingClosingIssueNoteDetail(String approvalFlag) {
		List<ToolingIssueNote> lstIssueNote = new ArrayList<ToolingIssueNote>();

		ToolingIssueNote issueNote = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			/*
			 * if("1".equalsIgnoreCase(approvalFlag)) { pstmt = con.
			 * prepareStatement("select * from (Select a.issueId issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, b.productname productname, b.drawingno drawingno, b.machinename machinename, "
			 * +
			 * " b.typeoftool typeoftool, a.originalId originalId, noofdays, toolinglotnumber from tooling_issue_note a, tooling_issue_note_detail b "
			 * +
			 * " where a.issueId = b.issueId and isAcknowledge = ?  and  toolinglotnumber not in(SELECT lotnumber FROM mst_clearance_no where isclosed=0) and "
			 * +
			 * "  a.approvalflag = ? and  a.originalId not in (select issueNo from tooling_production_return_note) order by a.issueId desc) x group by originalId "
			 * ); pstmt.setInt(1, 1); pstmt.setInt(2, 1); }else { pstmt = con.
			 * prepareStatement("select * from (Select a.issueId issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, b.productname productname, b.drawingno drawingno, b.machinename machinename, "
			 * +
			 * " b.typeoftool typeoftool, a.originalId originalId, noofdays, toolinglotnumber from tooling_issue_note a, tooling_issue_note_detail b "
			 * +
			 * " where a.issueId = b.issueId and isAcknowledge = ? and  toolinglotnumber not in(SELECT lotnumber FROM mst_clearance_no where isclosed=0) and "
			 * +
			 * " a.originalId not in (select issueNo from tooling_production_return_note) order by a.issueId desc) x group by originalId "
			 * ); pstmt.setInt(1, 1); }
			 */
			if ("1".equalsIgnoreCase(approvalFlag)) {
				pstmt = con.prepareStatement(
						"select * from (Select a.issueId issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, b.productname productname, b.drawingno drawingno, b.machinename machinename, "
								+ " b.typeoftool typeoftool, a.originalId originalId, noofdays, toolinglotnumber, batchqty, requestqty from tooling_issue_note a, tooling_issue_note_detail b "
								+ " where a.issueId = b.issueId and isAcknowledge = ?  and  "
								//+ " a.issueId in (select issueid from mst_clearance_no where isClosed=0) and "
								// + " a.approvalflag = ? and "
								+ " a.originalId not in (select issueNo from tooling_production_return_note) order by a.issueId desc) x group by originalId ");
				pstmt.setInt(1, 1);
				// pstmt.setInt(2, 1);
			} else {
				pstmt = con.prepareStatement(
						"select * from (Select a.issueId issueId, issueDate, requestNo, requestDate, requestedBy, issueBy, b.productname productname, b.drawingno drawingno, b.machinename machinename, "
								+ " b.typeoftool typeoftool, a.originalId originalId, noofdays, toolinglotnumber, batchqty, requestqty from tooling_issue_note a, tooling_issue_note_detail b "
								+ " where a.issueId = b.issueId and isAcknowledge = ? and "
								//+ " a.issueId in (select issueid from mst_clearance_no where isClosed=0) and "
								+ " a.originalId not in (select issueNo from tooling_production_return_note) order by a.issueId desc) x group by originalId ");
				pstmt.setInt(1, 1);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				issueNote = new ToolingIssueNote();
				issueNote.setOriginalId(rs.getInt("originalId"));
				issueNote.setIssueId(rs.getInt("issueId"));
				issueNote.setIssueDate(rs.getString("issueDate"));
				issueNote.setRequestNo(rs.getInt("requestNo"));
				issueNote.setRequestDate(rs.getString("requestDate"));
				issueNote.setRequestBy(rs.getString("requestedBy"));
				issueNote.setIssueBy(rs.getString("issueBy"));
				issueNote.setProductName1(rs.getString("productname"));
				issueNote.setMachineName1(rs.getString("machinename"));
				issueNote.setTypeOfTooling1(rs.getString("typeoftool"));
				issueNote.setDrawingNo1(rs.getString("drawingno"));
				issueNote.setToolingLotNumber1(rs.getString("toolinglotnumber"));
				issueNote.setRequestQty1(rs.getLong("requestqty"));
				issueNote.setBatchQty1(rs.getLong("batchqty"));
				issueNote.setUploadPath(productDao.getProductUploadedPath(rs.getString("productname"),
						rs.getString("drawingno"), rs.getString("machinename"), rs.getString("typeoftool")));

				String dt = rs.getString("issueDate"); // Start date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(dt));
				c.add(Calendar.DATE, rs.getInt("noofdays")); // number of days to add
				dt = sdf.format(c.getTime());
				Date d = new Date();
				Date d1 = sdf.parse(sdf.format(d.getTime()));
				Date d2 = sdf.parse(rs.getString("issueDate"));
				if (d1.compareTo(d2) > 0) {
					issueNote.setNoOfDays(1);
				}
				lstIssueNote.add(issueNote);
			}
		} catch (Exception ex) {
			System.out.println("Exception when getting the Autoincrement tooling_issue_note list : " + ex.getMessage());
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
						.println("Exception when closing the connection in the Autoincrement tooling_issue_note list : "
								+ ex.getMessage());
			}
		}
		return lstIssueNote;
	}

	public ProductReport getNextQty(String lotNumber, String productName, String drawingNumber, String machineName,
			String typeOfTool) {
		int lotNumberCount = productReport.getLotNumberCount(lotNumber);
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductReport obj = new ProductReport();
		try {
			con = datasource.getConnection();
			/*
			 * pstmt = con.
			 * prepareStatement("select toolinglotnumber, a.productname productname, a.drawingno drawingno, a.machinetype machinetype, b.typeoftool typeoftool, "
			 * +
			 * " sum(b.producedqty) producedqty, a.serviceintervalqty serviceintervalqty,  a.toolinglife toolinglife"
			 * + " from mst_product a, tooling_production_return_note_detail b" +
			 * " where a.productname = b.productname and a.drawingno = b.drawingno  and" +
			 * " a.productname = ? and a.drawingno = ? and a.machinetype = ? and b.typeoftool = ?"
			 * + " group by toolinglotnumber");
			 */
			pstmt = con.prepareStatement(
					"select b.toolinglotnumber toolinglotnumber, a.productname productname, a.drawingno drawingno, a.machinetype machinetype, b.typeoftool typeoftool, "
							+ " sum(b.producedqty) producedqty, a.serviceintervalqty serviceintervalqty,  a.toolinglife toolinglife"
							+ " from tooling_receipt_product a, tooling_production_return_note_detail b"
							+ " where a.productname = b.productname and a.drawingno = b.drawingno  and "
							+ " a.productname = ? and a.drawingno = ? and a.machinetype = ? and b.typeoftool = ? and b.toolinglotnumber = ? "
							+ " group by b.toolinglotnumber");
			pstmt.setString(1, productName);
			pstmt.setString(2, drawingNumber);
			pstmt.setString(3, machineName);
			pstmt.setString(4, typeOfTool);
			pstmt.setString(5, lotNumber);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				obj = new ProductReport();
				obj.setLotNumber(rs.getString("toolinglotnumber"));
				obj.setProductName(rs.getString("productname"));
				obj.setMachine(rs.getString("machinetype"));
				obj.setDrawingNumber(rs.getString("drawingno"));
				obj.setToolingLife(rs.getInt("toolinglife"));
				obj.setTypeOfTool(rs.getString("typeoftool"));
				obj.setServiceIntervalQty(rs.getInt("serviceintervalqty"));
				obj.setProducedQty(rs.getInt("producedqty"));
				// System.out.println(rs.getInt("producedqty"));

				long totalIntervalQuantity = lotNumberCount * rs.getInt("serviceintervalqty");
				long balance = rs.getInt("producedqty") - totalIntervalQuantity;
				long balanceProdQty = rs.getInt("serviceintervalqty") - balance;
				long nextQty = rs.getInt("producedqty") + balanceProdQty;
				//if (lotNumberCount == 0) 
				{
					nextQty = rs.getInt("serviceintervalqty");
				}
				obj.setNextDueQty(nextQty);
				obj.setBalanceProducedQty(balanceProdQty);
				// System.out.println("lotNumberCount: "+lotNumberCount+",totalIntervalQuantity:
				// "+totalIntervalQuantity+", "+balance+", balanceProdQty: "+balanceProdQty);
				return obj;

			}

		} catch (Exception ex) {
			System.out.println("Exception in ProductReportDao getProductReport : " + ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductReportDao getProductReport  : "
						+ ex.getMessage());
			}
		}

		return obj;

	}

	public long getNextQtyFromProduct(String productName, String drawingNumber, String machineName, String typeOfTool) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			// pstmt = con.prepareStatement("select serviceintervalqty from mst_product
			// where productname = ? and drawingno = ? and machinetype = ? and typeoftool =
			// ?");
			pstmt = con.prepareStatement(
					"select serviceintervalqty from tooling_receipt_product where productname = ? and drawingno = ? and machinetype = ? and typeoftool = ?");
			pstmt.setString(1, productName);
			pstmt.setString(2, drawingNumber);
			pstmt.setString(3, machineName);
			pstmt.setString(4, typeOfTool);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				long nextQty = rs.getInt("serviceintervalqty");
				return nextQty;

			}

		} catch (Exception ex) {
			System.out.println("Exception in ProductReportDao getProductReport : " + ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductReportDao getProductReport  : "
						+ ex.getMessage());
			}
		}

		return 0;

	}

	public ProductReport getNextQtyFromReceipt(String productName, String drawingNumber, String machineName,
			String typeOfTool, String lotNumber) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductReport product = new ProductReport();
		try {
			con = datasource.getConnection();
			// pstmt = con.prepareStatement("select serviceintervalqty from mst_product
			// where productname = ? and drawingno = ? and machinetype = ? and typeoftool =
			// ?");
			pstmt = con.prepareStatement(
					"select serviceintervalqty, toolinglife from tooling_receipt_product where productname = ? and"
							+ " drawingno = ? and machinetype = ? and typeoftool = ? and toolinglotnumber = ?");
			pstmt.setString(1, productName);
			pstmt.setString(2, drawingNumber);
			pstmt.setString(3, machineName);
			pstmt.setString(4, typeOfTool);
			pstmt.setString(5, lotNumber);
			// System.out.println(productName+", "+ drawingNumber+", "+ machineName+ ", "+
			// typeOfTool);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// System.out.println("next qty: "+rs.getInt("serviceintervalqty"));
				product.setNextDueQty(rs.getInt("serviceintervalqty"));
				product.setToolingLife(rs.getInt("toolinglife"));

			}

		} catch (Exception ex) {
			System.out.println("Exception in ProductReportDao getProductReport : " + ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductReportDao getProductReport  : "
						+ ex.getMessage());
			}
		}

		return product;

	}

	public long getStockQty(String lotNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select stockqty from stock where toolinglotnumber = ?");
			pstmt.setString(1, lotNo);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				long stockQty = rs.getInt("stockqty");
				return stockQty;

			}

		} catch (Exception ex) {
			System.out.println("Exception in getStockQty : " + ex.getMessage());
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
				System.out.println("Exception when closing the connection in  getStockQty  : " + ex.getMessage());
			}
		}

		return 0;

	}

	public long getIntervalQuantity(String productName, String drawingNumber, String machineType, String typeOfTool,
			String lotNumber) {

		List<ProductReport> lstProductReports = new ArrayList<ProductReport>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long balanceProducedQty = 0;
		try {
			con = datasource.getConnection();
			String query = "select b.toolinglotnumber toolinglotnumber, a.productname productname, a.drawingno drawingno, a.machinetype machinetype, b.typeoftool typeoftool, "
					+ " sum(b.producedqty) producedqty, a.serviceintervalqty serviceintervalqty,  a.toolinglife toolinglife"
					+ " from tooling_receipt_product a, tooling_production_return_note_detail b"
					+ " where a.productname = b.productname and a.drawingno = b.drawingno  " + "  and a.productname = '"
					+ productName + "' " + "  and a.drawingno = '" + drawingNumber + "' " + "  and a.machinetype = '"
					+ machineType + "'" + "  and b.typeoftool = '" + typeOfTool + "'" + " and b.toolinglotnumber = '"
					+ lotNumber + "'  ";

			System.out.println("query: " + query);

			pstmt = con.prepareStatement(query);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductReport obj = new ProductReport();
				int noOfLot = getLotNumberCount(rs.getString("toolinglotnumber"));
				obj.setLotNumber(rs.getString("toolinglotnumber"));
				obj.setProductName(rs.getString("productname"));
				obj.setMachine(rs.getString("machinetype"));
				obj.setDrawingNumber(rs.getString("drawingno"));
				obj.setToolingLife(rs.getInt("toolinglife"));
				obj.setTypeOfTool(rs.getString("typeoftool"));
				obj.setServiceIntervalQty(rs.getInt("serviceintervalqty"));
				obj.setProducedQty(rs.getInt("producedqty"));

				long totalIntervalQuantity = noOfLot * rs.getInt("serviceintervalqty");
				long balance = rs.getInt("producedqty") - totalIntervalQuantity;
				float serviceAvg = (float) balance / (float) obj.getServiceIntervalQty();
				float productServiceIntervalQty = serviceAvg * 100;
				balanceProducedQty = obj.getServiceIntervalQty() - balance;

				obj.setBalanceProducedQty(balanceProducedQty);
				obj.setProducedQtyPercentage(productServiceIntervalQty);
				// if(productServiceIntervalQty <= obj.getProducedQty())
				lstProductReports.add(obj);
			}

		} catch (Exception ex) {
			System.out.println("Exception in ProductReportDao getProductReport : " + ex.getMessage());
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
				System.out.println("Exception when closing the connection in  ProductReportDao getProductReport  : "
						+ ex.getMessage());
			}
		}

		return balanceProducedQty;

	}

	public int getLotNumberCount(String lotNumber) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int lotCount = 0;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"SELECT count(toolinglotnumber) noOfLotNumber FROM periodic_inspection_report_detail where toolinglotnumber = ?");
			pstmt.setString(1, lotNumber);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				lotCount = rs.getInt("noOfLotNumber");
			}

		} catch (Exception ex) {
			System.out.println("Exception in getLotNumberCount : " + ex.getMessage());
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
				System.out.println("Exception when closing the connection in getLotNumberCount: " + ex.getMessage());
			}
		}

		return lotCount;
	}

	public void updateOriginalId(ToolingIssueNote issueNote) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_issue_note set originalid = ? " + " where issueId = ?");

			pstmt.setInt(1, issueNote.getOriginalId());
			pstmt.setInt(2, issueNote.getIssueId());
			pstmt.executeUpdate();

		} catch (Exception ex) {
			System.out.println(
					"Exception when updating the tooling receipt note detail in ToolingIssueNote updateOriginalId : "
							+ ex.getMessage());
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
						"Exception when closing the connection in updating detail in ToolingIssueNote updateOriginalId : "
								+ ex.getMessage());
			}
		}
	}

	/**
	 * Get original Id from tooling_receiving_request
	 * 
	 * @param requestId
	 * @return
	 */
	private void getRequestVersions(ToolingIssueNote issueNote) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement(
					"select originalId, versionNumber from tooling_issue_note " + " where issueId = ?");

			pstmt.setInt(1, issueNote.getIssueId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				issueNote.setOriginalId(rs.getInt("originalId"));
				int revisionNumber = rs.getInt("versionNumber");
				revisionNumber++;
				issueNote.setRevisionNumber(revisionNumber);
			}
		} catch (Exception ex) {
			System.out.println(
					"Exception when updating the tooling receipt note detail in ToolingIssueNote updateOriginalId : "
							+ ex.getMessage());
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
						"Exception when closing the connection in updating detail in ToolingIssueNote updateOriginalId : "
								+ ex.getMessage());
			}
		}

	}

	public boolean isIntegratedWithProductionRequest(int originalId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = datasource.getConnection();
			// pstmt = con.prepareStatement("SELECT requestNo FROM tooling_issue_note where
			// requestNo in (select requestId from tooling_request_note where originalId =
			// ?)");
			pstmt = con.prepareStatement("SELECT requestNo FROM tooling_issue_note where requestNo = ?");

			pstmt.setInt(1, originalId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception ex) {
			System.out.println(
					"Exception when ToolingIssueDao  isIntegratedWithProductionRequest method : " + ex.getMessage());
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
						"Exception when closing the connection in updating detail in ToolingIssueDao isIntegratedWithProductionRequest : "
								+ ex.getMessage());
			}
		}

		return false;
	}

	public boolean isIntegratedWithProductionIssue(int originalId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = datasource.getConnection();
			pstmt = con.prepareStatement("SELECT issueNo FROM tooling_production_return_note where issueNo = ?");

			pstmt.setInt(1, originalId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception ex) {
			System.out.println(
					"Exception when ToolingIssueDao  isIntegratedWithProductionIssue method : " + ex.getMessage());
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
						"Exception when closing the connection in updating detail in ToolingIssueDao isIntegratedWithProductionIssue : "
								+ ex.getMessage());
			}
		}

		return false;
	}

	/**
	 * Get original Id from tooling_receiving_request
	 * 
	 * @param requestId
	 * @return
	 */
	private int getMaxOriginalId() {
		int originalId = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("select max(originalid) originalid from tooling_issue_note");
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				originalId = rs.getInt("originalid");
			}

		} catch (Exception ex) {
			System.out.println(
					"Exception when updating the tooling receipt note detail in tooling_issue_note getMaxOriginalId : "
							+ ex.getMessage());
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
						"Exception when closing the connection in updating detail in tooling_issue_note getMaxOriginalId : "
								+ ex.getMessage());
			}
		}

		return originalId;
	}

	private void updateToolingRequestStatus(ToolingIssueNote issueNote, int status) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = datasource.getConnection();
			pstmt = con.prepareStatement("update tooling_request_note set isreport = ? where originalId = ?");
			pstmt.setInt(1, status);
			pstmt.setInt(2, issueNote.getRequestNo());
			pstmt.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in updateToolingRequestStatus  : "
					+ ex.getMessage());
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
						"Exception when closing the connection in tooling receipt note detail in updateToolingRequestStatus table : "
								+ ex.getMessage());
			}
		}

	}

	public String getStorageLocation(String lotNumber) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String storageLocation = "";
		try {
			con = datasource.getConnection();
			pstmt = con
					.prepareStatement("SELECT storagelocation FROM tooling_receipt_product where toolinglotnumber = ?");
			pstmt.setString(1, lotNumber);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				storageLocation = rs.getString("storagelocation");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception when adding the tooling receipt note detail in getStorageLocation  : "
					+ ex.getMessage());
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
						"Exception when closing the connection in tooling receipt note detail in getStorageLocation table : "
								+ ex.getMessage());
			}
		}
		return storageLocation;

	}

}
