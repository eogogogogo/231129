package dto;

public class ChampInfo {
	
	private int cid;  //챔피언 아이디
	private String cname; //챔피언 이름
	private double winrate; //승률
	public double getWinrate() {
		return winrate;
	}
	public void setWinrate(double winrate) {
		this.winrate = winrate;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
		
	
}
