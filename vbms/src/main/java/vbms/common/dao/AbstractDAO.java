package vbms.common.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class AbstractDAO {
	protected Log log = LogFactory.getLog(AbstractDAO.class);
	
	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	@Resource(name="sqlSessionTemplate2")
	private SqlSessionTemplate sqlSession2;
	
	@Autowired
	@Resource(name="sqlSessionTemplate3")
	private SqlSessionTemplate sqlSession3;
	
	protected void printQueryId(String queryId) {
		if(log.isDebugEnabled()){
			log.debug("\t QueryId  \t:  " + queryId);
		}
	}
	
	public Object insert(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}
	
	public Object update(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.update(queryId, params);
	}
	
	public Object delete(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.delete(queryId, params);
	}
	
	public Object selectOne(String queryId){
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}
	
	public Object selectOne(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId){
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession.selectList(queryId,params);
	}
		
	@SuppressWarnings("unchecked")
	public Object selectPagingList(String queryId, Object params){
		printQueryId(queryId);
		Map<String,Object> map = (Map<String,Object>)params;
		
		String strPageIndex = (String)map.get("PAGE_INDEX");
		String strPageRow = (String)map.get("PAGE_ROW");
		int nPageIndex = 0;
		int nPageRow = 20;
		
		if(StringUtils.isEmpty(strPageIndex) == false){
			nPageIndex = Integer.parseInt(strPageIndex)-1;
		}
		if(StringUtils.isEmpty(strPageRow) == false){
			nPageRow = Integer.parseInt(strPageRow);
		}
		map.put("START", (nPageIndex * nPageRow) + 1);
		map.put("END", (nPageIndex * nPageRow) + nPageRow);
		
		return sqlSession.selectList(queryId, map);
	}
	
	
	//=========================================================================================================
	//SSO �α��� ó��
	//SSOX_USER, SSOV_DEPT
	//=========================================================================================================
	public Object selectOne2(String queryId){
		printQueryId(queryId);
		return sqlSession2.selectOne(queryId);
	}
	
	public Object selectOne2(String queryId, Object params){
		printQueryId(queryId);		
		return sqlSession2.selectOne(queryId, params);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList2(String queryId){
		printQueryId(queryId);
		return sqlSession2.selectList(queryId);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList2(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession2.selectList(queryId,params);
	}
		
	
	//=========================================================================================================
    // ����/�˸��� �߼�
	// 
	//=========================================================================================================
		
	public Object insert2(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession3.insert(queryId, params);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList3(String queryId){
		printQueryId(queryId);
		return sqlSession3.selectList(queryId);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList3(String queryId, Object params){
		printQueryId(queryId);
		return sqlSession3.selectList(queryId,params);
	}
	
	
	@SuppressWarnings("unchecked")
	public Object selectPagingList3(String queryId, Object params){
		printQueryId(queryId);
		Map<String,Object> map = (Map<String,Object>)params;
		
		String strXls = (String)map.get("XLS");
		String strPageIndex = (String)map.get("PAGE_INDEX");
		String strPageRow = (String)map.get("PAGE_ROW");
		int nPageIndex = 0;
		int nPageRow = 20;
		
		if(StringUtils.isEmpty(strPageIndex) == false){
			nPageIndex = Integer.parseInt(strPageIndex)-1;
		}
		if(StringUtils.isEmpty(strPageRow) == false){
			nPageRow = Integer.parseInt(strPageRow);
		}
		
		if(StringUtils.isEmpty(strXls) == false) //��������
		{
			map.put("START", "1");
			map.put("END", "3000000");
		}
		else
		{
			map.put("START", (nPageIndex * nPageRow) + 1);
			map.put("END", (nPageIndex * nPageRow) + nPageRow);
		}
		return sqlSession3.selectList(queryId, map);
	}
}
