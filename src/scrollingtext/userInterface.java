package scrollingtext;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;


public class userInterface extends JFrame implements ActionListener {
	
	int currentList;
	int currentItem;
	
	JFrame guiframe;
	
	JList incoming;
	JList showed;
	JList deleted;
	
	JTextArea textPreview;
	JTextField alert;
	Container contentPane;
	JButton show = new JButton("Show");
    JButton delete = new JButton("Delete");
    JButton back = new JButton("Back");
    
    JScrollPane showedSelection;
    JScrollPane incomingSelection;
    JScrollPane deletedSelection;
    
    DefaultListModel newModel, showedModel, deletedModel;
    
    Object lastelement;
    int lastselected = 0; // 0 = no selection, 1 = new selected, 2-showed selected, 3 - deleted selected
    
    int handleID;
    
	//static String[] listItems = {"message1","message2","message3","message4","message5","message6"};
	//static List<messageItem> allItems = new ArrayList<messageItem>();
	
	static String[] incomingItems;
	static String[] showingItems;
	static String[] deletedItems;
	
	boolean reallyselected = false;
	
	public userInterface() {
		lastelement = "";
		
		this.guiframe = new JFrame("Welche Zukunft?! - Kommentare");
		this.guiframe.setBounds(10, 10, 510, 710);
	    Dimension d = new Dimension(500,700);
	    Container con = this.guiframe.getContentPane();
	    con.setPreferredSize(d);
	    
		contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		
		//Label 1
		JLabel head1 = new JLabel("incoming messages");
		contentPane.add(head1);
		
		//List 1 = Incoming Messages
		newModel = new DefaultListModel();
		incoming = new JList(newModel);
		incoming.setSelectedIndex(0);
		incoming.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		incomingSelection = new JScrollPane(incoming,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		incomingSelection.setPreferredSize(new Dimension(280,80));
		contentPane.add(incomingSelection);
		incoming.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(java.awt.event.MouseEvent e) {	
	        	reallyselected = true;
				int index = incoming.locationToIndex(e.getPoint());    
				String selectedItem = (String) newModel.getElementAt(index);
				
				deleted.clearSelection();
				showed.clearSelection();
				lastselected = 1;
				lastelement = newModel.getElementAt(index);
				
				String text = selectedItem;
				String[] splitID = text.split(" : ");
				handleID = Integer.parseInt(splitID[0]);
				textPreview.setText(text);
				show.setEnabled(true);
				delete.setEnabled(true);
				back.setEnabled(false);
			}	
				
		});
	
		//Label 2
		JLabel head2 = new JLabel("showed messages");
		contentPane.add(head2);
		
