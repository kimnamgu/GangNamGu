/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������չ������� actionform
 * ����:
 */
package nexti.ejms.workplan.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class WorkplanInfoForm extends ActionForm {

    private static final long serialVersionUID = 1L;
    
    private int planno              = 0;        //�ý��۹�����ȣ
    private String title            = "";       //��������
    private String content          = "";       //�������
    private String strdt            = "";       //������
    private String enddt            = "";       //������
    private String status           = "";       //�������
    private int readcnt             = 0;        //��ȸ��
    private String useyn            = "";       //��뿩��
    private String upperdeptcd      = "";       //�����μ��ڵ�
    private String upperdeptnm      = "";       //�����μ���
    private String deptcd           = "";       //�μ��ڵ�
    private String deptnm           = "";       //�μ���
    private String depttel          = "";       //�μ���ȭ��
    private int chrgunitcd          = 0;        //�������ڵ�
    private String chrgunitnm       = "";       //��������
    private String chrgusrcd        = "";       //������ڵ�
    private String chrgusrnm        = "";       //����ڸ�
    private String crtdt            = "";       //�����Ͻ�
    private String crtusrid         = "";       //�������ڵ�
    private String uptdt            = "";       //�����Ͻ�
    private String uptusrid         = "";       //�������ڵ�
    
    private FormFile attachFile1    = null;     //÷������1
    private FormFile attachFile2    = null;     //÷������2
    private FormFile attachFile3    = null;     //÷������3
    private String attachFileYN1    = "";       //÷�����ϻ���1
    private String attachFileYN2    = "";       //÷�����ϻ���2
    private String attachFileYN3    = "";       //÷�����ϻ���3
    private int fileno1             = 0;        //���Ϲ�ȣ1
    private int fileno2             = 0;        //���Ϲ�ȣ2
    private int fileno3             = 0;        //���Ϲ�ȣ3
    private String filenm1          = "";       //÷�����ϸ�1
    private String filenm2          = "";       //÷�����ϸ�2
    private String filenm3          = "";       //÷�����ϸ�3
    private String orgfilenm1       = "";       //�������ϸ�1
    private String orgfilenm2       = "";       //�������ϸ�2
    private String orgfilenm3       = "";       //�������ϸ�3
    private int filesize1           = 0;        //����ũ��1
    private int filesize2           = 0;        //����ũ��2
    private int filesize3           = 0;        //����ũ��3
    private String ext1             = "";       //Ȯ����1
    private String ext2             = "";       //Ȯ����2
    private String ext3             = "";       //Ȯ����31
    private int ord1                = 0;        //���ļ���1
    private int ord2                = 0;        //���ļ���2
    private int ord3                = 0;        //���ļ���3
    
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