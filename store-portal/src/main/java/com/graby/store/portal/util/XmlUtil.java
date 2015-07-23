package com.graby.store.portal.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * XML操作工具类
 * 
 * @author 杨敏
 *
 */
public class XmlUtil {

	/**
	 * xml类字符串转化成map对象
	 * 
	 * @param doc
	 * @return
	 * @throws DocumentException 
	 */
	public static Map<String, Object> Dom2Map(String xmlString) throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlString); 
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			// System.out.println(e.getName());
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), Dom2Map(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	public static Map Dom2Map(Element e) {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = Dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	/**
	 * map转化成XML文本串
	 * 
	 * @param dataMap
	 * @param xmlEnums
	 *            类型，请求还是返回参数
	 * @return
	 */
	public static String converterPayPalm(Map<String, Object> dataMap) {
		synchronized (XmlUtil.class) {
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("<?xml version='1.0' encoding='UTF-8' ?>");
			strBuilder.append("<request>");
			Set<String> objSet = dataMap.keySet();
			for (Object key : objSet) {
				if (key == null) {
					continue;
				}
				strBuilder.append("\n");
				strBuilder.append("<").append(key.toString()).append(">\n");
				Object value = dataMap.get(key);
				strBuilder.append(coverter(value,null).trim());
				strBuilder.append("</").append(key.toString()).append(">\n");
			}
			strBuilder.append("<request>");
			strBuilder.append("</xml>");
			return strBuilder.toString();
		}
	}

	

	public static void main(String[] args) throws DocumentException {
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<request>"
				+ "<itemCode>组合商品的ERP编码,string(50),必填</itemCode>" + "<items>" + "<item11>"
				+ "<itemCode>商品编码,string(50),必填</itemCode>" + "<quantity>组合商品中的该商品个数，int，必填</quantity>"
				+ "</item11>"+ "<item11>"
				+ "<itemCode>商品编码,string(50),必填</itemCode>" + "<quantity>组合商品中的该商品个数，int，必填</quantity>"
				+ "</item11>"+ "<item11>"
				+ "<itemCode>商品编码,string(50),必填</itemCode>" + "<quantity>组合商品中的该商品个数，int，必填</quantity>"
				+ "</item11>"+ "<item11>"
				+ "<itemCode>商品编码,string(50),必填</itemCode>" + "<quantity>组合商品中的该商品个数，int，必填</quantity>"
				+ "</item11>" + "</items>" + "</request>";
		Map<String,Object> xmlMap=XmlUtil.Dom2Map(xmlStr.trim());
		Map<String,Object> itemMap=(Map<String, Object>) xmlMap.get("items");
		System.err.println(xmlMap);   
		
		
		String converterPayPalm = XmlUtil.converterPayPalm(xmlMap);
		System.out.println("converterPayPalm:"+converterPayPalm);
		
		
	}

	
	
	/**
	 * MAP转化为XML
	 * 
	 * @param dataMap
	 *            MAP对象
	 * @param xmlRootStart
	 *            xml开始根节点
	 * @param xmlRootEnd
	 *            xml结束根节点
	 * @return
	 */
	public static String converter(Map<Object, Object> dataMap, String xmlRootStart, String xmlRootEnd) {
		synchronized (XmlUtil.class) {
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append(xmlRootStart);
			Set<Object> objSet = dataMap.keySet();
			for (Object key : objSet) {
				if (key == null) {
					continue;
				}
				strBuilder.append("\n");
				strBuilder.append("<").append(key.toString()).append(">\n");
				Object value = dataMap.get(key);
				strBuilder.append(coverter(value,null));
				strBuilder.append("</").append(key.toString()).append(">\n");
			}
			strBuilder.append(xmlRootEnd);
			return strBuilder.toString();
		}
	}

	public static String coverter(Object[] objects,String key) {
		StringBuilder strBuilder = new StringBuilder();
		for (Object obj : objects) {
			strBuilder.append("<"+key+">\n");
			strBuilder.append(coverter(obj,null));
			strBuilder.append("</"+key+">\n");
		}
		return strBuilder.toString();
	}

	public static String coverter(Collection<?> objects,String key) {
		StringBuilder strBuilder = new StringBuilder();
		for (Object obj : objects) {
			strBuilder.append("<"+key+">\n");
			strBuilder.append(coverter(obj,null));
			strBuilder.append("</"+key+">\n");
		}
		return strBuilder.toString();
	}
	
	
	public static String coverter(HashMap<Object,Object> objects) {
		StringBuilder strBuilder = new StringBuilder();
		Set<Object> objSet = objects.keySet();
		for (Object key : objSet) {
			if (key == null) {
				continue;
			}
			Object value = objects.get(key);
			if (value instanceof Object[] || value   instanceof Collection ) {
				return strBuilder.append(coverter(value,key.toString())).toString();
			}
			strBuilder.append("<").append(key.toString()).append(">\n");
			strBuilder.append(coverter(value,null));
			strBuilder.append("</").append(key.toString()).append(">\n");
		}
		return strBuilder.toString();
	}

	/**
	 * 对象转化为XML子节点
	 * 
	 * @param object
	 * @return
	 */
	public static String coverter(Object object,String key) {
		if (object instanceof Object[]) {
			return coverter((Object[]) object,key);
		}
		if (object instanceof Collection) {
			return coverter((Collection<?>) object,key);
		}
		if (object instanceof HashMap) {
			return coverter((HashMap<Object,Object>)object);
		}
		StringBuilder strBuilder = new StringBuilder();
		if (isObject(object)) {
			Class<? extends Object> clz = object.getClass();
			Field[] fields = clz.getDeclaredFields();

			for (Field field : fields) {
				field.setAccessible(true);
				if (field == null) {
					continue;
				}
				String fieldName = field.getName();
				Object value = null;
				try {
					value = field.get(object);
				} catch (IllegalArgumentException e) {
					continue;
				} catch (IllegalAccessException e) {
					continue;
				}
				strBuilder.append("<").append(fieldName).append(" className=\"").append(value.getClass().getName())
						.append("\">\n");
				if (isObject(value)) {
					strBuilder.append(coverter(value,null));
				} else if (value == null) {
					strBuilder.append("null\n");
				} else {
					strBuilder.append(value.toString() + "\n");
				}
				strBuilder.append("</").append(fieldName).append(">\n");
			}
		} else if (object == null) {
			strBuilder.append("null\n");
		} else {
			strBuilder.append(object.toString() + "\n");
		}
		return strBuilder.toString();
	}

	/**
	 * 是否对象
	 * 
	 * @param obj
	 * @return
	 */
	private static boolean isObject(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			return false;
		}
		if (obj instanceof Integer) {
			return false;
		}
		if (obj instanceof Double) {
			return false;
		}
		if (obj instanceof Float) {
			return false;
		}
		if (obj instanceof Byte) {
			return false;
		}
		if (obj instanceof Long) {
			return false;
		}
		if (obj instanceof Character) {
			return false;
		}
		if (obj instanceof Short) {
			return false;
		}
		if (obj instanceof Boolean) {
			return false;
		}
		return true;
	}
	
	
}
