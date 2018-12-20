package cn.edu.bit.cs;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import cn.edu.bit.cs.model.Gift;
import cn.edu.bit.cs.model.Income;
import cn.edu.bit.cs.model.ListWrapper;
import cn.edu.bit.cs.model.Payment; 
import cn.edu.bit.cs.view.AnalysController;
import cn.edu.bit.cs.view.AppOverviewController;
import cn.edu.bit.cs.view.EditIncomeController;
import cn.edu.bit.cs.view.EditPaymentController;
import cn.edu.bit.cs.view.IncomeOverviewController;
import cn.edu.bit.cs.view.PaymentOverviewController;
import cn.edu.bit.cs.view.RootLayoutController;
import cn.edu.bit.cs.view.SaveForController;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
  
/**
 * @author CXY
 *�����������
 */
public class MainApp extends Application {
 
	private Stage primaryStage;
	private BorderPane rootLayout; 
	
	//������
    static Node reflection() {
        Text t = new Text();
        t.setX(1.0f);
        t.setY(40.0f);
        t.setCache(true);
        t.setText("Welcome!");
        t.setFill(Color.BLACK.darker());
        t.setFont(Font.font("null", FontWeight.SEMI_BOLD, 35)); 
        Reflection r = new Reflection();
        r.setFraction(0.9); 
        t.setEffect(r); 
        t.setTranslateY(40);
        t.setTranslateX(40);
        return t;
    } 
	
	 
	/*  
	 *start Stage  
	 */ 
	@Override
	public void start(Stage primaryStage) throws InterruptedException {  
		  
		this.primaryStage=primaryStage;
		this.primaryStage.setTitle("Application");
		primaryStage.setResizable(false);
		this.primaryStage.getIcons().add(new Image("file:resources/icon/MainAppicon.png"));
		
		showWelcome();
		
		initRootLayout();
			
		showAppOverView();
 	
	}
 

