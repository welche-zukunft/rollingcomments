package scrollingtext;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;


public class userInterface extends JFrame implements ActionListener {
	
	int currentList;
	int currentItem;
	
	JList incoming;
	JList showed;
	JList deleted;
	
	JTextField textPreview;
	Container contentPane;
	JButton show = new JButton("Show");
    JButton delete = new JButton("Delete");
    JButton back = new JButton("Back");
    
    JScrollPane showedSelection;
    JScrollPane incomingSelection;
    JScrollPane deletedSelection;
    
    DefaultListModel newModel, showedModel, deletedModel;
    
    int handleID;
    
	//static String[] listItems = {"message1","message2","message3","message4","message5","message6"};
	//static List<messageItem> allItems = new ArrayList<messageItem>();
	
	static String[] incomingItems;
	static String[] showingItems;
	static String[] deletedItems;
	
	public static void main(String args[]) {
		new userInterface();
	}
	
	userInterface() {
		
		super("user interface");

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
				int index = incoming.locationToIndex(e.getPoint());
	            String selectedItem = (String) newModel.getElementAt(index);
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
	        	int index = showed.locationToIndex(e.getPoint());
	            String selectedItem = (String) showedModel.getElementAt(index);
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
	        	int index = deleted.locationToIndex(e.getPoint());
	            String selectedItem = (String) deletedModel.getElementAt(index);
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
		textPreview = new JTextField("preview selected mesage here");
		textPreview.setSize(new Dimension(460,80));
		textPreview.setPreferredSize(new Dimension(460,80));
		textPreview.setEditable(false);
		contentPane.add(textPreview);
		
		//add Buttons
		show.addActionListener(this); 
		contentPane.add(show);
		delete.addActionListener(this); 
		contentPane.add(delete);
		back.addActionListener(this); 
		contentPane.add(back);
		
		show.setEnabled(false);
		delete.setEnabled(false);
		back.setEnabled(false);
		
		this.setSize(500, 500);
		setVisible(true);
		
		//update gui
		createArrays();
	}
	
	
	public void actionPerformed(ActionEvent event) {
        if (event.getSource() == show) {
        	scrollDraw.kommentare.get(handleID-1).setStatus(Status.SHOW);
        	scrollDraw.updateMessage();
        } 
        
        else if(event.getSource() == delete){
        	scrollDraw.kommentare.get(handleID-1).setStatus(Status.DELETE);
        }
        
        else if(event.getSource() == back){
        	scrollDraw.kommentare.get(handleID-1).setStatus(Status.NEW);	
        }
        //update gui
        createArrays();
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
					if(komm.getStatus() == Status.NEW) {
						newModel.addElement(listentry);
					}
					else if(komm.getStatus() == Status.SHOW) {
						showedModel.addElement(listentry);
					}			
					else if(komm.getStatus() == Status.DELETE) {
						deletedModel.addElement(listentry);
					}								
				}
				
				showed.setModel(showedModel);
				incoming.setModel(newModel);
				deleted.setModel(deletedModel);
		    }
		});	
	}
	
}




