package service.common.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	
	public Map<String, Object> insert(String queryId, Object params){
		int result = 0;
		int idx = 0;
		
		String tmp = null;
		boolean rtn = false;
		
		Map<String, Object> rtmap = new HashMap<>();
		
		printQueryId(queryId);
		try{
			result = sqlSession.insert(queryId, params);
			log.debug("\t insert result  \t:  " + result);
		}
		catch(RuntimeException e){
			tmp = e.getMessage();
			idx = tmp.indexOf("Exception:");
			tmp = tmp.substring(idx+11);
			idx = tmp.indexOf("\n");
			tmp = tmp.substring(0, idx);
		}
		finally{
			if (result > 0){
				tmp = "success";
				rtn = true;
			}
		}
		
		rtmap.put("result", rtn);
		rtmap.put("msg", tmp);
		
		return rtmap;
	}
	
	public Map<String, Object> update(String queryId, Object params){
		int result = 0;
		String tmp = null;
		boolean rtn = false;
		
		Map<String, Object> rtmap = new HashMap<>();
		
		printQueryId(queryId);
		
		try{
			result = sqlSession.update(queryId, params);
			log.debug("log update suc==> [" + result + "]");			
		}
		catch(RuntimeException e){
			log.debug("log abstractdao err ==> [[" + e.getMessage() + "]]");
			tmp = e.getMessage();
		}
		finally{
			if (result > 0){
				tmp = "success";
				rtn = true;
			}
		}

				
		rtmap.put("result", rtn);
		rtmap.put("msg", tmp);
		
		return rtmap;
		
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
	//占쏙옙占쏙옙 SSO 占쏙옙占쏙옙占� /占싸쇽옙 占쏙옙占쏙옙占싱븝옙  (占쏙옙회占쏙옙 占쏙옙占쏙옙)
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
    // 홈占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙
	// 
	//=========================================================================================================
		
	public Object selectOne3(String queryId){
		printQueryId(queryId);
		return sqlSession3.selectOne(queryId);
	}
	
	public Object selectOne3(String queryId, Object params){
		printQueryId(queryId);		
		return sqlSession3.selectOne(queryId, params);
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
		
		return sqlSession3.selectList(queryId, map);
	}
}
