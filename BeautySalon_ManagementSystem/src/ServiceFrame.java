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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import java.util.Objects;

public class ServiceFrame extends JFrame {

	private final JTextField txt_serviceid;
	private final JTextField txt_servicename;
	private final JTextField txt_serviceprice;
	private JTable table;

	PreparedStatement preparedStatement;
	ResultSet resultSet;

	public void showData() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Service ID");
		model.addColumn("Service Name");
		model.addColumn("Price");

		try {
			String query = "SELECT * FROM Service";
			preparedStatement = ConnectionManager.connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				model.addRow(new Object[]{
						resultSet.getString("id"),
						resultSet.getString("name"),
						resultSet.getFloat("price"),
				});
			}
			table.setModel(model);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServiceFrame frame = new ServiceFrame(-1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServiceFrame(int id) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
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

		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(194, 10, 404, 92);
		contentPane.add(lblLogo);
		Image img_logo = new ImageIcon(Objects.requireNonNull(LoginFrame.class.getResource("res/LOGO-2.png"))).getImage().getScaledInstance(300, 90, Image.SCALE_SMOOTH);
		lblLogo.setIcon(new ImageIcon(img_logo));

		JLabel lblBookingTransaction = new JLabel("SERVICE MANAGEMENT");
		lblBookingTransaction.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookingTransaction.setForeground(new Color(114, 115, 115));
		lblBookingTransaction.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		lblBookingTransaction.setBounds(162, 136, 456, 32);
		contentPane.add(lblBookingTransaction);

		JLabel lblServiceId = new JLabel("SERVICE ID:");
		lblServiceId.setForeground(new Color(114, 115, 115));
		lblServiceId.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblServiceId.setBounds(22, 191, 122, 23);
		contentPane.add(lblServiceId);

		txt_serviceid = new JTextField();
		txt_serviceid.setEditable(false);
		txt_serviceid.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		txt_serviceid.setForeground(new Color(114, 115, 115));
		txt_serviceid.setColumns(10);
		txt_serviceid.setBorder(null);
		txt_serviceid.setBackground(new Color(250, 234, 240));
		txt_serviceid.setBounds(135, 192, 153, 23);
		contentPane.add(txt_serviceid);

		JLabel lblServiceName = new JLabel("SERVICE NAME:");
		lblServiceName.setForeground(new Color(114, 115, 115));
		lblServiceName.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblServiceName.setBounds(22, 226, 114, 23);
		contentPane.add(lblServiceName);

		txt_servicename = new JTextField();
		txt_servicename.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		txt_servicename.setForeground(new Color(114, 115, 115));
		txt_servicename.setColumns(10);
		txt_servicename.setBorder(null);
		txt_servicename.setBackground(new Color(250, 234, 240));
		txt_servicename.setBounds(135, 225, 153, 23);
		contentPane.add(txt_servicename);

		JLabel lblPrice = new JLabel("PRICE:");
		lblPrice.setForeground(new Color(114, 115, 115));
		lblPrice.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblPrice.setBounds(22, 292, 99, 23);
		contentPane.add(lblPrice);

		txt_serviceprice = new JTextField();
		txt_serviceprice.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		txt_serviceprice.setForeground(new Color(114, 115, 115));
		txt_serviceprice.setColumns(10);
		txt_serviceprice.setBorder(null);
		txt_serviceprice.setBackground(new Color(250, 234, 240));
		txt_serviceprice.setBounds(135, 292, 153, 23);
		contentPane.add(txt_serviceprice);

		JButton btnCreate = new JButton("ADD");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String serviceName = txt_servicename.getText();
				float servicePrice = Float.parseFloat(txt_serviceprice.getText());

				try {
					preparedStatement = ConnectionManager.connection.prepareStatement("INSERT INTO SERVICE(name, price) VALUES (?,?)");
					preparedStatement.setString(1, serviceName);
					preparedStatement.setFloat(2, servicePrice);

					for (int i = 0; i < table.getRowCount(); i++) {
						if (serviceName.isEmpty() | Float.toString(servicePrice).isEmpty())
							throw new Exception("Please enter complete value!");
					}
					preparedStatement.executeUpdate();

					JOptionPane.showMessageDialog(null, "Successfully added!");
					showData();
					txt_serviceid.setText("");
					txt_servicename.setText("");
					txt_serviceprice.setText("");
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
				String servicePrice = txt_serviceprice.getText();

				try {
					preparedStatement = ConnectionManager.connection.prepareStatement("UPDATE SERVICE SET name='" + serviceName + "', price='" + servicePrice + "' WHERE iD='" + ID + "'");
					int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to make changes?", "ALERT!", JOptionPane.YES_NO_OPTION);
					if (input == JOptionPane.YES_OPTION) {
						preparedStatement.execute();
						JOptionPane.showMessageDialog(null, "Successfully updated!");
						showData();
					}
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
		btnUpdate.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBackground(new Color(252, 193, 213));
		btnUpdate.setBounds(22, 381, 266, 33);
		contentPane.add(btnUpdate);

		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int SelectRowIndex = table.getSelectedRow();
				String hold = model.getValueAt(SelectRowIndex, 0).toString();
				String query = "DELETE FROM SERVICE WHERE id='" + hold + "'";

				try {
					PreparedStatement pst = ConnectionManager.connection.prepareStatement(query);
					int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "ALERT!", JOptionPane.YES_NO_OPTION);
					{
						if (input == JOptionPane.YES_OPTION) {
							pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Deleted successfully.");
							showData();
						}
					}
					txt_serviceid.setText("");
					txt_servicename.setText("");
					txt_serviceprice.setText("");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Deleted successfully.");
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
			}
		});
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
				JOptionPane.showConfirmDialog(null, "Are you sure you want to clear your data?", "Warning", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				txt_serviceid.setText("");
				txt_servicename.setText("");
				txt_serviceprice.setText("");
			}
		});
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
				AdminDashboardFrame cv = null;
				try {
					cv = new AdminDashboardFrame(id);
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(304, 191, 472, 266);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setBackground(new Color(250, 234, 240));
		table.setFont(new Font("Century Gothic", Font.PLAIN, 9));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int SelectRowIndex = table.getSelectedRow();
				txt_serviceid.setText(model.getValueAt(SelectRowIndex, 0).toString());
				txt_servicename.setText(model.getValueAt(SelectRowIndex, 1).toString());
				txt_serviceprice.setText(model.getValueAt(SelectRowIndex, 2).toString());
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
				new Object[][]{},
				new String[]{}
		));
		setLocationRelativeTo(null);

		JTableHeader JTHeader = table.getTableHeader();
		JTHeader.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		JTHeader.setBackground(new Color(252, 193, 213));
	}
}