package datacvg.gather.embeddedDB;
/*
 * 
 */
public class DBInstance {

	public static DBBehaivor mydb;
	public static void init(String myDBPath){
		try {
			mydb = new DBBehaivor(myDBPath);
		} catch (Exception e) {
			try {
				Thread.sleep(300 * 1000L);
			} catch (InterruptedException e2) {
				e.printStackTrace();
			}
			e.printStackTrace();
			return;
		}
	}
	
	public static void destroy() {
		mydb = null;
	}
}
