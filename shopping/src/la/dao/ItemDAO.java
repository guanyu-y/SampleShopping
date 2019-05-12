package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import la.bean.CategoryBean;
import la.bean.ItemBean;

public class ItemDAO {
	private Connection con;

	public ItemDAO() throws DAOException{
		getConnection();
	}

	// カテゴリ一覧の表示
	public List<CategoryBean> findAllCategory() throws DAOException{
		if(con==null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT * FROM category ORDER BY code";
			// PreparedStatemetオブジェクトの取得
			st = con.prepareStatement(sql);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得と表示
			List<CategoryBean> list = new ArrayList<CategoryBean>();
			while(rs.next()) {
				int code = rs.getInt("code");
				String name = rs.getString("name");
				CategoryBean bean = new CategoryBean(code,name);
				list.add(bean);
			}
			// カテゴリ一覧をListとして返す
			return list;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}finally {
			try {
				// リソースの開放
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				close();
			}catch(Exception e) {
				e.printStackTrace();
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}

	// カテゴリによって商品をソート
	public List<ItemBean> findByCategory(int categoryCode) throws DAOException{
		if(con==null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT * FROM item WHERE category_code=? ORDER BY code";
			// PreparedStatemetオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setInt(1,categoryCode); // カテゴリの設定
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得と表示
			List<ItemBean> list = new ArrayList<ItemBean>();
			NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.JAPAN);
				while(rs.next()) {
				int code = rs.getInt("code");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				String url = rs.getString("figure_url");
				ItemBean bean = new ItemBean(code,name,price, url);
				// 価格を3桁ずつカンマする数値整形(NumberFormatクラス)
				bean.setCurrency(nf.format(price));
				list.add(bean);
			}
			// 商品一覧をListとして返す
			return list;
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}finally {
			try {
				// リソースの開放
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				close();
			}catch(Exception e) {
				e.printStackTrace();
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}

	// 主キーによって商品を検索
	public ItemBean findByPrimaryKey(int key) throws DAOException{
		if(con==null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			// SQL文の作成
			String sql = "SELECT * FROM item WHERE code=?";
			// PreparedStatemetオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setInt(1,key); // カテゴリの設定
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得と表示
			NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.JAPAN);
			if(rs.next()) {
				int code = rs.getInt("code");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				String url = rs.getString("figure_url");
				ItemBean bean = new ItemBean(code,name,price,url);
				bean.setCurrency(nf.format(price));
				return bean;
			}else {
				// 主キーに該当するレコードなし
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}finally {
			try {
				// リソースの開放
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				close();
			}catch(Exception e) {
				e.printStackTrace();
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}

	private void getConnection() throws DAOException{
		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
			// URL,ユーザ名,パスワードの設定
			String url = "jdbc:postgresql:sample";
			String user = "yamada";
			String pass = "yamada";
			// データベースへの接続
			con = DriverManager.getConnection(url,user,pass);
		}catch(Exception e) {
			e.printStackTrace();
			throw new DAOException("接続に失敗しました。");
		}
	}

	private void close() throws SQLException{
		if(con!=null) {
			con.close();
			con = null;
		}
	}
}
