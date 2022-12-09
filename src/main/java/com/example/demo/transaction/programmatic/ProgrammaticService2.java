package com.example.demo.transaction.programmatic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.example.demo.dao.CustomerDao;
import com.example.demo.entity.CustomerEntity;

/**
 * サービスサンプル＜明示的トランザクション＞
 * PlatformTransactionManagerでトランザクション制御
 * 
 * @author yatty0812
 */
@Service
public class ProgrammaticService2 {
	
	@Autowired
	private PlatformTransactionManager txManager;

	@Autowired
	CustomerDao customerDao;
	
	/**
	 * サービスメソッド
	 * @param id ID
	 * @return Entity
	 */
	public CustomerEntity getEntity(Integer id) {
		return customerDao.getEntity(id);
	}

	/**
	 * サービスメソッド
	 * 【ポイント】
	 *   伝搬トランザクション処理のサンプル
	 * @param customer Entity
	 * @return 登録件数
	 */
	public void insert(CustomerEntity customer) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = txManager.getTransaction(def);
		
		customerDao.insert(customer);
		txManager.commit(status);
	}

	/**
	 * サービスメソッド
	 * 【ポイント】
	 *   別トランザクション処理のサンプル
	 * @param customer Entity
	 * @return 登録件数
	 */
	public void update(CustomerEntity customer) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = txManager.getTransaction(def);
		
		customerDao.update(customer);
		txManager.commit(status);
	}

}
