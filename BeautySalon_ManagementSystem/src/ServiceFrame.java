import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServiceFrame extends JFrame {
	
	private Image img_logo = new ImageIcon(LoginFrame.class.getResource("res/LOGO-2.png")).getImage().getScaledInstance(300, 90, Image.SCALE_SMOOTH);
	private JPanel contentPane;
//	private JTextField txt_serviceid;
//	private JTextField txt_servicename;

	private JTextField txt_serviceid;
	private JTextField txt_servicename;
	private JTextField txt_serviceprice;

//	private JTextField txt_stylist;
//	private JTextField txt_service;
	private JTable table;
//	private static JComboBox<String> cbx_type;

	static Connection con;
	PreparedStatement pst;

	public static Connection Connection() {

		con = null;
		String url = "jdbc:mysql://localhost:3306/beauty_salon_db";
		String username="root";
		String password="";

		try {
			String driverName = "com.mysql.cj.jdbc.Driver";
			Class.forName(driverName);
			try {
				con = DriverManager.getConnection(url, username, password);
			} catch (SQLException ex) {
				System.out.println("Failed to create the database connection.");
			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver not found.");
		}
		return con;
	}



	public void ShowData() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Service ID");
		//		model.addColumn("Type");
		model.addColumn("Service");
		model.addColumn("Price");


		
		try {
			String query = "SELECT * FROM Service";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				model.addRow(new Object [] {
					rs.getString("Service_ID"),	
					rs.getString("Service_Name"),
//					rs.getString("Employee_Type"),
					rs.getFloat("Service_Price"),
				});
					
				}
			
			table.setModel(model);		
			
		} catch (NullPointerException|SQLException ex) {
			//ex.printStackTrace();
			System.out.println("");
		}}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServiceFrame frame = new ServiceFrame();
					frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public class Person {
		public String serviceName; // private = restricted access
//		public float servicePrice;
		public float servicePrice;
//		public String type;
		public String service;
		  // Getter
		  public String getName() {
			 				
		    return serviceName;
		  }

		  // Setter
		  public void setName() {
			  this.serviceName = txt_servicename.getText();
//			  this.type = (String) cbx_type.getSelectedItem();
			  this.servicePrice = Float.parseFloat(txt_serviceprice.getText());
		    try {
				pst = con.prepareStatement("INSERT INTO Service(Service_Name, Service_Price)values(?,?)");
				pst.setString(1, serviceName);
				pst.setFloat(2, servicePrice);
				pst.executeUpdate();
				System.out.println("inserted");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		}

	/**
	 * Create the frame.
	 */
	public ServiceFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				ShowData();
			}
		});
		Connection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550); //Frame size
		contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 213, 225)); //background color of the panel
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(194, 10, 404, 92);
		contentPane.add(lblLogo);
		lblLogo.setIcon(new ImageIcon(img_logo));
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(null);
		panel.setBackground(new Color(250, 234, 240));
		panel.setBounds(0, 110, 800, 16);
		contentPane.add(panel);
		
		JLabel lblBookingTransaction = new JLabel("SERVICE MANAGEMENT");
		lblBookingTransaction.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookingTransaction.setForeground(new Color(114, 115, 115));
		lblBookingTransaction.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		lblBookingTransaction.setBounds(162, 136, 456, 32);
		contentPane.add(lblBookingTransaction);
		
		txt_serviceid = new JTextField();
		txt_serviceid.setEditable(false);
		txt_serviceid.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		txt_serviceid.setForeground(new Color(114, 115, 115));
		txt_serviceid.setColumns(10);
		txt_serviceid.setBorder(null);
		txt_serviceid.setBackground(new Color(250, 234, 240));
		txt_serviceid.setBounds(135, 192, 153, 23);
		contentPane.add(txt_serviceid);
		
		JLabel lblCustomerId = new JLabel("SERVICE ID:");
		lblCustomerId.setForeground(new Color(114, 115, 115));
		lblCustomerId.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblCustomerId.setBounds(22, 191, 122, 23);
		contentPane.add(lblCustomerId);
		
		JLabel lblName = new JLabel("SERVICE NAME:");
		lblName.setForeground(new Color(114, 115, 115));
		lblName.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblName.setBounds(22, 226, 114, 23);
		contentPane.add(lblName);
		
		txt_servicename = new JTextField();
		txt_servicename.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		txt_servicename.setForeground(new Color(114, 115, 115));
		txt_servicename.setColumns(10);
		txt_servicename.setBorder(null);
		txt_servicename.setBackground(new Color(250, 234, 240));
		txt_servicename.setBounds(135, 225, 153, 23);
		contentPane.add(txt_servicename);
		
