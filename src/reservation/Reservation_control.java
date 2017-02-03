package reservation;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.awt.Dialog; 

public class Reservation_control {
	MySQL mysql;
	Statement sqlStmt;
	String reservation_userid;
	private boolean flagLogin;
	
	public Reservation_control() {
		this.mysql = new MySQL();
		flagLogin=false;
	}
	
	
		public String getReservationOn( String facility, String ryear_str, String rmonth_str, String rday_str){
			String res = "";
			
			try {
				int ryear = Integer.parseInt( ryear_str);
				int rmonth = Integer.parseInt( rmonth_str);
				int rday = Integer.parseInt( rday_str);
			} catch(NumberFormatException e){
				res ="�N�����ɂ͐������w�肵�Ă�������";
				return res;
			}
			res = facility + " �\���\n\n";

	
			if (rmonth_str.length()==1) {
				rmonth_str = "0" + rmonth_str;
			}
			if ( rday_str.length()==1){
				rday_str = "0" + rday_str;
			}
			
			String rdate = ryear_str + "-" + rmonth_str + "-" + rday_str;

			
			try {
			
				ResultSet rs = mysql.selectReservation(rdate, facility);
					boolean exist = false;
					while(rs.next()){
						String start = rs.getString("start_time");
						String end = rs.getString("end_time");
						res += " " + start + " -- " + end + "\n";
						exist = true;
					}

					if ( !exist){ 
						res = "�\��͂���܂���";
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				return res;
			}
		
		public String loginLogout(Reservation_view frame){
			String res=""; 
			if (flagLogin){ 
				flagLogin = false;
				frame.buttonLog.setLabel("���O�C��"); 
			} else {
				
				LoginDialog ld = new LoginDialog(frame);
				ld.setVisible(true);
				ld.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
				
				if ( ld.canceled){
					return "";
				}

				
				reservation_userid = ld.tfUserID.getText();
			
				String password = ld.tfPassword.getText();

			
				try { 
					
					ResultSet rs = mysql.selectUser(reservation_userid);
					if (rs.next()){
						rs.getString("password");
						String password_from_db = rs.getString("password");
						if ( password_from_db.equals(password)){
							flagLogin = true;
							frame.buttonLog.setLabel("���O�A�E�g");
							res = "";
						}else {
							
							res = "���O�C���ł��܂���.ID �p�X���[�h���Ⴂ�܂�";
						}
					} else {
						res = "���O�C���ł��܂���.ID �p�X���[�h���Ⴂ�܂�";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			return res;
		}

		private boolean checkReservationDate( int y, int m, int d){
			// �\���
			Calendar dateR = Calendar.getInstance();
			dateR.set( y, m-1, d);	// ������1�����Ȃ���΂Ȃ�Ȃ����Ƃɒ��ӁI

			// �����̂P����
			Calendar date1 = Calendar.getInstance();
			date1.add(Calendar.DATE, 1);

			// �����̂R������i90����)
			Calendar date2 = Calendar.getInstance();
			date2.add(Calendar.DATE, 90);

			if ( dateR.after(date1) && dateR.before(date2)){
				return true;
			}
			return false;
		}
		
		public String makeReservation(Reservation_view frame){

			String res="";		

			if ( flagLogin){
				ReservationDialog rd = new ReservationDialog(frame);

				rd.tfYear.setText(frame.tfYear.getText());
				rd.tfMonth.setText(frame.tfMonth.getText());
				rd.tfDay.setText(frame.tfDay.getText());

				rd.setVisible(true);
				if ( rd.canceled){
					return res;
				}
				try {
					String ryear_str = rd.tfYear.getText();
					String rmonth_str = rd.tfMonth.getText();
					String rday_str = rd.tfDay.getText();

					int ryear = Integer.parseInt( ryear_str);
					int rmonth = Integer.parseInt( rmonth_str);
					int rday = Integer.parseInt( rday_str);

					if ( checkReservationDate( ryear, rmonth, rday)){	

						String facility = rd.choiceFacility.getSelectedItem();
						String st = rd.startHour.getSelectedItem()+":" + rd.startMinute.getSelectedItem() +":00";
						String et = rd.endHour.getSelectedItem() + ":" + rd.endMinute.getSelectedItem() +":00";

						if( st.equals(et)){		
							res = "�J�n�����ƏI�������������ł�";
						} else {


							try {

								if (rmonth_str.length()==1) {
									rmonth_str = "0" + rmonth_str;
								}
								if ( rday_str.length()==1){
									rday_str = "0" + rday_str;
								}
		
								String rdate = ryear_str + "-" + rmonth_str + "-" + rday_str;
							      ResultSet rs = mysql.selectReservation(rdate, facility);
							      boolean ng = false;	
							      while(rs.next()){
								  		
								        String start = rs.getString("start_time");
								        String end = rs.getString("end_time");

								        if ( (start.compareTo(st)<0 && st.compareTo(end)<0) ||		
								        	 (st.compareTo(start)<0 && start.compareTo(et)<0)){		
											 	
								        	ng = true; break;
								        }
							      }
							

							      if (!ng){	
							    	  int rs_int = mysql.insertReservation(rdate, facility, st, et, reservation_userid);
							    	  res ="�\�񂳂�܂���";
							      } else {	
							    	  res = "���ɂ���\��ɏd�Ȃ��Ă��܂�";
							      }
							}catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else {
						res = "�\����������ł��D";
					}
				} catch(NumberFormatException e){
					res ="�\����ɂ͐������w�肵�Ă�������";
				}
			} else { 
				res = "���O�C�����Ă�������";
			}
			return res;
		}
		
		public String getFacility( String facility){
			String kyk = null;
			try {
				ResultSet rs = mysql.getEx( facility); 
					boolean exist = false;
					while(rs.next()){
					kyk = rs.getString("explanation")+"\n";
				    exist = true;
					}		
				}catch(Exception e){
					e.printStackTrace();
				}	
			return kyk ;
		}
		
		public String getDelete() {
			if(flagLogin == false)
				return "���O�C�����Ă�������.";	
			try {
				int rs = mysql.selectReservation(reservation_userid); 
					boolean exist = false;
				}catch(Exception e){
					e.printStackTrace();
				}
			return "�\��̃L�����Z�����������܂���.";
		}

		public String getRes() {
			// TODO �����������ꂽ���\�b�h�E�X�^�u	
			String re = "";

	if(flagLogin == false)
		return "���O�C�����Ă�������.";
			try {
				// �N�G���[�����s���Č��ʃZ�b�g���擾
				ResultSet rs = mysql.selectReservationA(reservation_userid); // �������ʂ���\��󋵂��쐬
					boolean exist = false;
					while(rs.next()){
						String start = rs.getString("start_time");
						String end = rs.getString("end_time");
						String date =rs.getString("date");
						String fn =rs.getString("facility_name");
						re += date+""+" " + start + " -- " + end +"   "+fn+ "\n";
						exist = true;
					}

					if (!exist){ 
						re = "�\��͂���܂���";
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				return re;
		}
}
