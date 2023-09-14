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

public class AdminDashboardFrame extends JFrame {
    private Image img_logo = new ImageIcon(Objects.requireNonNull(LoginFrame.class.getResource("res/LOGO-2.png"))).getImage().getScaledInstance(300, 90, Image.SCALE_SMOOTH);
    private JPanel contentPane;
    private Statement statement;
    private ResultSet resultSet;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminDashboardFrame frame = new AdminDashboardFrame(-1);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AdminDashboardFrame(int id) throws SQLException {
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

            String sqlQuery = String.format("SELECT name FROM SalonTPS.ACCOUNT WHERE id = %s", id);
            statement = ConnectionManager.connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            if(!resultSet.next()) throw new Exception("User Not Found");
            String name = resultSet.getString("name");

            JLabel lblAdminDashboard = new JLabel(String.format("WELCOME TO ADMIN DASHBOARD %s", name));
            lblAdminDashboard.setHorizontalAlignment(SwingConstants.CENTER);
            lblAdminDashboard.setForeground(new Color(114, 115, 115));
            lblAdminDashboard.setFont(new Font("Century Gothic", Font.PLAIN, 20));
            lblAdminDashboard.setBounds(75, 131, 500, 45);
            contentPane.add(lblAdminDashboard);

            JLabel lblUsers = new JLabel("ACCOUNTS");
            lblUsers.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
//                UserFrame cv = new UserFrame();
//                cv.setVisible(true);
                    AdminDashboardFrame.this.dispose();
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    lblUsers.setForeground(Color.BLACK);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    lblUsers.setForeground(Color.GRAY);
                }});
            lblUsers.setHorizontalAlignment(SwingConstants.CENTER);
            lblUsers.setForeground(new Color(114, 115, 115));
            lblUsers.setFont(new Font("Century Gothic", Font.PLAIN, 18));
            lblUsers.setBounds(41, 225, 150, 33);
            contentPane.add(lblUsers);

            JLabel lblService = new JLabel("SERVICES");
            lblService.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
//                ServiceFrame cv = new ServiceFrame();
//                cv.setVisible(true);
                    AdminDashboardFrame.this.dispose();
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    lblService.setForeground(Color.BLACK);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    lblService.setForeground(Color.GRAY);
                }});

            lblService.setHorizontalAlignment(SwingConstants.CENTER);
            lblService.setForeground(new Color(114, 115, 115));
            lblService.setFont(new Font("Century Gothic", Font.PLAIN, 18));
            lblService.setBounds(219, 225, 105, 33);
            contentPane.add(lblService);

            JLabel lblTransact = new JLabel("TRANSACTIONS");
            lblTransact.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
//                TransactionFrame cv = new TransactionFrame();
//                cv.setVisible(true);
                    AdminDashboardFrame.this.dispose();
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    lblTransact.setForeground(Color.BLACK);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    lblTransact.setForeground(Color.GRAY);
                }});
            lblTransact.setHorizontalAlignment(SwingConstants.CENTER);
            lblTransact.setForeground(new Color(114, 115, 115));
            lblTransact.setFont(new Font("Century Gothic", Font.PLAIN, 18));
            lblTransact.setBounds(357, 225, 150, 33);
            contentPane.add(lblTransact);

            JLabel lbllogout = new JLabel("EXIT");
            lbllogout.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    LoginFrame cv = new LoginFrame();
                    cv.setVisible(true);
                    AdminDashboardFrame.this.dispose();
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
                    AdminDashboardFrame.this.dispose();
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