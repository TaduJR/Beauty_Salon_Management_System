import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class UserDashboardFrame extends JFrame {
	private Image img_logo = new ImageIcon(Objects.requireNonNull(LoginFrame.class.getResource("res/LOGO-2.png"))).getImage().getScaledInstance(300, 90, Image.SCALE_SMOOTH);
	private JPanel contentPane;
	private Statement statement;
	private ResultSet resultSet;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserDashboardFrame frame = new UserDashboardFrame(-1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UserDashboardFrame(int id) throws SQLException {
		try {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 700, 390); //Frame size
			contentPane = new JPanel();
			contentPane.setBackground(new Color(251, 213, 225)); //background color of the panel
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			setLocationRelativeTo(null);

			JLabel lbllogo = new JLabel("");
			lbllogo.setBounds(185, 10, 310, 92);
			contentPane.add(lbllogo);
			lbllogo.setIcon(new ImageIcon(img_logo));

			String sqlQuery = String.format("SELECT name FROM ACCOUNT WHERE id = %s AND role = 'customer'", id);
			statement = ConnectionManager.connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
			if(!resultSet.next()) throw new Exception("User Not Found");
			String name = resultSet.getString("name");

			JLabel lblUserDashboard = new JLabel(String.format("WELCOME TO CUSTOMER DASHBOARD %s", name));
			lblUserDashboard.setHorizontalAlignment(SwingConstants.CENTER);
			lblUserDashboard.setForeground(new Color(114, 115, 115));
			lblUserDashboard.setFont(new Font("Century Gothic", Font.PLAIN, 20));
			lblUserDashboard.setBounds(100, 131, 500, 47);
			contentPane.add(lblUserDashboard);

			JLabel lblBooking = new JLabel("BOOKING");
			lblBooking.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					BookingFrame cv = new BookingFrame(id);
					cv.setVisible(true);
					UserDashboardFrame.this.dispose();
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					lblBooking.setForeground(Color.BLACK);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					lblBooking.setForeground(Color.GRAY);
				}
			});
			lblBooking.setHorizontalAlignment(SwingConstants.CENTER);
			lblBooking.setForeground(new Color(114, 115, 115));
			lblBooking.setFont(new Font("Century Gothic", Font.PLAIN, 18));
			lblBooking.setBounds(41, 225, 150, 33);
			contentPane.add(lblBooking);

			JLabel lbllogout = new JLabel("EXIT");
			lbllogout.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					LoginFrame cv = new LoginFrame();
					cv.setVisible(true);
					UserDashboardFrame.this.dispose();
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					lbllogout.setForeground(Color.BLACK);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					lbllogout.setForeground(Color.GRAY);
				}});
			lbllogout.setHorizontalAlignment(SwingConstants.CENTER);
			lbllogout.setForeground(new Color(114, 115, 115));
			lbllogout.setFont(new Font("Century Gothic", Font.PLAIN, 18));
			lbllogout.setBounds(529, 225, 105, 33);
			contentPane.add(lbllogout);

			JLabel lblBack = new JLabel("BACK");
			lblBack.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					LoginFrame cv = new LoginFrame();
					cv.setVisible(true);
					UserDashboardFrame.this.dispose();
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
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
}