//		JLabel lblAddress = new JLabel("TYPE:");
//		lblAddress.setForeground(new Color(114, 115, 115));
//		lblAddress.setFont(new Font("Century Gothic", Font.PLAIN, 15));
//		lblAddress.setBounds(22, 259, 70, 23);
//		contentPane.add(lblAddress);
		
		JLabel lblContactNo = new JLabel("PRICE:");
		lblContactNo.setForeground(new Color(114, 115, 115));
		lblContactNo.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblContactNo.setBounds(22, 292, 99, 23);
		contentPane.add(lblContactNo);
		
		txt_serviceprice = new JTextField();
		txt_serviceprice.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		txt_serviceprice.setForeground(new Color(114, 115, 115));
		txt_serviceprice.setColumns(10);
		txt_serviceprice.setBorder(null);
		txt_serviceprice.setBackground(new Color(250, 234, 240));
		txt_serviceprice.setBounds(135, 292, 153, 23);
		contentPane.add(txt_serviceprice);
		
//		JComboBox<String> cbx_type = new JComboBox();
//		cbx_type.setForeground(new Color(114, 115, 115));
//		cbx_type.setFont(new Font("Century Gothic", Font.PLAIN, 15));
//		cbx_type.addItem("Part-time");
//		cbx_type.addItem("Full-time");
//		cbx_type.setBackground(new Color(250, 234, 240));
//		cbx_type.setBounds(135, 259, 153, 23);
//		contentPane.add(cbx_type);
		
		//Add button function with database
		JButton btnCreate = new JButton("ADD");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//variable declaration
				String serviceName = txt_servicename.getText();
//				String type = (String) cbx_type.getSelectedItem();
				Float servicePrice = Float.valueOf(txt_serviceprice.getText());
				System.out.println(serviceName + servicePrice);

				try {
					pst = con.prepareStatement("INSERT INTO Service(Service_Name,Service_Price)values(?,?)");
					pst.setString(1, serviceName);
//					pst.setString(2, type);
					pst.setFloat(2, servicePrice);
					

				    for(int i = 0; i < table.getRowCount(); i++) {
				    	 if (serviceName.isEmpty() | servicePrice.toString().isEmpty()) {

			                JOptionPane.showMessageDialog(null, "Please enter complete value!"); break;
			        } 
				    } 
				    
				  //to add the inputs of the users that doesn't duplicates the row of the name, user and password column.

					System.out.println("h");
						JOptionPane.showConfirmDialog(null, "Are you sure you want to save?", "CONFIRMATION!", JOptionPane.YES_NO_OPTION);
						pst.executeUpdate();
						
						JOptionPane.showMessageDialog(null, "Successfully added!");
						ShowData();
						txt_serviceid.setText("");
						txt_servicename.setText("");
//						cbx_type.setSelectedItem(null);
						txt_serviceprice.setText("");


				} catch (NullPointerException | SQLException e2) 
				{
					JOptionPane.showMessageDialog(null, "Enter complete values!");
				}			
			}
		});
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCreate.setForeground(Color.BLACK);
				btnCreate.setBackground(new Color(253, 139, 180));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnCreate.setForeground(Color.GRAY);
				btnCreate.setBackground(new Color(252, 193, 213));
			}});
		btnCreate.setForeground(new Color(114, 115, 115));
		btnCreate.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnCreate.setBorderPainted(false);
		btnCreate.setBackground(new Color(252, 193, 213));
		btnCreate.setBounds(22, 338, 266, 33);
		contentPane.add(btnCreate);
		
		JButton btnUpdate = new JButton("EDIT");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ID = txt_serviceid.getText();
				String serviceName = txt_servicename.getText();
//				String type = (String) cbx_type.getSelectedItem();
				String servicePrice = txt_serviceprice.getText();
				
				try {
					pst = con.prepareStatement("UPDATE Service SET Service_Name='"+serviceName+"', Service_Price='"+servicePrice+"' WHERE Service_ID='"+ID+"'");
					int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to make changes?", "ALERT!", JOptionPane.YES_NO_OPTION);
					if(input == JOptionPane.YES_OPTION) {
						pst.execute();
						JOptionPane.showMessageDialog(null, "Successfully updated!");
						ShowData();
					}else {
						
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnUpdate.setForeground(Color.BLACK);
				btnUpdate.setBackground(new Color(253, 139, 180));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnUpdate.setForeground(Color.GRAY);
				btnUpdate.setBackground(new Color(252, 193, 213));
			}});
		btnUpdate.setForeground(new Color(114, 115, 115));
		btnUpdate.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBackground(new Color(252, 193, 213));
		btnUpdate.setBounds(22, 381, 266, 33);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
		        int SelectRowIndex = table.getSelectedRow();
		        String hold = model.getValueAt(SelectRowIndex, 0).toString();
	        	String query = "DELETE FROM Service WHERE Service_ID='"+hold +"'";
	        	
	        	 try {
					PreparedStatement pst = con.prepareStatement(query);
					int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "ALERT!", JOptionPane.YES_NO_OPTION); {
						if (input == JOptionPane.YES_OPTION) {
							pst.executeUpdate();
				               JOptionPane.showMessageDialog(null, "Deleted successfully.");
				               ShowData();
						}
					}
					txt_serviceid.setText("");
					txt_servicename.setText("");
			        txt_serviceprice.setText("");
