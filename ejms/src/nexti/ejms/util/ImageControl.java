package nexti.ejms.util;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
*절대 경로(URL) 상에 있는 이미지 파일의 Width와 Height을 알아낸다.
*/

public class ImageControl {
	 private int width = -1;
	 private int height = -1;
	 private int compare;

	 public ImageControl(){}

	 /**
	 *Constructor
	 *@param Object obj : 이미지 객체를 얻고자 하는 절대 경로나 URL의 instance
	 *@param int compare : 비교 하고자 하는 Width
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
	 *이미지의 width을 반환 한다. Image객체를 얻지 못 했을 경우 -1을 리턴한다
	 *@return int : 이미지의 Width
	 */
	 public int getWidth(){
	  return width;
	 }

	 /**
	 *이미지의 height을 반환 한다. Image객체를 얻지 못 했을 경우 -1을 리턴한다
	 *@return int : 이미지의 Height
	 */
	 public int getHeight(){
	  return height;
	 }
	 /**
	 *이미지의 width가 비교하고자 하는 크기(compare)보다 큰가를 검증한다.
	 *@boolean true : 크다
	 */
	 public boolean isBig(){
	  return width > compare;
	 }

	 /**
	 *이미지의 width의 비교값을 반환 한다.
	 *@return int : 비교값(compare)
	 */ 
	 public int getCompare(){
	  return compare;
	 }

	 /**
	 *이미지 width가 compare보다 클 경우 compare/width의 
	 * 비율로 새로운 Height을 반환 한다.<br>
	 * Image객체를 얻지 못 했을 경우 compare를 리턴한다
	 *@return int compare/width로 계산된 height
	 */
	 public int getNewHeight(){
	  return isBig()?(int)( 
	   height *( new Float( compare ).floatValue() / 
		  new Float( width ).floatValue() )  ):width;
	 }
}
