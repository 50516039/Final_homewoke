package reservation;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class LoginDialog extends Dialog implements ActionListener,WindowListener{

	boolean canceled;		
	TextField tfUserID;		
	TextField tfPassword;	
	Button buttonOK;		
	Button buttonCancel;
	Panel panelNorth;		
	Panel panelMid;			
	Panel panelSouth;		



	
	public LoginDialog(Frame owner) {
			super(owner,"Login",true);	
			canceled = true;
			
			tfUserID = new TextField("",10);
			tfPassword = new TextField("",10);	
			tfPassword.setEchoChar('*');
	
			buttonOK = new Button("OK");
			buttonCancel = new Button("キャンセル");
		
			panelNorth = new Panel();
			panelMid = new Panel();
			panelSouth = new Panel();
			
			panelNorth.add( new Label("ユーザID"));
			panelNorth.add(tfUserID);
		
			panelMid.add( new Label("パスワード"));
			panelMid.add(tfPassword);
	
			panelSouth.add(buttonCancel);
			panelSouth.add(buttonOK);
	
			setLayout( new BorderLayout());
			add( panelNorth,BorderLayout.NORTH);
			add( panelMid, BorderLayout.CENTER);
			add( panelSouth, BorderLayout.SOUTH);
	
			addWindowListener(this);
		
			buttonOK.addActionListener(this);
			buttonCancel.addActionListener(this);

			this.setBounds( 100, 100, 350, 150);
			setResizable(false);
		}
	

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		setVisible(false);
		canceled = true;
		dispose();		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ( e.getSource() == buttonCancel){
			canceled = true;
		} else if ( e.getSource() == buttonOK){
			canceled = false;
		}
		setVisible(false);
		dispose();
	}

}
