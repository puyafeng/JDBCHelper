package pyf.java.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pyf.java.db.convert.Map2Bean;
import pyf.java.db.convert.Rs2Map;

public class JDBCHelper implements JDBCConstants {

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void other(String sql, Object... objs) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				ps.setObject(i+1, objs[i]);
			}
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, ps, conn);
		}
	}
	

	/**
	 * ��ѯ����
	 * 
	 * @param cls
	 * @param parser
	 *            ����resultset�ľ���ʵ��
	 * @param sql
	 * @param objs
	 * @return
	 */
	public static <T> List<T> query(Class<T> cls, ResultSetParser<T> parser, String sql, Object... objs) {

		List<T> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				ps.setObject(i+1, objs[i]);
			}
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				rs.first();
				do {
					if (parser != null) {
						T t = parser.parse(rs);
						if (t != null) {
							result.add(t);
						}
					}
				} while (rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return result;
	}

	/**
	 * �ṩĬ�Ͻ���ʵ��
	 * 
	 * @param cls
	 * @param sql
	 * @param objs
	 * @return
	 */
	public static <T> List<T> query(Class<T> cls, String sql, Object... objs) {
		Rs2Map r2m = new Rs2Map();
		return query(cls, new JDBCHelper.ResultSetParser<T>() {

			@Override
			public T parse(ResultSet rs) {
				T t = null;
				try {
					Map<?, ?> map = r2m.parse(rs);
					if(Map.class.isAssignableFrom(cls)){
						return (T) map;
					}else{
						t = Map2Bean.parseWithAnnotation(map, cls);	
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return t;
			}

		}, sql, objs);
	}

	/**
	 * ������ݿ�����
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * �ر����ݿ���ض���
	 * 
	 * @param rs
	 * @param ps
	 * @param conn
	 */
	public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public interface ResultSetParser<T> {
		T parse(ResultSet rs) throws SQLException;
	}

}
