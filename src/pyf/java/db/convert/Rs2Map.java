package pyf.java.db.convert;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import pyf.java.db.JDBCHelper.ResultSetParser;

public class Rs2Map implements ResultSetParser<Map<?, ?>> {

	@Override
	public Map<?, ?> parse(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int columnsCount = metaData.getColumnCount();
		Map<String, Object> map = new HashMap<>();
		for (int i = 1; i <= columnsCount; i++) {
			map.put(metaData.getColumnName(i), rs.getObject(i));
		}
		return map;
	}

}
