/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: "일" 목록
 * 설명:
 */
package nexti.ejms.list.form;

import java.util.Collection;
import java.util.Vector;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

public class DayListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;	

    public Collection getBeanCollection() {
        if (beanCollection == null) {
            Vector entries = new Vector();
            String s_day="";
            
    		for(int i=1;i<32;i++){
    			if (i<10) {
    				s_day="0"+String.valueOf(i);
    			} else {
    				s_day=String.valueOf(i);
    			}
	    		entries.add(new LabelValueBean(String.valueOf(i),s_day));    
    		}			

    		beanCollection = entries;    	
        }
        return (beanCollection);
    }

    public void setBeanCollection(Collection beanCollection) {
        this.beanCollection = beanCollection;
    } 
}
