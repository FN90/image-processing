import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;


public class MainWindow extends JFrame implements ActionListener,MouseMotionListener{ // la classe implémente l'interface ActionListener pour mettre tous les composantes de la fenetre à l'écoute
	/**
	 *  Classe qui définit la fenetre principale
	 */
	private static final long serialVersionUID = 1L; 
	// image de test:
	static BufferedImage originalImage=null;
	// panneau image
	private static ImagePanel imagePanel=new ImagePanel();
	
	// drapeau qui test si l'image a été chargée dans le panneau ou nn :
	private boolean imageLoaded=false; 
	
	// Matrices de pixel:
	int[][] matRedPixelsInt;
	int[][] matBluePixelsInt;
	int[][] matGreenPixelsInt;
	
	// Barre de menu:
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fileMenu = new JMenu(); // Menu file
	private final JMenuItem openMenu = new JMenuItem(); // l'item Open
	private final JMenuItem quitMenu = new JMenuItem(); // ...
	private final JMenu pixelMenu = new JMenu();
	private final JMenuItem pixelArrayMenu = new JMenuItem();
	private final JMenuItem pixelRedMenu = new JMenuItem();
	private final JMenuItem pixelBlueMenu = new JMenuItem();
	private final JMenuItem pixelGreenMenu = new JMenuItem();
	// Les panneaux principeaux :
	public JPanel topPanel;
	public JPanel centerPanel;
	public JPanel bottomPanel;
	public JPanel leftPanel;
	public JPanel rightPanel;
	public JPanel sizePanel;
	private JScrollPane scrollPane=null;
	// Les labels
	private JLabel widthLabel; 
	private JLabel heightLabel;
	private JLabel statusBarLabel;	
	private JLabel sizeLabel;
	private JLabel xLabel;
	private JLabel yLabel;
	private JLabel rLabel;
	private JLabel gLabel;
	private JLabel bLabel;
	
	// L'emplacement du fichier image:
	private String path=null;
	// Boitde de dialogue ouvrir fichier:
	private JFileChooser fileOpenImage;
	
	// fichier image ouvert:
	private File fileImage;
	
