package com.example.demo.transaction.programmatic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.demo.dao.CustomerDao;
import com.example.demo.entity.CustomerEntity;

/**
 * サービスサンプル＜明示的トランザクション＞
 * TransactionTemplateでトランザクション制御
 * 
 * @author yatty0812
 */
@Service
public class ProgrammaticService1 {
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	@Autowired
	CustomerDao customerDao;

	/**
	 * サービスメソッド
	 * 【ポイント】
	 *   伝搬トランザクション処理のサンプル（executeバージョン）
	 * @param id ID
	 * @return Entity
	 */
	public CustomerEntity getEntity(Integer id) {
		return transactionTemplate.execute(status -> {
				return customerDao.getEntity(id);
		});
	}

	/**
	 * サービスメソッド
	 * 【ポイント】
	 *   伝搬トランザクション処理のサンプル（executeWithoutResultバージョン）
	 * @param customer Entity
	 * @return 登録件数
	 */
	public void insert(CustomerEntity customer) {
		transactionTemplate.executeWithoutResult(status -> {
				customerDao.insert(customer);
		});
		
	}

	/**
	 * サービスメソッド
	 * 【ポイント】
	 *   別トランザクション処理のサンプル
	 * @param customer Entity
	 * @return 登録件数
	 */
	public void update(CustomerEntity customer) {
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		transactionTemplate.executeWithoutResult(status -> {
				customerDao.update(customer);
		});
		
	}
}
