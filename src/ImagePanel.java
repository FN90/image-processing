import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*
 * A class that defines a panel to display an image in a window, it implements the JPanel class
 */
public class ImagePanel extends JPanel{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

		BufferedImage myImage = null;
		// Constructor by default, contains no parameters
		public ImagePanel() {
			super();
			setBackground(Color.gray); // Background color config
		}
		// 2nd constructor, it takes as parameter a BufferedImage
		public ImagePanel(BufferedImage myImage) {
			super();
			this.myImage=myImage; //Assign the attribute "myImage" by the variable passed as parameter
		}
		// 3rd constructor, it takes as parameter an image file
		public ImagePanel(File imageFile) {
			super();
			try{
				this.myImage=ImageIO.read(imageFile); // Reading the image file using the "read" method of the "ImageIO" class
			}catch (IOException e) {
				// If image file loading error occurs
				System.err.println("Erreur de lecture du fichier image");
				e.printStackTrace();
			}
		}
		// A specialized method that draws an image in a JPanel, it takes as parameter "Graphics" object, it allows us to draw on a JPanel object
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			if(myImage != null){
				g.drawImage(myImage, 0, 0, null); // Draw an image into "myImage" in position (0,0)
				this.setSize(new Dimension(myImage.getWidth(),myImage.getHeight())); // setting up the size of the panel to cover the entire image we have used the two getWidth (), getHeight () methods.
			}
		}
		/*
		 *  A method that adds an image to the image panel, it takes as a parameter the image file:
		* - reads an image from the file fileImage
		* - configures the size of the panel to cover the image
		* - displays a message to err in case of failure to read the image
		 */
		protected void addImage(File fileImage)
		{   // draw an image to the screen
			try {
				myImage = ImageIO.read(fileImage);	// lecture d'image a partir du fichier image
				this.setPreferredSize(new Dimension(myImage.getWidth(),myImage.getHeight())); // configuration de la taille du panneau image
			} catch (IOException e) {
				e.printStackTrace();
			}
			repaint();	// repaint the image into the panel
		}

	}


