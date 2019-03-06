package com.chezhibao.bigdata.gateway.auth;

import com.chezhibao.bigdata.gateway.auth.pojo.Group;
import com.chezhibao.bigdata.gateway.auth.pojo.Policy;
import org.casbin.adapter.JDBCAdapter;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Model;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class EnforcerFactory implements InitializingBean {
 
	private static Enforcer enforcer;
 
	@Autowired
	private EnforcerConfigProperties enforcerConfigProperties;
	private static EnforcerConfigProperties config;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		config = enforcerConfigProperties;
		//从数据库读取策略
		JDBCAdapter jdbcAdapter = new JDBCAdapter(config.getDriverClassName(),config.getUrl(),config.getUsername(),
													config.getPassword(), true);
		Model m = new Model();
		m.loadModel(new ClassPathResource(config.getModelPath()).getURI().getRawPath());
		enforcer = new Enforcer(m, jdbcAdapter);
		enforcer.loadPolicy();//Load the policy from DB.
	}
	
	/**
	 * 添加权限
	 * @param policy
	 * @return
	 */
	public static boolean addPolicy(Policy policy){
		boolean addPolicy = enforcer.addPolicy(policy.getSub(),policy.getObj(),policy.getAct());
		enforcer.savePolicy();
		
		return addPolicy;
	}
	/**
	 * 添加分组
	 * @param group
	 * @return
	 */
	public static boolean addGroupingPolicy(Group group){
		boolean addPolicy = enforcer.addGroupingPolicy(group.getName(),group.getGroupName());
		enforcer.savePolicy();
		return addPolicy;
	}
	
	/**
	 * 删除权限
	 * @param policy
	 * @return
	 */
	public static boolean removePolicy(Policy policy){
		boolean removePolicy = enforcer.removePolicy(policy.getSub(),policy.getObj(),policy.getAct());
		enforcer.savePolicy();
		
		return removePolicy;
	}
	
	public static Enforcer getEnforcer(){
		return enforcer;
	}
}