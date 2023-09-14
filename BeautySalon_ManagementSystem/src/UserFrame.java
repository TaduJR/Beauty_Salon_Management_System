import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Font;
import java.awt.HeadlessException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class UserFrame extends JFrame {

	private final JTextField txt_id;
	public JTextField txt_name;
	private final JTextField txt_phone;
	private JTextField txt_username;
	private final JPasswordField passwordFieldPass;
	private final JTable table;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	//To Launch the user frame
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserFrame frame = new UserFrame(-1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//A method to show and fetch data from the database to the Jtable
	public void showData() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Phone");
		model.addColumn("Password");
		model.addColumn("Name");
		model.addColumn("Role");
		try {
			String query = "SELECT * FROM Account;";
			preparedStatement = ConnectionManager.connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				model.addRow(new Object [] {
					resultSet.getString("id"),
					resultSet.getString("phone"),
					resultSet.getString("password"),
					resultSet.getString("name"),
					resultSet.getString("role"),
				});		
			}
			table.setModel(model);
//			table.setAutoResizeMode(JTable.AUT);
//			table.getColumnModel().getColumn(0).setPreferredWidth(50);
//			table.getColumnModel().getColumn(1).setPreferredWidth(80);
//			table.getColumnModel().getColumn(2).setPreferredWidth(80);
//			table.getColumnModel().getColumn(3).setPreferredWidth(120);
//			table.getColumnModel().getColumn(4).setPreferredWidth(60);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	
	public UserFrame(int id) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				//To automatically loads the 
				showData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550); //Frame size
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 213, 225)); //background color of the panel
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserManagement = new JLabel("ACCOUNT MANAGEMENT");
		lblUserManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserManagement.setForeground(new Color(114, 115, 115));
		lblUserManagement.setFont(new Font("Century Gothic", Font.PLAIN, 19));
		lblUserManagement.setBounds(224, 114, 346, 32);
		contentPane.add(lblUserManagement);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(194, 0, 376, 100);
		contentPane.add(lblLogo);
		Image img_logo = new ImageIcon(Objects.requireNonNull(LoginFrame.class.getResource("res/LOGO-2.png"))).getImage().getScaledInstance(300, 90, Image.SCALE_SMOOTH);
		lblLogo.setIcon(new ImageIcon(img_logo));

		JLabel lblId = new JLabel("ID:");
		lblId.setForeground(new Color(114, 115, 115));
		lblId.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblId.setBounds(23, 156, 107, 24);
		contentPane.add(lblId);

		JLabel lblPhone = new JLabel("Phone NO.");
		lblPhone.setForeground(new Color(114, 115, 115));
		lblPhone.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblPhone.setBounds(23, 190, 85, 24);
		contentPane.add(lblPhone);

		JLabel lblPassword = new JLabel("PASSWORD:");
		lblPassword.setForeground(new Color(114, 115, 115));
		lblPassword.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblPassword.setBounds(23, 224, 107, 24);
		contentPane.add(lblPassword);
		
		JLabel lblName = new JLabel("NAME:");
		lblName.setForeground(new Color(114, 115, 115));
		lblName.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblName.setBounds(23, 258, 107, 24);
		contentPane.add(lblName);

		JLabel lblRole = new JLabel("ROLE:");
		lblRole.setForeground(new Color(114, 115, 115));
		lblRole.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblRole.setBounds(23, 292, 107, 24);
		contentPane.add(lblRole);

		JLabel lblBack = new JLabel("BACK");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AdminDashboardFrame cv = null;
				try {
					cv = new AdminDashboardFrame(id);
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
				cv.setVisible(true);
	            UserFrame.this.dispose();
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
		
		txt_id = new JTextField();
		txt_id.setEditable(false);
		txt_id.setForeground(new Color(114, 115, 115));
		txt_id.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		txt_id.setColumns(10);
		txt_id.setBorder(null);
		txt_id.setBackground(new Color(250, 234, 240));
		txt_id.setBounds(130, 156, 140, 23);
		contentPane.add(txt_id);

		txt_phone = new JTextField();
		txt_phone.setForeground(new Color(114, 115, 115));
		txt_phone.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		txt_phone.setColumns(10);
		txt_phone.setBorder(null);
		txt_phone.setBackground(new Color(250, 234, 240));
		txt_phone.setBounds(130, 190, 140, 23);
		contentPane.add(txt_phone);

		passwordFieldPass = new JPasswordField();
		passwordFieldPass.setForeground(new Color(114, 115, 115));
		passwordFieldPass.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		passwordFieldPass.setColumns(10);
		passwordFieldPass.setBorder(null);
		passwordFieldPass.setBackground(new Color(250, 234, 240));
		passwordFieldPass.setBounds(130, 224, 140, 23);
		contentPane.add(passwordFieldPass);

		txt_name = new JTextField();
		txt_name.setForeground(new Color(114, 115, 115));
		txt_name.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		txt_name.setColumns(10);
		txt_name.setBorder(null);
		txt_name.setBackground(new Color(250, 234, 240));
		txt_name.setBounds(130, 258, 140, 23);
		contentPane.add(txt_name);
		
		JComboBox<String> cbx_role = new JComboBox<String>();
		cbx_role.addItem("admin");
		cbx_role.addItem("customer");
		cbx_role.setForeground(new Color(114, 115, 115));
		cbx_role.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		cbx_role.setBackground(new Color(250, 234, 240));
		cbx_role.setBounds(130, 292, 140, 23);
		contentPane.add(cbx_role);
		setLocationRelativeTo(null);

		//CREATE/ADD BUTTON FUNCTIONS
		JButton btnCreate = new JButton("ADD");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txt_id.getText();
				String phone = txt_phone.getText();
				String pass = new String(passwordFieldPass.getPassword());
				String name = txt_name.getText();
				String role = (String) cbx_role.getSelectedItem();
					
				try {
					if(!id.isEmpty())
						throw new Exception("Please clear all fields before adding.");

					preparedStatement = ConnectionManager.connection.prepareStatement("insert into ACCOUNT(phone, password, name, role)values(?,?,?,?)");
					preparedStatement.setString(1, phone);
					preparedStatement.setString(2, pass);
					preparedStatement.setString(3, name);
					preparedStatement.setString(4, role);
					preparedStatement.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully added!");
					showData();
					txt_name.setText("");
					cbx_role.setSelectedItem(-1);
					txt_phone.setText("");
					passwordFieldPass.setText("");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
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
			}
		});
		btnCreate.setForeground(new Color(114, 115, 115));
		btnCreate.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnCreate.setBorderPainted(false);
		btnCreate.setBackground(new Color(252, 193, 213));
		btnCreate.setBounds(20, 350, 250, 32);
		contentPane.add(btnCreate);

		JButton btnUpdate = new JButton("EDIT");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ID = txt_id.getText();
				String phone = txt_phone.getText();
				String pass = new String(passwordFieldPass.getPassword());
				String name = txt_name.getText();
				String role = (String) cbx_role.getSelectedItem();

				System.out.println(ID+phone+pass+name+role);
				
				try {
					preparedStatement = ConnectionManager.connection.prepareStatement("UPDATE SalonTPS.ACCOUNT SET phone='"+phone+"', password='"+pass+"', name='"+name+"', role='"+role+"' WHERE id="+ID);
					preparedStatement.executeUpdate();
					System.out.println("edited");
					showData();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
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
			}
		});
		btnUpdate.setForeground(new Color(114, 115, 115));
		btnUpdate.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBackground(new Color(252, 193, 213));
		btnUpdate.setBounds(20, 392, 250, 33);
		contentPane.add(btnUpdate);

		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 	DefaultTableModel model = (DefaultTableModel)table.getModel();
			    int selectRowIndex = table.getSelectedRow();
			    String hold = model.getValueAt(selectRowIndex, 0).toString();
		    	String query = "DELETE FROM SalonTPS.ACCOUNT WHERE id='"+hold +"'";
		        	 
			     try {
					 preparedStatement = ConnectionManager.connection.prepareStatement(query);
			    	 int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "ALERT!", JOptionPane.YES_NO_OPTION); {
			    		 if(input == JOptionPane.YES_OPTION) {
							 preparedStatement.executeUpdate();
			    			 JOptionPane.showMessageDialog(null, "Deleted successfully.");
			    			 showData();
						 }
					 }
			            txt_id.setText("");
					 	txt_phone.setText("");
					 	passwordFieldPass.setText("");
						txt_name.setText("");
						cbx_role.setSelectedIndex(-1);
				 } catch(Exception ex){
					 JOptionPane.showMessageDialog(null, ex.getMessage());
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
		btnDelete.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnDelete.setBorderPainted(false);
		btnDelete.setBackground(new Color(252, 193, 213));
		btnDelete.setBounds(23, 434, 107, 33);
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
				//clear function
			}			
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showConfirmDialog(null, "Are you sure you want to clear your data?", "Warning", JOptionPane.WARNING_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
				txt_id.setText("");
				txt_name.setText("");
				cbx_role.setSelectedIndex(-1);
				txt_phone.setText("");
				passwordFieldPass.setText("");
			}});
		btnClear.setForeground(new Color(114, 115, 115));
		btnClear.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnClear.setBorderPainted(false);
		btnClear.setBackground(new Color(252, 193, 213));
		btnClear.setBounds(163, 434, 107, 33);
		contentPane.add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(280, 156, 498, 353);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
					int SelectRowIndex = table.getSelectedRow();
					txt_id.setText(model.getValueAt(SelectRowIndex, 0).toString());
					txt_phone.setText(model.getValueAt(SelectRowIndex, 1).toString());
					passwordFieldPass.setText(model.getValueAt(SelectRowIndex, 2).toString());
					txt_name.setText(model.getValueAt(SelectRowIndex, 3).toString());
					cbx_role.setSelectedItem(model.getValueAt(SelectRowIndex, 4).toString());
			}
		});
		table.setBorder(null);
		table.setBackground(new Color(250, 234, 240));
		table.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		
		//to customize the header/column
		JTableHeader JTHeader = table.getTableHeader();
		JTHeader.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		JTHeader.setBackground(new Color(252, 193, 213));
		
	}
}