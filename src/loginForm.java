import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class loginForm extends JDialog {

    private JTextField textField5;
    private JPanel panel1;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel LoginPanel;
    private JPasswordField passwordField1;
    private JPanel panel2;
    private JPanel panel3;


    public loginForm(JFrame parent) {

     // LoginForm object constructor with JFrame as parent as the parameters

     super(parent);
     setTitle("Login");
     setContentPane(LoginPanel);
     setMinimumSize(new Dimension(450, 474));
     setModal(true);
     setLocationRelativeTo(parent);
     setDefaultCloseOperation(DISPOSE_ON_CLOSE);
     setVisible(true);

     OKButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {

             String email = textField5.getText();
             String password = String.valueOf(passwordField1.getPassword());

             user = getAuthenticatedUser(email, password);

             if (user != null){

                 dispose();

             } else {

                 JOptionPane.showMessageDialog(loginForm.this,
                         "Email or password Invalid",
                         "Try again",
                         JOptionPane.ERROR_MESSAGE);
             }

         }
     });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });

        setVisible(true);
    }

 public static User user;
 private User getAuthenticatedUser(String email , String password){
     User user = null;

     final String DB_URL = "jdbc:mysql://localhost/mikkjurgeniktkhk_Impandmed?serverTimezone=UTC";
     final String USERNAME = "mikkjurgeniktkhk_jurgen";
     final String PASSWORD = "Lollakas123";

     try{

         Connection conn = DriverManager.getConnection(DB_URL, USERNAME , PASSWORD);
         // DB connection success

         Statement stmt = conn.createStatement();
         String sql = "SELECT * FROM users WHERE email=? AND password=?";
         PreparedStatement preparedStatement = conn.prepareStatement(sql);
         preparedStatement.setString(1, email);
         preparedStatement.setString(2, password);


         ResultSet resultSet = preparedStatement.executeQuery();

         if (resultSet.next()){

             user = new User();

             user.name = resultSet.getString("name");
             user.email = resultSet.getString("email");
             user.phone = resultSet.getString("phone");
             user.address = resultSet.getString("address");
             user.password = resultSet.getString("password");


         }

         stmt.close();
         conn.close();

     } catch(Exception e){

         e.printStackTrace();

     }

     return user;

 }

 public static void main(String[] args) {

     // main method
     loginForm LoginForm = new loginForm(null);
     User user = loginForm.user;
     if (user != null){

         System.out.println("Successful Authentication of: " + user.name);
         System.out.println("                     Email  : " + user.email);
         System.out.println("                     Phone  : " + user.phone);
         System.out.println("                     Aadress: " + user.address);


     } else{

         System.out.println("Authentication canceled");

     }


   }

}
