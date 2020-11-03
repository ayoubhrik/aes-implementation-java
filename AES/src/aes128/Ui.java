package aes128;

import java.awt.EventQueue;
import javax.swing.JFrame;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("all")
public class Ui {

	public static JFrame frmPadlockj;
	public static JTextField inputBox;
	public static JPanel modePanel;
	public static JPasswordField keyBox;
	public static JTextField outputBox;
	public static JRadioButton encryptRadio;
	public static JRadioButton decryptRadio;
	public static JButton startButton;

	public Ui() {
		initForm();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ui window = new Ui();
					window.frmPadlockj.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void initForm() {
		class InvalidInputException extends Exception {
			public InvalidInputException() {
			}

			public InvalidInputException(String message) {
				super(message);
			}
		}
		class InvalidKeyException extends Exception {
			public InvalidKeyException() {
			}

			public InvalidKeyException(String message) {
				super(message);
			}
		}
		AES aes = new AES();
		frmPadlockj = new JFrame();
		frmPadlockj.setTitle("AES [encrypt/decrypt]");
		frmPadlockj.setResizable(false);
		frmPadlockj.setBounds(100, 100, 300, 190);
		frmPadlockj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(10, 11, 161, 40);
		inputPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Input",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));

		inputBox = new JTextField();
		inputBox.setColumns(10);
		GroupLayout gl_inputPanel = new GroupLayout(inputPanel);
		gl_inputPanel.setHorizontalGroup(gl_inputPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_inputPanel.createSequentialGroup()
						.addComponent(inputBox, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_inputPanel.setVerticalGroup(gl_inputPanel.createParallelGroup(Alignment.LEADING).addComponent(inputBox,
				GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE));
		inputPanel.setLayout(gl_inputPanel);

		modePanel = new JPanel();
		modePanel.setBounds(180, 11, 93, 66);
		modePanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Mode",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel keyPanel = new JPanel();
		keyPanel.setBounds(10, 52, 161, 40);
		keyPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Key",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		encryptRadio = new JRadioButton("Encrypt");
		encryptRadio.setSelected(true);

		decryptRadio = new JRadioButton("Decrypt");
		frmPadlockj.getContentPane().setLayout(null);
		frmPadlockj.getContentPane().add(keyPanel);

		ButtonGroup bg1 = new ButtonGroup();

		bg1.add(encryptRadio);
		bg1.add(decryptRadio);

		keyBox = new JPasswordField();
		GroupLayout gl_keyPanel = new GroupLayout(keyPanel);
		gl_keyPanel.setHorizontalGroup(gl_keyPanel.createParallelGroup(Alignment.LEADING).addComponent(keyBox,
				GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE));
		gl_keyPanel.setVerticalGroup(gl_keyPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_keyPanel.createSequentialGroup()
						.addComponent(keyBox, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		keyPanel.setLayout(gl_keyPanel);
		frmPadlockj.getContentPane().add(inputPanel);
		frmPadlockj.getContentPane().add(modePanel);
		GroupLayout gl_modePanel = new GroupLayout(modePanel);
		gl_modePanel.setHorizontalGroup(gl_modePanel.createParallelGroup(Alignment.LEADING).addComponent(encryptRadio)
				.addComponent(decryptRadio));
		gl_modePanel.setVerticalGroup(gl_modePanel.createParallelGroup(Alignment.LEADING)
				.addComponent(encryptRadio, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_modePanel.createSequentialGroup().addGap(19).addComponent(decryptRadio)));
		modePanel.setLayout(gl_modePanel);

		startButton = new JButton("Start");

		startButton.setBounds(182, 80, 89, 18);
		frmPadlockj.getContentPane().add(startButton);

		JPanel outputPanel = new JPanel();
		outputPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Output",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		outputPanel.setBounds(10, 100, 263, 42);
		frmPadlockj.getContentPane().add(outputPanel);

		outputBox = new JTextField();
		outputBox.setBackground(Color.WHITE);
		outputBox.setColumns(10);
		GroupLayout gl_outputPanel = new GroupLayout(outputPanel);
		gl_outputPanel.setHorizontalGroup(gl_outputPanel.createParallelGroup(Alignment.LEADING).addComponent(outputBox,
				GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE));
		gl_outputPanel.setVerticalGroup(gl_outputPanel.createParallelGroup(Alignment.LEADING).addComponent(outputBox,
				GroupLayout.PREFERRED_SIZE, 16, Short.MAX_VALUE));
		outputPanel.setLayout(gl_outputPanel);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					if (inputBox.getText() == null || inputBox.getText().isEmpty()) {
						throw new InvalidInputException();
					}
					if (keyBox.getPassword() == null || String.valueOf(keyBox.getPassword()).isEmpty()) {
						throw new InvalidKeyException();
					}
					String enc="";

					if (encryptRadio.isSelected()) {
						String str = aes.bytesToHex(inputBox.getText().getBytes());
						String keytext = aes.bytesToHex(keyBox.getText().getBytes());
						enc = aes.encrypt(str, keytext); 
						System.out.println("Output : "+enc);
						outputBox.setText(enc);
					} else if (decryptRadio.isSelected()) {
						String keytext = aes.bytesToHex(keyBox.getText().getBytes());
						enc = aes.decrypt(inputBox.getText(), keytext);
						byte[] binputFile = aes.hexToBytes(enc);
						
						String src = new String(binputFile,StandardCharsets.UTF_8);
						System.out.println("Output : "+src);
						outputBox.setText(src);
					} 

				} catch (InvalidInputException ex) {
					JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (InvalidKeyException ex) {
					JOptionPane.showMessageDialog(null, "Invalid key!", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}
}