package gui.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.components.RoundedPanel;
import gui.factory.BackgroundFactory;
import gui.factory.ButtonFactory;
import gui.factory.FontFactory;
import gui.factory.LogoFactory;
import resources.ColorResources;
import resources.TextResources;

public class MyOrdersWindow extends JFrame implements ActionListener{

	private JPanel panel;
	private JPanel insidePanel;

	private String path = "buttonImages/Back Button";
	private String lang = TextResources.imageLang;
	private ImageIcon backImage = new ImageIcon(path + lang);
	private JButton backBtn;
	
	//αν δεν υπάρχει παραγγελια τοτε θα εμφανιζει αναλογο μηνυμα
	private JLabel orderNumber;
	private JLabel orderPrice;
	private JLabel roomNum;
	private JLabel orderDate;
	private JLabel paymentMethod;
	private JButton rating;
	
	public MyOrdersWindow() {
		initializePanelToFrame();
		windowsConfiguration();
		showWindow(this, true);
	}

	public void windowsConfiguration() {
		this.setTitle("My Orders");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}

	public void initializePanelToFrame() {

		new TextResources().changeLanguage();
		panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(375, 812));
		panel.setBackground(new Color(216, 223, 224));

		configureButtons();
		addComponentsToPanel();
		addListeners();

		this.setContentPane(panel);
		this.pack();
	}

	
	public void initializeInsidePanel() {
		insidePanel = new RoundedPanel(50, new Color(177, 206, 209));
		insidePanel.setOpaque(false);
		insidePanel.setBorder(new EmptyBorder(50, 20, 380, 20));
		insidePanel.setLayout(null);
		insidePanel.setBounds(11, 250, 351, 300);
		configureButtons();
		
	}
	
	public void showWindow(JFrame frame, boolean show) {
		frame.setVisible(show);
	}

	public void addComponentsToPanel() {
		panel.add(backBtn);
		panel.add(LogoFactory.addLogoScaled());
		
		initializeInsidePanel();
		addComponentsToInsidePanel();
		panel.add(insidePanel);
		panel.add(BackgroundFactory.addBackgroundLight());

	}
	
	public void addComponentsToInsidePanel() {
		insidePanel.add(rating);
		
		//εδω θα μπουν και τα labels για την παραγγελια
	}
	
	public void configureButtons() {
		backBtn = ButtonFactory.createButtonIcon(backImage);
		backBtn.setBounds(12, 40, 67, 45);
		
		rating = ButtonFactory.createButton("Rate this order", FontFactory.poppins(15), ColorResources.bgLoginWindow, Color.white);
		rating.setBounds(210, 260, 120, 22);
	}
	
	public void addListeners() {
		backBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backBtn) {
			this.dispose();
			new ProfileWindow();
		}
		
	}
	
}