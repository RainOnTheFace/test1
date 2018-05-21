package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class ReadFile {

	public static void main(String[] args) throws IOException {
		// �ҵ�Ŀ���ļ�
		File pendingDir = new File("C:\\Users\\Administrator\\Desktop\\log");

		// ȡ����־Ŀ¼��������־�ļ���
		String[] files = pendingDir.list();

		if (files.length == 0) {
			System.err.println("There isn't any file in directory:" + pendingDir);
			System.exit(1);
		} else {

			// ������־�ļ�һ��һ��for����,��ÿ����־�ļ���Ȼ����һ���ж���java��
			for (String fn : files) {
				// System.out.println(fn);
				getData("C:\\Users\\Administrator\\Desktop\\log\\" + fn);
			}
		}

	}

	public static void getData(String file) throws IOException {
		// UTF-8�����������������
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String temp;
		int flag = 0;
		Map<String, String> hsMap = new HashMap<String, String>();
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://192.168.200.69:3306/gradevin_test?useUnicode\\=true&characterEncoding\\=UTF-8&zeroDateTimeBehavior\\=round";
		String user = "root";
		String password = "test1rootmysql";
		Connection conn = null;
		Statement st = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed()) {
				System.out.println("Succeeded connecting to the Database!");
			}
			st = conn.createStatement();
			// ÿ��һ�����ݾ͵���stringSub()������ÿ��
			while ((temp = br.readLine()) != null) {

				if (temp.equals("url:http://wineapi.xcook.cn/linkcook/tft/devices/findWeatherTime")) {
					flag = 5;
					hsMap.clear();
					hsMap.put("type", "findWeatherTime");
				} else if (temp.equals("url:http://wineapi.xcook.cn/linkcook/tft/devices/findWeather")) {
					flag = 5;
					hsMap.clear();
					hsMap.put("type", "findWeather");
				}
				switch (flag) {
				case 1:
					hsMap.put("startTime", temp);

					String deviceId = hsMap.get("deviceId");
					String msg = hsMap.get("msg");
					String startTime = hsMap.get("startTime").substring(0, 23);
					String type = hsMap.get("type");

					String latitude = hsMap.get("latitude");
					if (!(latitude == null || latitude == "")) {
						latitude = latitude.substring(0, 5);
					} else {
						latitude = "0";
					}
					String longitude = hsMap.get("longitude");
					if (!(longitude == null || longitude == "")) {
						longitude = longitude.substring(0, 6);
					} else {
						longitude = "0";
					}
					StringBuffer sb = new StringBuffer();
					// sb.append("insert into wo_open_pre values (");
					sb.append("insert into log_table2(deviceId,msg,startTime,type,latitude,longitude) values (");
					sb.append("'").append(deviceId).append("'");
					sb.append(",'").append(msg).append("'");
					sb.append(",'").append(startTime).append("'");
					sb.append(",'").append(type).append("'");
					sb.append(",'").append(latitude).append("'");
					sb.append(",'").append(longitude).append("')");
					String sql = sb.toString();
					System.out.println(sql);
					st.execute(sql);
					flag = 0;
					break;
				case 2:
					flag--;
					break;
				case 3:
					if (temp.contains("�����ɹ�")) {
						hsMap.put("msg", "�����ɹ�");
					} else {
						hsMap.put("msg", "����ʧ��");
					}
					flag--;
					break;
				case 4:
					temp = temp.substring(8);

					JSONObject json = JSONObject.parseObject(temp);
					hsMap.put("deviceId", json.getString("deviceId"));
					hsMap.put("latitude", json.getString("latitude"));
					hsMap.put("longitude", json.getString("longitude"));
					flag--;
					break;

				case 5:
					flag--;
					break;

				}
			}
			st.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

		}
		br.close();
	}

}
