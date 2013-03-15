package com.google.gwt.forum.client;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;


public class Forum implements EntryPoint {
 

  private AbsolutePanel mainPanel = new AbsolutePanel();
  private HorizontalPanel addPanel = new HorizontalPanel();
  private HorizontalPanel insertPanel = new HorizontalPanel();
  private HorizontalPanel toolbarPanel = new HorizontalPanel();
  
  private FlexTable forumFlexTable = new FlexTable();
  private TextBox newElementTextBox = new TextBox();
  private Button addProjectButton = new Button("Add");
  private Label lastUpdatedLabel = new Label();
 
  private TextArea insertCityTextA = new TextArea();
  private Button insertProjectButton = new Button("Insert");
  
  private ArrayList<Topics> topics  = new ArrayList<Topics>();
  private String results;
  
  private ArrayList<String> awards = new ArrayList<String>();
  //private static final int REFRESH_INTERVAL = 5000; // ms
  private Label errorMsgLabel = new Label();
  public int currentElementId = -1;
  public char currentElementType = 'X';
  private int topics_index = -1;
  
  /**
   * Entry point method.
   */
  public void onModuleLoad() {
	  
    setUncaughtExceptionHandler();	// Useful!
        
	// Setting panels
	RootPanel.get().setWidth("1000px");
	mainPanel.setWidth("1000px");
	RootPanel.get().addStyleName("rootStyle");
	mainPanel.addStyleName("mainStyle");

	 
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
		
    // Assemble Add Stock panel.
	newElementTextBox.addStyleName("textBox");
	addProjectButton.addStyleName("button");
	addProjectButton.setWidth("100px");
    addPanel.add(newElementTextBox);
    addPanel.add(addProjectButton);
    addPanel.addStyleName("addPanel");
    
    // Assemble Insert Stock panel.
    insertCityTextA.setVisibleLines(4);
    insertCityTextA.addStyleName("textBox");
    insertProjectButton.addStyleName("button");
    insertProjectButton.setWidth("100px");
    insertPanel.add(insertCityTextA);
    insertPanel.add(insertProjectButton);
	    
    insertPanel.addStyleName("addPanel");

    // Assemble Error panel.
    errorMsgLabel.setStyleName("errorMessage");
    errorMsgLabel.setVisible(false);
    mainPanel.add(errorMsgLabel);
	    
    createToolbar();
    
    // Assemble Main panel.
    mainPanel.add(toolbarPanel);
    mainPanel.add(forumFlexTable);
    mainPanel.add(addPanel);
    mainPanel.add(insertPanel);
    mainPanel.add(lastUpdatedLabel);
      
    // Add panels to the root panel
    RootPanel.get().add(mainPanel);

  // refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

    // Listen for mouse events on the Add button.
    addProjectButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        //addTopic();
    	  showTopics();
      }
    });
    
    // Listen for keyboard events in the input box.
    newElementTextBox.addKeyPressHandler(new KeyPressHandler() {
      public void onKeyPress(KeyPressEvent event) {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
          addTopic();
        }
      }
    });
    
    // Listen for mouse events on the Add button.
    insertProjectButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
       // insertCity();
      }
    });
    
    // Listen for keyboard events in the input box.
    insertCityTextA.addKeyPressHandler(new KeyPressHandler() {
      public void onKeyPress(KeyPressEvent event) {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
       //   insertCity();
        }
      }
    });
    
    // Adding topics to the flextable
	load_topics();
	
	pruebas_mary();
  }

  /**
   * Class to load the topics int the topic object from the database
   */
  private void load_topics(){

	    MyServiceAsync dbService = (MyServiceAsync) GWT.create(MyService.class);

	    String temp = " cadena ";
	    dbService.get_topics(temp, new AsyncCallback<String>(){
	    	public void onSuccess(String result) {
	    		System.out.println("TOPICS:" + result);
	    		results = result;
	    		
	    		ArrayList<String> myList = new ArrayList<String>(Arrays.asList(results.split(", ")));
	    		for(int i=0; i<myList.size()-1; i=i+2){
	    			int obt_id = Integer.parseInt(myList.get(i));
	    			String obt_topic = myList.get(i+1);
	    			
	    			
	    			topics.add(new Topics(obt_id, obt_topic));
	    		}

	    		showTopics();
	          }

	          public void onFailure(Throwable caught) {
	        	Window.alert("RPC to initialize_db() failed.");
	      		System.out.println("Fail\n" + caught);
	          }
	    } );	  
  }
  
  
  /**
   * Add a new topic to FlexTable adn db.
   */
  private void addTopic() {
	  
	  	// The constructor does the work of insert the new topic in the database
	  	final Topics n_top = new Topics( newElementTextBox.getText().toUpperCase() );
	 
	  	newElementTextBox.setFocus(true);
	    
	  	// TODO: Check there is no topic with the same subject
	  	/*
		if (!topics.contains(n_top)){
			Window.alert("The inserted city: '" + city + "' is not a valid city.");
		      newElementTextBox.selectAll();
		      return;
		}*/
		
		if (topics.contains(n_top)){
			Window.alert("The inserted topic: '" + n_top + "' is already in the system.");
		      newElementTextBox.selectAll();
		      return;
		}
		
		// Add the city data	TODO: Add the date of the last message
	    addDataToSource(n_top.subject, "N\\A" ,null, n_top.id);

	    // Get the stock price.
	    showTopics();
	    newElementTextBox.setText("");

  }
  
  /**
   * Insert data on the source table
   * */
  private void addDataToSource(final String col1, final String col2, final String col3, final int id){
	  
	  int row = forumFlexTable.getRowCount();
	    
	  HorizontalPanel col1ParentPanel = new HorizontalPanel();
	  final Label column_1 = new Label(col1);
	  col1ParentPanel.add(column_1);
	  forumFlexTable.setWidget(row, 0, col1ParentPanel);
	  
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
	  column_1.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
	    	  currentElementId = id;
	    	  if(currentElementType == 'P'){
	    		  load_threads();
	    	  }else if(currentElementType == 'T'){
	    		  //load_messages();
	    	  }else{
	    		  System.out.println("Not able to check current type");
	    	  }
	    	  
	      }
	    });

	  // Add a button to remove this stock from the table.
	  Button removeStockButton = new Button(">");
	  removeStockButton.addStyleDependentName("remove");
	  removeStockButton.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
	      //int removedIndex = 1;		// TODO: CAMBIAr ESto PARA QUE SIRVA PARA ALGO
	      //awards.remove(removedIndex);        
	      //forumFlexTable.removeRow(removedIndex + 1);
	    	currentElementId = id;
	    	  if(currentElementType == 'P'){
	    		  load_threads();
	    	  }else if(currentElementType == 'T'){
	    		  //load_messages();
	    	  }else{
	    		  System.out.println("Not able to check current type");
	    	  }
	    }
	  });
	  forumFlexTable.setWidget(row, 3, removeStockButton);
	  
  }

	/**
	 * Show the list of topics.
	 */
	private void showTopics() {
		
		clean_table();
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
		currentElementType = 'T';
		for(Thread th:topics.get(topics_index).threads){
			addDataToSource(th.title, String.valueOf( th.no_messages ), null, th.id);
		}
		
	}
	
	  /**
	   * Class to load the threads in the topic object from the database
	   */
	  private void load_threads(){

		  final ArrayList<Thread> result = new ArrayList<Thread>();	
		  MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);
		  
		  for(int i=0; i<topics.size(); i++){
			  if(topics.get(i).id == currentElementId) topics_index = i;
		  }
		    
		  if( (currentElementId != -1) && (topics_index != -1) ){		    	

		    Service.get_threads(currentElementId, new AsyncCallback<String>(){
		    	public void onSuccess(String results) {
		    		System.out.println("RESULTADO GET THREADS:" + results);
		    		
		    		ArrayList<String> myList = new ArrayList<String>(Arrays.asList(results.split(", ")));
		    		for(int i=0; i<myList.size()-2; i=i+3){
		    			
		    			Thread output = new Thread(Integer.parseInt(myList.get(0)), currentElementId, myList.get(1), Integer.parseInt(myList.get(2)));
		    			result.add(output);
		    		}
		    		
		    		// Save the threads in their parent topic arraylist
		    		topics.get(topics_index).threads = result;
		    		for(Thread x:topics.get(topics_index).threads)
		    			System.out.println("DI: "+x.id+" Subject: "+x.title+" N.Messages: "+x.no_messages+" Parent: "+x.parent_topic_id);
		    		showThreads();

		          }
		    	
		          public void onFailure(Throwable caught) {
		        	Window.alert("RPC to initialize_db() failed.");
		      		System.out.println("Fail\n" + caught);
		          }
		    } );		    
		  }	    
	  }
	  
	
	  /**
	   * Update the Price and Change fields all the rows in the stock table.
	   *
	   * @param prices Stock data for all rows.
	   */
	/*  private void updateTable(InvestData[] prices) {
	    for (int i = 0; i < prices.length; i++) {
	      updateTable(prices[i]);
	    }
	
	    // Display timestamp showing last refresh.
	    lastUpdatedLabel.setText("Last update : "
	        + DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(new Date()));
	    
	    // Clear any errors.
	    errorMsgLabel.setVisible(false);
	  }*/
	  
	  /**
	   * Update a single row in the stock table.
	   *
	   * @param price Stock data for a single row.
	   */
	/*
	 private void updateTable(InvestData ammount) {
	    // Make sure the stock is still in the stock table.
	    if (!awards.contains(ammount.getCity())) {
	      return;
	    }

	    int row = awards.indexOf(ammount.getCity()) + 1;

	    // Format the data in the Price and Change fields.
	    String priceText = NumberFormat.getFormat("#,##0.00").format(
	    		ammount.getAmmount());
	    NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
	    String changeText = changeFormat.format(ammount.getChange());

	    // Populate the Price and Change fields with new data.
	    forumFlexTable.setText(row, 1, priceText);
	    Label changeWidget = (Label)forumFlexTable.getWidget(row, 2);
	    changeWidget.setText(   changeText + "%");
	    
	    // Change the color of text in the Change field based on its value.
	    String changeStyleName = "noChange";
	    if (ammount.getChange() < -0.1f) {
	      changeStyleName = "negativeChange";
	    }
	    else if (ammount.getChange() > 0.1f) {
	      changeStyleName = "positiveChange";
	    }

	    changeWidget.setStyleName(changeStyleName);
	    
	  }
	 */
	/**
	 * Inserts a new City on the table that didn't exists until now
	 * */
	/*
	 private void insertCity( ) {

	  final String text = insertCityTextA.getText().toUpperCase().trim();
	  insertCityTextA.setFocus(true);
	  String[] result = text.split("\\s");
	  int size = result.length;
	  int j= 0;
	  int obt_amount=0;

	 
	 Checks for invalid input: 
	  * 	1 - less than 2 parameters
	  *     2 - City already in the system
	  *	    3 - Non-numeric character in the amount
	*//*
	  if (size <2){
	    	Window.alert("It must content: CITY AMMOUNT");
		      return;
	  }
	  if (cities.contains(result[j])){
		  
	    	Window.alert("The city: '" + result[j] + "' is already in the system.");
		      return;
	  }	  
	  String money = result[j+1];
	  
	  try {  
		  //int temp = Integer.parseInt(money);
	  
	  }catch(NumberFormatException nfe){  
		  Window.alert("The parameter amount must be numeric ");
	      return;  
	  }  

	  obt_amount = Integer.parseInt(money);
	  elements.add(new InvestData(result[j],obt_amount,0));
	  addTopic(result[j]);
	  cities.add(result[j]);
	  amounts.add(obt_amount);
	  insertCityTextA.setText("");
	  
	  
	    MyServiceAsync emailService = (MyServiceAsync) GWT.create(MyService.class);
	    emailService.insert_into_db("cities", "('"+result[j]+"', "+ obt_amount +")", new AsyncCallback<String>(){
	    	public void onSuccess(String result) {
	    		results = result;
	          }

	          public void onFailure(Throwable caught) {
	        	Window.alert("Inserting new city in the DataBase failed.");
	      		System.out.println("Fail\n" + caught);
	          }
	    } );
	    // Depuration prints
	    System.out.println(cities);
	    System.out.println(amounts);
	}*/
	  public void createToolbar(){
		  
		  Button backButton = new Button("<");
		  backButton.addStyleDependentName("remove");
		  backButton.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		      //int removedIndex = 1;		// TODO: CAMBIAr ESto PARA QUE SIRVA PARA ALGO
		      //awards.remove(removedIndex);        
		      //forumFlexTable.removeRow(removedIndex + 1);
		    	
		    	  if(currentElementType == 'P'){
		    		  // Do nothing
		    	  }else if(currentElementType == 'T'){
		    		  showTopics();	// Show not update
		    	  }else{
		    		  System.out.println("Not able to check current type");
		    	  }
		    }
		  });
		  toolbarPanel.add(backButton);
	  }
	  
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
	  
	public void pruebas_mary(){
<<<<<<< HEAD

=======
>>>>>>> Boton back añadido
		
				
	};

}