//			        cbx_type.setSelectedItem(null);
			            
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnDelete.setForeground(Color.BLACK);
				btnDelete.setBackground(new Color(253, 139, 180));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnDelete.setForeground(Color.GRAY);
				btnDelete.setBackground(new Color(252, 193, 213));
			}});
		btnDelete.setForeground(new Color(114, 115, 115));
		btnDelete.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnDelete.setBorderPainted(false);
		btnDelete.setBackground(new Color(252, 193, 213));
		btnDelete.setBounds(22, 424, 114, 33);
		contentPane.add(btnDelete);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnClear.setForeground(Color.BLACK);
				btnClear.setBackground(new Color(253, 139, 180));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnClear.setForeground(Color.GRAY);
				btnClear.setBackground(new Color(252, 193, 213));
			}			
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showConfirmDialog(null, "Are you sure you want to clear your data?", "Warning", JOptionPane.WARNING_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
				txt_serviceid.setText("");
				txt_servicename.setText("");
				txt_serviceprice.setText("");
//				cbx_type.setSelectedItem(null);
			}});
		btnClear.setForeground(new Color(114, 115, 115));
		btnClear.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnClear.setBorderPainted(false);
		btnClear.setBackground(new Color(252, 193, 213));
		btnClear.setBounds(174, 424, 114, 33);
		contentPane.add(btnClear);
		
		JLabel lblBack = new JLabel("BACK");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AdminDashboardFrame cv = new AdminDashboardFrame();
		    	cv.setVisible(true);
		    	ServiceFrame.this.dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblBack.setForeground(Color.BLACK);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBack.setForeground(Color.GRAY);
			
			}
		});
		lblBack.setHorizontalAlignment(SwingConstants.CENTER);
		lblBack.setForeground(new Color(114, 115, 115));
		lblBack.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblBack.setBounds(0, 0, 85, 37);
		contentPane.add(lblBack);
		
		JLabel lblclose = new JLabel("CLOSE");
		lblclose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
					ServiceFrame.this.dispose();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblclose.setForeground(Color.BLACK);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblclose.setForeground(Color.GRAY);
			}
		});
		lblclose.setHorizontalAlignment(SwingConstants.CENTER);
		lblclose.setForeground(new Color(114, 115, 115));
		lblclose.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblclose.setBounds(715, 0, 85, 37);
		contentPane.add(lblclose);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(304, 191, 472, 266);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(new Color(250, 234, 240));
		table.setFont(new Font("Century Gothic", Font.PLAIN, 9));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int SelectRowIndex = table.getSelectedRow();
				txt_serviceid.setText(model.getValueAt(SelectRowIndex, 0).toString());
				txt_servicename.setText(model.getValueAt(SelectRowIndex, 1).toString());
//				txt_serviceprice.setText(model.getValueAt(SelectRowIndex,2));
//				cbx_type.setSelectedItem(model.getValueAt(SelectRowIndex, 2).toString());
				txt_serviceprice.setText( model.getValueAt(SelectRowIndex, 2).toString());
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		//to customize the header/column
		JTableHeader JTHeader = table.getTableHeader();
		JTHeader.setFont(new Font("Century Gothic", Font.PLAIN, 9));
		JTHeader.setBackground(new Color(252, 193, 213));
	}


	//public JTextField getTxt_serviceid() {
	//	return txt_serviceid;
	//}
	//public void setTfNoOfPeople(String tfNoOfPeople) {
	///	this.tfNoOfPeople.setText(tfNoOfPeople);
	//}

	public void setTxt_serviceid(String txt_serviceid) {
		this.txt_serviceid.setText(txt_serviceid);
	}


	//public void setTxt_serviceid(JTextField sserviceID) {
	//	this.txt_serviceid = sserviceID;
	}