		//List 2 = showed Messages
		showedModel = new DefaultListModel();
		showed = new JList(showedModel);
		showed.setSelectedIndex(0);
		showed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		showedSelection = new JScrollPane(showed,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		showedSelection.setPreferredSize(new Dimension(280,80));
		contentPane.add(showedSelection);
		showed.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(java.awt.event.MouseEvent e) {	
	        	reallyselected = true;
	        	int index = showed.locationToIndex(e.getPoint());
	            String selectedItem = (String) showedModel.getElementAt(index);
	            
	            incoming.clearSelection();
	            deleted.clearSelection();
				lastselected = 2;
				lastelement = showedModel.getElementAt(index);
	            
				String text = selectedItem;
				String[] splitID = text.split(" : ");
				handleID = Integer.parseInt(splitID[0]);	
				textPreview.setText(text);
				show.setEnabled(false);
				delete.setEnabled(true);
				back.setEnabled(false);
			}	
				
		});		
	
		
		//Label 3
		JLabel head3 = new JLabel("deleted messages");
		contentPane.add(head3);
		
		//List 3 = deleted Messages
		deletedModel = new DefaultListModel();
		deleted = new JList(deletedModel);
		deleted.setSelectedIndex(0);
		deleted.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		deletedSelection = new JScrollPane(deleted,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		deletedSelection.setPreferredSize(new Dimension(280,80));
		contentPane.add(deletedSelection);
		deleted.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(java.awt.event.MouseEvent e) {	
	        	reallyselected = true;
	        	int index = deleted.locationToIndex(e.getPoint());
	            String selectedItem = (String) deletedModel.getElementAt(index);
	            
	            incoming.clearSelection();
	            showed.clearSelection();
				lastselected = 3;
				lastelement = deletedModel.getElementAt(index);

				String text = selectedItem;
				String[] splitID = text.split(" : ");
				handleID = Integer.parseInt(splitID[0]);	
				textPreview.setText(text);
				show.setEnabled(true);
				delete.setEnabled(false);
				back.setEnabled(true);
			}	
		});	
		
		//Preview Textfield
		textPreview = new JTextArea("preview selected mesage here");
		textPreview.setSize(new Dimension(460,280));
		textPreview.setPreferredSize(new Dimension(460,280));
		textPreview.setLineWrap(true);
		textPreview.setWrapStyleWord(true);
		textPreview.setAlignmentY(TOP_ALIGNMENT);
		textPreview.setAlignmentX(LEFT_ALIGNMENT);
		textPreview.setAutoscrolls(true);
		textPreview.setEditable(false);
		contentPane.add(textPreview);
		
		alert = new JTextField("");
		alert.setSize(new Dimension(200,40));
		alert.setEditable(false);
		alert.setForeground(Color.RED);
		Font font1 = new Font("SansSerif", Font.BOLD, 20);
		alert.setFont(font1);
		
		//add Buttons
		show.setFocusable(false);
		delete.setFocusable(false);
		back.setFocusable(false);
		
		show.addActionListener(this); 
		contentPane.add(show);
		delete.addActionListener(this); 
		contentPane.add(delete);
		back.addActionListener(this); 
		contentPane.add(back);
		
		contentPane.add(alert);
		
		show.setEnabled(false);
		delete.setEnabled(false);
		back.setEnabled(false);
		
		this.setSize(500, 700);
		//this.guiframe.setSize(500, 700);
		this.guiframe.setContentPane(contentPane);  
		this.guiframe.pack(); 
		this.guiframe.setVisible(true);   

		//update gui
		createArrays();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	if( reallyselected == true) {
		    		alert.setText("");
		    		if (event.getSource() == show) { 
					        	Status newstat = scrollDraw.kommentare.get(handleID-1).setStatus(Status.SHOW);
					        	//System.out.println(handleID + " / " + newstat);
					        	scrollDraw.updateMessage();
					        	lastelement = "";
					        	lastselected = 0;
					        	textPreview.setText("");
					            //update gui
					            createArrays();        
		    		} 
		        
		    		else if(event.getSource() == delete){
		        				Status newstat = scrollDraw.kommentare.get(handleID-1).setStatus(Status.DELETE);
		        				//System.out.println(handleID + " / " + newstat);
					        	lastelement = "";
					        	lastselected = 0;
					        	textPreview.setText("");
					            //update gui
					            createArrays();
		    		}
		        
		    		else if(event.getSource() == back){    
		        				Status newstat = scrollDraw.kommentare.get(handleID-1).setStatus(Status.LOAD);
		        				//System.out.println(handleID + " / " + newstat);
					        	lastelement = "";
					        	lastselected = 0;
					        	textPreview.setText("");
					            //update gui
					            createArrays();
		    		}
		    	  reallyselected = false;
		    	}
		    	else if(reallyselected == false){
		    		alert.setText("do again!");
		    		guiframe.revalidate();
		    		
		    	}
		    }	   
		});
	}
    
	
	public void createArrays() {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
				newModel.clear();
				showedModel.clear();
				deletedModel.clear();
				//sort elements
				for(kommentar komm : scrollDraw.kommentare) {
					String listentry = komm.getId() + " : " + komm.getContent();
					if(komm.getStatus() == Status.LOAD) {
						newModel.add(0,listentry);
					}
					else if(komm.getStatus() == Status.SHOW || komm.getStatus() == Status.SHOWED) {
						showedModel.add(0,listentry);
					}			
					else if(komm.getStatus() == Status.DELETE) {
						deletedModel.add(0,listentry);
					}								
				}				
				showed.setModel(showedModel);
				incoming.setModel(newModel);
				deleted.setModel(deletedModel);
				
				if(lastelement != "") {
					if(lastselected == 1) {
						int idx = newModel.indexOf(lastelement);
						incoming.setSelectedIndex(idx);
					}
					else if(lastselected == 2) {
						int idx = showedModel.indexOf(lastelement);
						showed.setSelectedIndex(idx);
					}
					else if(lastselected == 3) {
						int idx = deletedModel.indexOf(lastelement);
						deleted.setSelectedIndex(idx);
					}
				}
				
		    }
		});	
	}
	
}




