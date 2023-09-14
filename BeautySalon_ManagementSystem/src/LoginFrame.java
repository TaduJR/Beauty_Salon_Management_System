import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.util.Arrays;
import java.util.Objects;

public class LoginFrame extends JFrame {
	private final JPasswordField passFieldPassword;
	private final JComboBox<String> comboBoxRole;
	private static Statement statement = null;
	private static ResultSet resultSet = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550); //Frame size
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 213, 225)); //background color of the panel
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel labelLogo = new JLabel("");
		labelLogo.setBounds(210, 20, 294, 197);
		contentPane.add(labelLogo);
		Image img_logo = new ImageIcon(Objects.requireNonNull(LoginFrame.class.getResource("res/LOGO.png"))).getImage().getScaledInstance(300, 177, Image.SCALE_SMOOTH);
		labelLogo.setIcon(new ImageIcon(img_logo));


		JTextField txtFieldPhone = new JTextField();
		txtFieldPhone.setBounds(220, 276, 284, 38);
		txtFieldPhone.setBorder(new LineBorder(new Color(252, 193, 213))); // border color
		txtFieldPhone.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		contentPane.add(txtFieldPhone);
		txtFieldPhone.setLayout(null);

		passFieldPassword = new JPasswordField();
		passFieldPassword.setBounds(220, 324, 284, 38);
		passFieldPassword.setBorder(new LineBorder(new Color(252, 193, 213)));
		passFieldPassword.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		contentPane.add(passFieldPassword);
		passFieldPassword.setLayout(null);


		JLabel labelUserName = new JLabel("Phone");
		labelUserName.setBounds(150, 277, 70, 37);
		contentPane.add(labelUserName);

		JLabel labelPass = new JLabel("Password");
		labelPass.setBounds(150, 325, 70, 37);
		contentPane.add(labelPass);

		String[] roleOptions = {"admin", "customer"};
		comboBoxRole = new JComboBox<>(roleOptions);
		comboBoxRole.setBounds(150, 389,100,30);
		comboBoxRole.setBackground(new Color(252, 193, 213));
		comboBoxRole.setSelectedIndex(-1);
		contentPane.add(comboBoxRole);

		JButton btnSignIn = new JButton("SIGN IN");
		btnSignIn.setForeground(new Color(114, 115, 115));
		btnSignIn.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnSignIn.setBorderPainted(false);
		btnSignIn.setBounds(400, 389, 100, 30);
		btnSignIn.setBackground(new Color(252, 193, 213));
		contentPane.add(btnSignIn);

		btnSignIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int userName = Integer.parseInt(txtFieldPhone.getText());
					String password = new String(passFieldPassword.getPassword());;
					String role = (String) comboBoxRole.getSelectedItem();

					if(Objects.equals(password, "") || role == null)
						throw new Exception("Please Fill all Required Fields.");

					String sqlQuery = String.format("SELECT * FROM SalonTPS.ACCOUNT WHERE role='%s' AND phone =%s AND password='%s'", role, userName, password);
					statement = ConnectionManager.connection.createStatement();
					resultSet = statement.executeQuery(sqlQuery);
					if(!resultSet.next()) throw new Exception("User Not Found");

					int id = resultSet.getInt(1);

					if(role.equals("admin")) {
						JOptionPane.showMessageDialog(null, "You Successfully logged in as Admin!");
						AdminDashboardFrame cv = new AdminDashboardFrame(id);
						cv.setVisible(true);
						LoginFrame.this.dispose();
					}else if(role.equals("customer")) {
						JOptionPane.showMessageDialog(null, "You Successfully logged in as Customer!");
						UserDashboardFrame cv = new UserDashboardFrame(id);
						cv.setVisible(true);
						LoginFrame.this.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Please enter correct credentials!");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Please Enter Only Number in Phone Field.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
	}
}
