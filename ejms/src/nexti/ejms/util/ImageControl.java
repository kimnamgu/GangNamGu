package nexti.ejms.util;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
*���� ���(URL) �� �ִ� �̹��� ������ Width�� Height�� �˾Ƴ���.
*/

public class ImageControl {
	 private int width = -1;
	 private int height = -1;
	 private int compare;

	 public ImageControl(){}

	 /**
	 *Constructor
	 *@param Object obj : �̹��� ��ü�� ����� �ϴ� ���� ��γ� URL�� instance
	 *@param int compare : �� �ϰ��� �ϴ� Width
	 */
	 public ImageControl( Object obj, int compare ){
	  BufferedImage img = null;
	  try{
	   if( obj instanceof String ){
		img = ImageIO.read( new File( (String)obj ) );
	   }else if( obj instanceof URL ){
		img = ImageIO.read( (URL)obj );
	   }
	   this.compare = compare;
	   if( img != null ){
		width = img.getWidth();
		height = img.getHeight();
	   }
	  }catch( IOException e ){
	   System.out.println( e );
	  }
	 }

	 /**
	 *�̹����� width�� ��ȯ �Ѵ�. Image��ü�� ���� �� ���� ��� -1�� �����Ѵ�
	 *@return int : �̹����� Width
	 */
	 public int getWidth(){
	  return width;
	 }

	 /**
	 *�̹����� height�� ��ȯ �Ѵ�. Image��ü�� ���� �� ���� ��� -1�� �����Ѵ�
	 *@return int : �̹����� Height
	 */
	 public int getHeight(){
	  return height;
	 }
	 /**
	 *�̹����� width�� ���ϰ��� �ϴ� ũ��(compare)���� ū���� �����Ѵ�.
	 *@boolean true : ũ��
	 */
	 public boolean isBig(){
	  return width > compare;
	 }

	 /**
	 *�̹����� width�� �񱳰��� ��ȯ �Ѵ�.
	 *@return int : �񱳰�(compare)
	 */ 
	 public int getCompare(){
	  return compare;
	 }

	 /**
	 *�̹��� width�� compare���� Ŭ ��� compare/width�� 
	 * ������ ���ο� Height�� ��ȯ �Ѵ�.<br>
	 * Image��ü�� ���� �� ���� ��� compare�� �����Ѵ�
	 *@return int compare/width�� ���� height
	 */
	 public int getNewHeight(){
	  return isBig()?(int)( 
	   height *( new Float( compare ).floatValue() / 
		  new Float( width ).floatValue() )  ):width;
	 }
}
