import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


public class ImageTools {
	private static BufferedImage myImage; // La sous-classe BufferedImage décrit d'une image avec un buffer de données d'image accessible.	
	static final int RED_COLOR_MAP=1; // drapeau pour dire qu'on va choisir le plan de couleur rouge
	static final int GREEN_COLOR_MAP=2; // drapeau pour dire qu'on va choisir le plan de couleur vert
	final static int BLUE_COLOR_MAP=3; // drapeau pour dire qu'on va choisir le plan de couleur bleu
	private static int[][] pixelArray=null; // Matrice qui va contenir les échantillons
	private static int imageWidth; // largeur de l'image
	private static int imageHeight; // hauteur de l'image
	private static int[][] redPixels = null; // Matrice de pixel du plan rouge
	private static int[][] greenPixels = null; // Matrice de pixel du plan rouge
	private static int[][] bluePixels = null; // Matrice de pixel du plan rouge
	
	public ImageTools(){
		super();

	}
	public ImageTools(File imageFile){
		super();
		try {
			myImage=ImageIO.read(imageFile);
		} catch (IOException e) {
			System.out.println("Erreur de lecture de l'image");
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Erreur de lecture du fichier", "Erreur",JOptionPane.ERROR_MESSAGE); // afficher un message d'erreur
			e.printStackTrace();
		}
	}
	
	
	public static int getImageWidth(){
		return myImage.getWidth();
	}
	public static int getImageHeight(){
		return myImage.getHeight();
	}
	
	/*
	 *  Méthode qui retourne la matrice correspondante au fichier image JPEG "imagFile":
	 *  Elle utilise un objet 'BufferedImage' pour récuperer la matrice RGB 'pixelArray' de l'image
	 */
	
	public static int[][] getArrayFromImage(File imageFile){
		BufferedImage myImage=null; // l'objet qui nous permet de manipuler l'image
		try{
	        myImage=ImageIO.read(imageFile); //La sous-classe BufferedImage décrit d'une image avec un buffer de données d'image accessible.
			}
			catch(Exception ee){
				ee.printStackTrace();
				System.err.println("Erreur de lecture du fichier image"); 
			}
	        imageHeight=myImage.getHeight(); // l'hauteur de l'image a partir de l'objet BufferedImage
	        imageWidth=myImage.getWidth(); // la largeur de l'image a partir de l'objet BufferedImage	
	        pixelArray=new int[imageWidth][imageHeight]; // matrice qui va contenir la matrice RGB de l'image	        
	        for(int i=0;i<imageWidth;i++){
	            for(int j=0;j<imageHeight;j++){
	                pixelArray[i][j]=myImage.getRGB(i, j); // Récupère la composante RGB de l'image a partir de l'objet BufferedImage	                	                               
	            }
	        }		
		return pixelArray;		
	}
	
