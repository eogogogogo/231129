package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.ChampInfo;

public class LoLDao {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	public LoLDao() {
		con = JdbcUtil.getConnection();
	}

	public void close() {
		JdbcUtil.dbClose(rs, pstmt, con);
	}

	public List<String> getLaneList() {
		List<String> laneList = null;
		String sql = "select DISTINCT(lane) lane from dsr where lane is not null";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			laneList = new ArrayList<String>();
			while (rs.next()) {
				laneList.add(rs.getNString("LANE"));
			}
			return laneList;
		} catch (SQLException e) {
			System.out.println("mDao getLaneList 예외");
			e.printStackTrace();
		}
		return null; // 실패
	}

	public List<ChampInfo> getChamList() {
		List<ChampInfo> champList = null;
		String sql = """
				SELECT championid AS CID, champion_name_kr AS CNAME
				FROM CN_KR ORDER BY CNAME; """; // 가나다 순으로 출력
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			champList = new ArrayList<ChampInfo>();
			while (rs.next()) {
				ChampInfo ci = new ChampInfo();
				ci.setCid(rs.getInt("CID")); // 프런트에서 챔피언의 아이디(PK)를 넘겨야 DB에서 검색이 빠르다
				ci.setCname(rs.getString("CNAME"));
				champList.add(ci);
			}
			return champList;
		} catch (SQLException e) {
			System.out.println("mDao getChamList 예외");
			e.printStackTrace();
		}
		return null; // 실패
	}

	public List<String[]> winrate(String lane) {
		String sql = """
			    SELECT CID, WINRATE
				   FROM TOTAL_WINRATE
				   WHERE LANE = ?
				   ORDER BY WINRATE DESC
				   LIMIT 3
				 """;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, lane);
			rs = pstmt.executeQuery();
			List<String[]> lList = new ArrayList<String[]>();
			int i = 0;
			while (rs.next()) {
				String[] row = new String[2]; // ["애니", "100.0"],[], []
				row[0] = String.valueOf(rs.getInt("CID"));
				row[1] = String.valueOf(rs.getDouble("WINRATE"));
				lList.add(row);
			}
			return lList;
		} catch (SQLException e) {
			System.out.println("winrate 예외");
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String, String>> getList3(String lane) {
		String sql = "SELECT * FROM rc WHERE lane=? Limit 3";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, lane);
			rs = pstmt.executeQuery();
			List<Map<String, String>> lList = new ArrayList<>();
			Map<String, String> hMap = null;
			int i = 0;
			while (rs.next()) {
				hMap = new HashMap<String, String>();
				hMap.put("cname", rs.getString("champion_name_kr"));
				hMap.put("lane", rs.getString("lane"));
				lList.add(hMap);
			}
			return lList;
		} catch (SQLException e) {
			System.out.println("getList3예외 발생");
			e.printStackTrace();
		}
		return null;
	}
}
