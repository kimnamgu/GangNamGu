/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �ڷ���� ��� actionform
 * ����:
 */
package nexti.ejms.workplan.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WorkplanListForm extends ActionForm {
    
    private static final long serialVersionUID = 1L;

    private int     page                = 1 ;   //������
    //private String  check_detail        = "";   //������
    private int     treescroll          = 0;    //������ ��ũ��
    private String currentyear          = "";
    private String  searchdept          = "";   //���úμ����� ��ü����
    
    private String searchyear           = "";       //����
    private String searchupperdeptnm    = "";       //����(�����μ�)
    private String searchupperdeptcd    = "";
    private String searchdeptnm         = "";       //�μ�
    private String searchdeptcd         = "";
    private String searchchrgunitnm     = "";       //������
    private String searchchrgunitcd     = "";
    private String searchstatus         = "";       //�����Ȳ
    private String searchstrdt          = "";       //������
    private String searchenddt          = "";       //������
    private String searchgubun          = "";       //�˻�����
    private String searchtitle          = "";       //�˻���
    
    private List    worklist;                    //�������
    private String  orggbn              = "";   //��������
    
    /** 
     * Method validate
     * @param mapping
     * @param request
     * @return ActionErrors
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        return null;
    }

    /** 
     * Method reset
     * @param mapping
     * @param request
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTreescroll() {
        return treescroll;
    }

    public void setTreescroll(int treescroll) {
        this.treescroll = treescroll;
    }

    public String getSearchyear() {
        return searchyear;
    }

    public void setSearchyear(String searchyear) {
        this.searchyear = searchyear;
    }

    public String getSearchupperdeptnm() {
        return searchupperdeptnm;
    }

    public void setSearchupperdeptnm(String searchupperdeptnm) {
        this.searchupperdeptnm = searchupperdeptnm;
    }

    public String getSearchdept() {
        return searchdept;
    }

    public void setSearchdept(String searchdept) {
        this.searchdept = searchdept;
    }

    public String getSearchchrgunitnm() {
        return searchchrgunitnm;
    }

    public void setSearchchrgunitnm(String searchchrgunitnm) {
        this.searchchrgunitnm = searchchrgunitnm;
    }

    public String getSearchstatus() {
        return searchstatus;
    }

    public void setSearchstatus(String searchstatus) {
        this.searchstatus = searchstatus;
    }

    public String getSearchstrdt() {
        return searchstrdt;
    }

    public void setSearchstrdt(String searchstrdt) {
        this.searchstrdt = searchstrdt;
    }

    public String getSearchenddt() {
        return searchenddt;
    }

    public void setSearchenddt(String searchenddt) {
        this.searchenddt = searchenddt;
    }

    public String getSearchgubun() {
        return searchgubun;
    }

    public void setSearchgubun(String searchgubun) {
        this.searchgubun = searchgubun;
    }

    public String getSearchtitle() {
        return searchtitle;
    }

    public void setSearchtitle(String searchtitle) {
        this.searchtitle = searchtitle;
    }

    public List getWorklist() {
        return worklist;
    }

    public void setWorklist(List worklist) {
        this.worklist = worklist;
    }

    public String getOrggbn() {
        return orggbn;
    }

    public void setOrggbn(String orggbn) {
        this.orggbn = orggbn;
    }

    public String getSearchdeptnm() {
        return searchdeptnm;
    }

    public void setSearchdeptnm(String searchdeptnm) {
        this.searchdeptnm = searchdeptnm;
    }

    public String getSearchupperdeptcd() {
        return searchupperdeptcd;
    }

    public void setSearchupperdeptcd(String searchupperdeptcd) {
        this.searchupperdeptcd = searchupperdeptcd;
    }

    public String getSearchdeptcd() {
        return searchdeptcd;
    }

    public void setSearchdeptcd(String searchdeptcd) {
        this.searchdeptcd = searchdeptcd;
    }

    public String getSearchchrgunitcd() {
        return searchchrgunitcd;
    }

    public void setSearchchrgunitcd(String searchchrgunitcd) {
        this.searchchrgunitcd = searchchrgunitcd;
    }

    public String getCurrentyear() {
        return currentyear;
    }

    public void setCurrentyear(String currentyear) {
        this.currentyear = currentyear;
    }
    
}