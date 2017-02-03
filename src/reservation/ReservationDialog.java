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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ReservationDialog  extends Dialog implements ActionListener, WindowListener,ItemListener{
	
	boolean canceled;		//キャンセルされたら true 予約実行ボタンのときは false
	// パネル
	Panel panelNorth;
	Panel panelMid;
	Panel panelSouth;
	// 入力用コンポーネント
	ChoiceFacility choiceFacility;	// 施設選択用ボックス
	TextField tfYear,tfMonth,tfDay;	// 年月日のテキストフィールド
	ChoiceHour startHour;			// 予約開始時刻　時の選択用ボックス
	ChoiceMinute startMinute;		// 予約開始時刻　分の選択用ボックス
	ChoiceHour endHour;				// 予約終了時刻 　時の選択用ボックス
	ChoiceMinute endMinute;			// 予約終了時刻　分の選択用ボックス
	// ボタン
	Button buttonOK;
	Button buttonCancel;

	public ReservationDialog(Frame owner) {
		super(owner, "新規予約", true);

		canceled = true;

		//施設選択ボックスの生成
		choiceFacility = new ChoiceFacility();
	
		tfYear = new TextField("",4);
		tfMonth = new TextField("",2);
		tfDay = new TextField("",2);

		startHour = new ChoiceHour();
		startMinute = new ChoiceMinute();

		endHour = new ChoiceHour();
		endMinute = new ChoiceMinute();

		buttonOK = new Button("予約実行");
		buttonCancel = new Button("キャンセル");

		panelNorth = new Panel();
		panelMid = new Panel();
		panelSouth = new Panel();

		panelNorth.add( new Label("施設"));
		panelNorth.add(choiceFacility);
		panelNorth.add( new Label("予約日"));
		panelNorth.add(tfYear);
		panelNorth.add(new Label("年"));
		panelNorth.add(tfMonth);
		panelNorth.add(new Label("月"));
		panelNorth.add(tfDay);
		panelNorth.add(new Label("日"));

		panelMid.add( new Label("予約時間"));
		panelMid.add( startHour);
		panelMid.add( new Label("時"));
		panelMid.add( startMinute);
		panelMid.add( new Label("分　～　"));
		panelMid.add( endHour);
		panelMid.add( new Label("時"));
		panelMid.add( endMinute);
		panelMid.add( new Label("分"));

		panelSouth.add(buttonCancel);
		panelSouth.add( new Label("　　　　"));
		panelSouth.add(buttonOK);

		setLayout(new BorderLayout());
		add(panelNorth, BorderLayout.NORTH);
		add(panelMid,BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);

		addWindowListener(this);
	
		buttonOK.addActionListener(this);
		buttonCancel.addActionListener(this);
		
		choiceFacility.addItemListener(this);
		startHour.addItemListener(this);
		startMinute.addItemListener(this);
		endHour.addItemListener(this);
		endMinute.addItemListener(this);

		resetTimeRange();

		this.setBounds( 100, 100, 500, 120);
		setResizable(false);
	}

	

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
			if ( e.getSource()==choiceFacility){
				// 施設が変更されたら，施設に応じた範囲を設定
				resetTimeRange();
			} else if ( e.getSource()==startHour){
				//開始時刻が変更されたら，終了時刻入力欄の時を開始時刻に合わせる
				int start = Integer.parseInt( startHour.getSelectedItem());
				endHour.resetRange(start, Integer.parseInt( endHour.getLast()));
			} else if ( e.getSource()==endHour){
				//終了時刻が変更され，最後の時刻の場合，分は 00分に設定
				if ( endHour.getSelectedIndex()==endHour.getItemCount()-1){
					endMinute.select(0);
				}
			} if( e.getSource()==endMinute){
				//終了時刻（分）が変更され，時が最後の場合，分は 00分に設定
				if ( endHour.getSelectedIndex()==endHour.getItemCount()-1){
					endMinute.select(0);
				}
			}
		}

	

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		setVisible(false);
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
			setVisible(false);
			dispose();
		} else if ( e.getSource() == buttonOK){
			canceled = false;
			setVisible(false);
			dispose();
		}

		
	}
	private void resetTimeRange() {
		// 選択されている施設によって，時刻の範囲を設定する．
		if ( choiceFacility.getSelectedIndex()==0){
			// 最初の施設(小ホールのとき)の設定
			startHour.resetRange(10, 20);
			endHour.resetRange(10, 21);
		} else {
			// 小ホール以外の設定
			startHour.resetRange(9, 19);
			endHour.resetRange(9, 20);
		}
	}

}
