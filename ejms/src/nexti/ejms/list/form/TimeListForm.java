/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: "��", "��" ���
 * ����:
 */
package nexti.ejms.list.form;

import java.util.Collection;
import java.util.Vector;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

public class TimeListForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private Collection beanCollection = null;
	private String type = "";
	private int gap = 0;	

    public Collection getBeanCollection() throws Exception {
        if (beanCollection == null) {
            Vector entries = new Vector();         	

    		try {
    			int max = 0;
    			String timeToString = "";
    			
    			//�� ���
    			if(type.equals("HOUR"))
    				max = 24;
    			//�� ���
    			else if(type.equals("MINUTE"))
    				max = 60;
    			
    			for(int i = 0; i < max; i += gap) {
    				timeToString = Integer.toString(i);
    				
    				if(i < 10)
    					timeToString = "0" + timeToString;
    				
    				entries.add(new LabelValueBean(timeToString, timeToString));
    			}

    			beanCollection = entries;
    		} catch(Exception e){

    		} finally {

    		}            
        }
        return (beanCollection);
    }

    public void setBeanCollection(Collection beanCollection) {
        this.beanCollection = beanCollection;
    }
    
	public void setType(String type) {
		this.type = type;
	}

	public void setGap(int gap) {
		this.gap = gap;
	}    
}
