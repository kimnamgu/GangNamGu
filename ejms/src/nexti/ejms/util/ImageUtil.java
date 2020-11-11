/**
 * 작성자명 : 이종일
 * 작성일자 : Feb 20, 2007
 * 기능설명 : 이미지 크기 변환 시 해상도 유지시켜주는 이미지 유틸
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
 * @author 이종일
 *
 */
public class ImageUtil {
	public static final int RATIO = 0;
	public static final int SAME = -1;

	public static void resize(File src, File dest, int width, int height)
			throws IOException {
		Image srcImg = null;
		// 원문에서는 src에 확장자가 포함되어 있을 경우에 대해서 되어 있었으나 
		// 파일 업로드 시 확장자가 포함되지 않은 상태로 업로드되므로 확장자를 
		// 별도로 설정한다.
		 String suffix = src.getName().substring(src.getName().lastIndexOf('.') + 1).toLowerCase();
		//String suffix = logSrc.substring(logSrc.lastIndexOf('.') + 1).toLowerCase();
		if (suffix.equals("bmp") || suffix.equals("png") || suffix.equals("gif")) {
			srcImg = ImageIO.read(src);
		} else {
			// BMP가 아닌 경우 ImageIcon을 활용해서 Image 생성
			// 이렇게 하는 이유는 getScaledInstance를 통해 구한 이미지를
			// PixelGrabber.grabPixels로 리사이즈 할때
			// 빠르게 처리하기 위함이다.
			//srcImg = new ImageIcon(src.toURL()).getImage();	//deprecated
			srcImg = new ImageIcon(src.toURI().toURL()).getImage();
		}

		// 원본 이미지의 가로, 세로 pixel 크기
		int srcWidth = srcImg.getWidth(null);
		int srcHeight = srcImg.getHeight(null);

		int destWidth = -1, destHeight = -1;

		// 대상 이미지의 가로, 세로 pixel 크기 설정
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

		// 대상 이미지의 크기를 원본 이미지로부터 비율로 설정
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

