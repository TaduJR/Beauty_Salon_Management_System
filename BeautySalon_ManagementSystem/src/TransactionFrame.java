import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class TransactionFrame extends JFrame {
	private JTable tableTransaction;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public void showDataReservationPayment() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("BookingID");
		model.addColumn("Account Owner Name");
		model.addColumn("Service");
		model.addColumn("Paid Amount");
		model.addColumn("Date of Transaction");
		model.addColumn("Account Owner Role");

		try {
			String query ="SELECT Booking.id,Account.name, Service.name,Service.price, Booking.date, Account.phone, Account.role " +
					"FROM Booking INNER JOIN Account ON Booking.account_id = Account.id " +
					"INNER JOIN Service ON Booking.service_id = Service.id";
			preparedStatement = ConnectionManager.connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				model.addRow(new Object [] {
					resultSet.getInt(1),
					resultSet.getString("Account.name"),
					resultSet.getString("Service.name"),
					resultSet.getString("Service.price"),
					resultSet.getString("Booking.date"),
					resultSet.getString("Account.phone"),
					resultSet.getString("Account.role")
				});
			}
			tableTransaction.setModel(model);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransactionFrame frame = new TransactionFrame(-1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public TransactionFrame(int id) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				//To automatically loads the tables from the dt to the gui
				showDataReservationPayment();
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550); //Frame size
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 213, 225)); //background color of the panel
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserManagement = new JLabel("TRANSACTIONS HISTORY");
		lblUserManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserManagement.setForeground(new Color(114, 115, 115));
		lblUserManagement.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		lblUserManagement.setBounds(171, 114, 414, 53);
		contentPane.add(lblUserManagement);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(194, 0, 372, 104);
		contentPane.add(lblLogo);
		Image img_logo = new ImageIcon(Objects.requireNonNull(LoginFrame.class.getResource("res/LOGO-2.png"))).getImage().getScaledInstance(300, 90, Image.SCALE_SMOOTH);
		lblLogo.setIcon(new ImageIcon(img_logo));
		
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
		    	TransactionFrame.this.dispose();
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 200, 758, 142);
		contentPane.add(scrollPane);

		tableTransaction = new JTable();
		tableTransaction.setBackground(new Color(250, 234, 240));
		tableTransaction.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		scrollPane.setViewportView(tableTransaction);
	}
}
