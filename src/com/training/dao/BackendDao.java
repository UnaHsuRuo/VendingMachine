package com.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.training.model.Goods;
import com.training.model.UpdateResult;
import com.training.vo.SalesReport;

public class BackendDao {

	private static BackendDao backendDao = new BackendDao();

	private BackendDao() {
	}

	public static BackendDao getInstance() {
		return backendDao;

	}

	/**
	 * 後臺查詢商品列表
	 * 
	 * @return Set(Goods)
	 */
	/**
	 * @return
	 */
	public List<Goods> queryGoods() {
		List<Goods> goods = new LinkedList();
		String querySQL = "SELECT * FROM BEVERAGE_GOODS";

		try (Connection conn = DBConnectionFactory.getLocalDBConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(querySQL)) {

			while (rs.next()) {
				int goodsID = rs.getInt("GOODS_ID");
				String goodsName = rs.getString("GOODS_NAME");
				int goodsPrice = rs.getInt("PRICE");
				int goodsQuantity = rs.getInt("QUANTITY");
				String goodsImageName = rs.getString("IMAGE_NAME");
				String status = rs.getString("STATUS");

				Goods good = new Goods();
				good.setGoodsID(goodsID);
				good.setGoodsName(goodsName);
				good.setGoodsPrice(goodsPrice);
				good.setGoodsQuantity(goodsQuantity);
				good.setGoodsImageName(goodsImageName);
				good.setStatus(status);

				goods.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return goods;
	}

	/**
	 * 後臺管理新增商品
	 * 
	 * @param goods
	 * @return int(商品編號)
	 */
	public int createGoods(Goods goods) {
	    int generatedId = -1;
	    String insert_stmt = "INSERT INTO BEVERAGE_GOODS (GOODS_ID,GOODS_NAME,PRICE,QUANTITY,IMAGE_NAME,STATUS) "
	                + "VALUES (BEVERAGE_GOODS_seq.NEXTVAL, ?, ?, ?, ?, ?)";

	    try (Connection conn = DBConnectionFactory.getLocalDBConnection();
	            PreparedStatement pstmt = conn.prepareStatement(insert_stmt, new String[]{"GOODS_ID"})) {

	        conn.setAutoCommit(false);
	        int count = 1;
	        pstmt.setString(count++, goods.getGoodsName());
	        pstmt.setInt(count++, goods.getGoodsPrice());
	        pstmt.setInt(count++, goods.getGoodsQuantity());
	        pstmt.setString(count++, goods.getGoodsImageName());
	        pstmt.setString(count++, goods.getStatus());
	        
	        int recordCount = pstmt.executeUpdate();
	        if (recordCount > 0) {
	            ResultSet rs = pstmt.getGeneratedKeys();
	            if (rs.next()) {
	                generatedId = rs.getInt(1);
	                goods.setGoodsID(generatedId);
	                conn.commit();
	            }
	        } else {
	            conn.rollback();
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return generatedId;
	}

	/**
	 * 後臺管理更新商品
	 * 
	 * @param goods
	 * @return boolean
	 */
	public UpdateResult updateGoods(Goods dbGoods) {
		UpdateResult result = new UpdateResult();
		try (Connection conn = DBConnectionFactory.getLocalDBConnection()) {
			conn.setAutoCommit(false);
			String updateSql = "UPDATE BEVERAGE_GOODS SET PRICE = ? , QUANTITY = ? , STATUS = ? WHERE GOODS_ID = ?";
			try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {

				int count = 1;
				stmt.setDouble(count++, dbGoods.getGoodsPrice());
				stmt.setInt(count++, dbGoods.getGoodsQuantity());
				stmt.setString(count++, dbGoods.getStatus());
				stmt.setInt(count++, dbGoods.getGoodsID());

				int recordCount = stmt.executeUpdate();
				conn.commit();

			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 後臺管理顧客訂單查詢
	 * 
	 * @param queryStartDate
	 * @param queryEndDate
	 * @return Set(SalesReport)
	 * @throws ParseException 
	 */
	public Set<SalesReport> queryOrderBetweenDate(String queryStartDate,
			String queryEndDate) throws ParseException {
		Set<SalesReport> reports = new LinkedHashSet<>();

		String querySQL = " SELECT O.ORDER_ID, M.CUSTOMER_NAME, O.ORDER_DATE, G.GOODS_NAME, G.PRICE, O.BUY_QUANTITY ,G.PRICE*O.BUY_QUANTITY as BUY_AMOUNT "
				+ " FROM BEVERAGE_ORDER O JOIN BEVERAGE_MEMBER M "
				+ " ON M.IDENTIFICATION_NO = O.CUSTOMER_ID "
				+ " JOIN BEVERAGE_GOODS G "
				+ " ON O.GOODS_ID = G.GOODS_ID "
				+ " WHERE O.ORDER_DATE BETWEEN ? AND ? "
				+ " ORDER BY O.ORDER_ID ";

		try (Connection conn = DBConnectionFactory.getLocalDBConnection();
				PreparedStatement stmt = conn.prepareStatement(querySQL)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			int count = 1;
			stmt.setDate(count++, new java.sql.Date(sdf.parse(queryStartDate)
					.getTime()));
			stmt.setDate(count++, new java.sql.Date(sdf.parse(queryEndDate + 1)
					.getTime()));

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {

					SalesReport s = new SalesReport();
					s.setOrderID(rs.getLong("ORDER_ID"));
					s.setCustomerName(rs.getString("CUSTOMER_NAME"));
					s.setOrderDate(rs.getString("ORDER_DATE"));
					s.setGoodsName(rs.getString("GOODS_NAME"));
					s.setGoodsBuyPrice(rs.getInt("PRICE"));
					s.setBuyQuantity(rs.getInt("BUY_QUANTITY"));
					s.setBuyAmount(rs.getInt("PRICE")
							* rs.getInt("BUY_QUANTITY"));

					reports.add(s);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reports;

	}
	
	
	public Goods queryGoodsByID(int i) {
		Goods good = new Goods();
		String querySQL = " SELECT * FROM BEVERAGE_GOODS WHERE GOODS_ID = ? ";
		try (Connection conn = DBConnectionFactory.getLocalDBConnection();
				PreparedStatement stmt = conn.prepareStatement(querySQL)) {
			int count = 1;
			stmt.setInt(count++, i);

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

}
