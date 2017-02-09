package com.bfd.utils;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;

public class EsUtil {

	
	private EsUtil() {  
	}  
	
	public static final EsUtil getUtil() {  
	    return esUtil;  
	}  
	
	// 初始化一个实例  
	private static final EsUtil esUtil = new EsUtil();  
	// 连接  
	private static TransportClient client;  
	// 索引库名称，一般都是一个库，只是里边的type不同，比如user/goods  
	private static final String index = "myindex";  
	// 添加修改操作锁  
	private static final Lock lock = new ReentrantLock();  
	
	static {  
		
	    // 通过 setting对象来指定集群配置信息  
	    Settings setting = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch")// 指定集群名称  
	            .put("client.transport.sniff", true)// 启动嗅探功能  
	            .build();  
	
	    // 创建客户端 通过setting来创建，若不指定则默认链接的集群名为elasticsearch 链接使用tcp协议即9300  
	    client = new TransportClient(setting);  
	    TransportAddress transportAddress = new InetSocketTransportAddress("127.0.0.1", 9300);  
	    client.addTransportAddresses(transportAddress);  
	}  
	
	/** 
	 *  
	 * @Description:创建索引(一般都为手动创建) 
	 * @author xinke.liu
	 * @date 2017年2月9日 
	 */  
	public void addIndex(String indexName) {  
	    client.admin().indices().prepareCreate(indexName).execute().actionGet();  
	}  
	


	/** 
	 *  
	 * @Description: 删除索引(一般都为手动删除) 
	 * @author xinke.liu
	 * @date 2017年2月9日 
	 */  
	public boolean deleteIndex(String index) {  
	    IndicesAdminClient indicesAdminClient = client.admin().indices();  
	    DeleteIndexResponse response = indicesAdminClient.prepareDelete(index).execute().actionGet();  
	    return response.isAcknowledged();  
	}  
	
	/** 
	 *  
	 * @Description:创建索引结构(一般都是手动设计) 
	 * @author xinke.liu
	 * @date 2017年2月9日 
	 */  
	public void createIndexStru() {  
	    XContentBuilder builder;  
	    try {  
	        builder = XContentFactory.jsonBuilder()//  
	                .startObject()//  
	                .startObject("article")//  
	                .startObject("properties")//  
	                .startObject("title").field("type", "string").field("store", "yes").field("analyzer", "ik").field("index", "analyzed").endObject()//  
	                .startObject("content").field("type", "string").field("store", "no").field("analyzer", "ik").field("index", "analyzed").endObject()//  
	                .startObject("url").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject().endObject().endObject().endObject();  
	        PutMappingRequest mapping = Requests.putMappingRequest(index).type("article").source(builder);  
	        client.admin().indices().putMapping(mapping).actionGet();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}  
	
	/** 
	 *  
	 * @Description:获取索引库文档总数 
	 * @author xinke.liu
	 * @date 2017年2月9日  
	 */  
	public long getDocCount() {  
	    return client.prepareCount(index).get().getCount();  
	}  
	
	/** 
	 *  
	 * @Description:添加文档 
	 * @author kang 
	 * @date 2017年1月3日 
	 */  
	public void addDoc(String type, Object id, Object object) {  
	    lock.lock();  
	    try {  
	        client.prepareIndex(index, type, id.toString()).setSource(JSON.toJSONString(object)).get();  
	    } finally {  
	        lock.unlock();  
	    }  
	}  
	
	/** 
	 *  
	 * @Description:更新文档 
	 * @author xinke.liu
	 * @date 2017年2月9日 
	 */  
	public void updateDoc(String type, Object id, Object object) {  
	    lock.lock();  
	    try {  
	        client.prepareUpdate(index, type, id.toString()).setDoc(JSON.toJSONString(object)).get();  
	    } finally {  
	        lock.unlock();  
	    }  
	}  
	
	/** 
	 *  
	 * @Description:删除文档 
	 * @author xinke.liu
	 * @date 2017年2月9日 
	 */  
	public void delDoc(String type, Object id) {  
	    lock.lock();  
	    try {  
	        client.prepareDelete(index, type, id.toString()).get();  
	    } finally {  
	        lock.unlock();  
	    }  
	}  

}
