/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 공통취합문서정보 actionform
 * 설명:
 */
package nexti.ejms.workplan.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class WorkplanInfoForm extends ActionForm {

    private static final long serialVersionUID = 1L;
    
    private int planno              = 0;        //시스템문서번호
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
    public String getDepttel() {
        return depttel;
    }
    public void setDepttel(String depttel) {
        this.depttel = depttel;
    }
    
}