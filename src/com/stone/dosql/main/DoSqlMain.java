package com.stone.dosql.main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import com.stone.dosql.codegenerator.from.impl.Column;
import com.stone.dosql.codegenerator.impl.ResultSet;
import com.stone.dosql.face.Face;
import com.stone.dosql.util.DbName;

public class DoSqlMain extends JFrame {
	
	private JComboBox comboBox;
	private JTable table;
	private JScrollPane scrollPane;
	private  final String DBMS_URL="stone//dosql//data//";
	
	//private DefaultTableModel model = new DefaultTableModel();

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			DoSqlMain frame = new DoSqlMain();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	public DoSqlMain() {
		super();
		
		
		getContentPane().setLayout(null);
		setBounds(100, 100, 610, 544);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 50, 492, 123);
		getContentPane().add(textArea);

		final JLabel label = new JLabel();
		label.setFont(new Font("", Font.BOLD, 15));
		label.setText("   输入语句");
		label.setBounds(22, 10, 130, 34);
		getContentPane().add(label);

		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				DefaultTableModel model = new DefaultTableModel();
				
				String selectStr=textArea.getText();
				System.out.println(selectStr);
				String db=(String)comboBox.getSelectedItem();
				//String db="test";
				Face face=new Face(db,selectStr);
				ResultSet rs=face.getResultSet();
				String err=face.getSelectError();
				if(err.trim().length()==0){
					
					Vector vectorShow=new Vector();
					int recordSize=rs.getRecordList().size();
					int colSize=rs.getSelColumnsList().size();
					Vector title=new Vector();
					for(int i=0;i<colSize;i++){
						Column col=(Column)rs.getSelColumnsList().get(i);
						System.out.println(col.getFullName());
						title.add((String)col.getFullName());
						
					}
					
					
					
					for(int i=0;i<recordSize;i++){
						List oneRecord=(List)rs.getRecordList().get(i);
						Vector vOne=new Vector();
						for(int j=0;j<colSize;j++){
							vOne.add((String)oneRecord.get(j));
						}
						vectorShow.add(vOne);
						
					}
					model.setDataVector(vectorShow, title);
					table = new JTable(model);
					table.setEnabled(false);
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					scrollPane.setViewportView(table);
					JOptionPane.showMessageDialog(button, face.getUserTime()+" 毫秒",
							"查找时间", JOptionPane.WARNING_MESSAGE);
				
				}else{
					JOptionPane.showMessageDialog(button, err,
							"错误提示", JOptionPane.WARNING_MESSAGE);
					table = new JTable(model);
					table.setEnabled(false);
					table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					scrollPane.setViewportView(table);
				}

				//model.setDataVector(dataVector, columnIdentifiers)

				
			}
		});
		button.setText("分析");
		button.setBounds(505, 135, 97, 26);
		getContentPane().add(button);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 191, 605, 326);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);

		comboBox = new JComboBox();
		comboBox.setBounds(505, 75, 94, 34);
		getContentPane().add(comboBox);

		
		final JLabel label_1 = new JLabel();
		label_1.setFont(new Font("", Font.BOLD, 15));
		label_1.setBounds(505, 50, 94, 26);
		getContentPane().add(label_1);
		
		DbName dbName=new DbName(DBMS_URL);
		int size=dbName.getDbList().size();
		for(int i=0;i<size;i++){
			comboBox.addItem((String)dbName.getDbList().get(i));
		}

		
		//
	}

}
