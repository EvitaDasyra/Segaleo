package creditCard;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreditCardGUI extends JFrame{
	JPanel panel =new JPanel();
	JPanel p1 =new JPanel();
	JPanel p2=new JPanel();
	JPanel p3 =new JPanel();
	JPanel p4 =new JPanel();
	JLabel cardNumLabel = new JLabel("Card Number:");
	JLabel nameLabel = new JLabel("Full Name :");
	JLabel dateLabel = new JLabel("Expire Date:");
	JLabel codeLabel = new JLabel("CVV/CVC Code:");
	JTextField cardNum = new RoundJTextField(16);
	JTextField name = new RoundJTextField(30);
	JTextField code = new RoundJTextField(3);
	JButton payButton = new JButton("payment");
	String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
	String[] year = {"2021","2022","2023","2024","2025","2026","2027","2028","2029","2030"};
   
	JComboBox monthsList = new JComboBox(months);
    JComboBox yearList = new JComboBox(year);
    
    CreditCard cc;
    public class RoundJTextField extends JTextField {
        private Shape shape;
        public RoundJTextField(int size) {
            super(size);
            setOpaque(false); // As suggested by @AVD in comment.
        }
        protected void paintComponent(Graphics g) {
             g.setColor(getBackground());
             g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
             super.paintComponent(g);
        }
        protected void paintBorder(Graphics g) {
             g.setColor(getForeground());
             g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }
        public boolean contains(int x, int y) {
             if (shape == null || !shape.getBounds().equals(getBounds())) {
                 shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
             }
             return shape.contains(x, y);
        }
    }
    
    public CreditCardGUI() {
    	payButton.setFont(new Font("Avenir",Font.BOLD,20));
		payButton.setForeground(new Color(32,150,171));
		payButton.setBackground(new Color(205,221,223));
		payButton.setFocusPainted(false);
		payButton.setBorder(null);
		payButton.setBounds(91, 426, 193, 48);
		payButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		payButton.setAlignmentX(CENTER_ALIGNMENT);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		p1.add(cardNumLabel);
		p1.add(cardNum);
		p2.add(nameLabel);
		p2.add(name);
		p3.add(dateLabel);
		p3.add(monthsList);
		p3.add(yearList);
		p4.add(codeLabel);
		p4.add(code);
		panel.add(p1);
		panel.add(p2);
		panel.add(p3);
		panel.add(p4);
		panel.add(payButton);
		
		PayButtonListener payButtonListener = new PayButtonListener();
		payButton.addActionListener(payButtonListener);
		
		this.setContentPane(panel);
		this.setSize(400,400);
		this.setVisible(true);
		this.setTitle("card");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	class PayButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {

			String cn = cardNum.getText();
			String n = name.getText();
			
			String m = (String) monthsList.getSelectedItem();
			int month = Integer.parseInt(m);

			String y = (String) yearList.getSelectedItem();
			int year = Integer.parseInt(y);
		
			String cod = code.getText();

			//create card object and check for validation
			cc = new CreditCard(cn,n,month,year,cod);
			cc.checkValidation();
		}
		
	}

}



	
	