	/*
	 * Constructeur principal
	 */
	public MainWindow(){
		super(); // appel a la classe mère
		setTitle("Tache 1: Application 2, ImagePrcessing"); // titre de la fenetre
		setDefaultCloseOperation(EXIT_ON_CLOSE); // mode de fermeture par défaut
		setSize(900,600); // taille de la fenetre
		try {
			creerMenu(); // Appel la méthode de construction du menu 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		init(); // appel a la méthode d'initialisation de comp de la fenetre principale
				
		setVisible(true);	// afficher la fenetre principale	
	}
	
	

	private void init() {
		// Le panneau en haut:
		topPanel=new JPanel();
		
		// Le panneau au center:
		centerPanel=new JPanel();
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.gray)); // Ajout d'une bordure bleue au panneau
		centerPanel.setBackground(Color.gray); // coleur d'arriere plan du panneau : gris
		scrollPane = new JScrollPane(centerPanel);	// Ajout d'une barre de défilement au panneau du centre, qui va contenir l'image	
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // la barre de défilement horizentale est toujours visible
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // la barre de défilement verticale est toujours visible
		centerPanel.setLayout(new BorderLayout()); // configuration du plan de la JPanel
		imagePanel.addMouseMotionListener(this);
		centerPanel.add(imagePanel); // Ajout du panneau de l'image au centre du panneau central de la fenetre principale	
		
		
		// Le panneau droit:
		leftPanel=new JPanel(); // panneau principale
		leftPanel.setPreferredSize(new Dimension(200,500)); // Configuration de la taille du panneau
		//rightPanel.setBorder(BorderFactory.createLineBorder(Color.gray)); // Ajout d'une bordure bleue au panneau
		//rightPanel.setBackground(Color.LIGHT_GRAY); // coleur d'arriere plan du panneau : gris
		// panneau Informations
		JPanel infosPanel=new JPanel(); // panneau informations sur l'image
		infosPanel.setPreferredSize(new Dimension(190,210)); // Configuration de la taille du panneau
		infosPanel.setBorder(new LineBorder(null, 1, true)); // Ajout d'une bordure bleue au panneau
		// Titre "Tnformations":
		JLabel titleLabel=new JLabel("Informations");
		titleLabel.setFont(new Font("Courier", Font.BOLD,11));
		titleLabel.setPreferredSize(new Dimension(180,20)); // Configuration de la taille du label
		// panneau largeur(width) et hauteur(height):
		JPanel widthHeightPanel=new JPanel();
		widthHeightPanel.setPreferredSize(new Dimension(180,20));
		// Titre "Largeur", "Width":
		widthLabel=new JLabel("Width:");
		widthLabel.setFont(new Font("Courier", Font.PLAIN,10)); // Configuration du font du texte
		widthLabel.setPreferredSize(new Dimension(80,15)); // Configuration de la taille du label
		// Titre "Hauteur", "Height":
		heightLabel=new JLabel("Height:");
		heightLabel.setFont(new Font("Courier", Font.PLAIN,10)); // Configuration du font du texte
		heightLabel.setPreferredSize(new Dimension(80,15)); // Configuration de la taille du label
		widthHeightPanel.add(widthLabel); // Ajout du label de la largeur dans un panneau
		widthHeightPanel.add(heightLabel);
		// Panneau vide :
		JPanel panel=new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.gray)); // Ajout d'une bordure bleue au panneau
		panel.setPreferredSize(new Dimension(170,1)); // Configuration de la taille du panneau
		// panneau infos sur la taille du fichier "Doc":
		sizePanel=new JPanel();
		sizePanel.setPreferredSize(new Dimension(188,15));
		// Titre "size":
		sizeLabel=new JLabel("Doc:");
		sizeLabel.setPreferredSize(new Dimension(163,10)); // Configuration de la taille du panneau
		sizeLabel.setFont(new Font("Courier", Font.PLAIN,10)); // Configuration du font du texte
		sizePanel.add(sizeLabel);
		// Ajout d'une ligne horizentale
		// Panneau vide :
		JPanel panel1=new JPanel();
		panel1.setBorder(BorderFactory.createLineBorder(Color.gray)); // Ajout d'une bordure bleue au panneau
		panel1.setPreferredSize(new Dimension(170,1)); // Configuration de la taille du panneau
		// Titre "Position":
		JLabel positionLabel=new JLabel("Position");
		positionLabel.setPreferredSize(new Dimension(160,15)); // Configuration de la taille du panneau
		positionLabel.setFont(new Font("Courier", Font.PLAIN,10)); // Configuration du font du texte
		// Titre "X":
		xLabel=new JLabel("X:");
		xLabel.setPreferredSize(new Dimension(140,10)); // Configuration de la taille du panneau
		xLabel.setFont(new Font("Courier", Font.BOLD,10)); // Configuration du font du texte
		// Titre "Y":
		yLabel=new JLabel("Y:");
		yLabel.setPreferredSize(new Dimension(140,10)); // Configuration de la taille du panneau
		yLabel.setFont(new Font("Courier", Font.BOLD,10)); // Configuration du font du texte
		// Ajout d'une ligne horizentale
		// Panneau vide :
		JPanel panel2=new JPanel();
		panel2.setBorder(BorderFactory.createLineBorder(Color.gray)); // Ajout d'une bordure bleue au panneau
		panel2.setPreferredSize(new Dimension(170,1)); // Configuration de la taille du panneau
		// Titre "Pixel":
		JLabel pixelLabel=new JLabel("Pixel");
		pixelLabel.setPreferredSize(new Dimension(160,15)); // Configuration de la taille du panneau
		pixelLabel.setFont(new Font("Courier", Font.PLAIN,10)); // Configuration du font du texte
		// Titre "R":
		rLabel=new JLabel("R:");
		rLabel.setPreferredSize(new Dimension(140,10)); // Configuration de la taille du panneau
		rLabel.setFont(new Font("Courier", Font.BOLD,10)); // Configuration du font du texte
		// Titre "G":
		gLabel=new JLabel("G:");
		gLabel.setPreferredSize(new Dimension(140,10)); // Configuration de la taille du panneau
		gLabel.setFont(new Font("Courier", Font.BOLD,10)); // Configuration du font du texte
		// Titre "B":
		bLabel=new JLabel("B:");
		bLabel.setPreferredSize(new Dimension(140,10)); // Configuration de la taille du panneau
		bLabel.setFont(new Font("Courier", Font.BOLD,10)); // Configuration du font du texte
		
		
		// Ajout des label au panneau principal
		infosPanel.add(titleLabel);
		infosPanel.add(widthHeightPanel);
		infosPanel.add(panel);
		infosPanel.add(sizePanel);
		infosPanel.add(panel1);
		infosPanel.add(positionLabel);
		infosPanel.add(xLabel);
		infosPanel.add(yLabel);
		infosPanel.add(panel2);
		infosPanel.add(pixelLabel);
		infosPanel.add(rLabel);
		infosPanel.add(gLabel);
		infosPanel.add(bLabel);
		leftPanel.add(infosPanel);
		
		// Le panneau gauche:
		rightPanel=new JPanel();
		
		// Le panneau en bas:
		bottomPanel=new JPanel();
		bottomPanel.setPreferredSize(new Dimension(900,20));
		// Barre de status:
		statusBarLabel=new JLabel("Path: ");

		statusBarLabel.setFont(new Font("Courier", Font.BOLD,10)); // Configuration du font du texte
		bottomPanel.add(statusBarLabel,0,0);
		//bottomPanel.setBorder(BorderFactory.createLineBorder(Color.gray)); // Ajout d'une bordure bleue au panneau
		
		/* Implementation de l'écouteur pour detecter la position du souris: */
		
		
		// Creation de l'interface:
		/*
		 *  le BorderLayout. Il est très pratique si vous voulez placer vos composants de
			façon simple par rapport à une position cardinale de votre conteneur. Si je parle 
			de positionnement cardinal, c'est parce que vous devez utiliser les valeurs NORTH, 
			SOUTH, EAST, WEST ou encore CENTER.
		 */
		this.setLayout(new BorderLayout());
		this.add(topPanel,BorderLayout.NORTH);
		this.add(scrollPane,BorderLayout.CENTER);
		this.add(rightPanel,BorderLayout.EAST);
		this.add(leftPanel,BorderLayout.WEST);
		this.add(bottomPanel,BorderLayout.SOUTH);
	}
	
	/*
	 * Méthode qui construire le menu de la fenetre principale
	 */
	private void creerMenu() throws Exception {

		// construction du menu
		setJMenuBar(menuBar);	
		menuBar.add(fileMenu);
		fileMenu.setText("File");
		fileMenu.setPreferredSize(new Dimension(40,30)); // config la taille du menu File
		fileMenu.add(openMenu);		
		openMenu.addActionListener((ActionListener)this); // met le menu "Open" a l'écoute des actions de clique
		openMenu.setText("Open image file");
		//openMenu.setPreferredSize(new Dimension(90,25)); // config de la taille
		fileMenu.addSeparator(); // ajout d'un séparateur dans le menu
		quitMenu.setText("Exit");
		quitMenu.addActionListener((ActionListener)this);  // met le menu "Quit" a l'écoute des actions de clique
		fileMenu.add(quitMenu); 
		pixelMenu.setText("Pixels");
		menuBar.add(pixelMenu);
		
		pixelArrayMenu.setText("Show Hex pixel array");
		pixelArrayMenu.addActionListener((ActionListener)this);  // met le menu "Show pixel array" a l'écoute des actions de clique
		pixelArrayMenu.setEnabled(false);
		
		pixelRedMenu.setText("Show red color map samples");
		pixelRedMenu.addActionListener((ActionListener)this);  // met le menu "Show red color map samples" a l'écoute des actions de clique
		pixelRedMenu.setEnabled(false);
		
		pixelBlueMenu.setText("Show blue color map samples");
		pixelBlueMenu.addActionListener((ActionListener)this);  // met le menu "Show red color map samples" a l'écoute des actions de clique
		pixelBlueMenu.setEnabled(false);
		
		pixelGreenMenu.setText("Show green color map samples");
		pixelGreenMenu.addActionListener((ActionListener)this);  // met le menu "Show red color map samples" a l'écoute des actions de clique
		pixelGreenMenu.setEnabled(false);
		
		pixelMenu.add(pixelArrayMenu);	
		pixelMenu.add(pixelRedMenu);
		pixelMenu.add(pixelBlueMenu);
		pixelMenu.add(pixelGreenMenu);
	}
	
	// Méthode appelée lorsque en clique sur les éléments écoutés
	@Override
	public void actionPerformed(ActionEvent cliqueMenu) {
		if (cliqueMenu.getSource().equals(openMenu)) // l'orsque on clique sur la commande "Open" du menu "File"
		{
			fileOpenImage = new JFileChooser(); // boite de dialogue "ouvrir fichier"
			if (fileOpenImage.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {	// affichage de la boite de dialogue "ouvrir fichier"
				fileImage=new File(fileOpenImage.getSelectedFile().getAbsolutePath()); // récupération de l'emplacement du fichier image séléctionner
				imagePanel.addImage(fileImage);		// ajout de l'image au panneau qui sert a l'affichage
				centerPanel.setSize(new Dimension(imagePanel.getWidth(),imagePanel.getHeight())); // config du dimension du panneau image pour qu'il couvre la totalité de l'image
				path=fileOpenImage.getSelectedFile().getAbsolutePath(); // récupération de l'emplacement de l'image pour l'afficher en bas de la fenetre dans le label "Path"
				pixelArrayMenu.setEnabled(true);
				pixelRedMenu.setEnabled(true);
				pixelBlueMenu.setEnabled(true);
				pixelGreenMenu.setEnabled(true);
				imageLoaded=true; // met le flag de test a true pour dire que l'image a été chargée dans le panneau
				initImageInformation(); // initialisation du panneau informations sur l'image , appel a la méthode initImageInformation
			}
		}else if (cliqueMenu.getSource().equals(quitMenu)){ // lorsque on clique sur la commande "Quit" du menu "File"
			System.exit(0); // Fermeture de la fenetre
		}else if(cliqueMenu.getSource().equals(pixelArrayMenu)){ // lorsque on clique sur la commande "Show pixel array" du menu "Pixels"
			//int[][] matPixelsInt=ImageTools.getArrayFromImage(fileImage); // récupère la matrice de couleur rouge
			String[][] matPixelsString=ImageTools.getHexArrayFromImage(fileImage); // matrice de string qui contient les couleurs en format hexadecimal, comme #FF0000 pour le rouge...
			new PixelArray("Hex samples",matPixelsString,imagePanel.myImage.getWidth(),imagePanel.myImage.getHeight()); // on affiche un tableau graphique contient les valeurs de couleur en format hexadecimale
		}else if(cliqueMenu.getSource().equals(pixelRedMenu)){ // lorsque on clique sur la commande "Show red color map samples" du menu "Pixels"			
			new PixelArray("Red color map",matRedPixelsInt,imagePanel.myImage.getWidth(),imagePanel.myImage.getHeight()); // on affiche un tableau graphique contient les valeur de plan de couleur rouge de chaque pixel
		}
		else if(cliqueMenu.getSource().equals(pixelBlueMenu)){ // lorsque on clique sur la commande "Show blue color map samples" du menu "Pixels"			
			new PixelArray("Blue color map",matBluePixelsInt,imagePanel.myImage.getWidth(),imagePanel.myImage.getHeight());// on affiche un tableau graphique contient les valeur de plan de couleur bleu de chaque pixel
		}else if(cliqueMenu.getSource().equals(pixelGreenMenu)){ // lorsque on clique sur la commande "Show green color map samples" du menu "Pixels"			
			new PixelArray("Green color map",matGreenPixelsInt,imagePanel.myImage.getWidth(),imagePanel.myImage.getHeight()); // on affiche un tableau graphique contient les valeur de plan de couleur vert de chaque pixel
		}
	}

	/*
	 * méthode qui remplit des informations sur l'image
	 */
	private void initImageInformation() {
		// TODO Auto-generated method stub
		widthLabel.setText("Width:"+imagePanel.myImage.getWidth()); // affichage du largeur de l'image
		heightLabel.setText("Height:"+imagePanel.myImage.getHeight()); // affichage de l'hauteur de l'image
		statusBarLabel.setText("Path | "+path); // affichage de l'emplacement de l'image
		DecimalFormat df = new DecimalFormat("#.#"); // formatage de la taille de l'image, on prend deux chiffres après virgule
		float fileSize=(float)fileImage.length()/1024; // la methode length de l'objet "File" retourne la taille du fichier en octet , on divise par 1024 pour trouver la taille en Ko
		sizeLabel.setText("Doc:"+df.format(fileSize)+"Ko"); // affichage de la taille en Ko du fichier image
		matRedPixelsInt=ImageTools.getSingleColorMapArray(fileImage,ImageTools.RED_COLOR_MAP); // récupère la matrice de couleur rouge
		matBluePixelsInt=ImageTools.getSingleColorMapArray(fileImage,ImageTools.BLUE_COLOR_MAP); // récupère la matrice de couleur bleu
		matGreenPixelsInt=ImageTools.getSingleColorMapArray(fileImage,ImageTools.GREEN_COLOR_MAP); // récupère la matrice de couleur bleu
	}
	
	
	// Controleurs de la souris: detectent et affiche la position courant de la souris et les couleur RGB de chaque pixel pointer par le curseur
	/*
	 * Méthode prend comme parametre une chaine servir comme message de description de l'evenement, et un evenement de la souris MouseEvent
	 */
	void eventOutput(String eventDescription, MouseEvent e) {
		if(imageLoaded){ // test si l'image a été chargée dans le panneau ou nn pour pouvoir afficher les données
			xLabel.setText("X:"+e.getX()); // mis a jours de la position en X dans le label "X:" a chaque fois que la souris se deplace sur le panneau de l'image
			yLabel.setText("Y:"+e.getY()); // mis a jours de la position en Y dans le label "Y:" a chaque fois que la souris se deplace sur le panneau de l'image		
			rLabel.setText("R:"+matRedPixelsInt[e.getX()][e.getY()]); // mis a jours du couleur "R:" a chaque fois que la souris se deplace sur le panneau de l'image
			bLabel.setText("B:"+matBluePixelsInt[e.getX()][e.getY()]); // mis a jours du couleur "B:" a chaque fois que la souris se deplace sur le panneau de l'image
			gLabel.setText("G:"+matGreenPixelsInt[e.getX()][e.getY()]); // mis a jours du couleur "G:" a chaque fois que la souris se deplace sur le panneau de l'image
		}
    }
    // méthode appelée lorsque le souris se deplace dans le panneau
    public void mouseMoved(MouseEvent e) {
        eventOutput("",e); // Appel a la méthode qui met a jours la position en x,y dans les label,
    }
   // méthode appelée lorsque le souris est cliquée et se deplace dans le panneau
    public void mouseDragged(MouseEvent e) {
        eventOutput("", e); //  Appel a la méthode qui met a jours la position en x,y dans les label
    }
}



