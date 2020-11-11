/**
 * �ۼ��ڸ� : ������
 * �ۼ����� : Feb 20, 2007
 * ��ɼ��� : �̹��� ũ�� ��ȯ �� �ػ� ���������ִ� �̹��� ��ƿ
 */
package nexti.ejms.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author ������
 *
 */
public class ImageUtil {
	public static final int RATIO = 0;
	public static final int SAME = -1;

	public static void resize(File src, File dest, int width, int height)
			throws IOException {
		Image srcImg = null;
		// ���������� src�� Ȯ���ڰ� ���ԵǾ� ���� ��쿡 ���ؼ� �Ǿ� �־����� 
		// ���� ���ε� �� Ȯ���ڰ� ���Ե��� ���� ���·� ���ε�ǹǷ� Ȯ���ڸ� 
		// ������ �����Ѵ�.
		 String suffix = src.getName().substring(src.getName().lastIndexOf('.') + 1).toLowerCase();
		//String suffix = logSrc.substring(logSrc.lastIndexOf('.') + 1).toLowerCase();
		if (suffix.equals("bmp") || suffix.equals("png") || suffix.equals("gif")) {
			srcImg = ImageIO.read(src);
		} else {
			// BMP�� �ƴ� ��� ImageIcon�� Ȱ���ؼ� Image ����
			// �̷��� �ϴ� ������ getScaledInstance�� ���� ���� �̹�����
			// PixelGrabber.grabPixels�� �������� �Ҷ�
			// ������ ó���ϱ� �����̴�.
			//srcImg = new ImageIcon(src.toURL()).getImage();	//deprecated
			srcImg = new ImageIcon(src.toURI().toURL()).getImage();
		}

		// ���� �̹����� ����, ���� pixel ũ��
		int srcWidth = srcImg.getWidth(null);
		int srcHeight = srcImg.getHeight(null);

		int destWidth = -1, destHeight = -1;

		// ��� �̹����� ����, ���� pixel ũ�� ����
		if (width == SAME) {
			destWidth = srcWidth;
		} else if (width > 0) {
			destWidth = width;
		}

		if (height == SAME) {
			destHeight = srcHeight;
		} else if (height > 0) {
			destHeight = height;
		}

		// ��� �̹����� ũ�⸦ ���� �̹����κ��� ������ ����
		if (width == RATIO && height == RATIO) {
			destWidth = srcWidth;
			destHeight = srcHeight;
		} else if (width == RATIO) {
			double ratio = ((double) destHeight) / ((double) srcHeight);
			destWidth = (int) ((double) srcWidth * ratio);
		} else if (height == RATIO) {
			double ratio = ((double) destWidth) / ((double) srcWidth);
			destHeight = (int) ((double) srcHeight * ratio);
		}

		
		Image imgTarget = srcImg.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH);
		int pixels[] = new int[destWidth * destHeight];
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, destWidth,	destHeight, pixels, 0, destWidth);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			throw new IOException(e.getMessage());
		}
		BufferedImage destImg = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
		destImg.setRGB(0, 0, destWidth, destHeight, pixels, 0, destWidth);

		ImageIO.write(destImg, "jpg", dest);
	}
}

