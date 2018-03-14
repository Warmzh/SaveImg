import java.io.File;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SaveImageDemo {
	Connection conn = null;

	public SaveImageDemo() {
		// TODO Auto-generated constructor stub
		try {
			String url = "jdbc:sqlserver://192.168.78.234:1433;DatabaseName=fghr";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url, "sa", "sasuper");
			if (conn != null) {
				System.out.println("����DB�ɹ�");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	public void getImageFromDB() {
		try {
			FileOutputStream ous = null;
			String sql = "SELECT hz.zgbh 'EmpID',hz.zgxm '�T������',hz.bmbh '���T��̖',hz.bmmx '���T���Q',hz.userpho 'EmpPhoto',hz.faceimg11 'ģ��1',\r\n"
					+ "hz.faceimg21 'ģ��2', hz.faceimg31 'ģ��3', hz.faceimg41 'ģ��4',hz.faceimg51 'ģ��5'\r\n"
					+ "FROM hp_zd01 hz WHERE hz.zgbh in ('1332786')";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int currRow = rs.getRow();
				System.out.println(currRow);
				String empName = rs.getString(2);
				Blob ob = null;
				//����5��
//				for(int i=5;i<10;i++) {
//					ous = new FileOutputStream(
//							new File("D:" + File.separator + "icons" + File.separator + empName.trim() + (i-4) + ".jpg"));
//					ob = rs.getBlob(i);
//				
//				if(ob!=null) {
//					long size = ob.length();
//					byte bs[] = ob.getBytes(1, (int) size);
//					ous.write(bs);
//					System.out.println(empName.trim()+"Ա���ĵ�"+(i-4)+"����Ƭ����ɹ�");
//				}else {
//					System.out.println(empName.trim()+"Ա���ĵ�"+(i-4)+"����Ƭ�����ڣ�������Ƭ����ʧ�ܣ�");				
//				}
//				}			
				//����һ��
				ous = new FileOutputStream(
						new File("D:" + File.separator + "icons" + File.separator + empName.trim() + ".jpg"));
				ob = rs.getBlob(5);
				
				if(ob!=null) {
					long size = ob.length();
					byte bs[] = ob.getBytes(1, (int) size);
					ous.write(bs);
					System.out.println(empName.trim()+"Ա������ɹ�");
				}else {
					System.out.println(empName.trim()+"Ա������Ƭ�����ڣ���Ա������ʧ�ܣ�");				
				}
				
			}
			ous.flush();
			ous.close();
			rs.close();
			conn.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SaveImageDemo sid = new SaveImageDemo();
		sid.getImageFromDB();
	}
}
