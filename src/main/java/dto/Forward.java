package dto;

public class Forward {
	//true: redirect 포워딩
	//false: dispatcher 포워딩
	private boolean isRedirect;
	private String path;  //포워딩할 뷰페이지 or url
	
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
 }