	/**
	 * show the welcome 
	 */
	private void showWelcome() {
	
//		���ɻ�ӭ����c
	    
		Stage dialogStage = new Stage(StageStyle.UNDECORATED); 
        dialogStage.initOwner(primaryStage);  
        
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		dialogStage.setX(screenBounds.getWidth()/2-125 );
		dialogStage.setY(screenBounds.getHeight()/2-75); 
		Scene scene;  
		Group group=new Group();
		scene = new Scene(group,250,150  );
		ObservableList<Node> content=FXCollections.observableArrayList()  ;//= ((Group)scene.getRoot()).getChildren();
	    content.add(reflection()); 
	    group.getChildren().addAll(content); 
	    scene.setFill(Color.rgb(123,145,150));  
        dialogStage.show();
 
        dialogStage.setScene(scene);    
        try { 
			Thread.sleep(500);
			 
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
        dialogStage.close(); 
	    
	}
	

	/**
	 * show the show AppOverview 
	 */
	private void showAppOverView() {
		try {
			//Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AppOverview.fxml"));
			AnchorPane AppOverview=(AnchorPane)loader.load();
		
		//show the scene contaioning the root layout.
			rootLayout.setCenter(AppOverview); 
			
			// Give the controller access to the main app.
	        AppOverviewController controller = loader.getController();
	        controller.setMainApp(this);

		}catch(IOException e){
			e.printStackTrace();
		}
	} 
	
	/**
	 * Initializes the root layout and tries to load the last opened
	 * person file.
	 */
	private void initRootLayout() {
		try {
			//Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout=(BorderPane)loader.load();
		
			//show the scene contaioning the root layout.
			Scene scene=new Scene(rootLayout);
			primaryStage.setScene(scene);
			
			// Give the controller access to the main app.
	        RootLayoutController controller = loader.getController();
	        controller.setMainApp(this);
			primaryStage.show();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		  // Try to load last opened person file.
		//ÿ�μ�����һ�δ򿪵��ļ�
 	    File file = getPaymentFilePath();
 	    if (file != null) {
 	    	System.out.println(file.getPath().toString());
 	        loadPaymentDataFromFile(file);
 	    }
	}
 
	public Stage getPrimaryStage() {
	      return primaryStage;
	}
	public static void main(String[] args) {  
		launch(args);
	}
	//���ϲ���Ϊ�����������ͼ
	
    // ... AFTER THE OTHER VARIABLES ...

    /**
     * The data as an observable list of Incomes.
     * The data as an observable list of Payments.
     * The data as an observable list of Gifts.
     */
	//���ڴ������ݵĲ���
    private ObservableList<Income> incomeData = FXCollections.observableArrayList();
    private ObservableList<Payment> paymentData = FXCollections.observableArrayList();
    private ObservableList<Gift> giftData = FXCollections.observableArrayList();  
    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        paymentData.add(new Payment("����",100.0,LocalDate.of(2018, 2, 21),"")  ); 
        paymentData.add(new Payment("�·�",120.0,LocalDate.of(2018, 4, 21),"") ); 
        incomeData.add(new Income("����",110.0,LocalDate.of(2018, 5, 20),"") ); 
        incomeData.add(new Income("ת��",150.0,LocalDate.of(2018, 4, 21),"" )); 
        giftData.add(new Gift("something",1100.0,LocalDate.of(2019, 12, 21),0 ));
    }
  
    /**
     * Returns the data as an observable list of Payments and Incomes. 
     * @return
     */
    public ObservableList<Income> getIncomeData() {
        return incomeData;
    }

	public ObservableList<Payment> getPaymentData() { 
		return paymentData;
	}

	public ObservableList<Gift> getGiftData(){
		return giftData;
	}
	
	
	//���ϲ���Ϊ���������֧���Ŀ��ӻ����б�
    // ... THE REST OF THE CLASS ...
 
	
	/**
	 * Opens a dialog to edit details for the specified Item. If the user
	 * clicks OK, the changes are saved into the provided item object and true
	 * is returned.
	 * 
	 * @param item the item object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean PaymentWindows(Payment payment) {
	    try {
	        // Load the fxml file and create a new stage for the pop up dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/PaymentOverview.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("������µ�֧����");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        dialogStage.setResizable(false);
	        dialogStage.getIcons().add(new Image("file:resources/icon/MainAppicon.png"));
			
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the payment into the controller.
	       PaymentOverviewController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setPayment(payment);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.issubmitClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean IncomeWindows(Income income) {
	    try {
	        // Load the fxml file and create a new stage for the pop up dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/IncomeOverview.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("������µ�������");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        dialogStage.setResizable(false);
	        dialogStage.getIcons().add(new Image("file:resources/icon/MainAppicon.png"));
			
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the income into the controller.
	       IncomeOverviewController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setIncome(income);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.issubmitClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	 
	//���ϲ���Ϊ����֧���������ͼ
	 
	/**
	 * ��һ����ʾÿ��������֧��������ͳ��ͼ
	 */
	
	 public void AnalysWindows() {
		 try {
		        // Load the fxml file and create a new stage for the pop up.
		        FXMLLoader loader = new FXMLLoader(); 
		        loader.setLocation(MainApp.class.getResource("view/AnalysOverview.fxml"));
		        AnchorPane page = (AnchorPane) loader.load();
		        Stage dialogStage = new Stage();
		        dialogStage.setTitle("�����ҵĲ���״��");
		        dialogStage.initModality(Modality.WINDOW_MODAL);
		        dialogStage.initOwner(primaryStage);

		        dialogStage.setResizable(false);
		        dialogStage.getIcons().add(new Image("file:resources/icon/MainAppicon.png"));
				
		        Scene scene = new Scene(page);
		        dialogStage.setScene(scene);
		        
		        //��������������б����ʽ����������ͼ
		        List<Payment> pay=  paymentData ;
		        List<Income> incom=incomeData;
		        AnalysController controller = loader.getController();
		        controller.setstorepayment(pay);
		        controller.setstoreincome(incom);
		        controller.setDialogStage(dialogStage);
		        
		        dialogStage.show();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	 }
	 
	//���ϲ���Ϊͳ�Ʋ���
	 public boolean SaveForWindows() {
		    try {
		    	
		        // Load the fxml file and create a new stage for the pop up dialog.
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(MainApp.class.getResource("view/SaveFor.fxml"));
		        AnchorPane page = (AnchorPane) loader.load();
		        
		        // Create the dialog Stage.
		        Stage dialogStage = new Stage();
		        dialogStage.setTitle("�ҵ���Ը��");
		        dialogStage.initModality(Modality.WINDOW_MODAL);
		        dialogStage.initOwner(primaryStage);

		        dialogStage.setResizable(false);
		        dialogStage.getIcons().add(new Image("file:resources/icon/MainAppicon.png"));
				
		        Scene scene = new Scene(page);
		        dialogStage.setScene(scene);
		        SaveForController controller = loader.getController();
		        controller.setDialogStage(dialogStage);
		        controller.setMainApp(this);
		        controller.setGift(giftData);
		        

		        // Show the dialog and wait until the user closes it
		        dialogStage.showAndWait();
		        return true;
		    } catch (IOException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
	 //����Ϊ��Ը������
	 public void EditIncomeWindow(Income income) {
		    try {
		    	
		        // Load the fxml file and create a new stage for the pop up dialog.
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(MainApp.class.getResource("view/EditIncome.fxml"));
		        AnchorPane page = (AnchorPane) loader.load();
		        
		        // Create the dialog Stage.
		        Stage dialogStage = new Stage();
		        dialogStage.setTitle("�༭����");
		        dialogStage.initModality(Modality.WINDOW_MODAL);
		        dialogStage.initOwner(primaryStage);

		        dialogStage.setResizable(false);
		        dialogStage.getIcons().add(new Image("file:resources/icon/MainAppicon.png"));
				
		        Scene scene = new Scene(page);
		        dialogStage.setScene(scene);
		        EditIncomeController controller = loader.getController();
		        controller.setDialogStage(dialogStage);
		        controller.setIncome(income);
		        

		        // Show the dialog and wait until the user closes it
		        dialogStage.showAndWait();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	 public void EditPaymentWindow(Payment payment) {
		    try {
		    	
		        // Load the fxml file and create a new stage for the pop up dialog.
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(MainApp.class.getResource("view/EditPayment.fxml"));
		        AnchorPane page = (AnchorPane) loader.load();
		        
		        // Create the dialog Stage.
		        Stage dialogStage = new Stage();
		        dialogStage.setTitle("�༭֧��");
		        dialogStage.initModality(Modality.WINDOW_MODAL);
		        dialogStage.initOwner(primaryStage);

		        dialogStage.setResizable(false);
		        dialogStage.getIcons().add(new Image("file:resources/icon/MainAppicon.png"));
				
		        Scene scene = new Scene(page);
		        dialogStage.setScene(scene);
		        EditPaymentController controller = loader.getController();
		        controller.setDialogStage(dialogStage);
		        controller.setPayment(payment);
		        

		        // Show the dialog and wait until the user closes it
		        dialogStage.showAndWait();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

	  
	 
	/**
	 * Returns the person file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	//������һ�δ��ļ���·�� ���û�еĻ���C���Զ�����
	public File getPaymentFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    String filePath = prefs.get("filePath", null); 
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	         return null;
	    }
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	//ȷ���ļ���Ӧ��·��
	public void setPaymentFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) { 
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("�ҵļ�������- " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("�ҵļ�������");
	    }
	}
	 
	/**
	 * Loads   data from the specified file. The current person data will
	 * be replaced.
	 * 
	 * @param file
	 */
	//װ�ش��������ݱ��е�֧�����ݲ���ʾ��ͼ����
	public void loadPaymentDataFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance( ListWrapper.class ); 
 	        Unmarshaller um = context.createUnmarshaller();
 	       
 	        // Reading XML from the file and unmarshalling.
  	         ListWrapper wrapper = ( ListWrapper) um.unmarshal(file); 

 	        paymentData.clear();
            paymentData.addAll(wrapper.getPayments());
            
            incomeData.clear();
            incomeData.addAll(wrapper.getIncomes());
         
            giftData.clear();
            giftData.addAll(wrapper.getGifts()); 
            
            // Save the file path to the registry.
 	        setPaymentFilePath(file);

	    } catch (Exception e) { // catches ANY exception 
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("������");
	    	alert.setHeaderText(null);
	    	alert.setContentText(e.getMessage()+"\n�����ļ�ʧ��\n"+file); 
	    	alert.showAndWait();    
	        e.printStackTrace();
	    }
	}

	/**
	 * Saves the current person data to the specified file.
	 * ���浱ǰ���ļ�����
	 * @param file
	 */
	public void savePaymentDataToFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(ListWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	         
	        // Wrapping our   data.
	        ListWrapper wrapper = new ListWrapper();  
	        wrapper.setPayments(paymentData); 
	        wrapper.setIncomes(incomeData);
	        wrapper.setGifts(giftData); 
	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file); 

	        // Save the file path to the registry.
	        setPaymentFilePath(file); 
	    } catch (Exception e) { // catches ANY exception
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("������");
	    	alert.setHeaderText(null);
	    	alert.setContentText(e.getMessage()+"\n�����ļ�ʧ��");

	    	alert.showAndWait();    
	        e.printStackTrace();
	    }
	}
	 
	
	//���ݰ���ʾ���������֧��
    public DoubleBinding totalIncome = new DoubleBinding() {
    	 
        {
            super.bind(incomeData);
        }
        @Override
        protected double computeValue() {
            
        	double total=0;
        	for(int i=0;i<incomeData.size();++i)
        		total+=incomeData.get(i).getValue(); 
        	//������λС��
        	DecimalFormat df = new DecimalFormat("#.00");
        	return Double.valueOf(df.format(total));
        }
    };
    
    public DoubleBinding totalPayment = new DoubleBinding() {
   	 
        {
            super.bind(paymentData);
        }
        @Override
        protected double computeValue() {
        	double total=0;
        	for(int i=0;i<paymentData.size();++i)
        		total+=paymentData.get(i).getValue(); 
        	DecimalFormat df = new DecimalFormat("#.00");
        	return Double.valueOf(df.format(total)); 
        }
    };  
}
 

