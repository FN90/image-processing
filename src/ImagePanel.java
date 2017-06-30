import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*
 * Classe qui définit un panneau pour afficher une image dans une fenetre, elle implémente la classe JPanel 
 */
public class ImagePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		BufferedImage myImage = null;
		// Constructeur par défaut, ne contient aucun paramtere
		public ImagePanel() {
			super();
			setBackground(Color.gray); // config du coleur de l'arrière plan
		}
		// 2éme constructeur, il prend comme paramètre une BufferedImage
		public ImagePanel(BufferedImage myImage) {
			super();
			this.myImage=myImage; // Affectation de l'attribut "myImage" par la variable passée en paramètre du constructeur	
		}
		// 3éme constructeur, il prend comme paramètre un fichier image
		public ImagePanel(File imageFile) {
			super();
			try{
				this.myImage=ImageIO.read(imageFile); // lecture du fichier image en utilisant la méthode "read" de la classe "ImageIO"
			}catch (IOException e) {
				// En cas d'erreur de chargement du fichier image
				System.err.println("Erreur de lecture du fichier image");
				e.printStackTrace();
			}	
		}
		// méthode spécialisée qui dessine une image dans un JPanel, elle prend comme paramètre un objet "Graphics", il nous permet de dessiner sur un objet JPanel
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			if(myImage != null){							
				g.drawImage(myImage, 0, 0, null); // dessiner une image "myImage" dans la position (0,0)	
				this.setSize(new Dimension(myImage.getWidth(),myImage.getHeight())); // configuration de la taille du panneau de l'image pour qu'il couvre la totalité de l'image on a utilisé les deux méthode getWidth(), getHeight().
			}			
		}
		/*
		 *  Méthode qui ajoute une image au panneau d'image, il prend comme parametre le fichier image:
		 *  -- lit une image a partir du fichier fileImage
		 *  -- configure les dimension du panneau pour couvre l'image
		 *  -- affiche un message d'errer en cas d'echec de lecture de l'image
		 */
		protected void addImage(File fileImage)
		{   // desiner une image à l'ecran	
			try {
				myImage = ImageIO.read(fileImage);	// lecture d'image a partir du fichier image			
				this.setPreferredSize(new Dimension(myImage.getWidth(),myImage.getHeight())); // configuration de la taille du panneau image				
			} catch (IOException e) {
				e.printStackTrace();
			}
			repaint();	// appel a la méthode //On redessine notre Panneau		
		}
		
	}


