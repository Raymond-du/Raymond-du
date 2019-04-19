package Server;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WebHandler extends DefaultHandler{
	private List<Entity> entityList;
	private List<Mapping> mappingList;	
	private Entity entity;
	private Mapping mapping;
	private String beginTag;
	boolean isEntity;
	
	@Override
	public void startDocument() throws SAXException {
		//开始解析文件
		this.entityList =new ArrayList<Entity>();
		this.mappingList =new ArrayList<Mapping>();			
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(null!=qName) {
			beginTag=qName;
    		if(qName.equals("servlet")) {
    			isEntity=true;
    			entity=new Entity();
    		}else if(qName.equals("servlet-mapping")) {
    			isEntity=false;
    			mapping=new Mapping();
    		}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(null!=beginTag) {
			String str=new String (ch,start,length);
    		if(isEntity) {
    			if(beginTag.equals("servlet-name")) {
    				entity.setName(str);
    			}else if(beginTag.equals("servlet-class")) {
    				entity.setClz(str);
    			}
    		}else {
    			if(beginTag.equals("servlet-name")) {
    				mapping.setName(str);
    			}else if(beginTag.equals("url-pattern")){
    				mapping.getPattern().add(str);
    			}
    		}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(null!=qName) {
			if(qName.equals("servlet")) {
				entityList.add(entity);
			}else if(qName.equals("servlet-mapping")) {
				mappingList.add(mapping);
			}
		}
		beginTag=null;
	}

	public List<Entity> getEntityList() {
		return entityList;
	}

	public List<Mapping> getMappingList() {
		return mappingList;
	}
}
