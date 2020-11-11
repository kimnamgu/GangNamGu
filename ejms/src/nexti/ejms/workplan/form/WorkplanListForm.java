/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 자료공유 목록 actionform
 * 설명:
 */
package nexti.ejms.workplan.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class WorkplanListForm extends ActionForm {
    
    private static final long serialVersionUID = 1L;

    private int     page                = 1 ;   //페이지
    //private String  check_detail        = "";   //상세조건
    private int     treescroll          = 0;    //조직도 스크롤
    private String currentyear          = "";
    private String  searchdept          = "";   //선택부서이하 전체포함
    
    private String searchyear           = "";       //연도
    private String searchupperdeptnm    = "";       //국명(상위부서)
    private String searchupperdeptcd    = "";
    private String searchdeptnm         = "";       //부서
    private String searchdeptcd         = "";
    private String searchchrgunitnm     = "";       //담당단위
    private String searchchrgunitcd     = "";
    private String searchstatus         = "";       //진행상황
    private String searchstrdt          = "";       //시작일
    private String searchenddt          = "";       //종료일
    private String searchgubun          = "";       //검색구분
    private String searchtitle          = "";       //검색명
    
    private List    worklist;                    //문서목록
    private String  orggbn              = "";   //조직구분
    
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