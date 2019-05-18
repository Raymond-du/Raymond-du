package SORM;

import SORM.MysqlQuery;
import SORM.Query;
import SORM.QueryFactory;

/**
 * 返回一个相应的query的对象
 * 根据配置信息创建query的对象
 * @author Raymond-du
 *
 */
public class QueryFactory {
	private static final QueryFactory factory=new QueryFactory();
	private QueryFactory() {
		if(factory!=null) {
			throw new RuntimeException();
		}
	}
	public static QueryFactory getFactory() {
		return factory;
	}
	
	public Query getMysqlQuery() {
		return new MysqlQuery();
	}
}
