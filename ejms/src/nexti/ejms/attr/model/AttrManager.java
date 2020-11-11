/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Ӽ���ϰ��� manager
 * ����:
 */
package nexti.ejms.attr.model;

import java.text.DecimalFormat;
import java.util.List;

public class AttrManager {
	private static AttrManager instance = null;
	private static AttrDAO dao = null;
	
	public static AttrManager instance() {
		if (instance==null) instance = new AttrManager(); 
		return instance;
	}
	
	private AttrDAO getAttrDAO(){
		if (dao==null) dao = new AttrDAO(); 
		return dao;
	}
	
	private AttrManager() {
	}
	
	/**
	 * ����ۼ� ��ļӼ���� ����
	 * @param gbn
	 * @param type
	 * @param mcd
	 * @param mnm
	 * @param dcd
	 * @param dnm
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public String saveAttrList(String gbn, String type, String mcd, String mnm, String dcd, String dnm, String user_id) throws Exception {
		String result = null;
		AttrBean bean = null;
		DecimalFormat df = new DecimalFormat("00000");
		
		if ( gbn.equals("master") == true ) {
			if ( type.equals("insert") == true ) {
				mcd = df.format(getAttrDAO().getNextListcd());
				bean = new AttrBean();
				bean.setListcd(mcd);
				bean.setListnm(mnm);		
			    bean.setCrtusrid(user_id);
			    bean.setCrtusrgbn("1");
				if ( insertAttrMst(bean) > 0 ) {
					result = mcd + "," + mnm;
				}
			} else if ( type.equals("update") == true ) {
				bean = new AttrBean();
				bean.setListcd(mcd);
				bean.setListnm(mnm);		
				if ( modifyAttrMst(bean) > 0 ) {
					bean = getFormatAttList(mcd);
					StringBuffer attoption = new StringBuffer();
					attoption.append(bean.getListcd() + "," + bean.getListnm());
					List listdtl = bean.getListdtlList();
					for(int j = 0; j < listdtl.size(); j++) {
						AttrBean attdtlbean = (AttrBean)listdtl.get(j);
						attoption.append("," + attdtlbean.getSeq() + "," + attdtlbean.getListdtlnm());
					}
					result = attoption.toString();
				}
			} else if ( type.equals("delete") == true ) {
				if ( deleteAttrMst(mcd) > 0 ) {
					result = mcd + "," + mnm;
				}
			}
		} else if ( gbn.equals("detail") == true ) {
			if ( type.equals("insert") == true ) {
				dcd = Integer.toString(getAttrDAO().getNextListcdSeq(mcd));
				bean = new AttrBean();
				bean.setListcd(mcd);
				bean.setSeq(Integer.parseInt(dcd));
				bean.setListdtlnm(dnm);
				bean.setAttr_desc("");
				if ( insertAttrDtl(bean) == 0 ) {
					return result;
				}
			} else if ( type.equals("update") == true ) {
				bean = new AttrBean();
				bean.setListcd(mcd);
				bean.setSeq(Integer.parseInt(dcd));
				bean.setListdtlnm(dnm);
				bean.setAttr_desc("");		
				if ( modifyAttrDtl(bean) == 0 ) {
					return result;
				}
			} else if ( type.equals("delete") == true ) {
				if ( deleteAttrDtl(mcd, Integer.parseInt(dcd)) == 0 ) {
					return result;
				}
			}
			bean = getFormatAttList(mcd);
			StringBuffer attoption = new StringBuffer();
			attoption.append(bean.getListcd() + "," + bean.getListnm());
			List listdtl = bean.getListdtlList();
			for(int j = 0; j < listdtl.size(); j++) {
				AttrBean attdtlbean = (AttrBean)listdtl.get(j);
				attoption.append("," + attdtlbean.getSeq() + "," + attdtlbean.getListdtlnm());
			}
			result = attoption.toString();
		}
			
		return result;
	}
	
	/**
	 * �Ӽ���� ��������
	 * @return
	 * @throws Exception
	 */	
	public AttrBean getFormatAttList(String listcd) throws Exception {
		
		AttrBean result = null;
		
		result = getAttrDAO().getFormatAttList(listcd);
		
		return result;
	}
	
