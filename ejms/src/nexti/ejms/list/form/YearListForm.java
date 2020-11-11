/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: "년" 목록
 * 설명:
 */
package nexti.ejms.list.form;

import java.util.Collection;
import java.util.Vector;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

public class YearListForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private Collection beanCollection = null;
	private int currentyear = 0;
	private int gap = 0;
	private String all_fl = "";
	private String title = "전체";
	
	/**
	 * @return the beanCollection
	 */
	public Collection getBeanCollection() {
		if(beanCollection == null) {
			Vector entries = new Vector();
			
			try {
				String yearToString = "";
				
				if("ALL".equals(all_fl)){
				    entries.add(new LabelValueBean(title.toLowerCase(), ""));
				}
				
				for(int i = currentyear - 10; i <= currentyear + 5; i += gap) {
					yearToString = Integer.toString(i) + "년";
					
					entries.add(new LabelValueBean(yearToString, yearToString));
				}
				
				beanCollection = entries;
				
			} catch (Exception e) {

			} finally {
				
			}
		}
		return beanCollection;
	}
	
	/**
	 * @param beanCollection the beanCollection to set
	 */
	public void setBeanCollection(Collection beanCollection) {
		this.beanCollection = beanCollection;
	}
	
	/**
	 * @param gap the gap to set
	 */
	public void setGap(int gap) {
		this.gap = gap;
	}

	/**
	 * @param currentyear the currentyear to set
	 */
	public void setCurrentyear(int currentyear) {
		this.currentyear = currentyear;
	}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAll_fl(String all_fl) {
        this.all_fl = all_fl;
    }
	
}