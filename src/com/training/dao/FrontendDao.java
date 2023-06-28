package com.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.training.model.Goods;
import com.training.model.Member;

public class FrontendDao {

	private static FrontendDao frontendDao = new FrontendDao();

	private FrontendDao() {
	}

	public static FrontendDao getInstance() {
		return frontendDao;
	}

	/**
	 * 前臺顧客登入查詢
	 * 
	 * @param identificationNo
	 * @return Member
	 */
	public Member queryMemberByIdentificationNo(String inputID) {
		Member member = null;
		String querySQL = "SELECT * FROM BEVERAGE_MEMBER WHERE IDENTIFICATION_NO = ?";
		try (Connection conn = DBConnectionFactory.getLocalDBConnection();
				PreparedStatement stmt = conn.prepareStatement(querySQL);) {
			stmt.setString(1, inputID);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					member = new Member();
					member.setIdentificationNo(rs
							.getString("IDENTIFICATION_NO"));
					member.setPassword(rs.getString("PASSWORD"));
					member.setCustomerName(rs.getString("CUSTOMER_NAME"));
					break;
				}
			} catch (SQLException e) {
				throw e;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}
	
	/**
	 * 前臺顧客透過(有/無)關鍵字，查所有商品
	 * 
	 * @param searchKeyword
	 */
	public List<Goods> searchAllGoods(String searchKeyword) {
		List<Goods> goods = new ArrayList<>();
		String querySQL = null;
		if (searchKeyword != null && searchKeyword != "") {
			querySQL = 
				        "select ROWNUM ROW_NUM, GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, "
				       + "QUANTITY, IMAGE_NAME, STATUS " + "from BEVERAGE_GOODS " 
				       + "where regexp_like(GOODS_NAME,?,'i')";
				 
		} else {
			querySQL = " SELECT * FROM BEVERAGE_GOODS WHERE STATUS = 1 ";
		}
		try (Connection conn = DBConnectionFactory.getLocalDBConnection();
				PreparedStatement stmt = conn.prepareStatement(querySQL)) {

			int count = 1;
			if (searchKeyword != null && searchKeyword != "") {
				stmt.setString(count++,searchKeyword);
			}

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {

					Goods good = new Goods();
					good.setGoodsID(rs.getInt("GOODS_ID"));
					good.setGoodsName(rs.getString("GOODS_NAME"));
					good.setGoodsPrice(rs.getInt("PRICE"));
					good.setGoodsQuantity(rs.getInt("QUANTITY"));
					good.setGoodsImageName(rs.getString("IMAGE_NAME"));
					good.setStatus(rs.getString("STATUS"));

					goods.add(good);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return goods;
	}

	/**
	 * 前臺顧客(有/無)搜尋關鍵字，指定頁數查商品
	 * 
	 * @param searchKeyword
	 * @param startRowNo
	 * @param endRowNo
	 * @return Set(Goods)
	 */
	public List<Goods> searchGoods(String searchKeyword,int start,int end) {
		List<Goods> goods = new ArrayList<>();
		String querySQL = null;
		if (searchKeyword != null && searchKeyword != "") {
			querySQL =  "select * from (" 
				       + "select ROWNUM ROW_NUM, GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, "
				       + "QUANTITY, IMAGE_NAME, STATUS " + "from BEVERAGE_GOODS " 
				       + "where regexp_like(GOODS_NAME,?,'i')"
				       + ")"
				       + "where ROW_NUM between ? and ?";
		} else {
			querySQL =  "select * from ("
					+ "select ROWNUM ROW_NUM, GOODS_ID, GOODS_NAME, DESCRIPTION, "
					+ "PRICE, QUANTITY, IMAGE_NAME, STATUS "
					+ "from BEVERAGE_GOODS " + ")"
					+ "where ROW_NUM between ? and ?";
		}
		try (Connection conn = DBConnectionFactory.getLocalDBConnection();
				PreparedStatement stmt = conn.prepareStatement(querySQL)) {

			int count = 1;
			if (searchKeyword != null && searchKeyword != "") {
				stmt.setString(count++,searchKeyword);
				stmt.setInt(count++, start);
				stmt.setInt(count++, end);
			}else{
				stmt.setInt(count++, start);
				stmt.setInt(count++, end);
			}

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {

					Goods good = new Goods();
					good.setGoodsID(rs.getInt("GOODS_ID"));
					good.setGoodsName(rs.getString("GOODS_NAME"));
					good.setGoodsPrice(rs.getInt("PRICE"));
					good.setGoodsQuantity(rs.getInt("QUANTITY"));
					good.setGoodsImageName(rs.getString("IMAGE_NAME"));
					good.setStatus(rs.getString("STATUS"));

					goods.add(good);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return goods;
	}

	/**
	 * 透過ID查詢商品
	 */

	public Goods queryAll(String goodsID) {
		Goods good = new Goods();
		String querySQL = " SELECT * FROM BEVERAGE_GOODS WHERE GOODS_ID = ? ";
		try (Connection conn = DBConnectionFactory.getLocalDBConnection();
				PreparedStatement stmt = conn.prepareStatement(querySQL)) {
			int count = 1;
			stmt.setString(count++, goodsID);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					good.setGoodsID(rs.getInt("GOODS_ID"));
					good.setGoodsName(rs.getString("GOODS_NAME"));
					good.setGoodsPrice(rs.getInt("PRICE"));
					good.setGoodsQuantity(rs.getInt("QUANTITY"));
					good.setGoodsImageName(rs.getString("IMAGE_NAME"));
					good.setStatus(rs.getString("STATUS"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return good;
	}

	/**
	 * 建立訂單資料
	 * 
	 * @param value
	 * 
	 * @param customerID
	 * @param goodsQuantity
	 * @return boolean
	 */
	public boolean batchCreateGoodsOrder(Map<Goods,
			Integer> shoppingCartGoods,String memberNo) {
		boolean insertSuccess = false;
		try (Connection conn = DBConnectionFactory.getLocalDBConnection()){
			conn.setAutoCommit(false);
			String insertStmt = "INSERT INTO BEVERAGE_ORDER (ORDER_ID,ORDER_DATE,CUSTOMER_ID,GOODS_ID,GOODS_BUY_PRICE,BUY_QUANTITY) "
					+ "VALUES (BEVERAGE_ORDER_seq.NEXTVAL, SYSDATE, ?, ?, (SELECT PRICE FROM BEVERAGE_GOODS WHERE GOODS_ID = ? ) , ?)";
			
			try (PreparedStatement pstmt = conn.prepareStatement(insertStmt)) {
				Set<Goods> set = shoppingCartGoods.keySet();
				Iterator<Goods> iterator = set.iterator();
				
				while(iterator.hasNext()){
					int count = 1;
					Goods goods = iterator.next();
					pstmt.setString(count++, memberNo);
					pstmt.setInt(count++, goods.getGoodsID());
					pstmt.setInt(count++, goods.getGoodsID());
					pstmt.setInt(count++, shoppingCartGoods.get(goods));
					pstmt.addBatch();
				}
					pstmt.executeBatch();
					insertSuccess = true;
					conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return insertSuccess;
	
	}

	/**
	 * 交易完成更新扣商品庫存數量
	 * 
	 * @param goods
	 * @return boolean
	 */
	public boolean batchUpdateGoodsQuantity(Set<Goods> goods) {
		boolean updateSuccess = false;

		try (Connection conn = DBConnectionFactory.getLocalDBConnection()) {
			conn.setAutoCommit(false);
			String updateSql = "UPDATE BEVERAGE_GOODS SET QUANTITY = ? WHERE GOODS_ID = ?";
			try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {

				for (Iterator<Goods> i = goods.iterator(); i.hasNext();) {
					Goods good = i.next();
					int count = 1;
					stmt.setInt(count++, good.getGoodsQuantity());
					stmt.setInt(count++, good.getGoodsID());
					stmt.addBatch();
				}
				stmt.executeBatch();
				conn.commit();
				return true;

			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return updateSuccess;
	}
}
