/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 취합문서 actionform
 * 설명:
 */
package nexti.ejms.workplan.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class WorkplanForm extends ActionForm {
    
    private static final long serialVersionUID = 1L;
    
    private String  mode        = "";       //모드
    private int     seqno       = 0;        //연번 
    private List    listWork    = null;     //취합문서리스트
    private String  selyear     = "";       //연도선택
    private String  initentry   = "first";  //초기진입여부
    private String[] listdelete = null;     //삭제문서 시스템문서번호
    
    private String  user_id     = "";   //사용자 아이디
    private String  user_name   = "";   //사용자 이름
    private String  dept_code   = "";   //사용자 부서코드
    private String  dept_name   = "";   //사용자 부서명
    private String  user_tel    = "";   //사용자 전화번호
        
    private int planno              = 0;        //계획번호
    private int resultno            = 0;        //실적번호
    private String title            = "";       //문서제목
    private String content          = "";       //사업내용
    private String strdt            = "";       //시작일
    private String enddt            = "";       //종료일
    private String status           = "";       //진행상태
    private int readcnt             = 0;        //조회수
    private String useyn            = "";       //사용여부
    private String upperdeptcd      = "";       //상위부서코드
    private String upperdeptnm      = "";       //상위부서명
    private String deptcd           = "";       //부서코드
    private String deptnm           = "";       //부서명
    private String depttel          = "";       //부서전화번
    private int chrgunitcd          = 0;        //담당단위코드
    private String chrgunitnm       = "";       //담당단위명
    private String chrgusrcd        = "";       //담당자코드
    private String chrgusrnm        = "";       //담당자명
    private String crtdt            = "";       //생성일시
    private String crtusrid         = "";       //생성자코드
    private String uptdt            = "";       //수정일시
    private String uptusrid         = "";       //수정자코드
    
    private FormFile attachFile1    = null;     //첨부파일1
    private FormFile attachFile2    = null;     //첨부파일2
    private FormFile attachFile3    = null;     //첨부파일3
    private String attachFileYN1    = "";       //첨부파일삭제1
    private String attachFileYN2    = "";       //첨부파일삭제2
    private String attachFileYN3    = "";       //첨부파일삭제3
    private int fileno1             = 0;        //파일번호1
    private int fileno2             = 0;        //파일번호2
    private int fileno3             = 0;        //파일번호3
    private String filenm1          = "";       //첨부파일명1
    private String filenm2          = "";       //첨부파일명2
    private String filenm3          = "";       //첨부파일명3
    private String orgfilenm1       = "";       //원본파일명1
    private String orgfilenm2       = "";       //원본파일명2
    private String orgfilenm3       = "";       //원본파일명3
    private int filesize1           = 0;        //파일크기1
    private int filesize2           = 0;        //파일크기2
    private int filesize3           = 0;        //파일크기3
    private String ext1             = "";       //확장자1
    private String ext2             = "";       //확장자2
    private String ext3             = "";       //확장자31
    private int ord1                = 0;        //정렬순서1
    private int ord2                = 0;        //정렬순서2
    private int ord3                = 0;        //정렬순서3
    
    //검색관련 값
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
    private String orggbn               = "";       //조직도 검색
    private int     page                = 1 ;       //페이지
    private int     treescroll          = 0;        //조직도 스크롤
    private String currentyear          = "";
    private String  searchdept          = "";       //선택부서이하 전체포함
    
    public String getUser_tel() {
        return user_tel;
    }
    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }
    public int getResultno() {
        return resultno;
    }
    public void setResultno(int resultno) {
        this.resultno = resultno;
    }
    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    public int getSeqno() {
        return seqno;
    }
    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public List getListWork() {
        return listWork;
    }
    public void setListWork(List listcolldoc) {
        this.listWork = listcolldoc;
    }
    public String getSelyear() {
        return selyear;
    }
    public void setSelyear(String selyear) {
        this.selyear = selyear;
    }
    public String getInitentry() {
        return initentry;
    }
    public void setInitentry(String initentry) {
        this.initentry = initentry;
    }
    public String[] getListdelete() {
        return listdelete;
    }
    public void setListdelete(String[] listdelete) {
        this.listdelete = listdelete;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getDept_code() {
        return dept_code;
    }
    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }
    public String getDept_name() {
        return dept_name;
    }
    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }
    public int getPlanno() {
        return planno;
    }
    public void setPlanno(int planno) {
        this.planno = planno;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getStrdt() {
        return strdt;
    }
    public void setStrdt(String strdt) {
        this.strdt = strdt;
    }
    public String getEnddt() {
        return enddt;
    }
    public void setEnddt(String enddt) {
        this.enddt = enddt;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getReadcnt() {
        return readcnt;
    }
    public void setReadcnt(int readcnt) {
        this.readcnt = readcnt;
    }
    public String getUseyn() {
        return useyn;
    }
    public void setUseyn(String useyn) {
        this.useyn = useyn;
    }
    public String getUpperdeptcd() {
        return upperdeptcd;
    }
    public void setUpperdeptcd(String upperdeptcd) {
        this.upperdeptcd = upperdeptcd;
    }
    public String getUpperdeptnm() {
        return upperdeptnm;
    }
    public void setUpperdeptnm(String upperdeptnm) {
        this.upperdeptnm = upperdeptnm;
    }
    public String getDeptcd() {
        return deptcd;
    }
    public void setDeptcd(String deptcd) {
        this.deptcd = deptcd;
    }
    public String getDeptnm() {
        return deptnm;
    }
    public void setDeptnm(String deptnm) {
        this.deptnm = deptnm;
    }
    public String getDepttel() {
        return depttel;
    }
    public void setDepttel(String depttel) {
        this.depttel = depttel;
    }
    public int getChrgunitcd() {
        return chrgunitcd;
    }
    public void setChrgunitcd(int chrgunitcd) {
        this.chrgunitcd = chrgunitcd;
    }
    public String getChrgunitnm() {
        return chrgunitnm;
    }
    public void setChrgunitnm(String chrgunitnm) {
        this.chrgunitnm = chrgunitnm;
    }
    public String getChrgusrcd() {
        return chrgusrcd;
    }
    public void setChrgusrcd(String chrgusrcd) {
        this.chrgusrcd = chrgusrcd;
    }
    public String getChrgusrnm() {
        return chrgusrnm;
    }
    public void setChrgusrnm(String chrgusrnm) {
        this.chrgusrnm = chrgusrnm;
    }
    public String getCrtdt() {
        return crtdt;
    }
    public void setCrtdt(String crtdt) {
        this.crtdt = crtdt;
    }
    public String getCrtusrid() {
        return crtusrid;
    }
    public void setCrtusrid(String crtusrid) {
        this.crtusrid = crtusrid;
    }
    public String getUptdt() {
        return uptdt;
    }
    public void setUptdt(String uptdt) {
        this.uptdt = uptdt;
    }
    public String getUptusrid() {
        return uptusrid;
    }
    public void setUptusrid(String uptusrid) {
        this.uptusrid = uptusrid;
    }
    public FormFile getAttachFile1() {
        return attachFile1;
    }
    public void setAttachFile1(FormFile attachFile1) {
        this.attachFile1 = attachFile1;
    }
    public FormFile getAttachFile2() {
        return attachFile2;
    }
    public void setAttachFile2(FormFile attachFile2) {
        this.attachFile2 = attachFile2;
    }
    public FormFile getAttachFile3() {
        return attachFile3;
    }
    public void setAttachFile3(FormFile attachFile3) {
        this.attachFile3 = attachFile3;
    }
    public String getAttachFileYN1() {
        return attachFileYN1;
    }
    public void setAttachFileYN1(String attachFileYN1) {
        this.attachFileYN1 = attachFileYN1;
    }
    public String getAttachFileYN2() {
        return attachFileYN2;
    }
    public void setAttachFileYN2(String attachFileYN2) {
        this.attachFileYN2 = attachFileYN2;
    }
    public String getAttachFileYN3() {
        return attachFileYN3;
    }
    public void setAttachFileYN3(String attachFileYN3) {
        this.attachFileYN3 = attachFileYN3;
    }
    public int getFileno1() {
        return fileno1;
    }
    public void setFileno1(int fileno1) {
        this.fileno1 = fileno1;
    }
    public int getFileno2() {
        return fileno2;
    }
    public void setFileno2(int fileno2) {
        this.fileno2 = fileno2;
    }
    public int getFileno3() {
        return fileno3;
    }
    public void setFileno3(int fileno3) {
        this.fileno3 = fileno3;
    }
    public String getFilenm1() {
        return filenm1;
    }
    public void setFilenm1(String filenm1) {
        this.filenm1 = filenm1;
    }
    public String getFilenm2() {
        return filenm2;
    }
    public void setFilenm2(String filenm2) {
        this.filenm2 = filenm2;
    }
    public String getFilenm3() {
        return filenm3;
    }
    public void setFilenm3(String filenm3) {
        this.filenm3 = filenm3;
    }
    public String getOrgfilenm1() {
        return orgfilenm1;
    }
    public void setOrgfilenm1(String orgfilenm1) {
        this.orgfilenm1 = orgfilenm1;
    }
    public String getOrgfilenm2() {
        return orgfilenm2;
    }
    public void setOrgfilenm2(String orgfilenm2) {
        this.orgfilenm2 = orgfilenm2;
    }
    public String getOrgfilenm3() {
        return orgfilenm3;
    }
    public void setOrgfilenm3(String orgfilenm3) {
        this.orgfilenm3 = orgfilenm3;
    }
    public int getFilesize1() {
        return filesize1;
    }
    public void setFilesize1(int filesize1) {
        this.filesize1 = filesize1;
    }
    public int getFilesize2() {
        return filesize2;
    }
    public void setFilesize2(int filesize2) {
        this.filesize2 = filesize2;
    }
    public int getFilesize3() {
        return filesize3;
    }
    public void setFilesize3(int filesize3) {
        this.filesize3 = filesize3;
    }
    public String getExt1() {
        return ext1;
    }
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    public String getExt2() {
        return ext2;
    }
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    public String getExt3() {
        return ext3;
    }
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }
    public int getOrd1() {
        return ord1;
    }
    public void setOrd1(int ord1) {
        this.ord1 = ord1;
    }
    public int getOrd2() {
        return ord2;
    }
    public void setOrd2(int ord2) {
        this.ord2 = ord2;
    }
    public int getOrd3() {
        return ord3;
    }
    public void setOrd3(int ord3) {
        this.ord3 = ord3;
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
    public String getSearchupperdeptcd() {
        return searchupperdeptcd;
    }
    public void setSearchupperdeptcd(String searchupperdeptcd) {
        this.searchupperdeptcd = searchupperdeptcd;
    }
    public String getSearchdeptnm() {
        return searchdeptnm;
    }
    public void setSearchdeptnm(String searchdeptnm) {
        this.searchdeptnm = searchdeptnm;
    }
    public String getSearchdeptcd() {
        return searchdeptcd;
    }
    public void setSearchdeptcd(String searchdeptcd) {
        this.searchdeptcd = searchdeptcd;
    }
    public String getSearchchrgunitnm() {
        return searchchrgunitnm;
    }
    public void setSearchchrgunitnm(String searchchrgunitnm) {
        this.searchchrgunitnm = searchchrgunitnm;
    }
    public String getSearchchrgunitcd() {
        return searchchrgunitcd;
    }
    public void setSearchchrgunitcd(String searchchrgunitcd) {
        this.searchchrgunitcd = searchchrgunitcd;
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
    public int getTreescroll() {
        return treescroll;
    }
    public void setTreescroll(int treescroll) {
        this.treescroll = treescroll;
    }
    public String getCurrentyear() {
        return currentyear;
    }
    public void setCurrentyear(String currentyear) {
        this.currentyear = currentyear;
    }
    public String getSearchdept() {
        return searchdept;
    }
    public void setSearchdept(String searchdept) {
        this.searchdept = searchdept;
    }
    public String getOrggbn() {
        return orggbn;
    }
    public void setOrggbn(String orggbn) {
        this.orggbn = orggbn;
    }
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {return null;}
    public void reset(ActionMapping mapping, HttpServletRequest request) {}
}