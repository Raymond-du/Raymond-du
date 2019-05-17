package SORM;

import java.util.List;
import java.util.Map;

/**
 * 存储表结构的信息
 * @author Raymond-du
 *
 */
public class TableInfo {
	//表名
	private String tName;
	//所有字段的信息(field名称,和该field的信息)
	private Map<String ,ColumnInfo> colums;
	//唯一主键(目前只能处理表中有且只有一个主键的情况)
	private ColumnInfo onlyPriKey;
	//如果是联合主键 则在这里储存
	private List<ColumnInfo> priKey;
	public TableInfo(String tName, Map<String, ColumnInfo> colums, ColumnInfo onlyPriKey) {
		this.tName = tName;
		this.colums = colums;
		this.onlyPriKey = onlyPriKey;
	}
	public TableInfo(String tName, Map<String, ColumnInfo> colums, List<ColumnInfo> priKey) {
		this.tName = tName;
		this.colums = colums;
		this.priKey = priKey;
	}
	public TableInfo() {
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public Map<String, ColumnInfo> getColums() {
		return colums;
	}
	public void setColums(Map<String, ColumnInfo> colums) {
		this.colums = colums;
	}
	public ColumnInfo getOnlyPriKey() {
		return onlyPriKey;
	}
	public void setOnlyPriKey(ColumnInfo onlyPriKey) {
		this.onlyPriKey = onlyPriKey;
	}
	public List<ColumnInfo> getPriKey() {
		return priKey;
	}
	public void setPriKey(List<ColumnInfo> priKey) {
		this.priKey = priKey;
	}
	
}