	/**
	 * �Ӽ���� ����Ʈ ��������
	 * @return
	 * @throws Exception
	 */	
	public List getFormatAttList(String crtusrid, String crtusrgbn) throws Exception {
		
		List result = null;
		
		result = getAttrDAO().getFormatAttList(crtusrid, crtusrgbn);
		
		return result;
	}
		
	/** 
	 * �Ӽ���� ������ �߰�	 
	 */
	public int insertAttrMst (AttrBean bean) throws Exception {
		return getAttrDAO().insertAttrMst(bean);
	}
	
	/** 
	 * �Ӽ���� ������ �߰�	 
	 */
	public int insertAttrDtl (AttrBean bean) throws Exception {
		return getAttrDAO().insertAttrDtl(bean);
	}

	/** 
	 * �Ӽ���� ������ ����
	 */
	public int modifyAttrMst (AttrBean bean) throws Exception {
		return getAttrDAO().modifyAttrMst(bean);
	}
	
	/** 
	 * �Ӽ���� ������ ����
	 */
	public int modifyAttrDtl (AttrBean bean) throws Exception {
		return getAttrDAO().modifyAttrDtl(bean);
	}

	/** 
	 * �Ӽ���� ������ ����	
	 */
	public int deleteAttrMst (String p_listcd) throws Exception {
		return getAttrDAO().deleteAttrMst(p_listcd);
	}
	
	/** 
	 * �Ӽ���� ������ ����	
	 */
	public int deleteAttrDtl (String p_listcd, int p_seq) throws Exception {
		return getAttrDAO().deleteAttrDtl(p_listcd, p_seq);
	}

	/** 
	 * ������ �Ӽ����	
	 * param : gbn - �ý��ۿ�(s), �����ڿ�(a)����
	 */
	public List attrMstList (String gbn) throws Exception {
		return getAttrDAO().attrMstList(gbn);
	}
	
	/** 
	 * Ư�� �������ڵ�(LISTCD)�� ���� Detail List
	 */
	public List attrDtlList(String p_listcd) throws Exception {
		List subList = null;
		
		subList = getAttrDAO().attrDtlList(p_listcd);
		
		return subList;
	}
	
	/** 
	 * �Ӽ���� �����ͺ���
	 */
	public AttrBean attrMstInfo(String p_listcd) throws Exception {
		return getAttrDAO().attrMstInfo(p_listcd);
	}

	/** 
	 * �Ӽ���� �����Ϻ���
	 */
	public AttrBean attrDtlInfo(String p_listcd, int p_seq) throws Exception {
		return getAttrDAO().attrDtlInfo(p_listcd, p_seq);
	}

	/** 
	 * ������ �ڵ�  �������� 
	 * return true : ������
	 * return false : �������
	 * */
	public boolean existedMst(String p_listcd) throws Exception {
		return getAttrDAO().existedMst(p_listcd);
	}
	
	/** 
	 * ������ �ڵ� �������� 
	 * return true : ������
	 * return false : �������
	 * */
	public boolean existedDtl(String p_listcd, int seq) throws Exception {
		return getAttrDAO().existedDtl(p_listcd, seq);
	}
	
	/** 
	 * ���ڵ�� ��������
	 */
	public String getMst_Name(String p_listcd) throws Exception {
		return getAttrDAO().getMst_Name(p_listcd);
	}
	
	/** 
	 * ���ڵ�� ��������
	 */
	public String getDtl_Name(String p_listcd, int seq) throws Exception {
		return getAttrDAO().getDtl_Name(p_listcd, seq);
	}
}
