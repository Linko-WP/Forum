package com.google.gwt.forum.client;

import java.util.ArrayList;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Forum implements EntryPoint {
 
	private Button insert_text_button = new Button("Insert Text");

	private AbsolutePanel mainPanel = new AbsolutePanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private HorizontalPanel insertPanel = new HorizontalPanel();
	private HorizontalPanel toolbarPanel = new HorizontalPanel();
	private HorizontalPanel loginPanel = new HorizontalPanel();
	private VerticalPanel vp = new VerticalPanel();
	HorizontalPanel userlist_panel = new HorizontalPanel();
	  
	private FlexTable forumFlexTable = new FlexTable();
	private TextBox username_textbox = new TextBox();
	private TextBox password_textbox = new TextBox();
	private TextBox email_textbox = new TextBox();
	private Button login_button = new Button("Login");
	private Button new_user = new Button("New User");
	private Button insert_user = new Button("Insert");
	private Button cancel_user = new Button("Cancel");
	private Label lastUpdatedLabel = new Label();
	  
	private ArrayList<Topics> topics  = new ArrayList<Topics>();
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	private ArrayList<Message> messages = new ArrayList<Message>();
	private ArrayList<User> users = new ArrayList<User>();	// Only for admin user
	  
	//private static final int REFRESH_INTERVAL = 5000; // ms
	private Label errorMsgLabel = new Label();
	public int currentElementId = -1;
	public char currentElementType = 'X';
	private User current_user = null;
	private boolean user_inserted = false;  
	private MyServiceAsync dbService = null;
  
	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		  
	    setUncaughtExceptionHandler();	// Useful!
	        
	    dbService = (MyServiceAsync) GWT.create(MyService.class);
	    
		// Setting panels
		RootPanel.get().setWidth("1000px");
		mainPanel.setWidth("1000px");
		RootPanel.get().addStyleName("rootStyle");
		mainPanel.addStyleName("mainStyle");
		
		toolbarPanel.setWidth("1000px");
	
	 	// Create table for stock data.
		forumFlexTable.setText(0, 0, "Subject");
		forumFlexTable.setText(0, 1, "Messages No.");
		forumFlexTable.setText(0, 2, "Last Message");
		forumFlexTable.setText(0, 3, "Enter");
			 
		// Add styles to elements in the stock list table.
		forumFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
		forumFlexTable.addStyleName("watchList");
		forumFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
		forumFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
		forumFlexTable.getCellFormatter().addStyleName(0, 3, "watchListNumericColumn");
	
	    // Assemble Error panel.
	    errorMsgLabel.setStyleName("errorMessage");
	    errorMsgLabel.setVisible(false);
	    mainPanel.add(errorMsgLabel);
		    
	    createToolbar();
	
	    // Assemble Main panel.
	    mainPanel.add(forumFlexTable);
	    mainPanel.add(addPanel);
	    mainPanel.add(insertPanel);
	    mainPanel.add(lastUpdatedLabel);
		
	    // Add panels to the root panel
		RootPanel.get().add(toolbarPanel);
	    RootPanel.get().add(mainPanel);
		    
	    // Adding topics to the flextable
		load_topics();
	}

	/**
	 * Show the list of topics.
	 */
	private void showTopics() {
		
		clean_table();
		forumFlexTable.setText(0, 1, "Messages No.");
		
		currentElementType = 'P';
		for(Topics top:topics){
			addDataToSource(top.subject, "N\\A", null, top.id);
		}
		
	}

	/**
	 * Show the list of topics.
	 */
	private void showThreads() {
		
		clean_table();
		forumFlexTable.setText(0, 1, "Messages No.");
		
		currentElementType = 'T';
		for(Thread th:threads){
			if(th.parent_topic_id == currentElementId)
				addDataToSource(th.title, String.valueOf( th.no_messages ), null, th.id);
		}
		
	}
	
	/**
	 * Show the list of messages.
	 */
	private void showMessages() {
		
		clean_table();
		forumFlexTable.setText(0, 1, "Author");
		
		currentElementType = 'M';
		for(Message ms:messages){
			if(ms.parent_thread_id == currentElementId)
				addDataToSource( ms.content, ms.author, ms.time_stamp.toString(), ms.id);
		}
		
	}
	
	/**
	 * Show the list of usres.
	 */
	private void showUsers() {
		
		clean_table();
		
		currentElementType = 'U';
		for(User x:users){
			if(x != null)
				addDataToSource( x.user_name, x.email, x.is_admin.toString(), 0);
		}
		
	}
  
	/**
	 * Refresh the forum table 
	 * */
	private void refresh(){
		
		clean_table();
		if(currentElementType == 'M'){
			for(Message ms:messages){
				if(ms.parent_thread_id == currentElementId){
					addDataToSource( ms.content, ms.author, ms.time_stamp.toString(), ms.id);
				}
			}
		}else if(currentElementType == 'T'){
			for(Thread th:threads){
				if(th.parent_topic_id == currentElementId)
					addDataToSource(th.title, String.valueOf( th.no_messages ), null, th.id);
			}
		}else if(currentElementType == 'P'){
			for(Topics top:topics){
				addDataToSource(top.subject, "N\\A", null, top.id);
			}
		}else if(currentElementType == 'U'){
			for(User x:users){
				if(x != null)
					addDataToSource( x.user_name, x.email, x.is_admin.toString(), 0);
			}
		}else{
			System.out.println("Refresh is not possible");
		}
		
	}
  
	/**
	 * Insert data on the source table
	 * */
	private void addDataToSource(final String col1, final String col2, final String col3, final int id){
	  
		int row = forumFlexTable.getRowCount();
	    
		HorizontalPanel optionsPanel = new HorizontalPanel();
	  
		final Label column_1 = new Label(col1);
		forumFlexTable.setWidget(row, 0, column_1);
	  
		final Label column_2 = new Label(col2);
		forumFlexTable.setWidget(row, 1, column_2);
	  
		final Label column_3 = new Label(col3);
		forumFlexTable.setWidget(row, 2, column_3);
	  
		// Style
		forumFlexTable.getCellFormatter().addStyleName(row, 0, "watchListCell");
		forumFlexTable.getCellFormatter().addStyleName(row, 1, "watchListCell");
		forumFlexTable.getCellFormatter().addStyleName(row, 2, "watchListCell");
		forumFlexTable.getCellFormatter().addStyleName(row, 3, "watchListCell");

		// Add a click listener to save the information about the row
		// Column1 used to have a click listener, but we will avoid it for now

		// Add a button to enter in the element (topic or thread).
		Button enterButton = new Button(">");
		enterButton.addStyleDependentName("enter");
		enterButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				currentElementId = id;
				System.out.println("CLICK");
				if(currentElementType == 'P'){
					System.out.println("Loading threads");
					load_threads();
				}else if(currentElementType == 'T'){
					System.out.println("Loading messages");
					load_messages();
				}else{
					System.out.println("Not able to check current type");
				}
			}
		});
	  
	  
		// Add a button to enter in the element (topic or thread).
		Button removeButton = new Button("X");
		removeButton.addStyleDependentName("remove");
		removeButton.addClickHandler(new ClickHandler() {
		  
			int current_id = id;
		    char current_type = currentElementType;
		    
		    public void onClick(ClickEvent event) {
		    	if(current_type != 'U'){
		    		remove_row(current_id, current_type);
		    	}else{
		    		remove_user(col1);
		    	}
		    }
	    
		});  
	  
		// The option you see depends on who is logged in
		if(currentElementType != 'M' && currentElementType != 'U') optionsPanel.add(enterButton);
		if( current_user != null ){	// If someone is logged in
			if(current_user.is_admin) 
				optionsPanel.add(removeButton);
		}
		forumFlexTable.setWidget(row, 3, optionsPanel);
	  
	}
	
	/**
	 * Removes a row from the local array
	 * */
	public void remove_row(int id, char type){
		  
			
		  if(type == 'P'){
			  for(Topics x:topics){
				  System.out.print("X:ID: "+x.id);
				  System.out.print("ID: "+id);
				  if(x.id == id){
					  topics.remove(x);
					  refresh();
					  System.out.print("ID: "+x.id);
					  
					  dbService.erase_topic(id, new AsyncCallback<String>(){
					    	public void onSuccess(String results) {}
					        public void onFailure(Throwable caught) {
					        	Window.alert("THREADS retrive attempt failed.");
					      		System.out.println("Fail\n" + caught);
					        }});		
					  return;
				  }
			  	} 
				  
			  
		  }else if(type == 'T'){
			  for(Thread x:threads){
				  if(x.id == id) {
					  threads.remove(x);
					  refresh();
					  dbService.erase_thread(id, new AsyncCallback<String>(){
					    	public void onSuccess(String results) {}
					        public void onFailure(Throwable caught) {
					        	Window.alert("THREADS retrive attempt failed.");
					      		System.out.println("Fail\n" + caught);
					        }});		
					  return;
				  }
			  }
				  	
			  
		  }else if(type == 'M'){
			  for(Message x:messages){
				  if(x.id == id){
					  messages.remove(x);
					  refresh();
					  
					  dbService.erase_message(id, new AsyncCallback<String>(){
					    	public void onSuccess(String results) {refresh();}
					        public void onFailure(Throwable caught) {
					        	Window.alert("THREADS retrive attempt failed.");
					      		System.out.println("Fail\n" + caught);
					        }});		
					  
					  return;
				  } 
			  }
				  	
			  
		  }else{
			  System.out.println("Error: fail when trying to erase a row.");
		  }
		  
	  }
	
	  /**
	   * Removes an user from the list and the database
	   * */
	  public void remove_user(String username){

		  for(User x:users){
			  	
			    if(x.user_name.equals(username) && !x.is_admin){ 	// If its the required one and it's not admin
			    	users.remove(x);
			 
				  	dbService.erase_user(username, new AsyncCallback<String>(){
				    	public void onSuccess(String results) {
				    		refresh();
				    	}
				        public void onFailure(Throwable caught) {
				        	Window.alert("Users retrive attempt failed.");
				      		System.out.println("Fail\n" + caught);
				        }
				    });
				  	return;
			     }
		  }
	  }
	  
	  /**
	   * Class to load the topics int the topic object from the database
	   */
	  private void load_topics(){
		  	
		dbService.get_topics(" cadena ", new AsyncCallback<ArrayList<Topics>>(){
			public void onSuccess(ArrayList<Topics> result) {
		    		
		    	topics = result;
		 
		    	showTopics();
		    }
		
		    public void onFailure(Throwable caught) {
		    	Window.alert("Load of TOPICS failed.");
		  		System.out.println("Fail\n" + caught);
		    }
		});	  
	  }
  
	  /**
	   * Class to load the threads in the topic object from the database
	   */
	  private void load_threads(){

		  if( (currentElementId != -1) ){		    	

		    dbService.get_threads(currentElementId, new AsyncCallback<ArrayList<Thread>>(){
		    	public void onSuccess(ArrayList<Thread> results) {
		    		
		    		threads = results;
		    		
		    		showThreads();
		        }
		    	
		        public void onFailure(Throwable caught) {
		        	Window.alert("THREADS retrive attempt failed.");
		      		System.out.println("Fail\n" + caught);
		        }
		    });		    
		  }	    
	  }
	  
	  
	  /**
	   * Class to load the messages in the topic object from the database
	   */
	  private void load_messages(){
		    
		  if( (currentElementId != -1) ){		    	

		    dbService.get_messages(currentElementId, new AsyncCallback<ArrayList<Message>>(){
		    	public void onSuccess(ArrayList<Message> results) {
		    		
		    		messages = results;
		    		
		    		showMessages();
		        }
		        public void onFailure(Throwable caught) {
		        	Window.alert("Messages retrieve attempt failed.");
		      		System.out.println("Fail\n" + caught);
		        }
		    });		    
		  }	    
	  }  
	  
	  
	  /**
	   * Class to load the users in the topic object from the database
	   */
	  private void load_users(){   	

	    dbService.get_users(" cadena ", new AsyncCallback<ArrayList<User>>(){
	    	public void onSuccess(ArrayList<User> results) {
	    		
	    		users = results;
	    		
	    		showUsers();
	        }
	        public void onFailure(Throwable caught) {
	        	Window.alert("Messages retrieve attempt failed.");
	      		System.out.println("Fail\n" + caught);
	        }
	    });		        
	  }
	  
	  
	  /**
	   * Logs out. Just set the current user to null
	   * */
	  public void logout(){
		  current_user = null;
		  username_textbox.setText("");
		  password_textbox.setText("");
		  
		  userlist_panel.clear();	// Remove users button
		  loginPanel.setStyleName("loginPanel");
		  loginPanel.clear();
		  loginPanel.removeFromParent();
		  login_zone();
		  vp.clear();
		  vp.removeFromParent();
		  
		  //TODO: bug. cuando hace loggout vuelve a hacer login inmediatamente :S
	  }
	  
	  /**
	   * Attemps to login in the system. Gets the current user only
	   * if the username and the password matches with a row of the
	   * users table in the database
	   * */
	  public void login(){
		  
		  String username = username_textbox.getText();
		  String password = password_textbox.getText();
		  username_textbox.setText("");
		  password_textbox.setText("");
		  if(username != "" && password != ""){  
			  dbService.check_user(username, password, new AsyncCallback<User>(){
				  
				  public void onSuccess(User result) {
				
					  current_user = result;
					  if(current_user != null){
						  createToolbar();
						  refresh();
					  }else{
						  Window.alert("User not valid.");
					  }
		          }
		    	
		          public void onFailure(Throwable caught) {
		        	Window.alert("Login attempt failed.");
		      		System.out.println("Fail\n" + caught);
		          }
		    } );
		  }else{
			  Window.alert("Username or password not valid.");
		  }
		 	  
	  }
	  
	  /**
	   * Creates a toolbar for the forum
	   * */
	  public void createToolbar(){
		  
		  loginPanel.clear();
		  toolbarPanel.clear();	// Reset the toolbar panel
		  
		  back_button();
		  refresh_button();
		  
		  if(current_user == null){
			  login_zone();
		  }else{
			  
			  if(current_user.is_admin){
				  // Ver usuarios (para poder gestionarlos)
				  System.out.println("Is admin");
				  user_list_button();
			  }
			  // Add here another logged functionalities
			  logged_message();
			  
			  // This is not part of the toolbar, but it should be shown
			  // only when the user is logged
			  new_message_panel();
		  }
		  
	  }
	  
	  /**
	   * Creates the login boxes and the login button
	   * */
	  public void login_zone(){
		  
		  Label username_label = new Label("Username:");
		  Label password_label = new Label("Password:");
		  username_label.addStyleName("loginText");
		  password_label.addStyleName("loginText");
		  
		  // Assemble Login panel.
		  username_textbox.addStyleName("username_textbox");
		  password_textbox.addStyleName("password_texrbox");
		  login_button.addStyleName("button");
		  login_button.setText("Login");
		  login_button.setWidth("70px");
		  new_user.addStyleName("button");
		  new_user.setWidth("80px");
			
		  loginPanel.add(username_label);
		  loginPanel.add(username_textbox);
		  loginPanel.add(password_label);
		  loginPanel.add(password_textbox);
		  loginPanel.add(login_button);
		  loginPanel.add(new_user);
		  loginPanel.setStyleName("loginPanel");
		  toolbarPanel.add(loginPanel);
		  
		  // Listen for mouse events on the Login button.
		  login_button.addClickHandler(new ClickHandler() {
			  public void onClick(ClickEvent event) {
				   login();
			  }
		  });
		    
		  // Listen for keyboard events in the input box.
		  password_textbox.addKeyPressHandler(new KeyPressHandler() {
		      public void onKeyPress(KeyPressEvent event) {
		    	  if (event.getCharCode() == KeyCodes.KEY_ENTER) {
		    		  login();
		    	  }
		      }
		  });
		  
		  // Listen for mouse events on the Login button.
		  new_user.addClickHandler(new ClickHandler() {
			  public void onClick(ClickEvent event) {
				   new_user_zone();
			  }
		  });
	  }
	  
	  /**
	   * Shows de username and the logout button in the toolbar panel
	   * */
	  public void logged_message(){
		  
		  Label username_label = new Label("Welcome " + current_user.user_name + "   ");
		  username_label.addStyleName("loginText");
		  
		  // Assemble Login panel.
		  
		  login_button.addStyleName("button");
		  login_button.setWidth("70px");
		  login_button.setText("Logout");
			
		  loginPanel.add(username_label);
		  loginPanel.add(login_button);
		  loginPanel.setStyleName("loggedPanel");
		  toolbarPanel.add(loginPanel);
		  
		  // Listen for mouse events on the Login button.
		  login_button.addClickHandler(new ClickHandler() {
			  public void onClick(ClickEvent event) {
				  logout();
			  }
		  });
	  }
	  
	  /**
	   * Creates the back button and its functionality
	   * */
	  public void back_button(){
		  
		  Button backButton = new Button("<");
		  backButton.addStyleDependentName("remove");
		  backButton.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	
		    	  if(currentElementType == 'P'){
		    		  // Do nothing. Topics is the upper node of the hierarchy
		    	  }else if(currentElementType == 'T' || currentElementType == 'U'){
		    		  showTopics();	// Show but NOT update. Be careful with that.
		    	  }else if(currentElementType == 'M'){
		    		  
		    		  System.out.println("Current id = "+currentElementId);
		    		  // Look for the id of the parent topic
		    		  int index = 0;
		    		  for(index=0; index < threads.size(); index++){
		    			  if(threads.get(index).id == currentElementId){
		    				  currentElementId = threads.get(index).parent_topic_id;
		    				  index = messages.size();
		    			  }
		    		  }
		    		  System.out.println("Modified id = "+currentElementId);
		    		  
		    		  // Show the threads of the parent topic
		    		  showThreads();
		    		  
		    	  }else{
		    		  System.out.println("Not able to check current type");
		    	  }
		    }
		  });
		  toolbarPanel.add(backButton);
	  }
	  
	  /**
	   * Creates the refresh button and its functionality
	   * */
	  public void refresh_button(){
		  
		  Button refreshButton = new Button("Refresh");
		  refreshButton.addStyleDependentName("remove");
		  refreshButton.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	refresh();
		    }
		  });
		  
		  toolbarPanel.add(refreshButton);
	  }
	  
	  /**
	   * Creates the "see users" button and its functionality
	   * */
	  public void user_list_button(){
		  
		  Button userListButton = new Button("Users");
		  userListButton.addStyleDependentName("userList");
		  userListButton.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	load_users();
		    }
		  });
		  userlist_panel.add(userListButton);
		  toolbarPanel.add(userlist_panel);
	  }
	  
	  /**
	   * Creates and shows the new_message_panel
	   * */
	  public void new_message_panel(){
		  
		  final RichTextArea textArea = new RichTextArea();
		  final RichTextToolbar toolBar = new RichTextToolbar(textArea);
	      textArea.setWidth("100%");  
	      vp.add(toolBar);
	      vp.add(textArea);
	      vp.add(insert_text_button);
	      vp.addStyleName("messagesPanel");
		  
	      //Text Editor Panel
	  	  RootPanel.get().add(vp);

		  // Listen for mouse events on the Login button.
		  insert_text_button.addClickHandler(new ClickHandler() {
			  public void onClick(ClickEvent event) { 
				  if( currentElementType == 'M'){
					  Message m = new Message(textArea.getText(), currentElementId,current_user.user_name);
					  
					  dbService.insert_message(m, new AsyncCallback<Message>(){		// Add message to the bd
						  
						  public void onSuccess(Message result) {				  
							  messages.add(result);
							  textArea.setText("");
							  refresh();
				          }
				    	
				          public void onFailure(Throwable caught) {
				        	Window.alert("New message attempt failed.");
				      		System.out.println("Fail\n" + caught);
				          }
				    } );
					  
				  }else if( currentElementType == 'T'){
					  final Thread m = new Thread(textArea.getText(), currentElementId);
					  
					  dbService.insert_thread(m, new AsyncCallback<Thread>(){		// Add message to the bd
						  
						  public void onSuccess(Thread result) {
							  threads.add(result);
							  textArea.setText("");
							  refresh();
				          }
				    	
				          public void onFailure(Throwable caught) {
				        	Window.alert("New thread attempt failed.");
				      		System.out.println("Fail\n" + caught);
				          }
				    } );
				  }else if( currentElementType == 'P'){
					  final Topics m = new Topics(textArea.getText());
					  
					  dbService.insert_topic(m, new AsyncCallback<Topics>(){		// Add message to the bd
						  
						  public void onSuccess(Topics result) {	  
							  topics.add(result);
							  textArea.setText("");
							  refresh();
				          }
				    	
				          public void onFailure(Throwable caught) {
				        	Window.alert("New thread attempt failed.");
				      		System.out.println("Fail\n" + caught);
				          }
				    } );
				  }
			  }
		  });

	  }
	  
	  /**
	   * Cleans the forumFlexTable exept for the heading
	   * */
	  public void clean_table(){
		  for(int i=forumFlexTable.getRowCount()-1; i > 0 ; --i){
			  forumFlexTable.removeRow(i);
		  }
	  }
	  
	  /**
	   * Set an uncaught exception handler
	   * This code is here to make it cleaner
	   * */
	  public void setUncaughtExceptionHandler(){
		  
		// Set uncaught exception handler
	    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
	      public void onUncaughtException(Throwable throwable) {
	        String text = "Uncaught exception: ";
	        while (throwable != null) {
	          StackTraceElement[] stackTraceElements = throwable.getStackTrace();
	          text += throwable.toString() + "\n";
	          for (int i = 0; i < stackTraceElements.length; i++) {
	            text += "    at " + stackTraceElements[i] + "\n";
	          }
	          throwable = throwable.getCause();
	          if (throwable != null) {
	            text += "Caused by: ";
	          }
	        }
	        DialogBox dialogBox = new DialogBox(true, false);
	        DOM.setStyleAttribute(dialogBox.getElement(), "backgroundColor", "#ABCDEF");
	        System.err.print(text);
	        text = text.replaceAll(" ", "&nbsp;");
	        dialogBox.setHTML("<pre>" + text + "</pre>");
	        dialogBox.center();
	      }
	    });
	    
	  }
	  
	  void new_user_zone(){
		  loginPanel.clear();
		  loginPanel.removeFromParent();
		  
		  Label username_label = new Label("Username:");
		  Label password_label = new Label("Password:");
		  Label email_label = new Label("Email:");
		  username_label.addStyleName("loginText");
		  password_label.addStyleName("loginText");
		  email_label.addStyleName("loginText");
		  
		  // Assemble Login panel.
		  username_textbox.addStyleName("username_textbox");
		  password_textbox.addStyleName("password_textbox");
		  email_textbox.addStyleName("email_textbox");
		  cancel_user.addStyleName("button");
		  cancel_user.setWidth("80px");
		  insert_user.addStyleName("button");
		  insert_user.setWidth("80px");
			
		  loginPanel.add(username_label);
		  loginPanel.add(username_textbox);
		  loginPanel.add(password_label);
		  loginPanel.add(password_textbox);
		  loginPanel.add(email_label);
		  loginPanel.add(email_textbox);
		  loginPanel.add(insert_user);
		  loginPanel.add(cancel_user);
		  loginPanel.addStyleName("new_user_Panel");
		  toolbarPanel.add(loginPanel);

		// Listen for mouse events on the Login button.
		  insert_user.addClickHandler(new ClickHandler() {
			  public void onClick(ClickEvent event) { 		  
				  
				  if(username_textbox.getText().isEmpty() || password_textbox.getText().isEmpty()){
					  	Window.alert("Please fill in username and password.");
				  }else{
					  
					  for(User us:users){
						  System.out.print("\nUSERS: " + us.user_name);
						  System.out.print("\nTEXTBOX: " + username_textbox.getText());
						  if(us.user_name.equals(username_textbox.getText())){
							  user_inserted = true;
							  Window.alert("User already in the database");
							  username_textbox.setText("");
							  new_user_zone();
					      	  System.out.println("Fail\n");
						  }else{
							  user_inserted = false;
						  }
					  }
					  if(user_inserted==false){
						  User user = new User(username_textbox.getText(), email_textbox.getText(), password_textbox.getText());
						  dbService.insert_user(user, new AsyncCallback<User>(){				  
							  public void onSuccess(User result) {				  
								  users.add(result);
								  email_textbox.setText("");
								  logout();  
					          }	
					          public void onFailure(Throwable caught) {
					        	Window.alert("New message attempt failed.");
					          }
						} );	 
					    
					  }
					 		
			  }	  
			 }
		  });
				  
			// Listen for mouse events on the Login button.
		  cancel_user.addClickHandler(new ClickHandler() {
			  public void onClick(ClickEvent event) {
				  logout();
				
			  }
		  });  
	 }


}


