package oms.common.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import oms.common.util.Nvl;

public class AbstractDAO {
	//protected Log log = LogFactory.getLog(AbstractDAO.class);
	Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	@Resource(name="sqlSessionTemplate2")
	private SqlSessionTemplate sqlSession2;
	
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
		String strPageIndex2 = Nvl.nvlStr((String)map.get("PAGE_INDEX2"));
		
		//log.debug("\t strPageIndex1  \t:  " + strPageIndex);
		//log.debug("\t strPageIndex2  \t:  " + strPageIndex2);
		
		String strPageRow = (String)map.get("PAGE_ROW");
		int nPageIndex = 0;
		int nPageRow = 20;
		
		if(StringUtils.isEmpty(strPageIndex2) == false){
			nPageIndex = Integer.parseInt(strPageIndex2)-1;
		}
		else{
			if(StringUtils.isEmpty(strPageIndex) == false){
				nPageIndex = Integer.parseInt(strPageIndex)-1;
			}
		}
		
		if(StringUtils.isEmpty(strPageRow) == false){
			nPageRow = Integer.parseInt(strPageRow);
		}
		map.put("START", (nPageIndex * nPageRow) + 1);
		map.put("END", (nPageIndex * nPageRow) + nPageRow);
		
		return sqlSession.selectList(queryId, map);
	}
	
	@SuppressWarnings("unchecked")
	public Object selectPagingList2(String queryId, Object params){
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
	//새올 SSO 사용자 /부서 뷰테이블  (조회만 가능)
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
}