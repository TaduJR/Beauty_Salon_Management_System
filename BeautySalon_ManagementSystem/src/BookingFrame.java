import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.border.LineBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import com.toedter.calendar.JDateChooser;

public class BookingFrame extends JFrame {
	private final int id;
	private final JTextField txt_bookid;
	public JComboBox<String> cbx_services;
	private final JTextField txt_price;
	private final JTable table;
	PreparedStatement preparedStatement;
	ResultSet resultSet;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookingFrame frame = new BookingFrame(-1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void fillComboBoxService() {
		try {
			String query = "SELECT name FROM SERVICE";
			preparedStatement = ConnectionManager.connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				cbx_services.addItem(resultSet.getString("name"));
			}
            cbx_services.setSelectedIndex(-1);
            txt_price.setText("");
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	private void showData() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Booking ID");
		model.addColumn("Service Name");
		model.addColumn("Price");
		model.addColumn("Date");

		try {
			String query = String.format("SELECT BOOKING.id AS id, SERVICE.name AS name, SERVICE.price AS price, BOOKING.date AS date " +
					"FROM SERVICE INNER JOIN BOOKING ON SERVICE.id = BOOKING.service_id " +
					"WHERE BOOKING.account_id = %s", id);
			preparedStatement = ConnectionManager.connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				model.addRow(new Object [] {
					resultSet.getString("id"),
					resultSet.getString("name"),
					resultSet.getString("price"),
					resultSet.getString("date"),
				});
			}
			table.setModel(model);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	private int serviceIDValue(String service_name) {
	 	String sql = "SELECT id, name FROM SERVICE WHERE name='" + service_name + "'";
		 int id = -1;
		try {
			preparedStatement = ConnectionManager.connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
            	id = resultSet.getInt("id");
            }
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
		return id;
	}

	public BookingFrame(int id) {
		this.id = id;
        Boolean initial = true;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				showData();
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550); //Frame size
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 213, 225));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(194, 10, 390, 92);
		contentPane.add(lblLogo);
		Image img_logo = new ImageIcon(Objects.requireNonNull(LoginFrame.class.getResource("res/LOGO-2.png"))).getImage().getScaledInstance(300, 90, Image.SCALE_SMOOTH);
		lblLogo.setIcon(new ImageIcon(img_logo));
		
		JLabel lblBookingTransaction = new JLabel("BOOKING TRANSACTION");
		lblBookingTransaction.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookingTransaction.setForeground(new Color(114, 115, 115));
		lblBookingTransaction.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		lblBookingTransaction.setBounds(224, 124, 360, 44);
		contentPane.add(lblBookingTransaction);

		JLabel lblBookingId = new JLabel("BOOKING ID:");
		lblBookingId.setForeground(new Color(114, 115, 115));
		lblBookingId.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblBookingId.setBounds(20, 172, 143, 23);
		contentPane.add(lblBookingId);

		txt_bookid = new JTextField();
		txt_bookid.setEditable(false);
		txt_bookid.setFont(new Font("Century Gothic", Font.PLAIN, 17));
		txt_bookid.setForeground(new Color(114, 115, 115));
		txt_bookid.setColumns(10);
		txt_bookid.setBorder(null);
		txt_bookid.setBackground(new Color(250, 234, 240));
		txt_bookid.setBounds(130, 171, 142, 23);
		contentPane.add(txt_bookid);

		JLabel lblService = new JLabel("SERVICE:");
		lblService.setForeground(new Color(114, 115, 115));
		lblService.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblService.setBounds(20, 220, 142, 23);
		contentPane.add(lblService);

		JLabel lblPrice = new JLabel("PRICE:");
		lblPrice.setForeground(new Color(114, 115, 115));
		lblPrice.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblPrice.setBounds(20, 269, 87, 22);
		contentPane.add(lblPrice);

		txt_price = new JTextField();
		txt_price.setEditable(false);
		txt_price.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		txt_price.setForeground(new Color(114, 115, 115));
		txt_price.setColumns(10);
		txt_price.setBorder(null);
		txt_price.setBackground(new Color(250, 234, 240));
		txt_price.setBounds(130, 269, 142, 23);
		contentPane.add(txt_price);

		cbx_services = new JComboBox<String>();
		cbx_services.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
                String str = (String) cbx_services.getSelectedItem();
                String sql = "SELECT price FROM SERVICE WHERE name='" + str + "'";
                try {
                    String fetchedPrice;
                    preparedStatement = ConnectionManager.connection.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        fetchedPrice = String.valueOf(resultSet.getFloat("price"));
                        txt_price.setText(fetchedPrice);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

			}
		});
		cbx_services.setForeground(new Color(114, 115, 115));
		cbx_services.setBackground(new Color(250, 234, 240));
		cbx_services.setBounds(130, 220, 142, 23);
		contentPane.add(cbx_services);
		fillComboBoxService();

		JLabel lblDate = new JLabel("DATE:");
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate.setForeground(new Color(114, 115, 115));
		lblDate.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblDate.setBounds(20, 318, 93, 23);
		contentPane.add(lblDate);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setFont(new Font("Century Gothic", Font.PLAIN, 15));
		dateChooser.setBounds(130, 318, 142, 23);
		contentPane.add(dateChooser);

		JButton btnCreate = new JButton("ADD");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String serviceName = (String) cbx_services.getSelectedItem();
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String date = sdf.format(dateChooser.getDate());

					if(cbx_services == null || Objects.equals(txt_price.getText(), "") || date.isEmpty())
						throw new Exception("Enter complete values!");

                    String sqlQuery = String.format("INSERT INTO BOOKING(service_id, account_id, date) VALUES(%s, %s, '%s')",serviceIDValue(serviceName), id, java.sql.Date.valueOf(date));
                    Statement statement = ConnectionManager.connection.createStatement();
                    statement.executeUpdate(sqlQuery);

                    JOptionPane.showMessageDialog(null, "Successfully added!");
                    showData();
                    cbx_services.setSelectedIndex(-1);
                    dateChooser.setDate(null);
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
		btnCreate.setBounds(20, 380, 252, 33);
		contentPane.add(btnCreate);

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
				txt_bookid.setText("");
				cbx_services.setSelectedIndex(-1);
				dateChooser.setDate(null);
			}
		});
		btnClear.setForeground(new Color(114, 115, 115));
		btnClear.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnClear.setBorderPainted(false);
		btnClear.setBackground(new Color(252, 193, 213));
		btnClear.setBounds(20, 425, 252, 33);
		contentPane.add(btnClear);

		JLabel lblBack = new JLabel("BACK");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserDashboardFrame cv = null;
				try {
					cv = new UserDashboardFrame(id);
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
				cv.setVisible(true);
		    	BookingFrame.this.dispose();
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
		scrollPane.setBackground(Color.LIGHT_GRAY);
		scrollPane.setFont(new Font("Century Gothic", Font.PLAIN, 9));
		scrollPane.setBounds(282, 171, 494, 293);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBorder(null);
		table.setBackground(new Color(250, 234, 240));
		table.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		scrollPane.setViewportView(table);

		JTableHeader JTHeader = table.getTableHeader();
		JTHeader.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		JTHeader.setBackground(new Color(252, 193, 213));
	}
}


