	/*
	 * Méthode qui construit une image a partir d'une matrice de pixel RGB:
	 * On a utilisé la méthode "setRGB()" de l'objet BufferedImage, elle a comme paramètre:la matrice de pixel RGB et les positions (en x et y)
	 */
	public static Image writeImageFromArray(int[][] pixelArray,String fileName){		
		BufferedImage myReconstImage=new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_BYTE_GRAY); // l'image reconstruite , image en niveau de gris grace au flag "BufferedImage.TYPE_BYTE_GRAY"
			try{
	        for(int y=0;y<imageHeight;y++){
	            for(int x=0;x<imageWidth;x++){                
	            	myReconstImage.setRGB(x, y, pixelArray[x][y]);   //  Remplissage de l'image en utilisant la méthode setRGB a partir de la matrice RGB           
	            }
	        }	
	         File outputfile = new File(fileName); // Création d'un nouveau fichier qui va contenir l'image reconstruite
	            ImageIO.write(myReconstImage, "jpg", outputfile); // écriture de l'image dans le fichier 
        }
        catch(Exception ee){
            ee.printStackTrace();
        }
		return myReconstImage; // retourne l'image reconstruite		
	}	
	
	/*
	 * Méthode qui retourne la matrice de plan de couleur rouge, vert ou bleu, ca depend de la variable 'colorMap':
	 * 		si colorMap=RED_COLOR_MAP (1) => retarne la matrice du plan rouge
     *  	si colorMap=BLUE_COLOR_MAP (2) => retourne la matrice du plan bleu
     *  	si colorMap=GREEN_COLOR_MAP (3) => retourne la matrice du plan vert
	 */
	public static int[][] getSingleColorMapArray(File imageFile,int colorMap){		
		try{
	        BufferedImage myImage=ImageIO.read(imageFile); //La sous-classe BufferedImage décrit d'une image avec un buffer de données d'image accessible.
	        imageHeight=myImage.getHeight(); // l'hauteur de l'image a partir de l'objet BufferedImage
	        imageWidth=myImage.getWidth(); // la largeur de l'image a partir de l'objet BufferedImage	
	        pixelArray=new int[imageWidth][imageHeight]; // matrice qui va contenir la matrice RGB de l'image
	        redPixels=new int[imageWidth][imageHeight]; // matrice du plan rouge de l'image
	        greenPixels=new int[imageWidth][imageHeight]; // matrice du plan rouge de l'image
	        bluePixels=new int[imageWidth][imageHeight]; //  matrice du plan rouge de l'image        
	        for(int i=0;i<imageWidth;i++){
	            for(int j=0;j<imageHeight;j++){
	                pixelArray[i][j]=myImage.getRGB(i, j); // Récupère la composante RGB de l'image a partir de l'objet BufferedImage
	                redPixels[i][j]= (int)((Math.pow(256,3)+pixelArray[i][j]) / 65536); // Récupère le plan rouge de l'image
	                greenPixels[i][j] = (int) (((Math.pow(256,3)+pixelArray[i][j]) / 256 ) % 256 ); // Récupère le plan vert de l'image
	                bluePixels[i][j] = (int) ((Math.pow(256,3)+pixelArray[i][j])%256); // Récupère le plan bleu de l'image
	            }
	        }	        
		}
        catch(Exception ee){
            ee.printStackTrace();
        }   
        /*
         *  la matrice retournée dépend de la variable d'entrée 'colorMap', 
         *  	si colorMap=RED_COLOR_MAP (1) => retarne la matrice du plan rouge
         *  	si colorMap=BLUE_COLOR_MAP (2) => retourne la matrice du plan bleu
         *  	si colorMap=GREEN_COLOR_MAP (3) => retourne la matrice du plan vert
         */
        if(colorMap==1)
			return redPixels; // retourne la matrice du plan rouge
		else if(colorMap==2)
			return greenPixels; // retourne la matrice du plan vert
		else if(colorMap==3)
			return bluePixels; // retourne la matrice du plan vert
		else
			return null;
	}
	
	/*
	 * Méthode qui retourne une matrice composée par les échantillons des trois couleur (Rouge,Vert et Bleu), 
	 * les pixels sont organisés dans l'ordre suivant: Rouge Vert Bleu dans trois cases consécutifs 
	 */
	
	public static int[][] getFullColorMatrix(File imageFile){
		BufferedImage myImage=null; // l'objet qui nous permet de manipuler l'image
		try{
	        myImage=ImageIO.read(imageFile); //La sous-classe BufferedImage décrit d'une image avec un buffer de données d'image accessible.
			}
			catch(Exception ee){
				ee.printStackTrace();
				new JOptionPane();
				JOptionPane.showMessageDialog(null, "Erreur de lecture du fichier", "Erreur",JOptionPane.ERROR_MESSAGE); // afficher un message d'erreur
				System.err.println("Erreur de lecture du fichier image"); 
			}
			
			imageHeight=myImage.getHeight(); // l'hauteur de l'image a partir de l'objet BufferedImage
	        imageWidth=myImage.getWidth(); // la largeur de l'image a partir de l'objet BufferedImage	
	        int[][] fullPixelArray = new int[imageWidth][imageHeight*3]; // matrice qui va contenir la matrice RGB de l'image
	        pixelArray=new int[imageWidth][imageHeight]; // matrice qui va contenir la matrice RGB de l'image
	        redPixels=new int[imageWidth][imageHeight]; // matrice du plan rouge de l'image
	        greenPixels=new int[imageWidth][imageHeight]; // matrice du plan rouge de l'image
	        bluePixels=new int[imageWidth][imageHeight]; //  matrice du plan rouge de l'image        
	        for(int i=0;i<imageWidth;i++){
	            for(int j=0;j<imageHeight;j++){
	            	pixelArray[i][j]=myImage.getRGB(i, j); // Récupère la composante RGB de l'image a partir de l'objet BufferedImage
	                redPixels[i][j]= (int)((Math.pow(256,3)+pixelArray[i][j]) / 65536); // Récupère le plan rouge de l'image
	                greenPixels[i][j] = (int) (((Math.pow(256,3)+pixelArray[i][j]) / 256 ) % 256 ); // Récupère le plan vert de l'image
	                bluePixels[i][j] = (int) ((Math.pow(256,3)+pixelArray[i][j])%256); // Récupère le plan bleu de l'image
	                /* On rassemble les trois composantes (Rouge, Verte, Bleue) dans une meme matrice respectivement :
	                 * par exemple : fullPixelArray[0][0]=redPixels[0][0]
	                 * 				 fullPixelArray[0][1]=greenPixels[0][0]
	                 * 				 fullPixelArray[0][2]=bluePixels[0][0]
	                 * 				 ...
	                 */
	                fullPixelArray[i][3*j]=redPixels[i][j]; // Récupère la composante Rouge et la mettre dans leur position dans la matrice globale
	                fullPixelArray[i][3*j+1]=bluePixels[i][j]; // Récupère la composante Bleu et la mettre dans leur position dans la matrice globale
	                fullPixelArray[i][3*j+2]=greenPixels[i][j]; // Récupère la composante Verte et la mettre dans leur position dans la matrice globale
	            }
	        }
		return fullPixelArray;
	}
	
	/* Méthode qui lit un fichier image et retourne une matrice de pixel RGB en format hexadécimale */
	public static String[][] getHexArrayFromImage(File imageFile){
		BufferedImage myImage=null; // l'objet qui nous permet de manipuler l'image
		try{
	        myImage=ImageIO.read(imageFile); //La sous-classe BufferedImage décrit d'une image avec un buffer de données d'image accessible.
			}
			catch(Exception ee){
				ee.printStackTrace();
				System.err.println("Erreur de lecture du fichier image"); 
			}
	        imageHeight=myImage.getHeight(); // l'hauteur de l'image a partir de l'objet BufferedImage
	        imageWidth=myImage.getWidth(); // la largeur de l'image a partir de l'objet BufferedImage	
	        String[][] pixelHexArray=new String[imageWidth][imageHeight]; // matrice qui va contenir la matrice RGB de l'image	        
	        for(int i=0;i<imageWidth;i++){
	            for(int j=0;j<imageHeight;j++){
	            	pixelHexArray[i][j]="#"+Integer.toHexString(myImage.getRGB(i,j)).substring(2); // Récupère la composante RGB de l'image a partir de l'objet BufferedImage et la convertir en hexadécimale (une chaine de caractère) 	                	                               
	            }
	        }		
		return pixelHexArray;		
	}
	
	/*
	 * Méthode qui construit une image a partir des trois matrices des plans de couleurs rouge, vert et bleu, en cours d'édition
	 */
	public Image writeImageFrom3ColorMap(int[][] redColorMap,int[][] greenColorMap,int[][] blueColorMap, File fileName){
		/* En cours d'édition */
		return null;
		
	}
	
	/*
	 * Méthode qui affiche les éléments d'une matrice (matrix) dans le console java 
	 */
	static void display2DArray(int[][] matrix,
			int imageWidth, int imageHeight) {
		for(int i=0;i<imageWidth;i++){
			for(int j=0;j<imageHeight;j++){
				System.out.print(" | "+matrix[i][j]);
			}
			System.out.println();
		}		
	}
}